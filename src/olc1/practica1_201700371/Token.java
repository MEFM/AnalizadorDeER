/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olc1.practica1_201700371;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author mynor
 */
public class Token {

    int numero_token, token;
    String lexema, tipo;
    int fila, columna;
    
    Token(){}
    Token(int numero_token, int token, int fila, int columna, String lexema, String tipo) {
        this.numero_token = numero_token;
        this.token = token;
        this.fila = fila;
        this.columna = columna;
        this.lexema = lexema;
        this.tipo = tipo;
    }

    public int getNumero_token() {
        return numero_token;
    }

    public void setNumero_token(int numero_token) {
        this.numero_token = numero_token;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    

    public void abrirarchivo(String archivo) {

        try {

            File objetofile = new File(archivo);
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }

    }

    public void tabla_tokens(ArrayList<Token> lista_toks) {
        FileWriter filewriter = null;
        PrintWriter printw = null;

        try {
            filewriter = new FileWriter("tabla_tokens.html");
            printw = new PrintWriter(filewriter);

            printw.println("<html lang=\"en\">");
            printw.println("<head><meta charset= \"utf-8\"><title>TABLA DE LEXEMAS, ANALIZADOR LEXICO</title></head>");
            printw.println("<body bgcolor=\"#A9CCE3\">");

            printw.println("<h1>Tabla de lexemas</h1>");
            printw.println("Cantidad de lexemas " + lista_toks.size() + "</br>");

            printw.println("<table border=\"1\""
                    + "cellpadding=\"10\">");
            printw.println("<tr>");
            printw.println("<th scope=\"col\"> No. </th>");
            printw.println("<th scope=\"col\"> Token </th>");
            printw.println("<th scope=\"col\"> Lexema </th>");
            printw.println("<th scope=\"col\"> Tipo </th>");
            printw.println("<th scope=\"col\"> Fila </th>");
            printw.println("<th scope=\"col\"> Columna </th>");
            printw.println("</tr>");

            for (Token i : lista_toks) {
                printw.println("<tr>");
                printw.println("<td>|" + i.getNumero_token() + "</td>");
                printw.println("<td>|" + i.getToken() + "</td>");
                printw.println("<td>|" + i.getLexema() + "</td>");
                printw.println("<td>|" + i.getTipo() + "</td>");
                printw.println("<td>|" + i.getFila() + "</td>");
                printw.println("<td>|" + i.getColumna() + "</td>");
                printw.println("</tr>");
            }

            printw.println("</table>");

            printw.println("</body>");
            printw.println("</html>");
            printw.close();
            abrirarchivo("tabla_tokens.html");
           // graf.transformar(lista_toks);

        } catch (Exception f) {

        }
    }

}
