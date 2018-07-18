package GUI;

import Herramientas.Principal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML TableView tTokens;
    @FXML TableColumn cToken;
    @FXML TableColumn cId;
    @FXML TextArea aCodigo;
    @FXML TextArea aConsola;
    @FXML Button bCompilar;

    private Principal p;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bCompilar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                p = new Principal(aCodigo.getText());
                System.out.println(p.getErrores());
            }
        });
    }
}
