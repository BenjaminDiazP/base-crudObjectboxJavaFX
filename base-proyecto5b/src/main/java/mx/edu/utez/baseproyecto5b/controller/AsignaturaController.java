package mx.edu.utez.baseproyecto5b.controller;

import io.objectbox.Box;
import io.objectbox.relation.ToMany;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.edu.utez.baseproyecto5b.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class AsignaturaController implements Initializable {

    private ObservableList<Alumno> alumnosList;

    @FXML
    private TableView<Alumno> tableviewEstudiante;
    @FXML
    private TableColumn<Alumno, String> txtNombres;
    @FXML
    private TableColumn<Alumno, String> txtApellidos;
    @FXML
    private TableColumn<Alumno, String> txtMatricula;

    //Declaraciones para llenar la tabla de materias

    private ObservableList<Materia> materiaList;
    @FXML
    private TableView<Materia> tableviewMateria;
    @FXML
    private TableColumn<Materia, String> txtNombre;
    @FXML
    private TableColumn<Materia, String> txtClave;

    //Declaraciones para el Buscador
    @FXML
    private TextField searchFieldMaterias;
    @FXML
    private TextField searchFieldAlumnos;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Habilitar la selección múltiple en las tablas
        tableviewEstudiante.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableviewMateria.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        OnAcctionGETAlumnos();
        OnAcctionGETMaterias();

        // Agregar un listener al campo de búsqueda de alumnos
        searchFieldAlumnos.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarAlumno();
        });

        // Agregar un listener al campo de búsqueda de materias
        searchFieldMaterias.textProperty().addListener((observable, oldValue, newValue) -> {
            buscarMateria();
        });
    }

    private void OnAcctionGETAlumnos(){

        txtNombres.setCellValueFactory(new PropertyValueFactory<>("name"));
        txtApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        txtMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));


        cargarDatosAlumno();
    }

    private void cargarDatosAlumno() {
        // Obtener la instancia del Box de Alumno
        Box<Alumno> alumnoBox = Database.get().boxFor(Alumno.class);

        tableviewEstudiante.getItems().clear();

        // Obtener todos los alumnos de la base de datos
        List<Alumno> listaAlumnos = alumnoBox.getAll();
        // Agregar los alumnos a la tabla
        tableviewEstudiante.getItems().addAll(listaAlumnos);

        // Inicializar alumnosList con los datos de la base de datos
        alumnosList = FXCollections.observableArrayList(listaAlumnos);
    }


    private void OnAcctionGETMaterias(){
        txtNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        txtClave.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClave()));
        cargarDatosMateria();
    }

    private void cargarDatosMateria(){
        // Obtener la instancia del Box de Alumno
        Box<Materia> materiaBox = Database.get().boxFor(Materia.class);

        tableviewMateria.getItems().clear();

        // Obtener todos los alumnos de la base de datos
        List<Materia> listaMateria = materiaBox.getAll();


        // Agregar los alumnos a la tabla
        tableviewMateria.getItems().addAll(listaMateria);

        materiaList = FXCollections.observableArrayList(listaMateria);
    }

    @FXML
    protected void AlumnoAsignarMaterias() {
        System.out.println("AlumnoAsignarMaterias");
        List<Alumno> selectedStudents = tableviewEstudiante.getSelectionModel().getSelectedItems();
        List<Materia> selectedSubjects = tableviewMateria.getSelectionModel().getSelectedItems();

        if (selectedStudents.isEmpty() && selectedSubjects.isEmpty()) {
            System.out.println("No hay nada seleccionado");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información Importante");
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un alumno y una materia");
            alert.showAndWait();
        }else{
            System.out.println("Algo seleccionado");
            for (Alumno selectedStudent : selectedStudents) {
                for (Materia selectedSubject : selectedSubjects) {
                    selectedStudent.getMaterias().add(selectedSubject);
                }
                Database.get().boxFor(Alumno.class).put(selectedStudent);

            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información Importante");
            alert.setHeaderText(null);
            alert.setContentText("Correcta asignacion");
            alert.showAndWait();
        }
    }

    @FXML
    public void MateriasAsignarAlumno() {
        List<Alumno> selectedStudents = tableviewEstudiante.getSelectionModel().getSelectedItems();
        List<Materia> selectedSubjects = tableviewMateria.getSelectionModel().getSelectedItems();

        if (selectedStudents.isEmpty() && selectedSubjects.isEmpty()) {
            System.out.println("No hay nada seleccionado");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información Importante");
            alert.setHeaderText(null);
            alert.setContentText("Seleccione un alumno y una materia");
            alert.showAndWait();
        }else{
            for (Materia selectedSubject : selectedSubjects) {
                for (Alumno selectedStudent : selectedStudents) {
                    selectedSubject.getAlumnos().add(selectedStudent);
                }
                Database.get().boxFor(Materia.class).put(selectedSubject);
            }
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Información Importante");
            alert.setHeaderText(null);
            alert.setContentText("Correcta asignacion");
            alert.showAndWait();
        }
    }







    //Metodos para la busqueda
    private void buscarAlumno() {
        String searchText = searchFieldAlumnos.getText().toLowerCase();

        FilteredList<Alumno> filteredData = alumnosList.filtered(alumno -> {
            return alumno.getName().toLowerCase().contains(searchText)
                    || alumno.getApellidos().toLowerCase().contains(searchText)
                    || alumno.getMatricula().toLowerCase().contains(searchText);
        });

        tableviewEstudiante.setItems(filteredData);
    }

    private void buscarMateria() {
        String searchText = searchFieldMaterias.getText().toLowerCase();

        FilteredList<Materia> filteredData = materiaList.filtered(materia -> {
            return materia.getNombre().toLowerCase().contains(searchText)
                    || materia.getClave().toLowerCase().contains(searchText);
        });

        tableviewMateria.setItems(filteredData);
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
