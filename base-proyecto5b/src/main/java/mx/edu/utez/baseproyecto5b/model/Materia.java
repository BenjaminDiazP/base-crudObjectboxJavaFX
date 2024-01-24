package mx.edu.utez.baseproyecto5b.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class Materia {

    @Id
    public long id;

    public String nombre;
    public String clave;

    @Backlink(to = "materias")
    public ToMany<Alumno> alumnos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public ToMany<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(ToMany<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public Materia(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
        this.alumnos = new ToMany<>(this, Materia_.alumnos);
    }

    public Materia(long id, String nombre, String clave, ToMany<Alumno> alumnos) {
        this.id = id;
        this.nombre = nombre;
        this.clave = clave;
        this.alumnos = alumnos;
        this.alumnos = new ToMany<>(this, Materia_.alumnos);
    }

    public Materia(String nombre, String clave, ToMany<Alumno> alumnos) {
        this.nombre = nombre;
        this.clave = clave;
        this.alumnos = alumnos;
        this.alumnos = new ToMany<>(this, Materia_.alumnos);
    }

    public Materia() {
        this.alumnos = new ToMany<>(this, Materia_.alumnos);
    }

    @Override
    public String toString() {
        return "Materia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", clave='" + clave + '\'' +
                ", alumnos=" + alumnos +
                '}';
    }
}