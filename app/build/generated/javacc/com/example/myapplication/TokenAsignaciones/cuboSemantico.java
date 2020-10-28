package com.example.myapplication.TokenAsignaciones;
import java.lang.String;

public class cuboSemantico {
/// Se definen los diferentes operadores y tipos

   public static String types[] = new String[]{"int", "float", "char"};
   public static String operators[] = new String[]{"+", "-", "*", "/", "==", "!=", ">=", "<=", "<", ">"};
    public static String[][][] cubo;

   public static void main(String[] args) {
       cuboSemantico cubo = new cuboSemantico();

   }

    public static void constructor() {


        for (int i = 0; i < types.length; i++) {
            cubo[i][0][0] = types[i];
            for (int j = 0; j < types.length; j++) {
                cubo[i][j][0] = types[j];
                for (int k = 0; k < operators.length; k++) {
                    cubo[i][j][k] = null;
                }
            }
        }
        /// int int
            /// +
        cubo[0][0][0]= "int";
            /// -
        cubo[0][0][1]= "int";
             /// *
        cubo[0][0][2]= "int";
             /// "/"
        cubo[0][0][3]= "int";
            /// "=="
        cubo[0][0][4]="bool";
            /// "!="
        cubo[0][0][5]="bool";
            /// ">="
        cubo[0][0][6]="bool";
            /// "<="
        cubo[0][0][7]="bool";
            /// "<"
        cubo[0][0][8]="bool";
            /// ">"
        cubo[0][0][9]="bool";

        //-------------------------------//

        /// int float
        /// +
        cubo[0][1][0]= "float";
        /// -
        cubo[0][1][1]= "float";
        /// *
        cubo[0][1][2]= "float";
        /// "/"
        cubo[0][1][3]= "float";
        /// "=="
        cubo[0][1][4]="null";
        /// "!="
        cubo[0][1][5]="null";
        /// ">="
        cubo[0][1][6]="bool";
        /// "<="
        cubo[0][1][7]="bool";
        /// "<"
        cubo[0][1][8]="bool";
        /// ">"
        cubo[0][1][9]="bool";

        //-------------------------------//
        /// int char
            /// +
        cubo[0][2][0]= "null";
            /// -
        cubo[0][2][1]= "null";
            /// *
        cubo[0][2][2]= "null";
            /// "/"
        cubo[0][2][3]= "null";
            /// "=="
        cubo[0][2][4]="null";
            /// "!="
        cubo[0][2][5]="null";
            /// ">="
        cubo[0][2][6]="null";
            /// "<="
        cubo[0][2][7]="null";
            /// "<"
        cubo[0][2][8]="null";
            /// ">"
        cubo[0][2][9]="null";

        ///*************************//
        //float int
            ///+
        cubo[1][0][0]= "float";
            /// -
        cubo[1][0][1]= "float";
            /// *
        cubo[1][0][2]= "float";
             /// "/"
        cubo[1][0][3]= "float";
            /// "=="
        cubo[1][0][4]="null";
            /// "!="
        cubo[1][0][5]="null";
            /// ">="
        cubo[1][0][6]="bool";
            /// "<="
        cubo[1][0][7]="bool";
            /// "<"
        cubo[1][0][8]="bool";
            /// ">"
        cubo[1][0][9]="bool";

        ///*************************//
        //float float
             ///+
        cubo[1][1][0]= "float";
            /// -
        cubo[1][1][1]= "float";
            /// *
        cubo[1][1][2]= "float";
            /// "/"
        cubo[1][1][3]= "float";
            /// "=="
        cubo[1][1][4]="bool";
            /// "!="
        cubo[1][1][5]="bool";
            /// ">="
        cubo[1][1][6]="bool";
            /// "<="
        cubo[1][1][7]="bool";
            /// "<"
        cubo[1][1][8]="bool";
            /// ">"
        cubo[1][1][9]="bool";
        ///*************************//
        //float char
             /// +
        cubo[1][2][0]= "null";
             /// -
        cubo[1][2][1]= "null";
             /// *
        cubo[1][2][2]= "null";
            /// "/"
        cubo[1][2][3]= "null";
            /// "=="
        cubo[1][2][4]="null";
            /// "!="
        cubo[1][2][5]="null";
            /// ">="
        cubo[1][2][6]="null";
            /// "<="
        cubo[1][2][7]="null";
            /// "<"
        cubo[1][2][8]="null";
            /// ">"
        cubo[1][2][9]="null";

        ///*************************//
        //char int
            ///+
        cubo[2][0][0]= "null";
            /// -
        cubo[2][0][1]= "null";
             /// *
        cubo[2][0][2]= "null";
            /// "/"
        cubo[2][0][3]= "null";
            /// "=="
        cubo[2][0][4]="null";
            /// "!="
        cubo[2][0][5]="null";
            /// ">="
        cubo[2][0][6]="null";
            /// "<="
        cubo[2][0][7]="null";
            /// "<"
        cubo[2][0][8]="null";
            /// ">"
        cubo[2][0][9]="null";

        ///*************************//
        //char float
            /// +
        cubo[2][1][0]= "null";
            /// -
        cubo[2][1][1]= "null";
            /// *
        cubo[2][1][2]= "null";
            /// "/"
        cubo[2][1][3]= "null";
            /// "=="
        cubo[2][1][4]="null";
            /// "!="
        cubo[2][1][5]="null";
            /// ">="
        cubo[2][1][6]="null";
            /// "<="
        cubo[2][1][7]="null";
            /// "<"
        cubo[2][1][8]="null";
            /// ">"
        cubo[2][1][9]="null";

        ///*************************//
        //char char
        /// +
        cubo[2][2][0]= "null";
        /// -
        cubo[2][2][1]= "null";
        /// *
        cubo[2][2][2]= "null";
        /// "/"
        cubo[2][2][3]= "null";
        /// "=="
        cubo[2][2][4]="bool";
        /// "!="
        cubo[2][2][5]="bool";
        /// ">="
        cubo[2][2][6]="null";
        /// "<="
        cubo[2][2][7]="null";
        /// "<"
        cubo[2][2][8]="null";
        /// ">"
        cubo[2][2][9]="null";
    }
    /// Funcion para aceder al tipo de cualquir renglon
    public String getType(int firstType, int secondType, String op){

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
            default: indice_I = -1;
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
            default: indice_J = -1;
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
            default: indice_K = -1;
                break;
        }

        return this.cubo[indice_I][indice_J][indice_K];
    }
    /// Prints
    public  static void printCombination() {
        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < types.length; j++) {
                for (int k = 0; k < operators.length; k++) {
                    System.out.println("Type 1: "+ types[i]+ " Type 2: "+types[j]+" OP: "+ operators[k]);
                    System.out.println("Produce: "+ cubo[i][j][k]);
                }
            }
        }
    }

    

}


