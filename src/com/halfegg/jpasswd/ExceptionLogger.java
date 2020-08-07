package com.halfegg.jpasswd;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExceptionLogger {

    private static final Path LOG_DIRECTORY_PATH = Paths.get("log");
    private static final Path DBG_DIRECTORY_PATH = Paths.get("log", "dbg");
    private static final Path DBG_LOG_PATH = Paths.get("log", "dbg", "dev-dbg.log");

    private ExceptionLogger() {}

    public static void createFiles() {
        try {
            if (Files.notExists(LOG_DIRECTORY_PATH)) Files.createDirectory(LOG_DIRECTORY_PATH);
            if (Files.notExists(DBG_DIRECTORY_PATH)) Files.createDirectory(DBG_DIRECTORY_PATH);
            if (Files.notExists(DBG_LOG_PATH)) Files.createFile(DBG_LOG_PATH);
        } catch (IOException ex) {
            ExceptionLogger.log(ExceptionLogger.class.getName(), "createFiles()", ex);
            ex.printStackTrace();
        }
    }

    public static void log(String className, String methodName, Exception exception) {
        checkFiles();
        var content = "\n\n\n#jtext Exception Log\n#" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")) +
                "\nSEVERE: " + className + " " + methodName + "\n";
        write(DBG_LOG_PATH.toFile(), content);
        write(DBG_LOG_PATH.toFile(), exception);
    }

    private static void checkFiles() {
        if (Files.notExists(DBG_LOG_PATH)) createFiles();
        if (DBG_LOG_PATH.toFile().length() > 150_000L) {
            try {
                Files.delete(DBG_LOG_PATH);
                Files.createFile(DBG_LOG_PATH);
            } catch (IOException ex) {
                ExceptionLogger.log(ExceptionLogger.class.getName(), "checkFiles()", ex);
                ex.printStackTrace();
            }
        }
    }

    private static void write(File file, String content) {
        try (var writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)))) {
            writer.write(content);
        } catch (NullPointerException | IOException ex) {
            ExceptionLogger.log(ExceptionLogger.class.getName(), "write(File, String, boolean)", ex);
            ex.printStackTrace();
        }
    }

    private static void write(File file, Throwable throwable) {
        try {
            throwable.printStackTrace(new PrintStream(new FileOutputStream(file, true)));
        } catch (FileNotFoundException ex) {
            ExceptionLogger.log(ExceptionLogger.class.getName(), "write(File, Throwable)", ex);
            ex.printStackTrace();
        }
    }
}
