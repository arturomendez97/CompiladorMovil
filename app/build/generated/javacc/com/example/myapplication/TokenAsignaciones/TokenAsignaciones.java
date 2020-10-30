package com.example.myapplication.TokenAsignaciones;
import com.example.myapplication.parser.Token;
import com.example.myapplication.TokenAsignaciones.cuboSemantico;
import com.example.myapplication.TokenAsignaciones.Quadruple;



import java.io.PrintStream;
import java.util.Hashtable;
import java.lang.String;
import java.util.ArrayList;
import java.util.*;



class CustomHash {
    int tipo;
    Hashtable tablaV = new Hashtable();
    public static void main(String[] args) {
        CustomHash myObj = new CustomHash();
    }
}
public class TokenAsignaciones {

    //Variable para validar asignaciones a caracteres(ichr)
    public static int segunda = 0;
    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION TABLAS
    //Tabla que almacenara los tokens declarados globalmente
    private static Hashtable<String, Integer> tablaVarsGlobal = new Hashtable();
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


    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION COSAS DEL CHECKASIGN
    //Listas que guardaran los tipos compatibles de las variables
    private static ArrayList<Integer> intComp = new ArrayList();
    private static ArrayList<Integer> decComp = new ArrayList();
    //private static ArrayList<Integer> strComp = new ArrayList();
    private static ArrayList<Integer> chrComp = new ArrayList();

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
            case 4:
                temp = contCI;
                contCI++;
                return temp;
            case 5:
                temp = contCF;
                contCF++;
                return temp;
            case 6:
                temp = contCC;
                contCC++;
                return temp;
            default: return -1;
        }
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


    //variable		//tipoDato
    public static String InsertarSimbolo(Token identificador, int tipo, Token nombreFuncion)
    {
        //En este metodo se agrega a la tabla de tokens el identificador que esta siendo declarado junto con su tipo de dato
        try
        {
            CustomHash tabla;
            tabla = (CustomHash) tablaFunc.get(nombreFuncion.image);
            tabla.tablaV.put(identificador.image, tipo);
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
        //En este metodo se agrega a la tabla de tokens el identificador que esta siendo declarado junto con su tipo de dato
        tablaVarsGlobal.put(identificador.image, tipo);

    }


    //Este metodo regresa el tipo int del token solicitado.
    public static int getType(Token checkTok, Token nombreFuncion)
    {
        CustomHash tabla;

        try
        {
            return tablaVarsGlobal.get(checkTok.image);
        }
        catch (Exception e)
        {
            try
            {
                tabla = tablaFunc.get(nombreFuncion.image);
                try
                {
                    //Intenta obtener el token a verificar(checkTok) de la tabla de los tokens
                    return (Integer)tabla.tablaV.get(checkTok.image);
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


    public static void SetTables()
    {
		/*En este metodo se inicializan las tablas, las cuales almacenaran los tipo de datos compatibles con:
		 entero = intComp
		 decimal = decComp
		 //cadena = strComp
		 caracter = chrComp
		*/
        intComp.add(4);
        intComp.add(38);

        decComp.add(4);
        decComp.add(5);
        decComp.add(38);
        decComp.add(39);

        chrComp.add(6);
        chrComp.add(41);

        //strComp.add(47);
        //strComp.add(51);
    }

    public static String checkAsing(Token TokenIzq, Token TokenAsig, Token nombreFuncion)
    {
        //variables en las cuales se almacenara el tipo de dato del identificador y de las asignaciones (n1(tipoIdent1) = 2(tipoIdent2) + 3(tipoIdent2))
        int tipoIdent1;
        int tipoIdent2;
        CustomHash tabla;
							/* De la tabla obtenemos el tipo de dato del identificador
								asi como, si el token enviado es diferente a algun tipo que no se declara como los numeros(38), los decimales(39),
								caracteres(41)
								entonces tipoIdent1 = tipo_de_dato, ya que TokenAsig es un dato*/
        if(TokenIzq.kind != 38 && TokenIzq.kind != 39) {
            try {
                tipoIdent1 = (Integer) tablaVarsGlobal.get(TokenIzq.image);
            } catch (Exception e) {//Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
                return "Error: El identificador " + TokenIzq.image + " No ha sido declarado \r\nLinea: " + TokenIzq.beginLine;
            }
        }
        else
            tipoIdent1 = 0;

        //TokenAsig.kind != 38 && TokenAsig.kind != 39 && TokenAsig.kind != 41
        if(TokenAsig.kind == 40)
        {
			/*Si el tipo de dato que se esta asignando, es algun identificador(kind == 39)
			se obtiene su tipo de la tabla de tokens para poder hacer las comparaciones*/
            try
            {
                tipoIdent2 = (Integer)tablaVarsGlobal.get(TokenIzq.image);
            }
            catch (Exception e)
            {

                        return "Error: El identificador " + TokenAsig.image + " No ha sido declarado \r\nLinea: " + TokenIzq.beginLine;
            }

    }



        //Si el dato es entero(38) o decimal(39) o caracter(41)
        //tipoIdent2 = tipo_del_dato
        else if(TokenAsig.kind == 38 || TokenAsig.kind == 39 || TokenAsig.kind == 41)
            tipoIdent2 = TokenAsig.kind;
        else //Si no, se inicializa en algun valor "sin significado(con respecto a los tokens)", para que la variable este inicializada y no marque error al comparar
            tipoIdent2 = 0;




        if(tipoIdent1 == 4) //Int
        {
            //Si la lista de enteros(intComp) contiene el valor de tipoIdent2, entonces es compatible y se puede hacer la asignacion
            if(intComp.contains(tipoIdent2))
                return " ";
            else //Si el tipo de dato no es compatible manda el error
                return "Error: No se puede convertir " + TokenAsig.image + " a Entero \r\nLinea: " + TokenIzq.beginLine;
        }
        else if(tipoIdent1 == 5) //double
        {
            if(decComp.contains(tipoIdent2))
                return " ";
            else
                return "Error: No se puede convertir " + TokenAsig.image + " a Decimal \r\nLinea: " + TokenIzq.beginLine;
        }
        else if(tipoIdent1 == 6) //char
        {
			/*variable segunda: cuenta cuantos datos se van a asignar al caracter:
				si a el caracter se le asigna mas de un dato (ej: 'a' + 'b') marca error
				NOTA: no se utiliza un booleano ya que entraria en asignaciones pares o impares*/
            segunda++;
            if(segunda < 2)
            {
                if(chrComp.contains(tipoIdent2))
                    return " ";
                else
                    return "Error: No se puede convertir " + TokenAsig.image + " a Caracter \r\nLinea: " + TokenIzq.beginLine;
            }
            else //Si se esta asignando mas de un caracter manda el error
                return "Error: No se puede asignar mas de un valor a un caracter \r\nLinea: " + TokenIzq.beginLine;

        }
        return "Error token no aceptado";
		/*
		else if(tipoIdent1 == 47) //string
		{
			if(strComp.contains(tipoIdent2))
				return " ";
			else
				return "Error: No se puede convertir " + TokenAsig.image + " a Cadena \r\nLinea: " + TokenIzq.beginLine;
		}
		else
		{
			return "El Identificador " + TokenIzq.image + " no ha sido declarado" + " Linea: " + TokenIzq.beginLine;
		}*/
    }


    /*Metodo que verifica si un identificador ha sido declarado,
        ej cuando se declaran las asignaciones: i++, i--)*/
    public static String checkVariable(Token checkTok, Token nombreFuncion)
    {
        CustomHash tabla;

        try
        {
            int tipoToken = (Integer)tablaVarsGlobal.get(checkTok.image);
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
                    int token = (Integer)tabla.tablaV.get(checkTok.image);
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
