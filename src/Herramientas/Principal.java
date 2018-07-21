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

    public void evaluar() {
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
                            palabra = palabra.replace(";", "");
                            token.setToken(palabra.toLowerCase());
                            token.setId("" + q);
                            tokens.add(token);
                            token = new Tokens();
                            q = 124;
                            palabra = ";";
                        }
                        if (q == 199) {
                            if (palabra.replace(" ", "").charAt(palabra.replace(" ", "").length()-1) == ';') {
                                palabra = palabra.replace(";", "");
                                token.setToken("" +palabra);
                                token.setId("" + q);
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
                                token.setId("" + id);
                                id++;
                            }

                        } else
                            token.setId("" + q);
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
