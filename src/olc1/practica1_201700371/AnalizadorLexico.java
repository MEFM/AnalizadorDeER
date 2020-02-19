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
public class AnalizadorLexico {

    ArrayList<Token> tken = new ArrayList<>();
    ArrayList<Error> error = new ArrayList<>();
    LinkedList<Expresiones> expresiones = new LinkedList<>();
    ArrayList<Expresiones> cadEr = new ArrayList<>();
    ArrayList<MacroConjunto> macro = new ArrayList<>();

    int numero_token = 0;

    boolean isIn(char caracter) {
        char[] simbolo = {
            '!', '"', '#', '%',
            '&', '\'', '(', ')', '*',
            '+', ',', '-', '.', '/', ':', ';',
            '<', '=', '>', '?', '@',
            '[', '\\', ']', '^',
            '_', '`', '{', '|', '}'
        };
        for (int i = 0; i < simbolo.length; i++) {
            if (caracter == simbolo[i]) {
                return true;
            }
        }
        return false;
    }

    boolean exR(char caracer) {
        char[] simbolo = {'.', '|', '*', '+'};

        for (int i = 0; i < simbolo.length; i++) {
            if (caracer == simbolo[i]) {

                return true;
            }
        }
        return false;
    }

    void analisis(String codigo) {
        int estado = 0;
        String lexema = "";

        String[] linea = codigo.split("\n");

        for (int i = 0; i < linea.length; i++) {
            char[] caracter = linea[i].toCharArray();
            for (int j = 0; j < caracter.length; j++) {
                switch (estado) {
                    case 0:
                        if (Character.isUpperCase(caracter[j]) || Character.isLowerCase(caracter[j])) {
                            //Estado 1
                            estado = 1;
                            lexema += caracter[j];
                        } else if (caracter[j] == '~') {
                            //Estado 11, macros
                            estado = 11;

                        } else if (Character.isDigit(caracter[j])) {
                            //Estado 2
                            estado = 2;
                            lexema += caracter[j];
                        } else if (isIn(caracter[j])) {
                            if (caracter[j] == '"'
                                    && (caracter[j + 1] == ',' || caracter[j + 1] == '~'
                                    || caracter[j - 1] == ',' || caracter[j - 1] == '~')) {
                                //Case 3 para recoleccion de simbolos
                                lexema += caracter[j];
                                estado = 3;
                            } else if (caracter[j] == '<' && caracter[j + 1] != '!') {
                                //Case 3 para recoleccion de simbolos
                                lexema += caracter[j];
                                estado = 3;
                            } else if (caracter[j] == '%' && caracter[j + 1] != '%') {
                                //Case 3 para recoleccion de simbolos
                                lexema += caracter[j];
                                estado = 3;
                            } else if (caracter[j] == '-' && caracter[j + 1] != '>') {
                                //Case 3 para recoleccion de simbolos
                                lexema += caracter[j];
                                estado = 3;
                            } else if (exR(caracter[j]) == true) {
                                //Case 3 para recoleccion de simbolos
                                lexema += caracter[j];
                                estado = 3;

                            } else {
                                if (caracter[j] == '"') {
                                    //Case 6 para cadenas
                                    lexema += caracter[j];
                                    estado = 6;
                                } else if (caracter[j] == '<') {
                                    //Case 5 para comentario multinea
                                    estado = 5;
                                } else if (caracter[j] == '/' && caracter[j + 1] == '/') {
                                    //Case 7 para comentario de linea
                                    estado = 7;
                                } else if (caracter[j] == '-') {
                                    //Case 4 para asignaciones
                                    estado = 4;
                                    lexema += caracter[j];
                                } else if (exR(caracter[j])) {
                                    //Case 8 para expresiones regulares
                                    lexema += caracter[j];
                                    estado = 8;
                                } else if (caracter[j] == '%') {
                                    //Case 10 para separador de expresiones
                                    lexema += caracter[j];
                                    estado = 10;
                                } else {
                                    //Enviarlas todas a Case 3 para recoleccion de simbolos
                                    estado = 3;
                                    lexema += caracter[j];
                                }

                            }

                        } else {
                            //Estado de error :( rip

                        }
                        break;
                    case 1:
                        if (Character.isUpperCase(caracter[j]) || Character.isLowerCase(caracter[j]) || Character.isDigit(caracter[j])) {
                            estado = 1;
                            lexema += caracter[j];
                        } else {
                            token(lexema, i, j);
                            estado = 0;
                            lexema = "";
                            j--;
                        }
                        break;
                    case 2:
                        if (Character.isDigit(caracter[j])) {
                            estado = 2;
                            lexema += caracter[j];
                        } else {
                            token(lexema, i, j);
                            estado = 0;
                            lexema = "";
                            j--;
                        }
                        break;
                    case 3:
                        /*
                        Sirve para la macros de simbolos
                         */
                        token(lexema, i, j);
                        estado = 0;
                        lexema = "";
                        j--;
                        break;
                    case 4:
                        if (caracter[j] == '>') {
                            lexema += caracter[j];
                            token(lexema, i, j);
                            estado = 0;
                            lexema = "";

                        }
                        break;
                    case 5:
                        if (caracter[j] == '!') {
                            estado = 9;
                        }
                        break;
                    case 6:
                        if (caracter[j] == '"') {
                            estado = 0;
                            token(lexema, i, j);
                            lexema = "";
                            //j--;
                        } else {
                            estado = 6;
                            lexema += caracter[j];
                        }
                        break;
                    case 7:

                        if (caracter.length != (j + 1)) {
                            lexema += "";
                            estado = 7;
                        } else {
                            lexema = "";
                            estado = 0;
                            j--;
                        }

                        break;
                    case 8:
                        estado = 0;
                        token(lexema, i, j);
                        lexema = "";
                        j--;
                        break;
                    case 9:
                        if (caracter[j] == '>') {
                            estado = 0;
                            lexema = "";
                        } else {
                            estado = 9;
                        }
                        break;
                    case 10:
                        if (caracter[j] != '%') {
                            lexema += caracter[j];
                            estado = 10;
                        } else {
                            estado = 0;
                            token(lexema, i, j);
                            lexema = "";
                            j--;
                        }
                        break;
                    case 11:
                        estado = 0;
                        token(lexema, i, j);
                        lexema = "";
                        j--;
                        break;

                }
            }

        }
        Token tkn = new Token();
        tkn.tabla_tokens(tken);
        creadorConjuntos();
    }

