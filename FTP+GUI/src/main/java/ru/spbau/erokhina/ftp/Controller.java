package ru.spbau.erokhina.ftp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

/**
 * Class that provides methods which are responsible for UI Controls behavior.
 */
public class Controller {
    /**
     * OnAction method for OK Button.
     * @param actionEvent given event
     */
    public void okButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        TextField hostName = (TextField) stage.getScene().lookup("#hostName");
        TextField portName = (TextField) stage.getScene().lookup("#portName");

        if (!hostName.getText().isEmpty()) {
            CurInfo.instance.setHost(hostName.getText());
        }
        if (!portName.getText().isEmpty()) {
            CurInfo.instance.setPort(Integer.parseInt(portName.getText()));
        }

        Parent panel = FXMLLoader.load(Controller.class.getResource("/file_manager.fxml"));
        Scene scene = new Scene(panel, 600, 590);

        stage.setScene(scene);

        Client client = new Client(CurInfo.instance.getHost(), CurInfo.instance.getPort());

        loadDirectory(new ArrayList<>(Collections.singletonList(File.separator)), client, scene);
    }

    private void loadDirectory(ArrayList<String> path, Client client, Scene scene) throws IOException {
        CurInfo.instance.setCurDirectory(path);
        CurInfo.instance.setCurClient(client);
        CurInfo.instance.setCurScene(scene);

        Button[] pathButtons = new Button[path.size()];
        for (int i = 0; i < path.size(); i++) {
            pathButtons[i] = new Button(path.get(i));
            pathButtons[i].setMaxHeight(40);

            int j = i;

            pathButtons[i].setOnAction(event -> {
                ArrayList<String> newPath = new ArrayList<>(path.subList(0, j+1));
                try {
                    loadDirectory(newPath, client, scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        HBox hBox = (HBox) scene.lookup("#curPath");
        hBox.getChildren().clear();
        hBox.getChildren().addAll(pathButtons);

        VBox vBox = (VBox) scene.lookup("#curFileList");
        vBox.getChildren().clear();

        HashMap<String, Boolean> fileList = client.list(String.join(File.separator, path));
        Set<Map.Entry<String, Boolean>> fileSet = fileList.entrySet();

        Set<Map.Entry<String, Boolean>> setOrder =
                new TreeSet<>((o1, o2) -> {
                    if (o1.getValue() != o2.getValue()) {
                        return (o2.getValue() ? 1 : 0) - (o1.getValue() ? 1 : 0);
                    }

                    return o1.getKey().compareTo(o2.getKey());
                });
        setOrder.addAll(fileSet);

        Button[] files = new Button[setOrder.size()];

        int i = 0;
        for (Map.Entry<String, Boolean> entry : setOrder) {
            files[i] = new Button(entry.getKey());
            if (entry.getValue()) {
                files[i].setStyle(Constants.instance.getDirectoryButtonStyle());
            } else {
                files[i].setStyle(Constants.instance.getFileButtonStyle());
            }
            files[i].setMaxWidth(600);

            int j = i;

            files[i].setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if (event.getClickCount() == 1){
                        if (CurInfo.instance.getHighlighted() != null) {
                            CurInfo.instance.getHighlighted().setStyle(CurInfo.instance.getPrevStyle());
                            CurInfo.instance.setHighlighted(null);
                        }
                        CurInfo.instance.setPrevStyle(files[j].getStyle());
                        files[j]
                                .setStyle(Constants.instance.getHighlightedButtonStyle());
                        CurInfo.instance.setHighlighted(files[j]);
                    } else if (event.getClickCount() == 2) {
                        if (entry.getValue()) {
                            ArrayList<String> newPath = new ArrayList<>(path);
                            newPath.add(entry.getKey());

                            try {
                                loadDirectory(newPath, client, scene);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                    "Do you want to download " + files[j].getText() + "?",
                                    ButtonType.YES, ButtonType.CANCEL);
                            alert.showAndWait();

                            if (alert.getResult() == ButtonType.YES) {
                                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

                                FileChooser fileChooser = new FileChooser();
                                fileChooser.setTitle("Open Resource File");
                                File dest = fileChooser.showSaveDialog(stage);

                                if (dest == null) {
                                    alert.close();
                                    return;
                                }

                                byte[] bytes;

                                try {
                                    bytes = client.get(String.join(File.separator, path) +
                                            File.separator + entry.getKey());
                                    int sizeFile = bytes.length;

                                    dest.createNewFile();

                                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

                                    FileOutputStream fileOutputStream = new FileOutputStream(dest);

                                    byte[] buff = new byte[1024];
                                    long cur = 0;
                                    while (cur < sizeFile) {
                                        int toRead = (int) Math.min(1024, sizeFile - cur);

                                        byteArrayInputStream.read(buff, 0, toRead);
                                        fileOutputStream.write(buff, 0, toRead);
                                        cur += toRead;
                                    }

                                    fileOutputStream.close();
                                    byteArrayInputStream.close();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                }
            });

            i++;
        }

        vBox.getChildren().addAll(files);
    }

    /**
     * OnAction method for Return Button.
     */
    public void returnButtonOnClick() throws IOException {
        if (CurInfo.instance.getCurDirectory().size() != 1) {
            ArrayList<String> newList = CurInfo.instance.getCurDirectory();
            newList.remove(CurInfo.instance.getCurDirectory().size() - 1);
            CurInfo.instance.setCurDirectory(newList);
        }
        loadDirectory(CurInfo.instance.getCurDirectory(), CurInfo.instance.getCurClient(), CurInfo.instance.getCurScene());
    }

    /**
     * OnAction method for ChangeHost Button.
     * @param actionEvent given event
     */
    public void changeHostOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        
        Parent panel = FXMLLoader.load(Main.class.getResource("/enter_host.fxml"));

        Scene scene = new Scene(panel, 600, 590);
        stage.setScene(scene);
    }

    /**
     * OnAction method for Exit Button.
     */
    public void exitOnAction() {
        Platform.exit();
    }
}
