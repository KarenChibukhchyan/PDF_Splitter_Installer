package pdfsplitter;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class WelcomeWindowController {

    private Scene scene;

    static File folderPath;

    public Stage myStage;



    public void quitButtonAction(ActionEvent actionEvent) {

        //rollback();
        System.exit(0);
    }

    public void welcomeContinueButtonAction(ActionEvent actionEvent) {
        if (!isWindows()) showWindowsDialog();
        else if (!existFramework()) showFrameworkDialog();
        else showFileChooseWindow();



    }
    public void showWindowsDialog () {
        try {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("isnotWindows.fxml"));
            Scene scene = new Scene(root);

            stage.setMinHeight(150);
            stage.setMinWidth(300);
            stage.setResizable(false);

            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) { }
    }

    public void showFrameworkDialog () {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("FrameworkWindow.fxml"));
            Scene scene=new Scene(root);
            myStage.setScene(scene);

        } catch (IOException ex) {}
    }

    boolean isWindows(){
        String osName=System.getProperty("os.name");
        return osName.indexOf("indows") == 1;
    }

    boolean existFramework() {

      try {

          List<String> keys  =  WinRegistry.subKeysForPath(WinRegistry.HKEY_LOCAL_MACHINE,
                  "SOFTWARE\\Microsoft");

               for (String k: keys) {
                if ((k.contains(".NETFramework")) || (k.contains(".NET Framework")))
                {
                    return true;
                }
            }
      }

       catch (InvocationTargetException ex){}
       catch (IllegalArgumentException ex){}
       catch (IllegalAccessException ex){ }

       return false;

   }

    void showFileChooseWindow()
    {
       try{

           Parent root = FXMLLoader.load(getClass().getResource("FileChooseWindow.fxml"));
           Scene scene=new Scene(root);
           myStage.setScene(scene);

       } catch (IOException ex) {}
   }

//        try {
//
//            Process process =
//                    new ProcessBuilder (folderPath.toString()+ File.separator+"KeyInstalling.exe", folderPath.toString()).start();
//
//            try
//            {
//                Process process1 =  process.onExit().get();
//                if (process1.exitValue()==1) {errorWindow(); return;}
//                if (process1.exitValue()==2) {folderAlreadyExists();return;}
//                if (process1.exitValue()==0) {installationFinished();return;}
//            }
//            catch (Exception ex) {errorWindow();}
//
//        } catch (IOException ex) {ex.getMessage();}

  //      myStage.close();



//    void fileCopy (String filename, JarFile jar) throws IOException
//    {
//        JarEntry file = jar.getJarEntry(filename);
//
//        File f = new File(folderPath + java.io.File.separator + file.getName());
//
//        InputStream is = jar.getInputStream(file); // get the input stream
//
//        FileOutputStream fos = new FileOutputStream(f);
//
//        while (is.available() > 0) {  // write contents of 'is' to 'fos'
//            fos.write(is.read());
//        }
//
//        fos.close();
//        is.close();
//
//    }
//
}
