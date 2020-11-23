////////////////////////////////////////////////////////////////////////////////////////////////////
// Proyecto de Clase de Compiladores
// Proyecto Especial de Diseño de CompiladoresCOVID19AD20: En Pareja - Lenguaje Par++
// Elaborado por:
// Jorge Arturo Méndez Vargas - A01176369
// Jorge Adrían Ramos Barrena - A01176234
////////////////////////////////////////////////////////////////////////////////////////////////////
// TOKENASIGNACIONES (archivo auxiliar)

// Varios imports
package com.example.myapplication.TokenAsignaciones;
import android.os.Environment;

import com.example.myapplication.parser.Token;
import com.example.myapplication.TokenAsignaciones.cuboSemantico;
import com.example.myapplication.TokenAsignaciones.Quadruple;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.lang.String;
import java.util.ArrayList;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.LinkedList;


class DimType {
    int linf;
    int lsup;
    int m;

    public DimType()
    {
        linf = 0;
        lsup = 0;
        m = 0;
    }


}

// Clase Tipo_Dir
// Esta clase nos permite almacenar tanto el tipo como la dirección de una variable
class Tipo_Dir{
    int tipo;
    int dir;
    boolean isArray;
    ArrayList<DimType> dim;

    public Tipo_Dir(int t, int d)
    {
        tipo = t;
        dir = d;
        isArray = false;
        dim = new ArrayList<DimType>();
    }
}

// Clase CustomHash
// Esta clase nos permite almacenar muchos elementos de una función dentro de la hashtable.
// Sirve para almacenar el tipo de la función, la tabla de variables, el cuadruplo inicial de la función
// los contadores para memoria y la cantidad de parametros.
class CustomHash {
    // Tipo de la función
    int tipo;
    // Tabla de variables de la función
    Hashtable<String, Tipo_Dir> tablaV = new Hashtable();
    // Cuadruplo inicial de la función
    int cuadruploInicial;
    // Arreglo que guarda la cantidad de variables de cada tipo que se van a necesitar dentro de la función
    int[] varArray = new int[9];

    // Arreglo que guarda la cantidad y tipo de parametros
    ArrayList<Integer> parametros = new ArrayList<Integer>();

    //Constructor
    public static void main(String[] args) {
        CustomHash myObj = new CustomHash();
    }
    //                                 int f  c  b ti tf  tc tb tp
    CustomHash(){
        varArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    }
}

// Clase TokenAsignaciones
// Aquí se hace basicamente todoo, y se guardan todas las tablas y estructuras.
public class TokenAsignaciones {


    //Variable para validar asignaciones a caracteres(ichr)
    public static int segunda = 0;
    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION TABLAS
    //Tabla que almacenara los tokens declarados globalmente
    private static Hashtable<String, Tipo_Dir> tablaVarsGlobal = new Hashtable();
    private static int[] varArrayGlobal = new int[]{0, 0, 0, 0};
    //Tabla que almacenara las funciones declaradas
    private static Hashtable<String,CustomHash> tablaFunc = new Hashtable<String,CustomHash>();
    private static Hashtable<Integer, String> tablaConst = new Hashtable<Integer, String>();
    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION PILAS
    private static Stack<Token> pilaOP = new Stack<Token>();
    private static Stack<Token> pilaVP = new Stack<Token>();
    private static cuboSemantico cubo = new cuboSemantico();
    private static Stack<Integer> pilaSaltos = new Stack<Integer>();
    private static int contCuadruplos = 0;

    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION ARRAY CUADRUPLOS
    private static ArrayList<Quadruple> cuadruplos = new ArrayList<Quadruple>();

    ///////////////////////////////////////////////////////////////////////////////////////////////         DECLARACION VARIABLES ARRAYS
    private static int dim = 0;
    private static int r = 1;



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
    private static int contTP = 14000;



