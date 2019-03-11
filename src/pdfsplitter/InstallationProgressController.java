package pdfsplitter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class InstallationProgressController  {


    public Button installProgressQuitButton;

    public Label installProgressLabel;

    Timer timer;

    Stage myStage;

    public File folderPath;

    void runInstallation(File folderPath, Stage stage) {

        this.folderPath = folderPath;

        myStage = stage;
//
//
//        timer = new Timer(2000, new ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                installingComponents();
//            }
//        });
//        timer.start();
//
//    }
//
//
//    void installingComponents() {

//        timer.stop();

        File keyinstalling = new File(folderPath + "\\PDFSplitter.exe");

        if (folderPath.exists() & keyinstalling.exists())
        {
            folderAlreadyExists();
            return;
        }

        try {

            if (!folderPath.exists())
                Files.createDirectory(folderPath.toPath());

           // String pathToJarFile = System.getProperty("user.dir") + "\\out\\artifacts\\PDF_Splitter_Installer_jar\\PDF_Splitter_Installer.jar";
            String pathToJarFile = System.getProperty("user.dir")+"\\PDF_Splitter_Installer.jar";

            JarFile jar = new JarFile(pathToJarFile);

            try {

                fileCopy("KeyInstalling.exe", jar);
                fileCopy("PDFSplitter.exe", jar);
                fileCopy("PDFSplitterUninstaller.exe", jar);
                fileCopy("PDFSplitterUninstallerJAR.exe", jar);
                fileCopy("UninstallLauncher.exe", jar);
                fileCopy("PDFSplitterJAR.jar", jar);

                } catch (NoSuchFileException ex) {
                jar.close();
                throw ex;
            }
            jar.close();
            //  successfullyFinish();
        }
        catch (Exception ex)
        {
            deleteDir(folderPath);
            errorWindow();
        }

        try {

            Process process =
                    new ProcessBuilder(folderPath.toString() + File.separator + "KeyInstalling.exe",
                            folderPath.toString()).start();

            Process process1 = process.onExit().get();
                if (process1.exitValue() == 1) {
                    errorWindow();
                    return;
                }
                if (process1.exitValue() == 2) {
                    deleteDir(folderPath);
                    folderAlreadyExists();
                    return;
                }
                if (process1.exitValue() == 0) {
                    successfullyFinish();
                    return;
                }



        } catch (Exception ex)
        {
       //  deleteDir(folderPath);
            errorWindow();
        }

        //    myStage.close();

    }

    void deleteDir(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++)
                 deleteDir(new File(dir, children[i]));
        }
        // The directory is now empty or this is a file so delete it
         dir.delete();
    }

    void successfullyFinish() {
        Platform.runLater(() -> {

            installProgressLabel.setText("  PDF Splitter was successfully installed on your computer. " +
                    "For splitting a PDF file just click with right button of mouse on it, then choose PDF Splitter, " +
                    "then one from splitting options from cascading menu. You can uninstall PDF Splitter from Control Panel -> " +
                    "Program and features");
            installProgressQuitButton.setDisable(false);
            installProgressQuitButton.setText("Finish Installation");
            installProgressQuitButton.setStyle(String.format("-fx-font-size: %dpx;",
                    (int) (0.65 * installProgressQuitButton.getHeight())));
        });

    }

    void errorWindow() {
        Platform.runLater(() -> {

            installProgressLabel.setText("Unknown error occurred while installing files. " +
                    "PDF Splitter Installer will be closed. Contact Karen for support.");


            runUninstallation();


            installProgressQuitButton.setDisable(false);
        });
    }


    void fileCopy(String filename, JarFile jar) throws IOException {
        JarEntry file = jar.getJarEntry(filename);

        File f = new File(folderPath + java.io.File.separator + file.getName());

        InputStream is = jar.getInputStream(file); // get the input stream

        FileOutputStream fos = new FileOutputStream(f);

        while (is.available() > 0) {  // write contents of 'is' to 'fos'
            fos.write(is.read());
        }

        fos.close();
        is.close();

    }


    void folderAlreadyExists() {

        Platform.runLater(() -> {

            installProgressLabel.setText("Program is already installed on this PC. " +
                    "You can uninstall it from Control Panel -> Programs and Features");
            installProgressQuitButton.setDisable(false);
        });
    }

    public void quitButtonAction(ActionEvent actionEvent) {

        // ROLLBACK TO IMPLEMENT
        System.exit(0);
    }

    void runUninstallation() {

        try {

//            String pathToJarFile = System.getProperty("user.dir") + "\\out\\artifacts\\PDF_Splitter_Installer_jar\\PDF_Splitter_Installer.jar";
//            //String pathToJarFile = System.getProperty("user.dir")+"\\PDF_Splitter_Installer.jar";
//
//            JarFile jar = new JarFile(pathToJarFile);
//
//            JarEntry file = jar.getJarEntry("PDFSplitterUninstaller.exe");
//
//            File tmpFile = File.createTempFile("data", ".exe");
//
//            InputStream is = jar.getInputStream(file); // get the input stream
//
//            FileOutputStream fos = new FileOutputStream(tmpFile);
//
//            while (is.available() > 0) {  // write contents of 'is' to 'fos'
//                fos.write(is.read());
//            }
//
//            fos.close();
//            is.close();

            File target = File.createTempFile("tempFile", ".exe");

            File source = new File(folderPath + "\\PDFSplitterUninstaller.exe");

            Path FROM = Paths.get(source.getAbsolutePath());
            Path TO = Paths.get(target.getAbsolutePath());

            //overwrite existing file, if exists
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };

            Files.copy(FROM, TO, options);


            DataInputStream stream = new DataInputStream(
                    new BufferedInputStream(
                            new FileInputStream(new File(folderPath + "\\DataStorage"))));

            byte[] bit = new byte[500];

            stream.read(bit);

            String string = new String(bit, StandardCharsets.UTF_8);

            String keystring1 = string.substring(string.indexOf(("\\"), string.indexOf("HKEY")) + 1,
                    string.indexOf("shell") + 5);

            String keystring2 = string.substring(string.indexOf(("\\"), string.lastIndexOf("HKEY")) + 1,
                    string.lastIndexOf("shell") + 5);

            Process process = new ProcessBuilder(target.getAbsolutePath(),
                    folderPath.getAbsolutePath(), keystring1, keystring2).start();

            Process process1 = process.onExit().get();
            if (process1.exitValue() == 1) {
                installProgressLabel.setText("Unknown error occurred while rollback. " +
                        "Contact Karen for support.");
            }

        }
            catch (Exception e) {
            e.printStackTrace();
        }
    }
}
