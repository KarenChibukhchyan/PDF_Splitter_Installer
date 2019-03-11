package pdfsplitter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application {

    public static WelcomeWindowController controller;
    @Override
    public void start(Stage primaryStage) throws Exception{


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("welcomeWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        controller = loader.getController();
        controller.myStage=primaryStage;

        primaryStage.setTitle("PDF Splitter Installer");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

//        Image image = new Image("C:\\Users\\Karen\\IdeaProjects\\PDF_Splitter_Installer\\src\\pdfsplitter\\icon.png");
//        primaryStage.getIcons().add(image);

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
