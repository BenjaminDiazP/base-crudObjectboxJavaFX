package mx.edu.utez.baseproyecto5b.controller;

import io.objectbox.Box;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        Box<Materia> materiaBox = Database.get().boxFor(Materia.class);
        Materia  selectedMateria = tableView.getSelectionModel().getSelectedItem();

        if(selectedMateria != null) {
            String nuevoNombre = txtNombreI.getText();
            String nuevoClave = txtClaveI.getText();

            selectedMateria.setNombre(nuevoNombre);
            selectedMateria.setClave(nuevoClave);

            materiaBox.put(selectedMateria);
        }else{
            Materia newmateria = new Materia(txtNombreI.getText(),txtClaveI.getText());

            Database.get().boxFor(Materia.class).put(newmateria);
        }
    }


    @FXML
    protected void OnAcctionGET(){
        txtNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        txtClave.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));
        cargarDatos();
    }

    private void cargarDatos(){
        // Obtener la instancia del Box de Alumno
        Box<Materia> materiaBox = Database.get().boxFor(Materia.class);

        tableView.getItems().clear();

        // Obtener todos los alumnos de la base de datos
        List<Materia> listaMateria = materiaBox.getAll();

        // Agregar los alumnos a la tabla
        tableView.getItems().addAll(listaMateria);
    }


    @FXML
    protected void OnAccionDELETE(){
        Materia selectedMateriaa = tableView.getSelectionModel().getSelectedItem();
        if (selectedMateriaa != null) {
            Database.get().boxFor(Materia.class).remove(selectedMateriaa);
            tableView.getItems().remove(selectedMateriaa);
        }else{

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
