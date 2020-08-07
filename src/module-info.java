module jpasswd {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.halfegg.jpasswd to javafx.fxml;

    exports com.halfegg.jpasswd;
}