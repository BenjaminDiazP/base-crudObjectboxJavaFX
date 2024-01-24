package mx.edu.utez.baseproyecto5b.model;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Alumno {

    @Id
    public long id;

    public String name;
    public String apellidos;
    public String matricula;

    public ToMany<Materia> materias;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public ToMany<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ToMany<Materia> materias) {
        this.materias = materias;
    }

    public Alumno(String name, String apellidos, String matricula) {
        this.name = name;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.materias = new ToMany<>(this, Alumno_.materias); // Inicializar la lista aquí
    }

    public Alumno(long id, String name, String apellidos, String matricula, ToMany<Materia> materias) {
        this.id = id;
        this.name = name;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.materias = materias;
        this.materias = new ToMany<>(this, Alumno_.materias);
    }

    public Alumno(long id, String name, String apellidos, String matricula) {
        this.id = id;
        this.name = name;
        this.apellidos = apellidos;
        this.matricula = matricula;
        this.materias = new ToMany<>(this, Alumno_.materias);
    }

    public Alumno() {
        this.materias = new ToMany<>(this, Alumno_.materias);
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", matricula='" + matricula + '\'' +
                ", materias=" + materias +
                '}';
    }
}