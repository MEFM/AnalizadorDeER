/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc1.practica1_201700371;

/**
 *
 * @author mynor
 */
class NodoArbol {

    NodoArbol derecha;
    NodoArbol izquierda;

    String dato;

    public NodoArbol(String dato) {
        this.dato = dato;
        this.derecha = null;
        this.izquierda = null;
    }
}

public class Arbol {

    NodoArbol raiz;

    boolean operador(String caracer) {
        String[] simbolo = {".", "|", "*", "" + ""};

        for (int i = 0; i < simbolo.length; i++) {
            if (caracer == simbolo[i]) {

                return true;
            }
        }
        return false;
    }

    void insertar(String nuevo) {
        if (this.raiz == null) {
            this.raiz = new NodoArbol(nuevo);
        } else {
            insertar(nuevo, this.raiz);
        }
    }

    void insertar(String nuevo, NodoArbol arbol) {
        if (arbol != null) {
            if (operador(nuevo) && operador(arbol.dato) && arbol.izquierda != null) {
                //Preguntar si el siguiente a la izquierda es operador
                if (!operador(arbol.izquierda.dato)) {
                    //No seguir buscando esta es una hoja 
                } else {
                    //Puede seguir buscando por la izquierda este es un operador
                }
            } else if (operador(nuevo) && operador(arbol.dato) && arbol.derecha != null) {
                if (!operador(arbol.derecha.dato)) {
                    //No se puede seguir con la busqueda, es una hoja
                } else {
                    //Puede seguir buscando por la derecha, es operador
                }
            } else if (!operador(nuevo) && operador(arbol.dato) && arbol.izquierda != null) {
                if (!operador(arbol.izquierda.dato)) {
                    //No se puede seguir con la busqueda, es una hoja
                } else {
                    //Puede seguir buscando por la derecha, es operador
                }
            } else if (!operador(nuevo) && operador(arbol.dato) && arbol.derecha != null) {
                if (!operador(arbol.derecha.dato)) {
                    //No se puede seguir con la busqueda, es una hoja
                } else {
                    //Puede seguir buscando por la derecha, es operador
                }
            }
        } else {
            arbol = new NodoArbol(nuevo);
        }
    }
}
