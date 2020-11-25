////////////////////////////////////////////////////////////////////////////////////////////////////
// Proyecto de Clase de Compiladores
// Proyecto Especial de Diseño de CompiladoresCOVID19AD20: En Pareja - Lenguaje Par++
// Elaborado por:
// Jorge Arturo Méndez Vargas - A01176369
// Jorge Adrían Ramos Barrena - A01176234
////////////////////////////////////////////////////////////////////////////////////////////////////
// CUBO SEMANTICO (Archivo eauxiliar)
// ¿Que hace? - Valida operaciones entre dos argumentos y saca el tipo del temporal resultante.
// ¿Que parametros recibe? - dos argumentos y un operador.
// ¿Que genera como salida? - El tipo del temporal resultante y si la expresión es válida.
// Modulos más importantes que hacen uso de este archivo. - TokenAsignaciones y Comp.jj


package com.example.myapplication.TokenAsignaciones;
import java.lang.String;

public class cuboSemantico {
/// Se definen los diferentes operadores y tipos

    //public static String types[] = new String[]{4, 5, "char"};
    //public static String operators[] = new String[]{"+", "-", "*", "/", "==", "!=", ">=", "<=", "<", ">"};
    public static int[][][] cubo = new int[3][3][11];



    public cuboSemantico() {



        /// int int
        /// +
        cubo[0][0][0]= 4;
        /// -
        cubo[0][0][1]= 4;
        /// *
        cubo[0][0][2]= 4;
        /// "/"
        cubo[0][0][3]= 4;
        /// "=="
        cubo[0][0][4]=47;
        /// "!="
        cubo[0][0][5]=47;
        /// ">="
        cubo[0][0][6]=47;
        /// "<="
        cubo[0][0][7]=47;
        /// "<"
        cubo[0][0][8]=47;
        /// ">"
        cubo[0][0][9]=47;
        /// "="
        cubo[0][0][10]=1;

        //-------------------------------//

        /// int float
        /// +
        cubo[0][1][0]= 5;
        /// -
        cubo[0][1][1]= 5;
        /// *
        cubo[0][1][2]= 5;
        /// "/"
        cubo[0][1][3]= 5;
        /// "=="
        cubo[0][1][4]=0;
        /// "!="
        cubo[0][1][5]=0;
        /// ">="
        cubo[0][1][6]=47;
        /// "<="
        cubo[0][1][7]=47;
        /// "<"
        cubo[0][1][8]=47;
        /// ">"
        cubo[0][1][9]=47;
        /// "="
        cubo[0][1][10]=0;

        //-------------------------------//
        /// int char
        /// +
        cubo[0][2][0]= 0;
        /// -
        cubo[0][2][1]= 0;
        /// *
        cubo[0][2][2]= 0;
        /// "/"
        cubo[0][2][3]= 0;
        /// "=="
        cubo[0][2][4]=0;
        /// "!="
        cubo[0][2][5]=0;
        /// ">="
        cubo[0][2][6]=0;
        /// "<="
        cubo[0][2][7]=0;
        /// "<"
        cubo[0][2][8]=0;
        /// ">"
        cubo[0][2][9]=0;
        /// "="
        cubo[0][2][10]=0;

        ///*************************//
        //float int
        ///+
        cubo[1][0][0]= 5;
        /// -
        cubo[1][0][1]= 5;
        /// *
        cubo[1][0][2]= 5;
        /// "/"
        cubo[1][0][3]= 5;
        /// "=="
        cubo[1][0][4]=0;
        /// "!="
        cubo[1][0][5]=0;
        /// ">="
        cubo[1][0][6]=47;
        /// "<="
        cubo[1][0][7]=47;
        /// "<"
        cubo[1][0][8]=47;
        /// ">"
        cubo[1][0][9]=47;
        /// "="
        cubo[1][0][10]=1;

        ///*************************//
        //float float
        ///+
        cubo[1][1][0]= 5;
        /// -
        cubo[1][1][1]= 5;
        /// *
        cubo[1][1][2]= 5;
        /// "/"
        cubo[1][1][3]= 5;
        /// "=="
        cubo[1][1][4]=47;
        /// "!="
        cubo[1][1][5]=47;
        /// ">="
        cubo[1][1][6]=47;
        /// "<="
        cubo[1][1][7]=47;
        /// "<"
        cubo[1][1][8]=47;
        /// ">"
        cubo[1][1][9]=47;
        /// "="
        cubo[1][1][10]=1;
        ///*************************//
        //float char
        /// +
        cubo[1][2][0]= 0;
        /// -
        cubo[1][2][1]= 0;
        /// *
        cubo[1][2][2]= 0;
        /// "/"
        cubo[1][2][3]= 0;
        /// "=="
        cubo[1][2][4]=0;
        /// "!="
        cubo[1][2][5]=0;
        /// ">="
        cubo[1][2][6]=0;
        /// "<="
        cubo[1][2][7]=0;
        /// "<"
        cubo[1][2][8]=0;
        /// ">"
        cubo[1][2][9]=0;
        /// "="
        cubo[1][2][10]=0;
        ///*************************//
        //char int
        ///+
        cubo[2][0][0]= 0;
        /// -
        cubo[2][0][1]= 0;
        /// *
        cubo[2][0][2]= 0;
        /// "/"
        cubo[2][0][3]= 0;
        /// "=="
        cubo[2][0][4]=0;
        /// "!="
        cubo[2][0][5]=0;
        /// ">="
        cubo[2][0][6]=0;
        /// "<="
        cubo[2][0][7]=0;
        /// "<"
        cubo[2][0][8]=0;
        /// ">"
        cubo[2][0][9]=0;
        /// "="
        cubo[2][0][10]=0;

        ///*************************//
        //char float
        /// +
        cubo[2][1][0]= 0;
        /// -
        cubo[2][1][1]= 0;
        /// *
        cubo[2][1][2]= 0;
        /// "/"
        cubo[2][1][3]= 0;
        /// "=="
        cubo[2][1][4]=0;
        /// "!="
        cubo[2][1][5]=0;
        /// ">="
        cubo[2][1][6]=0;
        /// "<="
        cubo[2][1][7]=0;
        /// "<"
        cubo[2][1][8]=0;
        /// ">"
        cubo[2][1][9]=0;
        /// "="
        cubo[2][1][10]=0;

        ///*************************//
        //char char
        /// +
        cubo[2][2][0]= 0;
        /// -
        cubo[2][2][1]= 0;
        /// *
        cubo[2][2][2]= 0;
        /// "/"
        cubo[2][2][3]= 0;
        /// "=="
        cubo[2][2][4]=47;
        /// "!="
        cubo[2][2][5]=47;
        /// ">="
        cubo[2][2][6]=0;
        /// "<="
        cubo[2][2][7]=0;
        /// "<"
        cubo[2][2][8]=0;
        /// ">"
        cubo[2][2][9]=0;
        /// "="
        cubo[2][2][10]=1;
    }
    /// Funcion para aceder al tipo de cualquir renglon
    public static int getType(Integer firstType, Integer secondType, String op){

        int indice_I;
        int indice_J;
        int indice_K;

        switch (firstType)
        {
            case 4:  indice_I = 0;
                break;
            case 5:  indice_I = 1;
                break;
            case 6:  indice_I = 2;
                break;
            case 38: indice_I = 0;
                break;
            case 39: indice_I = 1;
                break;
            case 41: indice_I = 2;
                break;
            default: indice_I = 0;
                break;
        }

        switch (secondType)
        {
            case 4:  indice_J = 0;
                break;
            case 5:  indice_J = 1;
                break;
            case 6:  indice_J = 2;
                break;
            case 38: indice_J = 0;
                break;
            case 39: indice_J = 1;
                break;
            case 41: indice_J = 2;
                break;
            default: indice_J = 2;
                break;
        }

        switch (op)
        {
            case "+":  indice_K = 0;
                break;
            case "-":  indice_K = 1;
                break;
            case "*":  indice_K = 2;
                break;
            case "/":  indice_K = 3;
                break;
            case "==":  indice_K = 4;
                break;
            case "!=":  indice_K = 5;
                break;
            case ">=":  indice_K = 6;
                break;
            case "<=":  indice_K = 7;
                break;
            case "<":  indice_K = 8;
                break;
            case ">":  indice_K = 9;
                break;
            case "=":  indice_K = 10;
                break;
            default: indice_K = 0;
                break;
        }
        //System.out.println("I: " + indice_I + "J: " + indice_J +  "K: " + indice_K);
        //System.out.println("resultado: " + cubo[indice_I][indice_J][indice_K]);

        return cubo[indice_I][indice_J][indice_K];
    }
}