    private static int contParametros = 0;
    ////////////////////////////////////////////////////////////////////////////////////////////////       TODO

    // FUnción para reiniciar todo
    public static void reiniciaTodo()
        {
            emptyPilaOP();
            emptyPilaVP();
            emptyCuadruplos();
            // RESETEAR TODOS LOS CONTADORES
            resetContsGlobal();
            resetContsLocal();
            resetContsTemporal();
            resetContsConstantes();
            emptyPilaSaltos();
            resetContCuadruplos();
            //TokenAsignaciones.printNumVarsGlobal();
            resetNumVarsGlobal();
            reiniciaTablas();
            dim = 0;
            r = 1;
        }

        public static void reiniciaTablas()
        {
            tablaVarsGlobal.clear();
            tablaFunc.clear();
            tablaConst.clear();
        }

    ////////////////////////////////////////////////////////////////////////////////////////////////        Comienza Maquina Virtual

    // Aquí termina la fase de compilación, y le pasamos toodo lo relevante a la maquina virtual para seguir con la ejecución.
    public static void comienzaMaquinaVirtual()
    {
        MaquinaVirtual.Comienza(tablaVarsGlobal, varArrayGlobal, tablaFunc, tablaConst, cuadruplos);
    }

    /*public static void guardarTodo()
    {
        int cont = 0;
        //Path donde se guardará el archivo
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaTutorial";
        File dir = new File(path);
        dir.mkdirs();

        File file = new File (path + "/savedFile.txt");
        String saveText[];    //declaring array
        saveText = new String[100];

        saveText[0] = "cuadruplos:";

        Quadruple aux;
        for (int j = 1; j < cuadruplos.size(); j++) {
            aux = cuadruplos.get(j);
            saveText[j] = (aux.operator + " " + aux.arg1 + " " + aux.arg2 + " " + aux.resultado);
            //System.out.println(aux.operator + " " + aux.arg1 + " " + aux.arg2 + " " + aux.resultado);
            cont++;
        }

        saveText[cont] = "funciones:";

        Tipo_Dir aux2;


        Save(file, saveText);
    }

    public static void Save(File file, String[] data)
    {


        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<cuadruplos.size(); i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

*/


    ////////////////////////////////////////////////////////////////////////////////////////////////        CUBO SEMANTICO

