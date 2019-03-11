package pdfsplitter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileChooseWindowController implements Initializable{
    @FXML
    public TextField installationTextField;
    @FXML
    public Button FileChooseContinueButton;
    @FXML
    public Button ChangeFolderButton;

    Stage myStage;

    File folderPath;

  /*  FileChooseWindowController()
    {
        folderPath = new File("C:\\Program Files\\PDF Splitter");
    }
*/

    public void quitButtonAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void FileChooseWindowContinueAction(ActionEvent actionEvent) {

        myStage=(Stage) FileChooseContinueButton.getScene().getWindow();
        showProgressWindow();
    }

    public void folderChooseAction(ActionEvent actionEvent) {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Folder");
        File defaultDirectory = new File("c:/Program Files");
        if (defaultDirectory.exists())
            chooser.setInitialDirectory(defaultDirectory);
        folderPath = new File(chooser.showDialog(ChangeFolderButton.getScene().getWindow()).toString()+"\\PDF Splitter");
        installationTextField.setText(folderPath.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        folderPath = new File("C:\\Program Files\\PDF Splitter");

    }

    void showProgressWindow()
    {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("InstallationProgress.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);

            myStage.setScene(scene);

            InstallationProgressController controller = loader.getController();

            controller.runInstallation(folderPath,myStage);
        }

        catch (IOException ex){ex.getMessage();}
    }
}
