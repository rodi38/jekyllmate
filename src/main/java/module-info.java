module soft.rodi38.jekyllmate {
    requires javafx.controls;
    requires javafx.fxml;


    opens soft.rodi38.jekyllmate to javafx.fxml;
    opens soft.rodi38.jekyllmate.controller to javafx.fxml;
    exports soft.rodi38.jekyllmate;
}