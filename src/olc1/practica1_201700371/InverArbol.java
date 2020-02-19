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
public class InverArbol {

    ArrayList<Token> prefij;
    Stack<String> pila;

    public InverArbol(ArrayList<Token> prefij) {
        this.prefij = prefij;
        this.pila = new Stack();

    }

    public String rpn() {
        String derecho = "", izquierdo = "";
        String acumulador = "";
        for (Token token : prefij) {
            if (operador(token.getLexema())) {
                try {
                    derecho = pila.pop();
                } catch (Exception e) {
                }

                if (pila.empty()) {
                    //System.out.println("So fking sad");
                }
                try {
                    izquierdo = pila.pop();
                } catch (Exception e) {
                }
                
                String infijo = operar(izquierdo, token.getLexema(), derecho);
                acumulador += " " + infijo;
            } else {
                pila.push(token.getLexema());
            }
        }

        return acumulador;
    }

    String operar(String izquierda, String operador, String derecho) {

        switch (operador) {
            case "|":
                
                return izquierda + operador + derecho;

            case "+":
                return izquierda + operador + derecho;

            case "*":
                return izquierda + operador + derecho;

            case ".":
                return izquierda + operador + derecho;

            case "?":
                return izquierda + operador + derecho;

        }
        return "";
    }

    boolean operador(String token) {
        return token.equals("|") || token.equals("+") || token.equals("*")
                || token.equals(".") || token.equals("?");
    }

}
