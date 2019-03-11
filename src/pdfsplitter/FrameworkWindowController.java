package pdfsplitter;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;

public class FrameworkWindowController {

    private WelcomeWindowController welcomeWindowController;

    void setWelcomeWindowController (WelcomeWindowController welcomeWindowController)
    {
        this.welcomeWindowController=welcomeWindowController;
    }

    public void quitButtonAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void frameworkContinueButtonAction(ActionEvent actionEvent) {


        setWelcomeWindowController(Main.controller);
        if (!welcomeWindowController.existFramework()) {
            try {

                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("isnotWindows.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                IsNotWindowsController controller = loader.getController();
                controller.IsNotWindowsLabel.setText("Install .NET Framework first and then click on Continue!");
                controller.closeandquitButton.setText("Close");
                stage.setMinHeight(150);
                stage.setMinWidth(300);
                stage.setResizable(false);

                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException ex) {
            }
        }
        else welcomeWindowController.showFileChooseWindow();
    }
}
