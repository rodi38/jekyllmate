package soft.rodi38.jekyllmate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import soft.rodi38.jekyllmate.service.BlogPostService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("post-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1315, 890);
//        stage.setTitle("posts!");
//        stage.setScene(scene);
//        stage.show();
//    }

    private static final String ASSETS_DIRECTORY = System.getenv("JEKYLL_ASSETS_LOCATION");
    private File selectedDirectory;

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

        Button uploadImageButton = new Button("Upload Image");


        Button optionsButton = new Button("Opções");

        Button uploadButton = getUpload(primaryStage, contentArea);

        Button selectDirectoryButton = getDirectory(primaryStage);

        optionsButton.setOnAction(e -> {
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Opções");

            dialog.getDialogPane().setContent(new VBox(uploadButton));
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.showAndWait();
        });

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

        vbox.getChildren().addAll(selectDirectoryButton, titleField, categoryField, contentArea, optionsButton, submitButton);

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button getDirectory(Stage primaryStage) {
        Button selectDirectoryButton = new Button("Selecionar Diretório do Blog");

        selectDirectoryButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (Files.notExists(Path.of(selectedDirectory.getAbsolutePath() + "\\assets"))) {
                try {
                    Files.createDirectory(Path.of(selectedDirectory.getAbsolutePath() + "\\assets"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            if (selectedDirectory != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atenção!!");
                alert.setHeaderText(null);
                alert.setContentText("Tenha certeza que o diretório selecionado seja exatamente o diretório RAIZ do seu repositório no github \n diretório selecionado: " + selectedDirectory.getAbsolutePath());
                alert.showAndWait();
            }
        });
        return selectDirectoryButton;
    }

    private Button getUpload(Stage primaryStage, TextArea contentArea) {
        Button uploadButton = new Button("Upload de Imagem");
        uploadButton.setOnAction(event -> {
            if (selectedDirectory == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Diretório não selecionado");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, selecione o diretório do repositório do seu blog antes de fazer o upload de uma imagem.");
                alert.showAndWait();
                return;
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            var selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                String sourcePath = selectedFile.getAbsolutePath();
                String targetPath = selectedDirectory.getAbsolutePath() + "\\assets\\" + selectedFile.getName();

                try {
                    Files.copy(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
                    contentArea.appendText("![image alt text](/assets/" + selectedFile.getName() + ")\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        return uploadButton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
