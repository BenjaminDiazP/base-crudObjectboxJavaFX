package mx.edu.utez.baseproyecto5b.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Alumno {

    @Id
    public long id;

    public String name;


}
