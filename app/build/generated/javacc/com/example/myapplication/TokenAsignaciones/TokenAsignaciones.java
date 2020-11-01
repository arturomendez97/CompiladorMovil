package com.example.myapplication.TokenAsignaciones;
import com.example.myapplication.parser.Token;
import com.example.myapplication.TokenAsignaciones.cuboSemantico;
import com.example.myapplication.TokenAsignaciones.Quadruple;



import java.io.PrintStream;
import java.util.Hashtable;
import java.lang.String;
import java.util.ArrayList;
import java.util.*;

class Tipo_Dir{
    int tipo;
    int dir;

    public Tipo_Dir(int t, int d)
    {
        tipo = t;
        dir = d;
    }
}

class CustomHash {
    int tipo;
    Hashtable<String, Tipo_Dir> tablaV = new Hashtable();
    public static void main(String[] args) {
        CustomHash myObj = new CustomHash();
    }
}
public class TokenAsignaciones {

    //Variable para validar asignaciones a caracteres(ichr)
    public static int segunda = 0;
    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION TABLAS
    //Tabla que almacenara los tokens declarados globalmente
    private static Hashtable<String, Tipo_Dir> tablaVarsGlobal = new Hashtable();
    //Tabla que almacenara las funciones declaradas
    private static Hashtable<String,CustomHash> tablaFunc = new Hashtable<String,CustomHash>();
    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION PILAS
    private static Stack<Token> pilaOP = new Stack<Token>();
    private static Stack<Token> pilaVP = new Stack<Token>();
    private static cuboSemantico cubo = new cuboSemantico();

    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION ARRAY CUADRUPLOS
    private static ArrayList<Quadruple> cuadruplos = new ArrayList<Quadruple>();


    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION DIRECCIONES DE MEMORIA

    // I = INT
    // F = FLOAT
    // C = CHAR
    // B = BOOL

    // G = GLOBAL
    // L = LOCAL
    // T = TEMPORAL
    // C = CTE

    private static int contGI = 1000;
    private static int contGF = 2000;
    private static int contGC = 3000;
    private static int contLI = 4000;
    private static int contLF = 5000;
    private static int contLC = 6000;
    private static int contTI = 7000;
    private static int contTF = 8000;
    private static int contTC = 9000;
    private static int contTB = 10000;
    private static int contCI = 11000;
    private static int contCF = 12000;
    private static int contCC = 13000;


    ////////////////////////////////////////////////////////////////////////////////////////////////        CUBO SEMANTICO

