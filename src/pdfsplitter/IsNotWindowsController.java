package pdfsplitter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class IsNotWindowsController {

    public Label IsNotWindowsLabel;
    public Button closeandquitButton;

    public void closeAndQuitButtonAction(ActionEvent actionEvent)
    {
//        if (IsNotWindowsLabel.getText().contains("Windows")) System.exit(0);
//        else
//        {
//            Stage stage = (Stage) closeandquitButton.getScene().getWindow();
//            stage.close();
//        }
        System.exit(0);
    }
}
