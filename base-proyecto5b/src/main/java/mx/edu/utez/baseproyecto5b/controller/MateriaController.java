package mx.edu.utez.baseproyecto5b.controller;

import io.objectbox.Box;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mx.edu.utez.baseproyecto5b.model.Alumno;
import mx.edu.utez.baseproyecto5b.model.Database;
import mx.edu.utez.baseproyecto5b.model.Materia;

import java.io.IOException;
import java.util.List;

public class MateriaController {

    //Declaraciones para guardar a un alumno en la base de datos
    @FXML
    private TextArea txtNombreI;
    @FXML
    private TextArea txtClaveI;

    //Declaraciones para hacer el get
    private ObservableList<Materia> materiaList;
    @FXML
    private TableView<Materia> tableView;
    @FXML
    private TableColumn<Materia, String> txtNombre;
    @FXML
    private TableColumn<Materia, String> txtClave;




    @FXML
    protected void OnAccionPUT(){
        String nuevoNombre = txtNombreI.getText();
        String nuevoClave = txtClaveI.getText();

        if(nuevoNombre.isEmpty() || nuevoClave.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Llena todos los campos");
            alert.showAndWait();
            return;
        }

        Box<Materia> materiaBox = Database.get().boxFor(Materia.class);
        Materia  selectedMateria = tableView.getSelectionModel().getSelectedItem();

        if(selectedMateria != null) {
            selectedMateria.setNombre(nuevoNombre);
            selectedMateria.setClave(nuevoClave);
            materiaBox.put(selectedMateria);
            OnAcctionGET();
        } else {
            Materia newmateria = new Materia(nuevoNombre, nuevoClave);
            Database.get().boxFor(Materia.class).put(newmateria);
            OnAcctionGET();
        }
    }


    @FXML
    protected void OnAcctionGET(){
        txtNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        txtClave.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));
        cargarDatos();
    }

    private void cargarDatos(){
        // Obtener la instancia del Box de Materia
        Box<Materia> materiaBox = Database.get().boxFor(Materia.class);

        tableView.getItems().clear();

        // Obtener todas las materias de la base de datos
        List<Materia> listaMateria = materiaBox.getAll();

        // Si la lista de materias está vacía, mostrar una alerta
        if (listaMateria.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información Importante");
            alert.setHeaderText(null);
            alert.setContentText("No hay materias para mostrar. Por favor, agrega al menos una materia.");
            alert.showAndWait();
        } else {
            // Agregar las materias a la tabla
            tableView.getItems().addAll(listaMateria);
        }
    }


    @FXML
    protected void OnAccionDELETE(){
        Materia selectedMateriaa = tableView.getSelectionModel().getSelectedItem();
        if (selectedMateriaa != null) {
            Database.get().boxFor(Materia.class).remove(selectedMateriaa);
            tableView.getItems().remove(selectedMateriaa);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona una materia");
            alert.showAndWait();
            return;
        }
    }



    @FXML
    protected void OnAccionMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/mx.edu.utez.baseproyecto5b/hello-view.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
