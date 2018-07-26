package Herramientas;

import TDA.Tokens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Principal {
    private Acceso ac;
    private ObservableList<Tokens> tokens;
    private ArrayList<String> errores;
    private int q, q0, x, y, id = 500, error = 0;
    private int finales[];
    private int[] valores = new int[]{180, 181, 182};
    private int[] tiposDato = new int[]{150, 151, 154, 155};
    private int[] tipoRetorno = new int[]{150, 151, 154, 155, 179};
    private int[] tiposEncapsulamiento = new int[]{160, 161, 162};
    private int[] comparativos = new int[]{143, 144, 145, 146, 177, 178};
    private int[] aritmeticos = new int[]{135, 136, 169, 170, 171, 172, 173};
    private int[] igualacion = new int[]{175, 137, 138};
    private int[] logicos = new int[]{147, 178};
    private int matriz[][];
    private String alfabeto[];
    private String entrada[];
    private String salida = "", texto = "";
    private int cont;
    private boolean bloque = false, si = false;

    public Principal(String texto) {
        configurar();
        this.texto = texto;
        analisisLexico();
        analisisSintactico();
    }

    public void configurar() {
        String aux;
        String auxA[];
        tokens = FXCollections.observableArrayList();
        errores = new ArrayList<String>();
        ac = new Acceso("Reglas.txt");
        alfabeto = ac.getLinea(0).split("@");
        q0 = Integer.parseInt(ac.getLinea(1));
        auxA = ac.getLinea(3).split(",");
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
                if (fin == 198)
                    salir = null;
                else
                    salir = true;
        }
        return salir;
    }

    public int encontrarIndex(String aux) {
        for (int i = 0; i < alfabeto.length; i++)
            if (alfabeto[i].equalsIgnoreCase(aux))
                return i;
        return -1;
    }

    public void analisisLexico() {
        Tokens token;
        String palabra = "";
        int index;
        boolean usar = false;
        Boolean aux;
        entrada = texto.split("\n");
        for (int i = 0; i < entrada.length; i++) {
            q = q0;
            texto = entrada[i];
            for (int j = 0; j <= texto.length(); j++) {
                aux = esFinal();
                if (aux != null) {
                    if (aux) {
                        usar = true;
                        token = new Tokens();
                        //palabra = palabra.replace(" ", "");
                        if (q == 180) {
                            if (palabra.contains(";")) {
                                palabra = palabra.replace(";", "");
                                token.setToken(palabra.toLowerCase());
                                token.setId(q);
                                tokens.add(token);
                                token = new Tokens();
                                q = 124;
                                palabra = ";";
                            } else {
                                palabra = palabra.replace(";", "");
                                token.setToken(palabra.toLowerCase());
                                token.setId(q);
                            }
                        }
                        if (q == 199) {
                            if (palabra.replace(" ", "").charAt(palabra.replace(" ", "").length() - 1) == ';') {
                                palabra = palabra.replace(";", "");
                                token.setToken("" + palabra);
                                token.setId(q);
                                tokens.add(token);
                                token = new Tokens();
                                q = 124;
                                palabra = ";";
                            }
                            for (int a = 0; a < tokens.size(); a++) {
                                if (tokens.get(a).getToken().equalsIgnoreCase(palabra)) {
                                    token.setId(tokens.get(a).getId());
                                    usar = false;
                                }
                            }
                            if (usar) {
                                token.setId(id);
                                id++;
                            }

                        } else
                            token.setId(q);
                        token.setToken(palabra);
                        tokens.add(token);
                        q = q0;
                        palabra = "";
                        j--;
                    } else {
                        if (j < texto.length()) {
                            index = encontrarIndex("" + texto.charAt(j));
                            if (index != -1) {
                                palabra += texto.charAt(j);
                                q = matriz[q][index];
                            } else {
                                errores.add("Error en la linea: " + (i + 1) + "\n");
                                error++;
                                palabra = "";
                                break;
                            }
                        }
                    }
                } else {
                    errores.add("Error en la linea: " + (i + 1) + "\n");
                    error++;
                    palabra = "";
                    break;
                }
            }
        }
    }

    public void analisisSintactico() {
        if (buscarEncapsulamiento(tokens.get(cont).getId())) {
            bloque = true;
            bloque();
        }
        if (buscarTipoDato(tokens.get(cont).getId())) {
            declaracion();
        }
        if (tokens.get(cont).getId() == 164) {
            declaracion();
        }
        if (tokens.get(cont).getId() >= 500) {
            operacion();
        }
    }
    public void analisisDelBloque() {
        if (buscarTipoDato(tokens.get(cont).getId())) {
            declaracion();
        }
        if (tokens.get(cont).getId() == 164) {
            declaracion();
        }
        if (tokens.get(cont).getId() >= 500) {
            operacion();
        }
        if(tokens.get(cont).getId() == 128){
            bloque = false;
        }
    }
    public void analisisDelSi() {
        if (buscarTipoDato(tokens.get(cont).getId())) {
            declaracion();
        }
        if (tokens.get(cont).getId() == 164) {
            declaracion();
        }
        if (tokens.get(cont).getId() >= 500) {
            operacion();
        }
        if(tokens.get(cont).getId() == 128){
            bloque = false;
        }
    }

    public boolean buscarTipoDato(int valor) {
        for (int aux : tiposDato)
            if (aux == valor)
                return true;
        return false;
    }

    public boolean buscarEncapsulamiento(int valor) {
        for (int aux : tiposEncapsulamiento)
            if (aux == valor)
                return true;
        return false;
    }

    public boolean buscarComparativo(int valor) {
        for (int aux : comparativos)
            if (aux == valor)
                return true;
        return false;
    }

    public boolean buscarEntradas(int valor) {
        for (int aux : valores)
            if (aux == valor)
                return true;
        return false;
    }

    public boolean buscarAritmetico(int valor) {
        for (int aux : aritmeticos)
            if (aux == valor)
                return true;
        return false;
    }

    public boolean buscarRetorno(int valor) {
        for (int aux : tipoRetorno)
            if (aux == valor)
                return true;
        return false;
    }

    public boolean buscarIgualacion(int valor) {
        for (int aux : igualacion)
            if (aux == valor)
                return true;
        return false;
    }

    public boolean buscarLogicos(int valor) {
        for (int aux : logicos)
            if (aux == valor)
                return true;
        return false;
    }

    public void declaracion() {
        if (incrementar()) {
            if (tokens.get(cont).getId() >= 500) {
                if (incrementar()) {
                    if (tokens.get(cont).getId() == 175) {
                        if (incrementar()) {
                            if (buscarEntradas(tokens.get(cont).getId())) {
                                if (incrementar()) {
                                    if (tokens.get(cont).getId() == 131) {
                                        declaracion();
                                    }
                                    if (tokens.get(cont).getId() == 134){
                                        if(incrementar()) {
                                            if (bloque) {
                                                if (si)
                                                    analisisDelSi();
                                                else
                                                    analisisDelBloque();
                                            }
                                            else{
                                                if(si)
                                                    analisisDelSi();
                                                else
                                                    analisisSintactico();
                                            }
                                        }
                                    }
                                    return;

                                }
                            } else {
                                for (int a = 0; a < tokens.size(); a++) {
                                    if (tokens.get(a).getId() == tokens.get(cont).getId()) {
                                        if (incrementar()) {
                                            if (tokens.get(cont).getId() == 131) {
                                                declaracion();
                                            }
                                            if (tokens.get(cont).getId() == 134){
                                                if(incrementar()) {
                                                    if (bloque) {
                                                        if (si)
                                                            analisisDelSi();
                                                        else
                                                            analisisDelBloque();
                                                    }
                                                    else {
                                                        if (si)
                                                            analisisDelSi();
                                                        else
                                                            analisisSintactico();
                                                    }
                                                }
                                            }
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (tokens.get(cont).getId() == 131) {
                            declaracion();
                        }
                        if (tokens.get(cont).getId() == 134 || tokens.get(cont).getId() == 128) {
                            if (incrementar()) {
                                if (bloque)
                                    analisisDelBloque();
                                else
                                    analisisSintactico();
                            }
                            return;
                        }
                        else{
                            System.out.println("Error");
                        }
                    }
                }

            }
        }
    }

    public void bloque() {
        if (incrementar()) {
            if(buscarRetorno(tokens.get(cont).getId())){
                if(incrementar()){
                    if(tokens.get(cont).getId()>=500){
                        if(incrementar()) {
                            if(tokens.get(cont).getId() == 125) {
                                do {
                                    if (incrementar()) {
                                        if (buscarTipoDato(tokens.get(cont).getId())){
                                            if(incrementar()){
                                                if (tokens.get(cont).getId()<500) {
                                                    System.out.println("Error");
                                                    return;
                                                }
                                                if (!incrementar()){
                                                    System.out.println("Error");
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                } while (tokens.get(cont).getId() == 131);
                                if(tokens.get(cont).getId() == 130){
                                    if(incrementar()) {
                                        if (tokens.get(cont).getId() == 127) {
                                            if(incrementar()) {
                                                analisisDelBloque();
                                            }
                                        }
                                        else{
                                            System.out.println("Error");
                                            return;
                                        }
                                    }
                                    else{
                                        System.out.println("Error");
                                        return;
                                    }
                                }
                                else{
                                    System.out.println("Error");
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void operacion() {
        if(incrementar()){
            if(buscarIgualacion(tokens.get(cont).getId())){
                if(tokens.get(cont).getId() == 175){
                    do{
                        if(incrementar()){
                            if(tokens.get(cont).getId() >= 500 || buscarEntradas(tokens.get(cont).getId())) {
                                if(incrementar()) {
                                    if (!buscarAritmetico(tokens.get(cont).getId())) {
                                        if(tokens.get(cont).getId() == 134)
                                            return;
                                        else {
                                            System.out.println("Error");
                                            return;
                                        }
                                    }
                                }
                                else{
                                    System.out.println("Error");
                                    return;
                                }
                            }
                            else {
                                System.out.println("Error");
                                return;
                            }
                        }
                        else {
                            System.out.println("Error");
                            return;
                        }
                    }while (tokens.get(cont).getId() != 134);
                }
                if(tokens.get(cont).getId() == 137 || tokens.get(cont).getId() == 138){
                    if(incrementar()){
                        if(!(tokens.get(cont).getId() >= 500 || buscarEntradas(tokens.get(cont).getId()))){
                            System.out.println("Error");
                            return;
                        }
                    }
                }
            }
        }
    }

    public void si() {

    }

    public boolean incrementar() {
        if (cont < tokens.size()-1) {
            cont++;
            return true;
        }
        return false;
    }

    public int getCantErrores() {
        return error;
    }

    public ArrayList<String> getErrores() {
        return errores;
    }

    public ObservableList<Tokens> getTokens() {
        return tokens;
    }
}
