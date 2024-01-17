package mx.edu.utez.baseproyecto5b;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.edu.utez.baseproyecto5b.Main;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class HelloView {

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private Parent root;
    @FXML
    private Label welcomeText;

    @FXML
    private TextField txtNombre;


    //Mandar ala vista de alumnos
    @FXML
    protected void onAlumnosButtonClick(javafx.event.ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/mx.edu.utez.baseproyecto5b/alumnos.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