    void token(String lexema, int fila, int columna) {
        switch (lexema) {
            case "\"":
                //Comilla doble
                tken.add(new Token(numero_token++, 1, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "'":
                tken.add(new Token(numero_token++, 2, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "CONJ":
                tken.add(new Token(numero_token++, 3, fila,
                        columna, lexema, "Palabra Reservada"));
                break;
            case "|":
                tken.add(new Token(numero_token++, 4, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "{":
                tken.add(new Token(numero_token++, 5, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "}":
                tken.add(new Token(numero_token++, 6, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "->":
                tken.add(new Token(numero_token++, 7, fila,
                        columna, lexema, "Asignacion"));
                break;
            case "!":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "%":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "#":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "&":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "(":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case ")":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "*":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "+":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case ",":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "-":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case ".":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "/":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case ":":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case ";":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "<":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "=":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case ">":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "?":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "@":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "[":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "\\":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "]":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "^":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "_":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;
            case "`":
                tken.add(new Token(numero_token++, 8, fila,
                        columna, lexema, "Simbolo"));
                break;

            default:
                if (lexema.length() > 1 && lexema.startsWith("\"")) {
                    tken.add(new Token(numero_token++, 8, fila,
                            columna, lexema.replace("\"", ""), "Cadena"));
                } else if ((lexema.startsWith("A") || lexema.startsWith("B") || lexema.startsWith("C") || lexema.startsWith("C") || lexema.startsWith("D") || lexema.startsWith("E") || lexema.startsWith("F") || lexema.startsWith("G")
                        || lexema.startsWith("H") || lexema.startsWith("I") || lexema.startsWith("J") || lexema.startsWith("K") || lexema.startsWith("L") || lexema.startsWith("M") || lexema.startsWith("N") || lexema.startsWith("Ñ")
                        || lexema.startsWith("O") || lexema.startsWith("P") || lexema.startsWith("Q") || lexema.startsWith("R") || lexema.startsWith("S") || lexema.startsWith("T") || lexema.startsWith("U") || lexema.startsWith("V")
                        || lexema.startsWith("W") || lexema.startsWith("X") || lexema.startsWith("Y") || lexema.startsWith("Z") || (lexema.startsWith("a") || lexema.startsWith("b") || lexema.startsWith("c") || lexema.startsWith("d")
                        || lexema.startsWith("e") || lexema.startsWith("f") || lexema.startsWith("g") || lexema.startsWith("h") || lexema.startsWith("i") || lexema.startsWith("j") || lexema.startsWith("k") || lexema.startsWith("l")
                        || lexema.startsWith("m") || lexema.startsWith("n") || lexema.startsWith("ñ") || lexema.startsWith("o") || lexema.startsWith("p") || lexema.startsWith("q") || lexema.startsWith("r") || lexema.startsWith("s")
                        || lexema.startsWith("t") || lexema.startsWith("u") || lexema.startsWith("v") || lexema.startsWith("w") || lexema.startsWith("x") || lexema.startsWith("y") || lexema.startsWith("z"))) && !(lexema.contains("+")) && !(lexema.contains("*"))) {

                    if ((lexema.contains("A") || lexema.contains("B") || lexema.contains("C") || lexema.contains("C") || lexema.contains("D") || lexema.contains("E") || lexema.contains("F") || lexema.contains("G")
                            || lexema.contains("H") || lexema.contains("I") || lexema.contains("J") || lexema.contains("K") || lexema.contains("L") || lexema.contains("M") || lexema.contains("N") || lexema.contains("Ñ")
                            || lexema.contains("O") || lexema.contains("P") || lexema.contains("Q") || lexema.contains("R") || lexema.contains("S") || lexema.contains("T") || lexema.contains("U") || lexema.contains("V")
                            || lexema.contains("W") || lexema.contains("X") || lexema.contains("Y") || lexema.contains("Z") || (lexema.contains("a") || lexema.contains("b") || lexema.contains("c") || lexema.contains("d")
                            || lexema.contains("e") || lexema.contains("f") || lexema.contains("g") || lexema.contains("h") || lexema.contains("i") || lexema.contains("j") || lexema.contains("k") || lexema.contains("l")
                            || lexema.contains("m") || lexema.contains("n") || lexema.contains("ñ") || lexema.contains("o") || lexema.contains("p") || lexema.contains("q") || lexema.contains("r") || lexema.contains("s")
                            || lexema.contains("t") || lexema.contains("u") || lexema.contains("v") || lexema.contains("w") || lexema.contains("x") || lexema.startsWith("y") || lexema.contains("z"))) && !(lexema.contains("+")) && !(lexema.contains("*")) || lexema.contains("0") || lexema.contains("1") || lexema.contains("2")
                            || lexema.contains("3") || lexema.contains("4") || lexema.contains("5")
                            || lexema.contains("6") || lexema.contains("7") || lexema.contains("8")
                            || lexema.contains("9") || lexema.contains("_")) {

                        tken.add(new Token(numero_token++, 8, fila,
                                columna, lexema, "Id"));
                    } else {
                        tken.add(new Token(numero_token++, 8, fila,
                                columna, lexema, "Id"));
                    }
                }
                break;
        }
    }

    void creadorConjuntos() {
        for (int i = 0; i < tken.size(); i++) {

            ArrayList<Token> palabras = new ArrayList<>();

            if (tken.get(i).getLexema().equals("CONJ") && tken.get(i + 2).getTipo().equals("Id")) {

                int j = i + 4;
                String macros = "";
                String nombreMacros = tken.get(i + 2).getTipo();

                System.out.println("----Previo a captar macros----");
                i = i + 3;
                while (!tken.get(i).getLexema().equals(";")) {
                    System.out.println("Captando macros");
                    System.out.println(tken.get(i).getLexema());
                    if (tken.get(i).getLexema().equals("->")) {

                    } else {
                        macros += tken.get(i).getLexema();
                    }

                    i++;
                }
                System.out.println("-----Analisis Macros Final-----");
                System.out.println(macros);
                System.out.println("");
                macro.add(new MacroConjunto(nombreMacros, macros));
            } else if (tken.get(i).getTipo().equals("Id") && (tken.get(i + 1).getLexema().equals("->") && !tken.get(i + 2).getTipo().equals("Cadena"))) {
                int j = i + 2;
                String id = tken.get(i).getLexema();
                String expresion = "";

                // Desde aca se insertan para el arbol y siguientes
                System.out.println("Previo a captar ER");
                while (!tken.get(j).getLexema().equals(";")) {
                    expresion += tken.get(j).getLexema();
                    if (tken.get(j).getLexema().equals("{") || tken.get(j).getLexema().equals("}")) {

                    } else {
                        palabras.add(tken.get(j));
                    }

                    j++;
                }

                expresiones.add(new Expresiones(id, expresion, palabras));
                InverArbol arbol = new InverArbol(palabras);
                System.out.println("ID" + id);
                System.out.println("Infijo :( " + arbol.rpn() + " A LA NOMBRE");
            } else if (tken.get(i).getTipo().equals("Id") && (tken.get(i + 1).getLexema().equals("->") || tken.get(i + 1).getLexema().equals(":"))) {

                boolean exitente = true;
                int j = 0;
                while (exitente) {
                    if (expresiones.get(j).getId().equals(tken.get(i).getLexema())) {
                        cadEr.add(new Expresiones(expresiones.get(j).getId(),
                                expresiones.get(j).getER(), palabras, tken.get(i + 2).getLexema()));
                        exitente = false;
                    }
                    j++;
                }

            }
        }
    }

    void crearArbolER(String id) {

    }
}
