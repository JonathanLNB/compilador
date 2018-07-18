package Herramientas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Acceso extends File {
    private BufferedReader br;
    private String textoIn;

    public Acceso(String textoIn) {
        super(textoIn);
        this.textoIn = textoIn;
    }

    public String getLinea(int linea) {
        String fila = null;
        try {
            br = new BufferedReader(new FileReader(textoIn));
            for (int i = 0; i <= linea; i++) {
                fila = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            return fila;
        }
    }
}
