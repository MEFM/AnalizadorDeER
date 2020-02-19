/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc1.practica1_201700371;

import java.util.*;

/**
 *
 * @author mynor
 */
public class Expresiones {

    String id;
    String ER;
    String cadena;
    ArrayList<Token> token = new ArrayList<>();

    public Expresiones(String id, String ER, ArrayList<Token> token) {
        this.id = id;
        this.ER = ER;
        this.token = token;
    }

    public Expresiones(String id, String Er, ArrayList<Token> token, String cadena) {
        this.id = id;
        this.ER = Er;
        this.cadena = cadena;
    }

    public boolean estado() {
        return true;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getER() {
        return ER;
    }

    public void setER(String ER) {
        this.ER = ER;
    }

    boolean operador(String caracer) {
        String[] simbolo = {".", "|", "*", "" + ""};

        for (int i = 0; i < simbolo.length; i++) {
            if (caracer == simbolo[i]) {

                return true;
            }
        }
        return false;
    }

    public void analisis() {
        //Crear Arbol binario de busqueda
        int i = 0;
        while(i < token.size()){
            if(operador(token.get(i).getLexema())){
                //Insertar a la cabeza como operador
                i++;
                if(operador(token.get(i).getLexema())){
                    //Insertar a la izquierda como operador
                    i++;
                    if(operador(token.get(i).getLexema())){
                        //Insertar a la derecha como operador
                    }else{
                        //Insertar a la derecha como operando
                    }
                }else{
                    //Insertar a la izquierda como operando
                }
            }else{
                //Insertar como operando lo cual dicata final
            }
            i++;
        }
    }



}
