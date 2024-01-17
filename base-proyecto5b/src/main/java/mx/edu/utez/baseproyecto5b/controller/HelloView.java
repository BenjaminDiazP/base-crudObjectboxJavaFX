package mx.edu.utez.baseproyecto5b;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloView {

    @FXML
    private TextField txtNombre;

    @FXML
    private Label welcometext;

    @FXML
    protected void onClickBoton() {
        welcometext.setText("Hola " + txtNombre.getText() + "!");
    }
}
