package GUI;

import Herramientas.Principal;
import TDA.Tokens;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML BorderPane pPrincipal;
    @FXML TableView<Tokens> tTokens;
    @FXML TableColumn cToken;
    @FXML TableColumn cId;
    @FXML TextArea aCodigo;
    @FXML TextArea aConsola;
    @FXML Button bCompilar;
    ObservableList<Tokens> tokens;
    ArrayList<String> errores;

    private Principal p;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aConsola.setEditable(false);
        bCompilar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                p = new Principal(aCodigo.getText());
                tTokens = new TableView<Tokens>();
                tokens = p.getTokens();
                tTokens.setItems(tokens);
                cToken = new TableColumn("Tokens");
                cId = new TableColumn("Ids");
                cToken.setCellValueFactory(new PropertyValueFactory<Tokens, String>("token"));
                cId.setCellValueFactory(new PropertyValueFactory<Tokens, String>("id"));
                tTokens.getColumns().setAll(cToken, cId);
                pPrincipal.setRight(tTokens);
                if(p.getCantErrores()==0)
                    aConsola.setText("El código no tiene errores léxicos");
                else {
                    aConsola.setText("Tienes un total de "+p.getCantErrores()+" de errores\n");
                    errores = p.getErrores();
                    for(int i = 0; i<errores.size(); i++)
                        aConsola.appendText(errores.get(i));
                }
            }
        });
    }
}