    public static int getCuboType(int arg1, int arg2, String op)
    {
        return cuboSemantico.getType(arg1, arg2, op);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////        QUADRUPLOS

    public static void completaGOTO( int cuadruploModificar, int cuadruploActual)
    {
        Quadruple aux;
        Token aux2 = new Token();
        aux = cuadruplos.get(cuadruploModificar);
        aux2.image = Integer.toString(cuadruploActual);
        aux.resultado = aux2;
        cuadruplos.set(cuadruploModificar, aux);
    }

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

    public static void subeContCuadruplos()
    {
        contCuadruplos += 1;
    }

    public static int getContCuadruplos()
    {
        return contCuadruplos;
    }

    public static void resetContCuadruplos()
    {
        contCuadruplos = 0;
    }

    public static int getContTemporalPointer()
    {
        int temp;
        temp = contTP;
        contTP++;
        return temp;
    }

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
                contLI+=1;
                return temp;
            case 5:
                temp = contLF;
                contLF+=1;
                return temp;
            case 6:
                temp = contLC;
                contLC+=1;
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
                contGI+=1;
                return temp;
            case 5:
                temp = contGF;
                contGF+=1;
                return temp;
            case 6:
                temp = contGC;
                contGC+=1;
                return temp;
            default: return -1;
        }
    }


    public static void aumentaContVar(Token func, int td)
    {

        if(func == null)
        {
            switch (td)
            {
                case 4:
                    contGI= contGI+(r-1);
                    break;
                case 5:
                    contGF= contGF+(r-1);
                    break;
                case 6:
                    contGC= contGC+(r-1);
                    break;
                default:         System.out.println("Error : "+td);

            }
        }
        else {
            int temp;
            switch (td)
            {
                case 4:
                    contLI= contLI+(r-1);
                    break;
                case 5:
                    contLF= contLF+(r-1);
                    break;
                case 6:
                    contLC= contLC+(r-1);
                    break;
                default:         System.out.println("Error : "+td);

            }
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
        contTP = 14000;
    }

    public static void resetContsConstantes()
    {
        contCI = 11000;
        contCF = 12000;
        contCC = 13000;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////        PILA SALTOS
    public static void pushPilaSaltos(int pos)
    {
        pilaSaltos.push(pos);
    }

    public static int popPilaSaltos()
    {
        return pilaSaltos.pop();
    }
    public static Stack returnPilaSaltos()
    {
        return pilaSaltos;
    }

    public static void emptyPilaSaltos()
    {
        pilaSaltos.clear();
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

    public static Token peekPilaOP()
    {
        try{
            return pilaOP.peek();
        }
        catch (Exception e){
            return null;
        }
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
        try{
            return pilaVP.peek();
        }
        catch (Exception e){
            return null;
        }
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

    public static void arraySegundaPasada(Token func, Token var)
    {
        int offset = 0;
        int Size = r;
        int mDim = 0;

        CustomHash tabla;
        Tipo_Dir aux;
        DimType aux2 = new DimType();

        try
        {
            aux = tablaVarsGlobal.get(var.image);
            //Obtienes el primer nodo
            aux2 = aux.dim.get(0);
            mDim = r/(aux2.lsup-aux2.linf+1);
            aux2.m = mDim;
            aux.dim.set(0, aux2);
            //r = mDim;
            offset = offset + aux2.linf*mDim;
            System.out.println("PRIMER NODO: " + aux2.linf + " " + aux2.lsup + " " + aux2.m);
            System.out.println("ARRAY SIZE: " + aux.dim.size());


            if (aux.dim.size() == 2)
            {
                aux2 = aux.dim.get(1);
                mDim = r/(aux2.lsup-aux2.linf+1);
                //r = mDim;
                offset = offset + aux2.linf*mDim;
                aux2.m = -offset;
                aux.dim.set(1, aux2);
                System.out.println("SEGUNDO NODO: " + aux2.linf + " " + aux2.lsup + " " + aux2.m);
            }
            else{
                aux2 = aux.dim.get(0);
                aux2.m = 0;
                aux.dim.set(0, aux2);
                System.out.println("PRIMER NODO final: " + aux2.linf + " " + aux2.lsup + " " + aux2.m);

            }

            tablaVarsGlobal.put(var.image, aux);


        }
        catch (Exception e)
        {
            try
            {
                tabla = tablaFunc.get(func.image);
                try
                {
                    //Intenta obtener el token a verificar(checkTok) de la tabla de los tokens
                    aux = tabla.tablaV.get(var.image);
                    //Obtienes el primer nodo
                    aux2 = aux.dim.get(0);
                    mDim = r/(aux2.lsup-aux2.linf+1);
                    aux2.m = mDim;
                    aux.dim.set(0, aux2);
                    //r = mDim;
                    offset = offset + aux2.linf*mDim;
                    System.out.println("PRIMER NODO: " + aux2.linf + " " + aux2.lsup + " " + aux2.m);

                    if (aux.dim.size() == 2)
                    {
                        aux2 = aux.dim.get(1);
                        mDim = r/(aux2.lsup-aux2.linf+1);
                        //r = mDim;
                        offset = offset + aux2.linf*mDim;
                        aux2.m = -offset;
                        aux.dim.set(1, aux2);
                        System.out.println("SEGUNDO NODO: " + aux2.linf + " " + aux2.lsup + " " + aux2.m);
                    }
                    else{
                        aux2 = aux.dim.get(0);
                        aux2.m = 0;
                        aux.dim.set(0, aux2);
                        System.out.println("PRIMER NODO final: " + aux2.linf + " " + aux2.lsup + " " + aux2.m);
                    }

                    tablaVarsGlobal.put(var.image, aux);

                }
                catch(Exception f)
                {

                }
            }
            catch(Exception g)
            {
            }
        }

    }

    public static void aumentaDim(Token func, Token var, Token numDim)
    {
        dim = dim + 1;
        CustomHash tabla;
        Tipo_Dir aux;
        DimType aux2 = new DimType();

        try
        {
            aux = tablaVarsGlobal.get(var.image);

            aux2.linf = 0;
            aux2.lsup = Integer.parseInt(numDim.image);
            aux.dim.add(aux2);

            r = (aux2.lsup - aux2.linf + 1) * r;
        }
        catch (Exception e)
        {
            System.out.println(e);
            try
            {
                tabla = tablaFunc.get(func.image);
                try
                {
                    //Intenta obtener el token a verificar(checkTok) de la tabla de los tokens
                    aux = tabla.tablaV.get(var.image);

                    aux2.linf = 0;
                    aux2.lsup = Integer.parseInt(numDim.image);

                    aux.dim.add(aux2);

                    r = (aux2.lsup - aux2.linf + 1) * r;
                }
                catch(Exception f)
                {

                }
            }
            catch(Exception g)
            {
            }
        }
    }

    public static void setVarAsArray(Token func, Token var, Token numDim)
    {
        CustomHash tabla;
        Tipo_Dir aux;
        DimType aux2 = new DimType();

        try
        {
            aux = tablaVarsGlobal.get(var.image);
            aux.isArray = true;

            aux2.linf = 0;
            aux2.lsup = Integer.parseInt(numDim.image);
            System.out.println("ARRAY SIZE+1: " + var);

            aux.dim.add(aux2);

            dim = 1;
            r = 1;

            r = (aux2.lsup - aux2.linf + 1) * r;

        }
        catch (Exception e)
        {
            try
            {
                tabla = tablaFunc.get(func.image);
                try
                {
                    //Intenta obtener el token a verificar(checkTok) de la tabla de los tokens
                    aux = tabla.tablaV.get(var.image);
                    aux.isArray = true;
                    aux2.linf = 0;
                    aux2.lsup = Integer.parseInt(numDim.image);
                    System.out.println("ARRAY SIZE+1: " + var);

                    aux.dim.add(aux2);

                    dim = 1;
                     r =1;

                    r = (aux2.lsup - aux2.linf + 1) * r;
                }
                catch(Exception f)
                {

                }
            }
            catch(Exception g)
            {
            }
        }
    }

    public static void aumentaVarFuncTempotalPointer(Token func)
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);
        tabla.varArray[8] += 1;
    }

    public static void aumentaVarFuncTemporal( int td, Token func)
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);
        //tabla.varArray[0] += 1;

        switch (td)
        {
            case 4:
                tabla.varArray[4] += 1;
                break;
            case 5:
                tabla.varArray[5] += 1;
                break;
            case 6:
                tabla.varArray[6] += 1;
                break;
            case 47:
                tabla.varArray[7] += 1;
                break;
            default: break;
        }
    }

    public static void aumentaVarFuncArrays( int td, Token func )
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);
        //tabla.varArray[0] += 1;

        switch (td)
        {
            case 4:
                tabla.varArray[0] += r-1;
                break;
            case 5:
                tabla.varArray[1] += r-1;
                break;
            case 6:
                tabla.varArray[2] += r-1;
                break;
            case 47:
                tabla.varArray[3] += r-1;
                break;
            default: break;
        }
    }

    public static void aumentaVarFunc( int td, Token func )
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);
        //tabla.varArray[0] += 1;

        switch (td)
        {
            case 4:
                tabla.varArray[0] += 1;
                break;
            case 5:
                tabla.varArray[1] += 1;
                break;
            case 6:
                tabla.varArray[2] += 1;
                break;
            case 47:
                tabla.varArray[3] += 1;
                break;
            default: break;
        }
    }

    public static void aumentaVarFuncGlobalArrays(int td)
    {

        switch (td)
        {
            case 4:
                varArrayGlobal[0] += r-1;
                break;
            case 5:
                varArrayGlobal[1] += r-1;
                break;
            case 6:
                varArrayGlobal[2] += r-1;
                break;
            case 47:
                varArrayGlobal[3] += r-1;
                break;
            default: break;
        }
    }

    public static void aumentaVarFuncGlobal( int td)
    {

        switch (td)
        {
            case 4:
                varArrayGlobal[0] += 1;
                break;
            case 5:
                varArrayGlobal[1] += 1;
                break;
            case 6:
                varArrayGlobal[2] += 1;
                break;
            case 47:
                varArrayGlobal[3] += 1;
                break;
            default: break;
        }
    }

    public static void printNumVars(Token func)
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);
        System.out.println("SSSSSSSSSSSSSSSSSSS Num vars: " + tabla.varArray[0] + " " + tabla.varArray[1] + " " + tabla.varArray[2] + " " + tabla.varArray[3] + " ");
    }

    public static void resetNumVarsGlobal()
    {
        varArrayGlobal[0] = 0;
        varArrayGlobal[1] = 0;
        varArrayGlobal[2] = 0;
        varArrayGlobal[3] = 0;
    }

    public static void printNumVarsGlobal()
    {
        System.out.println("SSSSSSSSSSSSSSSSSSS Num vars: " + varArrayGlobal[0] + " " + varArrayGlobal[1] + " " + varArrayGlobal[2] + " " + varArrayGlobal[3] + " ");
    }

    /*
    public static void guardaContadores(Token func)
    {
        int contLocales;
        contLocales = (contLI - 4000) + (contLF - 5000) + (contLC - 6000);

        int contTemporales;
        contTemporales = (contTI - 7000) + (contTF - 8000) + (contTC - 9000) + (contTB-10000);


        CustomHash tabla;
        tabla = tablaFunc.get(func.image);
        tabla.varArray[0] = contLocales;
        tabla.varArray[1] = contTemporales;

    }*/

    public static int getContParams()
    {
        return contParametros;
    }

    public static void resetContParams()
    {
        contParametros = 0;
    }

    public static ArrayList getParams(Token func)
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);
        return tabla.parametros;

    }

    public static int getInitialAddress(Token func)
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);
        return tabla.cuadruploInicial;
    }

    public static int getParamSize(Token func)
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);
        return tabla.parametros.size();
    }

    // Checa si el tipo enviado es igual al del parametro quse le va a enviar
    public static Boolean checaTipoParams(int td, Token func)
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);

        try
        {
            if (tabla.parametros.get(contParametros) == td)
            {
                contParametros++;
                return true;
            }
            else
            {
                contParametros++;
                return false;
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public static String InsertarParametrosFunc( int td, Token func)
    {
        CustomHash tabla;
        tabla = tablaFunc.get(func.image);

        switch (td)
        {
            case 4:
                tabla.parametros.add(4);
                return " ";
            case 5:
                tabla.parametros.add(5);
                return " ";
            case 6:
                tabla.parametros.add(6);
                return " ";
            default: return "El tipo :" + td + " no es válido.";
        }
    }

    public static String InsertarConstante( String constante, int direccion )
    {
        tablaConst.put(direccion, constante);
        return Integer.toString(direccion);
    }

    // Esta función obtiene la dirección de una variable dentro de una función
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

    // Esta función obtiene la dirección de una variable dentro de una función
    public static int getDim2(Token checkTok, Token nombreFuncion, int index)
    {
        CustomHash tabla;
        Tipo_Dir aux;
        ArrayList<DimType> aux2;
        DimType aux3;

        try
        {
            aux = tablaVarsGlobal.get(checkTok.image);
            if (aux.isArray)
            {
                aux2 = aux.dim;
                aux3 = aux2.get(index);
                return (aux3.lsup-aux3.linf)+1;

            }
            else{
                return -1;
            }
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
                    if (aux.isArray)
                    {
                        aux2 = aux.dim;
                        aux3 = aux2.get(index);
                        return (aux3.lsup-aux3.linf)+1;

                    }
                    else{
                        return -1;
                    }
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

    // Esta función obtiene la dirección de una variable dentro de una función
    public static int getLimSup(Token checkTok, Token nombreFuncion, int index)
    {
        CustomHash tabla;
        Tipo_Dir aux;
        ArrayList<DimType> aux2;
        DimType aux3;

        try
        {
            aux = tablaVarsGlobal.get(checkTok.image);
            if (aux.isArray)
            {
                aux2 = aux.dim;
                aux3 = aux2.get(index);
                return aux3.lsup;

            }
            else{
                return -1;
            }
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
                    if (aux.isArray)
                    {
                        aux2 = aux.dim;
                        aux3 = aux2.get(index);
                        return aux3.lsup;

                    }
                    else{
                        return -1;
                    }
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


    //En este metodo se agrega a la tabla de tokens el identificador que esta siendo declarado junto con su tipo de dato y su dirección
    public static String InsertarSimbolo(Token identificador, int tipo, Token nombreFuncion)
    {
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

    //En este metodo se agrega a la tabla de tokens global el identificador que esta siendo declarado junto con su tipo de dato y direccion
    public static void InsertarSimboloGlobal(Token identificador, int tipo)
    {
        Tipo_Dir objeto = new Tipo_Dir(tipo, getContGlobal(tipo));
        tablaVarsGlobal.put(identificador.image, objeto);

    }

    //Metes la función como una variable global, para ahí guardar el valor del return
    public static void InsertarVarReturnFuncion(Token nombreFuncion, int tipo)
    {
        Tipo_Dir objeto = new Tipo_Dir(tipo, getContGlobal(tipo));
        System.out.println("EL QUE METES: " + nombreFuncion);
        tablaVarsGlobal.put(nombreFuncion.image, objeto);
    }

    // Regresa el tipo de la función
    public static int getfunctipo(Token nombreFuncion)
    {
        CustomHash tabla = tablaFunc.get(nombreFuncion.image);
        return tabla.tipo;
    }

    //Sirve para ver si es void o no
    public static boolean getTypeFunc(Token nombreFuncion)
    {
        CustomHash tabla = tablaFunc.get(nombreFuncion.image);
        if(tabla.tipo == 7)
        {
            return false;
        }
        else
        {
            return true;
        }
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


    // Inserta función a la tabla de funciones
    public static String InsertarFuncion(Token nombreFuncion, int tipo, int contCuadruplos)
    {
        try
        {
            CustomHash tabla = new CustomHash();
            tabla.tipo=tipo;
            tabla.cuadruploInicial = contCuadruplos;
            tablaFunc.put(nombreFuncion.image,tabla);
            return " ";
        }
        catch(Exception e)
        {
            return "Error: La funcion " + nombreFuncion.image + " No se pudo declarar \r\nLinea: ";
        }
    }

    // Inserta el main a la tabla de funciones
    public static void InsertarMain(Token identificador)
    {
        //En este metodo se agrega a la tabla de funciones el identificador que esta siendo declarado junto con su tipo de dato
        CustomHash tabla = new CustomHash();
        tabla.tipo=7;
        tablaFunc.put(identificador.image,tabla);
    }


    /*Metodo que verifica si un identificador ha sido declarado,
        ej cuando se declaran las asignaciones: i++, i--)*/
    // Primero checa en la tabla de variables globales, y después en la local, con el nombre de la función
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
    }


}
