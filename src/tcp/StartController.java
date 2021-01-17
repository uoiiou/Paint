package tcp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tcp.client.ClientThread;
import tcp.server.ServerThread;

import java.io.IOException;

public class StartController {
    private boolean startserver = true;

    @FXML
    void buttonclickedclient(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        ServerThread.setBsend(true);
        startserver = false;
        initstages();
    }

    @FXML
    void buttonclickedserver() {
        startserver = true;
        initstages();
    }

    void initstages(){
        Parent part;
        Stage stage = new Stage();

        try{
            if (startserver)
            {
                part = FXMLLoader.load(getClass().getResource("PaintWindow.fxml"));
                stage.setTitle("Paint Application (S)");
                stage.setOnCloseRequest(windowEvent -> ServerThread.stopserver());
            }
            else
            {
                part = FXMLLoader.load(getClass().getResource("ClientWindow.fxml"));
                stage.setTitle("Paint Application (C)");
                stage.setOnCloseRequest(windowEvent -> ClientThread.stopclient());
            }

            Scene scene = new Scene(part);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex)
        {
            if (startserver)
                System.out.println("(StartController) Server - " + ex);
            else
                System.out.println("(StartController) Client - " + ex);
        }
    }
}
