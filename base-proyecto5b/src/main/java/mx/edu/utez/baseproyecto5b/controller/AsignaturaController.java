package mx.edu.utez.baseproyecto5b.controller;

import io.objectbox.Box;
import io.objectbox.relation.ToMany;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import mx.edu.utez.baseproyecto5b.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    @FXML
    private Button searchButtonAlumnos;
    @FXML
    private Button searchButtonMaterias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        OnAcctionGETAlumnos();
        OnAcctionGETMaterias();
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
    }

  @FXML
  protected void AlumnoAsignarMaterias() {
      System.out.println("Entro al Alumno asignarle Materias");
    Alumno selectedStudent = tableviewEstudiante.getSelectionModel().getSelectedItem();  // primer error estaba   
    Materia selectedSubject = tableviewMateria.getSelectionModel().getSelectedItem();

    if (selectedStudent != null && selectedSubject != null) {
        selectedStudent.getMaterias().add(selectedSubject);
        Database.get().boxFor(Alumno.class).put(selectedStudent);
    }
}

    @FXML
    public void MateriasAsignarAlumno() {
    Alumno selectedStudent = tableviewEstudiante.getSelectionModel().getSelectedItem();
    Materia selectedSubject = tableviewMateria.getSelectionModel().getSelectedItem();

    if (selectedStudent != null && selectedSubject != null) {
        selectedSubject.getAlumnos().add(selectedStudent);
        Database.get().boxFor(Materia.class).put(selectedSubject);
    }
}




    @FXML
    protected void handleSearchButtonActionAlumnos(){
        buscarAlumno();
    }

    @FXML
    protected void handleSearchButtonActionMaterias(){
        buscarMateria();
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