    public static int getCuboType(int arg1, int arg2, String op)
    {
        return cuboSemantico.getType(arg1, arg2, op);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////        QUADRUPLOS

    public static void meterCuadruplo(Quadruple quad)
    {
        cuadruplos.add(quad);
    }

    public static void printCuadruplos()
    {
        Quadruple aux;
        for (int j = 0; j < cuadruplos.size(); j++) {
            aux = cuadruplos.get(j);
            aux.print(j);
        }

    }

    public static void emptyCuadruplos()
    {
        cuadruplos.clear();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////        CONTADORES


    //Este se usa para crear variables temporales. (Se usa dentro de expresiones etc...)
    public static int getContTemporal(int tipo)
    {
        int temp;
        switch (tipo)
        {
            case 4:
                temp = contTI;
                contTI++;
                return temp;
            case 5:
                temp = contTF;
                contTF++;
                return temp;
            case 6:
                temp = contTC;
                contTC++;
                return temp;
            case 47:
                temp = contTB;
                contTB++;
                return temp;
            default: return -1;
        }
    }

    //Este se usa para crear variables locales, se usa en el vars local.
    public static int getContLocal(int tipo)
    {
        int temp;
        switch (tipo)
        {
            case 4:
                temp = contLI;
                contLI++;
                return temp;
            case 5:
                temp = contLF;
                contLF++;
                return temp;
            case 6:
                temp = contLC;
                contLC++;
                return temp;
            default: return -1;
        }
    }

    //Este se usa para crear variables globales, se usa en el vars global.
    public static int getContGlobal(int tipo)
    {
        int temp;
        switch (tipo)
        {
            case 4:
                temp = contGI;
                contGI++;
                return temp;
            case 5:
                temp = contGF;
                contGF++;
                return temp;
            case 6:
                temp = contGC;
                contGC++;
                return temp;
            default: return -1;
        }
    }

    //Este se usa para guardar constantes
    public static int getContConst(int tipo)
    {
        int temp;
        switch (tipo)
        {
            case 38:
                temp = contCI;
                contCI++;
                return temp;
            case 39:
                temp = contCF;
                contCF++;
                return temp;
            case 41:
                temp = contCC;
                contCC++;
                return temp;
            default: return -1;
        }
    }

    public static void resetContsGlobal()
    {
        contGI = 1000;
        contGF = 2000;
        contGC = 3000;
    }

    public static void resetContsLocal()
    {
        contLI = 4000;
        contLF = 5000;
        contLC = 6000;
    }

    public static void resetContsTemporal()
    {
        contTI = 7000;
        contTF = 8000;
        contTC = 9000;
        contTB = 10000;
    }

    public static void resetContsConstantes()
    {
        contCI = 11000;
        contCF = 12000;
        contCC = 13000;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////        PILAOP

    public static void pushPilaOP(Token token)
    {
        pilaOP.push(token);
    }

    public static Token popPilaOP()
    {
        return pilaOP.pop();
    }

    public static Stack returnPilaOP()
    {
        return pilaOP;
    }

    public static Boolean checkPilaOP(String token)
    {
        try
        {
            Token tokenPila = pilaOP.peek();
            //System.out.println("1: " + tokenPila.image);
            //System.out.println("2: " + token);


            if (tokenPila.image == token)
            {
                return true;            }
            else
            {
                return false;
            }
        }
        catch( Exception e)
        {
            return false;
        }
    }

    public static void emptyPilaOP()
    {
        pilaOP.clear();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////        PILAVP

    public static void pushPilaVP(Token token)
    {
        pilaVP.push(token);
    }

    public static Token popPilaVP()
    {
        return pilaVP.pop();
    }

    public static Stack returnPilaVP()
    {
        return pilaVP;
    }

    public static Token peekPilaVP()
    {
        return pilaVP.peek();
    }

    public static void emptyPilaVP()
    {
        pilaVP.clear();
    }


    public static Hashtable GetTabla()
    {
        return tablaVarsGlobal;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////        TABLAS

    public static int getDir(Token checkTok, Token nombreFuncion)
    {
        CustomHash tabla;
        Tipo_Dir aux;

        try
        {
            aux = tablaVarsGlobal.get(checkTok.image);
            return aux.dir;
        }
        catch (Exception e)
        {
            try
            {
                tabla = tablaFunc.get(nombreFuncion.image);
                try
                {
                    //Intenta obtener el token a verificar(checkTok) de la tabla de los tokens
                    aux = tabla.tablaV.get(checkTok.image);
                    return aux.dir;
                }
                catch(Exception f)
                {
                    //Si no lo puede obtener, manda el error
                    return -1;
                }
            }
            catch(Exception g)
            {
                return -1;
            }
        }
    }


    //variable		//tipoDato
    public static String InsertarSimbolo(Token identificador, int tipo, Token nombreFuncion)
    {
        //En este metodo se agrega a la tabla de tokens el identificador que esta siendo declarado junto con su tipo de dato y su dirección
        try
        {
            CustomHash tabla;
            Tipo_Dir objeto = new Tipo_Dir(tipo, getContLocal(tipo));
            tabla = (CustomHash) tablaFunc.get(nombreFuncion.image);
            tabla.tablaV.put(identificador.image, objeto);
            tablaFunc.put(nombreFuncion.image,tabla);
            return " ";

        }
        catch(Exception e)
        {
            return "Error: La funcion " + nombreFuncion.image + " No ha sido declarada \r\nLinea: ";
        }
    }

    public static void InsertarSimboloGlobal(Token identificador, int tipo)
    {
        //En este metodo se agrega a la tabla de tokens el identificador que esta siendo declarado junto con su tipo de dato y direccion
        Tipo_Dir objeto = new Tipo_Dir(tipo, getContGlobal(tipo));
        tablaVarsGlobal.put(identificador.image, objeto);

    }


    //Este metodo regresa el tipo int del token solicitado.
    public static int getType(Token checkTok, Token nombreFuncion)
    {
        CustomHash tabla;
        Tipo_Dir aux;

        try
        {
            aux = tablaVarsGlobal.get(checkTok.image);
            return aux.tipo;
        }
        catch (Exception e)
        {
            try
            {
                tabla = tablaFunc.get(nombreFuncion.image);
                try
                {
                    //Intenta obtener el token a verificar(checkTok) de la tabla de los tokens
                    aux = tabla.tablaV.get(checkTok.image);
                    return aux.tipo;
                }
                catch(Exception f)
                {
                    //Si no lo puede obtener, manda el error
                    return -1;
                }
            }
            catch(Exception g)
            {
                return -1;
            }
        }

    }


    public static String InsertarFuncion(Token nombreFuncion, int tipo)
    {
        try
        {
            CustomHash tabla = new CustomHash();
            tabla.tipo=tipo;
            tablaFunc.put(nombreFuncion.image,tabla);
            return " ";
        }
        catch(Exception e)
        {
            return "Error: La funcion " + nombreFuncion.image + " No se pudo declarar \r\nLinea: ";
        }
    }
    public static void InsertarMain(Token identificador)
    {
        //En este metodo se agrega a la tabla de funciones el identificador que esta siendo declarado junto con su tipo de dato
        CustomHash tabla = new CustomHash();
        tabla.tipo=7;
        tablaFunc.put(identificador.image,tabla);
    }


    /*Metodo que verifica si un identificador ha sido declarado,
        ej cuando se declaran las asignaciones: i++, i--)*/
    public static String checkVariable(Token checkTok, Token nombreFuncion)
    {
        CustomHash tabla;
        Tipo_Dir aux;

        try
        {
            aux = tablaVarsGlobal.get(checkTok.image);
            int tipoToken = aux.tipo;
            return " ";
        }
        catch (Exception e)
        {
            try
            {
                tabla = tablaFunc.get(nombreFuncion.image);
                try
                {
                    //Intenta obtener el token a verificar(checkTok) de la tabla de los tokens
                    aux = tabla.tablaV.get(checkTok.image);
                    int token = aux.tipo;
                    return " ";
                }
                catch(Exception f)
                {
                    //Si no lo puede obtener, manda el error
                    return "Error: El identificador " + checkTok.image + " No ha sido declarado \r\nLinea: " + checkTok.beginLine;
                }
            }
            catch(Exception g)
            {
                //Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
                return "Error: La funcion  " + nombreFuncion.image + " No ha sido declarada \r\nLinea: ";
            }
        }
    }

    /*Metodo que verifica si una funcion ha sido declarada
        ej cuando hace una llamada*/
    public static String checkFuncion(Token nombreFuncion)
    {

        CustomHash tabla = tablaFunc.get(nombreFuncion.image);

        if (tabla != null){
            return " ";
        }
        else{
            //Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
            return "Error: La funcion " + nombreFuncion.image + " No ha sido declarada \r\nLinea: ";
        }

        /*try
        {
            CustomHash tabla = tablaFunc.get(nombreFuncion.image);
            return " ";
        }
        catch(Exception e)
        {
            //Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
            return "Error: La funcion " + nombreFuncion.image + " No ha sido declarada \r\nLinea: ";
        }*/
    }
}
