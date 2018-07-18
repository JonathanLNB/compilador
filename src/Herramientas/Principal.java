package Herramientas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Principal {
    private Acceso ac;
    private ObservableList<String> tokens, ids;
    private int q, q0, x, y, id=500, error = 0;
    private int finales[];
    private int matriz[][];
    private String alfabeto[];
    private String entrada[];
    private String salida = "", texto = "";

    public Principal(String texto) {
        configurar();
        this.texto = texto;
        evaluar();
    }

    public void configurar() {
        String aux;
        String auxA[];
        tokens = FXCollections.observableArrayList();
        ids = FXCollections.observableArrayList();
        ac = new Acceso("Reglas.txt");
        alfabeto = ac.getLinea(0).split("@");
        q0 = Integer.parseInt(ac.getLinea(1));
        auxA = ac.getLinea(3).split(" ");
        finales = new int[auxA.length];
        for (int i = 0; i < auxA.length; i++)
            finales[i] = Integer.parseInt(auxA[i]);
        aux = ac.getLinea(2);
        x = Integer.parseInt(aux.split(" ")[0]);
        y = Integer.parseInt(aux.split(" ")[1]);
        matriz = new int[x][y];
        for (int i = 0; i < x; i++) {
            aux = ac.getLinea(i + 4);
            for (int j = 0; j < y; j++)
                matriz[i][j] = Integer.parseInt(aux.split(",")[j]);

        }
    }

    public Boolean esFinal() {
        Boolean salir = false;
        for (int fin : finales) {
            if (fin == q)
                if (fin == 148)
                    salir = null;
                else
                    salir = true;
        }
        return salir;
    }

    public int encontrarIndex(String aux) {
        for (int i = 0; i < alfabeto.length; i++)
            if (alfabeto[i].equals(aux))
                return i;
        return -1;
    }

    public void evaluar() {
        int index;
        entrada = texto.split(" ");
        for (int i = 0; i < entrada.length; i++) {
            q = q0;
            texto = entrada[i];
            for (int j = 0; j < texto.length(); j++) {
                if(esFinal())
                    break;
                else {
                    index = encontrarIndex(""+texto.charAt(j));
                    if (index != -1)
                        q = matriz[q][index];
                    else {
                        error++;
                        break;
                    }
                }
            }
            tokens.add(texto);
            if(q == 147) {
                ids.add("" + id);
                id++;
            }
            else
                ids.add(""+q);
        }
    }
    public int getErrores(){
        return error;
    }
    public ObservableList<String> getTokens(){
        return tokens;
    }
    public ObservableList<String> getIds(){
        return ids;
    }
}
