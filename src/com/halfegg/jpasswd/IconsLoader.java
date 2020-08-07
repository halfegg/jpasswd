package com.halfegg.jpasswd;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class IconsLoader {

    private static final Path ICONS_DIRECTORY_PATH = Paths.get("icons");

    private IconsLoader() {}

    public static void load(Stage stage) {
        if (Files.exists(ICONS_DIRECTORY_PATH)) {
            ObservableList<Image> icons = stage.getIcons();
            var imageFiles = ICONS_DIRECTORY_PATH.toFile().listFiles(
                    (file, name) -> name.matches("(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$"));
            assert imageFiles != null;
            var imagePaths = Arrays.stream(imageFiles).map(File::getAbsolutePath).collect(Collectors.toList());
            var imageList = imagePaths.stream().map(path -> "file:" + path).map(Image::new).collect(Collectors.toList());
            icons.addAll(imageList);
        }
    }
}
