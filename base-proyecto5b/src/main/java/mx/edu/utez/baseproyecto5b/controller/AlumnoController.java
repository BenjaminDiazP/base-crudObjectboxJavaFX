package mx.edu.utez.baseproyecto5b.controller;

import io.objectbox.Box;
import io.objectbox.relation.ToMany;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.edu.utez.baseproyecto5b.model.Alumno;
import mx.edu.utez.baseproyecto5b.model.Alumno_;
import mx.edu.utez.baseproyecto5b.model.Database;
import mx.edu.utez.baseproyecto5b.model.Materia;

import java.io.IOException;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlumnoController {

    //Declaraciones para guardar a un alumno en la base de datos
    @FXML
    private TextArea txtNombreI;
    @FXML
    private TextArea txtApellidosI;
    @FXML
    private TextArea txtMatriculaI;

    //Declaraciones para poder mostrar a los alumnos en la tablaview
    private ObservableList<Alumno> alumnosList;

    @FXML
    private TableView<Alumno> tableView;
    @FXML
    private TableColumn<Alumno, String> txtNombres;
    @FXML
    private TableColumn<Alumno, String> txtApellidos;
    @FXML
    private TableColumn<Alumno, String> txtMatricula;

    @FXML
    private TableColumn<Alumno, String> txtMaterias;


    @FXML
    protected void OnAccionAgregarAlumno() {
        if(txtNombreI.getText().isEmpty() || txtApellidosI.getText().isEmpty() || txtMatriculaI.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al agregar alumno");
            alert.setContentText("No se puede agregar un alumno con campos vacios");
            alert.showAndWait();
        }else{
            Box<Alumno> alumnoBox = Database.get().boxFor(Alumno.class);
            Alumno alumnoo = new Alumno(txtNombreI.getText(), txtApellidosI.getText(), txtMatriculaI.getText());
            Alumno selectedAlumno = tableView.getSelectionModel().getSelectedItem();


            if (selectedAlumno != null) {
                // Actualizar el alumno seleccionado
                selectedAlumno.setName(txtNombreI.getText());
                selectedAlumno.setApellidos(txtApellidosI.getText());
                selectedAlumno.setMatricula(txtMatriculaI.getText());
                alumnoBox.put(selectedAlumno);
                OnAcctionGET();
            } else {
                // Agregar un nuevo alumno
                Alumno alumnoo1 = new Alumno(txtNombreI.getText(), txtApellidosI.getText(), txtMatriculaI.getText());
                long id = alumnoBox.put(alumnoo1);
                Alumno recuperado = alumnoBox.get(id);
                System.out.println(recuperado.getName());
                System.out.println(recuperado.getMaterias());
                OnAcctionGET();
            }
        }
    }



    @FXML
    protected void OnAcctionGET(){

        txtNombres.setCellValueFactory(new PropertyValueFactory<>("name"));
        txtApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        txtMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        txtMaterias.setCellValueFactory(cellData -> {
            ToMany<Materia> materias = cellData.getValue().getMaterias();
            StringBuilder materiasStr = new StringBuilder();
            for (Materia materia : materias) {
                materiasStr.append(materia.getNombre()).append(", ");
            }
            // Eliminar la última coma y espacio
            if (materiasStr.length() > 0) {
                materiasStr.setLength(materiasStr.length() - 2);
            }
            return new SimpleStringProperty(materiasStr.toString());
        });

        cargarDatos();
    }

    private void cargarDatos() {
        // Obtener la instancia del Box de Alumno
        Box<Alumno> alumnoBox = Database.get().boxFor(Alumno.class);

        tableView.getItems().clear();

        // Obtener todos los alumnos de la base de datos
        List<Alumno> listaAlumnos = alumnoBox.getAll();

        // Si la lista de alumnos está vacía, mostrar una alerta
        if (listaAlumnos.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información Importante");
            alert.setHeaderText(null);
            alert.setContentText("No hay alumnos para agregar. Por favor, agrega al menos un alumno.");
            alert.showAndWait();
        } else {
            // Agregar los alumnos a la tabla
            tableView.getItems().addAll(listaAlumnos);
        }
    }


    @FXML
    protected  void OnAccionDELETE(){
        Alumno selectedAlumnoo = tableView.getSelectionModel().getSelectedItem();

        if (selectedAlumnoo != null) {
            Database.get().boxFor(Alumno.class).remove(selectedAlumnoo);
            tableView.getItems().remove(selectedAlumnoo);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exitoso");
            alert.setHeaderText("Exito al eliminar");
            alert.setContentText("");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al eliminar alumno");
            alert.setContentText("No se puede eliminar un alumno sin seleccionar");
            alert.showAndWait();

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
