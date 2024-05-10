package soft.rodi38.jekyllmate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import soft.rodi38.jekyllmate.service.BlogPostService;

import java.io.IOException;

public class Main extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("post-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1315, 890);
//        stage.setTitle("posts!");
//        stage.setScene(scene);
//        stage.show();
//    }
    private BlogPostService blogPostService = new BlogPostService();
    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        TextField titleField = new TextField();
        titleField.setPromptText("Título do Post");

        TextField categoryField = new TextField();
        categoryField.setPromptText("Categoria");

        TextArea contentArea = new TextArea();
        contentArea.setPromptText("Conteúdo do Post");

        Button submitButton = new Button("Criar Post");
        submitButton.setOnAction(e -> {
            String title = titleField.getText();
            String category = categoryField.getText();
            String content = contentArea.getText();

            blogPostService.createPost(title, content, category);

            titleField.clear();
            categoryField.clear();
            contentArea.clear();
        });

        vbox.getChildren().addAll(titleField, categoryField, contentArea, submitButton);

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
