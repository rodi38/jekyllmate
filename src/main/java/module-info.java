module soft.rodi38.jekyllmate {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.httpserver;


    opens soft.rodi38.jekyllmate to javafx.fxml;
    exports soft.rodi38.jekyllmate;
}