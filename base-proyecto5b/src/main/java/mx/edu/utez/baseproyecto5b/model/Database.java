package mx.edu.utez.baseproyecto5b.model;

import io.objectbox.BoxStore;

public class Database {
    private static BoxStore boxStore;

    public static BoxStore get() {
        if (boxStore == null) {
            boxStore = MyObjectBox.builder()
                    .name("Escuela")
                    .build();
        }
        return boxStore;
    }
}