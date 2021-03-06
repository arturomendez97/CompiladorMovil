////////////////////////////////////////////////////////////////////////////////////////////////////
// Proyecto de Clase de Compiladores
// Proyecto Especial de Diseño de CompiladoresCOVID19AD20: En Pareja - Lenguaje Par++
// Elaborado por:
// Jorge Arturo Méndez Vargas - A01176369
// Jorge Adrían Ramos Barrena - A01176234
////////////////////////////////////////////////////////////////////////////////////////////////////
// MAQUINA VIRTUAL  (Ejecución)
// ¿Que hace? - Realiza todoo lo relacionado con la parte de ejecución, incluyendo la creación de memoria y ejecución de los cuadruplos.
// ¿Que parametros recibe? - Todas las tablas, cuadruplos e información necesaria para la ejecución.
// ¿Que genera como salida? - Los resultados reales del código.
// Modulos más importantes que hacen uso de este archivo. - TokenAsignaciones y MainActivity.

// Se importa como paquete lo que está dentro del folder Tokenasignaciones
package com.example.myapplication.TokenAsignaciones;

// Import de android para acceder al path del dispositivo
import android.os.Environment;

// Import de la clase token
import com.example.myapplication.parser.ParseException;
import com.example.myapplication.parser.Token;

// Imports para manejo de archivos
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

// Imports de estructuras
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

// Clase para la memoria global.
// Incluye arreglos de ints, floats, y chars.
class MemoriaGlobal {
    int[] arrayInts;
    float[] arrayFloats;
    char[] arrayChars;

    // El tamaño de los arreglos depende de lo que le mandemos
    MemoriaGlobal(int numInts, int numFloats, int numChars){

        arrayInts = new int[numInts];
        arrayFloats = new float[numFloats];
        arrayChars = new char[numChars];
    }
}

// Clase para la memoria local
// En este caso tenemos también arreglos para bool y para temporales
class MemoriaLocal {
    int[] arrayInts;
    float[] arrayFloats;
    char[] arrayChars;
    boolean[] arrayBools;

    int[] arrayIntsTemporales;
    float[] arrayFloatsTemporales;
    char[] arrayCharsTemporales;
    boolean[] arrayBoolsTemporales;

    int[] arrayPointersTemporales;


    // el tamaño de los arreglos depende de lo que le mandemos
    MemoriaLocal(int numInts, int numFloats, int numChars, int numBools, int numIntsTemporales, int numFloatsTemporales, int numCharsTemporales, int numBoolsTemporales, int numpointersTemporales){
        arrayInts = new int[numInts];
        arrayFloats = new float[numFloats];
        arrayChars = new char[numChars];
        arrayBools = new boolean[numBools];

        arrayIntsTemporales = new int[numIntsTemporales];
        arrayFloatsTemporales = new float[numFloatsTemporales];
        arrayCharsTemporales = new char[numCharsTemporales];
        arrayBoolsTemporales = new boolean[numBoolsTemporales];

        arrayPointersTemporales = new int[numpointersTemporales];
    }

    // También se puede crear una vacia.
    MemoriaLocal(){
        arrayInts = new int[0];
        arrayFloats = new float[0];
        arrayChars = new char[0];
        arrayBools = new boolean[0];

        arrayIntsTemporales = new int[0];
        arrayFloatsTemporales = new float[0];
        arrayCharsTemporales = new char[0];
        arrayBoolsTemporales = new boolean[0];

        arrayPointersTemporales = new int[0];
    }
}

// Clase maquina virtual
// Aquí se hace toda la ejecución
public class MaquinaVirtual {
/*

    public static String str = "hola";

    public static void print()
    {
        System.out.println(str);
    }

    public static String readFile()
    {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaTutorial";
        File file = new File (path + "/savedFile.txt");
        String [] loadText = Load(file);

        String finalString = "";

        for( int i= 0; i < loadText.length; i++)
        {
            finalString += loadText[i] + System.getProperty("line.separator");
        }

        return finalString;
    }

    public static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }

 */

    // Se declaran todas las variables que se van a necesitar
    private static Hashtable<String, Tipo_Dir> tablaVarsGlobal = new Hashtable();
    private static int[] varArrayGlobal = new int[]{0, 0, 0, 0};
    //Tabla que almacenara las funciones declaradas
    private static Hashtable<String,CustomHash> tablaFunc = new Hashtable<String,CustomHash>();
    private static Hashtable<Integer, String> tablaConst = new Hashtable<Integer, String>();
    private static ArrayList<Quadruple> cuadruplos = new ArrayList<Quadruple>();
    private static int ip = 0;
    private static String text = "";

    private static Stack<MemoriaLocal> pilaMemorias = new Stack<MemoriaLocal>();
    private static Stack<Integer> contCuadruplo = new Stack<Integer>();
    private static MemoriaLocal memLocal2 = new MemoriaLocal();




    //Esta es la función principal, y donde se ejecuta tdodo el programa. Recibe muchos argumentos de tokenasignaciones. Todas las tablas y cuadruplos.
    public static void Comienza(Hashtable<String, Tipo_Dir> vGlobal, int[] arrGlobal, Hashtable<String, CustomHash> funcs, Hashtable<Integer, String> constantes, ArrayList<Quadruple> cuads) throws ParseException {
        //Se obtienen las variables necesarias.
        ip = 0;
        text = "";
        tablaVarsGlobal = vGlobal;
        varArrayGlobal = arrGlobal;
        tablaFunc = funcs;
        tablaConst = constantes;
        cuadruplos = cuads;
        printNumVarsGlobal();

        //CREA MEMORIA GLOBAL
        MemoriaGlobal memGlobal = new MemoriaGlobal(varArrayGlobal[0], varArrayGlobal[1], varArrayGlobal[2]);

        //CREA MEMORIA LOCAL
        CustomHash tabla;
        tabla = tablaFunc.get("main");
        //System.out.println(tablaFunc);
        MemoriaLocal memLocal = new MemoriaLocal(tabla.varArray[0], tabla.varArray[1], tabla.varArray[2], tabla.varArray[3], tabla.varArray[4], tabla.varArray[5], tabla.varArray[6], tabla.varArray[7], tabla.varArray[8]);

        // Variables auxiliares
        Quadruple aux;
        int constInt;
        float constFloat;
        char constChar;
        boolean constBool;
        String auxString;


        int dirResultado;
        int dirArg1;
        int dirArg2;


        int intValue = 0;
        float floatValue;
        char charValue;
        boolean boolValue;

        Object res = 0;
        Object res2 = 0;

        int auxI1 = 0,auxI2 = 0;
        float auxF1 = 0, auxF2 = 0;
        char auxC1 = 'a', auxC2 = 'a';
        boolean auxB1 = false, auxB2 = false;

        boolean arg1Int = false, arg1Float = false, arg2Int = false, arg2Float = false, arg1Char = false, arg2Char = false;

        int contParamsInt = 0, contParamsFloat = 0, contParamsChar = 0;

        int auxDirArray = 0;

        //RECORRE CUADRUPLOS

        while (ip < cuadruplos.size())
        {
            //System.out.println("PRINT EN EL WHILE +: " + memLocal.arrayIntsTemporales[0]);
            System.out.println("PRINT IP : " + ip);

            aux = cuadruplos.get(ip);

                switch (aux.operator)
                {
                    case "return":
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        auxString = aux.resultado.image;
                        auxI1 = 0; auxF1 = 0; auxC1 = 'a';
                        arg1Int = false; arg1Float = false; arg1Char = false;

                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //CONSTANTES
                        if (dirArg1 >= 11000)
                        {
                            switch (aux.arg1.kind)
                            {
                                case 4:  auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;
                                    break;
                                case 5:  auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;
                                    break;
                                case 6:  auxString = tablaConst.get(dirArg1);
                                    res = auxString.charAt(0);
                                    arg1Char = true;

                                    break;

                                case 38: auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;
                                    break;
                                case 39: auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;
                                    break;
                                case 41: auxString = tablaConst.get(dirArg1);
                                    auxC1 = auxString.charAt(1);
                                    arg1Char = true;
                                    break;
                                default:  System.out.println("ALgo salio mal " + aux.resultado.kind);
                                    break;
                            }
                        }

                        //ARG1 GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true; }
                        if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true; }
                        if (dirArg1 >= 3000 && dirArg1 < 4000) { auxC1 = memGlobal.arrayChars[dirArg1-3000]; arg1Char = true; }

                        //ARG1 LOCALES
                        if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true; }
                        if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true; }
                        if (dirArg1 >= 6000 && dirArg1 < 7000) { auxC1 = memLocal.arrayChars[dirArg1-6000]; arg1Char = true; }

                        //TEMPORALES
                        if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true; }
                        if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true; }
                        if (dirArg1 >= 9000 && dirArg1 < 10000) { auxC1 = memLocal.arrayCharsTemporales[dirArg1-9000]; arg1Char = true; }


                        //////////////////////////////////////////////////////////////////////////// RESULTADO
                        Tipo_Dir dir;
                        dir = tablaVarsGlobal.get(auxString);
                        dirResultado = dir.dir;
                        System.out.println("AYUDAAAAAAAAAAAAAAAAAAAAAA: " + auxI1 + " " + dirResultado);

                        //ARG1 GLOBALES

                        if (dirResultado >= 1000 && dirResultado < 2000) { memGlobal.arrayInts[dirResultado-1000] = auxI1; }
                        if (dirResultado >= 2000 && dirResultado < 3000) {
                            if (arg1Int) { memGlobal.arrayFloats[dirResultado-2000] = auxI1; }
                            else if(arg1Float) { memGlobal.arrayFloats[dirResultado-2000] = auxF1; } }
                        if (dirResultado >= 3000 && dirResultado < 4000) { memGlobal.arrayChars[dirResultado-3000] = auxC1; }

                        //Rregresar a la memoria anterior
                        memLocal = pilaMemorias.pop();
                        ip = contCuadruplo.pop()+1;
                        break;
                    case "GOSUB" :
                        //Guardar memoria actual en el stack
                        pilaMemorias.push(memLocal);
                        memLocal = memLocal2;
                        //Guardar posición actual en el stack
                        contCuadruplo.push(ip);
                        //Cambiar de posición
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        ip = dirResultado;
                        contParamsInt = 0;
                        contParamsFloat = 0;
                        contParamsChar = 0;
                        break;
                    case "Ver" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.resultado.image);

                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { TokenAsignaciones.createParseException("No se puede indexar un arreglo con floats"); }
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { TokenAsignaciones.createParseException("No se puede indexar un arreglo con floats"); }
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { TokenAsignaciones.createParseException("No se puede indexar un arreglo con floats"); }
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { TokenAsignaciones.createParseException("No se puede indexar un arreglo con floats"); }

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { TokenAsignaciones.createParseException("No se puede indexar un arreglo con floats"); }
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { TokenAsignaciones.createParseException("No se puede indexar un arreglo con floats"); }
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { TokenAsignaciones.createParseException("No se puede indexar un arreglo con floats"); }
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { TokenAsignaciones.createParseException("No se puede indexar un arreglo con floats"); }


                        if (auxI1 >= auxI2)
                        {
                            System.out.println("EL VER: " + auxI1 + " " + auxI2);
                            TokenAsignaciones.createParseException("Se está intentando acceder a una casilla inexistente de un arreglo.");
                        }
                        ip++;
                        break;
                    case "ENDFUNC" :
                        //Rregresar a la memoria anterior
                        memLocal = pilaMemorias.pop();
                        ip = contCuadruplo.pop()+1;
                        break;
                    case "ERA" :
                        // Crear nueva memoria local
                        auxString = aux.resultado.image;
                        CustomHash tablaAux;
                        tablaAux = tablaFunc.get(auxString);
                        memLocal2 = new MemoriaLocal(tablaAux.varArray[0], tablaAux.varArray[1], tablaAux.varArray[2], tablaAux.varArray[3], tablaAux.varArray[4], tablaAux.varArray[5], tablaAux.varArray[6], tablaAux.varArray[7], tablaAux.varArray[8]);
                        ip++;
                        break;
                    case "PARAM" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        auxI1 = 0; auxF1 = 0; auxC1 = 'a';
                        arg1Int = false; arg1Float = false; arg1Char = false;

                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //CONSTANTES
                        if (dirArg1 >= 11000)
                        {
                            switch (aux.arg1.kind)
                            {
                                case 4:  auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;
                                    break;
                                case 5:  auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;
                                    break;
                                case 6:  auxString = tablaConst.get(dirArg1);
                                    res = auxString.charAt(0);
                                    arg1Char = true;

                                    break;

                                case 38: auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;
                                    break;
                                case 39: auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;
                                    break;
                                case 41: auxString = tablaConst.get(dirArg1);
                                    auxC1 = auxString.charAt(1);
                                    arg1Char = true;
                                    break;
                                default:  System.out.println("ALgo salio mal " + aux.resultado.kind);
                                    break;
                            }
                        }

                        //ARG1 GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true; }
                        if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true; }
                        if (dirArg1 >= 3000 && dirArg1 < 4000) { auxC1 = memGlobal.arrayChars[dirArg1-3000]; arg1Char = true; }

                        //ARG1 LOCALES
                        if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true; }
                        if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true; }
                        if (dirArg1 >= 6000 && dirArg1 < 7000) { auxC1 = memLocal.arrayChars[dirArg1-6000]; arg1Char = true; }

                        //TEMPORALES
                        if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true; }
                        if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true; }
                        if (dirArg1 >= 9000 && dirArg1 < 10000) { auxC1 = memLocal.arrayCharsTemporales[dirArg1-9000]; arg1Char = true; }

                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Resultado
                        //ARG1 LOCALES
                        if (arg1Int == true) { memLocal2.arrayInts[contParamsInt] = auxI1; contParamsInt++;}
                        if (arg1Float == true) { memLocal2.arrayFloats[contParamsFloat] = auxF1; contParamsFloat++;}
                        if (arg1Char == true) { memLocal2.arrayChars[contParamsChar] = auxC1; contParamsChar++;}

                        ip++;
                        break;
                    case "GOTO" :
                        ip = Integer.parseInt(aux.resultado.image);
                        break;
                    case "GOTOV" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        if (memLocal.arrayBoolsTemporales[dirArg1-10000]) { ip = dirResultado; }
                        else {ip++;}
                        break;
                    case "GOTOF" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        if (!memLocal.arrayBoolsTemporales[dirArg1-10000]) { ip = dirResultado; }
                        else {ip++;}
                        break;
                    case "write":
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        //ARRAYS
                        if (dirResultado >= 14000 && dirResultado < 15000) { dirResultado =  memLocal.arrayPointersTemporales[dirResultado-14000]; }
                        // GLOBALES
                        if (dirResultado >= 1000 && dirResultado < 2000) { intValue = memGlobal.arrayInts[dirResultado-1000]; text += Integer.toString(intValue); }
                        else if (dirResultado >= 2000 && dirResultado < 3000) { floatValue = memGlobal.arrayFloats[dirResultado-2000]; text += Float.toString(floatValue); }
                        else if (dirResultado >= 3000 && dirResultado < 4000) { charValue = memGlobal.arrayChars[dirResultado-3000]; text += charValue; }
                        //LOCALES
                        if (dirResultado >= 4000 && dirResultado < 5000) { intValue = memLocal.arrayInts[dirResultado-4000]; text += Integer.toString(intValue); }
                        else if (dirResultado >= 5000 && dirResultado < 6000) { floatValue = memLocal.arrayFloats[dirResultado-5000]; text += Float.toString(floatValue); }
                        else if (dirResultado >= 6000 && dirResultado < 7000) { charValue = memLocal.arrayChars[dirResultado-6000]; text += charValue; }
                        //TEMPORALES
                        if (dirResultado >= 7000 && dirResultado < 8000) { intValue = memLocal.arrayIntsTemporales[dirResultado-7000]; text += Integer.toString(intValue); }
                        else if (dirResultado >= 8000 && dirResultado < 9000) { floatValue = memLocal.arrayFloatsTemporales[dirResultado-8000]; text += Float.toString(floatValue); }
                        else if (dirResultado >= 9000 && dirResultado < 10000) { charValue = memLocal.arrayCharsTemporales[dirResultado-9000]; text += charValue; }
                        else if (dirResultado >= 10000 && dirResultado < 11000) { auxString = Boolean.toString(memLocal.arrayBoolsTemporales[dirResultado-10000]); text += auxString; }
                        // CONSTANTES
                        else if (dirResultado >= 11000 && dirResultado < 12000) { auxString = tablaConst.get(dirResultado); text += auxString; }
                        else if (dirResultado >= 12000 && dirResultado < 13000) { auxString = tablaConst.get(dirResultado); text += auxString; }
                        else if (dirResultado >= 13000 && dirResultado < 14000) { auxString = tablaConst.get(dirResultado); constChar = auxString.charAt(1); text += constChar; }
                        text += "\r\n";
                        ip++;
                        break;
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "+"
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    case "+" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        //Entra aquí en el caso del for
                        if(dirResultado >= 1000 && dirResultado < 6000)
                        {
                            /// CASO ESPECIAL PARA LA SUMA DENTRO DEL FOR
                            //GLOBALES
                            if (dirResultado >= 1000 && dirResultado < 2000) { memGlobal.arrayInts[dirResultado-1000] = auxI2+auxI1; System.out.println( "RESPUESTA + : " + memGlobal.arrayInts[dirResultado-1000]);}
                            else if (dirResultado >= 2000 && dirResultado < 3000) { memGlobal.arrayFloats[dirResultado-2000] = auxI2+auxF1; System.out.println( "RESPUESTA + : " + memGlobal.arrayFloats[dirResultado-2000]);}
                            //LOCALES
                            else if (dirResultado >= 4000 && dirResultado < 5000) { memLocal.arrayInts[dirResultado-4000] = auxI2+auxI1; System.out.println( "RESPUESTA + : " + memLocal.arrayInts[dirResultado-4000]);}
                            else if (dirResultado >= 5000 && dirResultado < 6000) { memLocal.arrayFloats[dirResultado-5000] = auxI2+auxF1; System.out.println( "RESPUESTA + : " + memLocal.arrayFloats[dirResultado-5000]);}
                            ////
                        }
                        //Entra acá en todo lo demás
                        else{
                            //ARRAYS
                            if (dirResultado >= 14000 && dirResultado < 15000) { memLocal.arrayPointersTemporales[dirResultado-14000] = auxI2+auxI1; System.out.println( "RESPUESTA + : " + memLocal.arrayPointersTemporales[dirResultado-14000]); }
                            else{
                                if (arg1Int && arg2Int) { memLocal.arrayIntsTemporales[dirResultado-7000] = auxI2+auxI1; System.out.println( "RESPUESTA + : " + memLocal.arrayIntsTemporales[dirResultado-7000]);}
                                else if (arg1Int && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2+auxI1; System.out.println( "RESPUESTA + : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                                else if (arg1Float && arg2Int) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxI2+auxF1; System.out.println( "RESPUESTA + : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                                else if (arg1Float && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2+auxF1; System.out.println( "RESPUESTA + : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                            }
                        }
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "-"
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    case "-" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (arg1Int && arg2Int) { memLocal.arrayIntsTemporales[dirResultado-7000] = auxI2-auxI1; System.out.println( "RESPUESTA - : " + memLocal.arrayIntsTemporales[dirResultado-7000]);}
                        else if (arg1Int && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2-auxI1; System.out.println( "RESPUESTA - : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        else if (arg1Float && arg2Int) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxI2-auxF1; System.out.println( "RESPUESTA - : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        else if (arg1Float && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2-auxF1; System.out.println( "RESPUESTA - : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "*"
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    case "*" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (arg1Int && arg2Int) { memLocal.arrayIntsTemporales[dirResultado-7000] = auxI2*auxI1; System.out.println( "RESPUESTA * : " + memLocal.arrayIntsTemporales[dirResultado-7000]);}
                        else if (arg1Int && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2*auxI1; System.out.println( "RESPUESTA * : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        else if (arg1Float && arg2Int) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxI2*auxF1; System.out.println( "RESPUESTA * : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        else if (arg1Float && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2*auxF1; System.out.println( "RESPUESTA * : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "/"
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    case "/" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (arg1Int && arg2Int) { memLocal.arrayIntsTemporales[dirResultado-7000] = auxI2/auxI1; System.out.println( "RESPUESTA / : " + memLocal.arrayIntsTemporales[dirResultado-7000]);}
                        else if (arg1Int && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2/auxI1; System.out.println( "RESPUESTA / : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        else if (arg1Float && arg2Int) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxI2/auxF1; System.out.println( "RESPUESTA / : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        else if (arg1Float && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2/auxF1; System.out.println( "RESPUESTA / : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "<"
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////

                    case "<" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (arg1Int && arg2Int) {
                            if (auxI2 < auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Int && arg2Float) {
                            if (auxF2 < auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Int) {
                            if (auxI2 < auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Float) {
                            if (auxF2 < auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso ">"
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////

                    case ">" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (arg1Int && arg2Int) {
                            if (auxI2 > auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA > : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Int && arg2Float) {
                            if (auxF2 > auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA > : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Int) {
                            if (auxI2 > auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA > : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Float) {
                            if (auxF2 > auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA > : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "<="
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////

                    case "<=" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (arg1Int && arg2Int) {
                            if (auxI2 <= auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA <= : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Int && arg2Float) {
                            if (auxF2 <= auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA <= : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Int) {
                            if (auxI2 <= auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA <= : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Float) {
                            if (auxF2 <= auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA <= : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso ">="
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////

                    case ">=" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (arg1Int && arg2Int) {
                            if (auxI2 >= auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA >= : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Int && arg2Float) {
                            if (auxF2 >= auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA >= : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Int) {
                            if (auxI2 >= auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA >= : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Float) {
                            if (auxF2 >= auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA >= : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "=="
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////

                    case "==" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (arg1Int && arg2Int) {
                            if (auxI2 == auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA == : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA == : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Int && arg2Float) {
                            if (auxF2 == auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA == : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Int) {
                            if (auxI2 == auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA == : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Float) {
                            if (auxF2 == auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA == : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "!="
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////

                    case "!=" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }
                        //GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true;}
                        else if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true;}
                        //LOCALES
                        else if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true;}
                        else if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true;}
                        //TEMPORALES
                        else if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true;}
                        else if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true;}
                        //CONSTANTES
                        else if (dirArg1 >= 11000 && dirArg1 < 12000) { auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;}
                        else if (dirArg1 >= 12000 && dirArg1 < 13000) { auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        // ARRAYS
                        if (dirArg2 >= 14000 && dirArg2 < 15000) { dirArg2 = memLocal.arrayPointersTemporales[dirArg2-14000]; }
                        //GLOBALES
                        if (dirArg2 >= 1000 && dirArg2 < 2000) { auxI2 = memGlobal.arrayInts[dirArg2-1000]; arg2Int = true;}
                        else if (dirArg2 >= 2000 && dirArg2 < 3000) { auxF2 = memGlobal.arrayFloats[dirArg2-2000]; arg2Float = true;}
                        //LOCALES
                        else if (dirArg2 >= 4000 && dirArg2 < 5000) { auxI2 = memLocal.arrayInts[dirArg2-4000]; arg2Int = true;}
                        else if (dirArg2 >= 5000 && dirArg2 < 6000) { auxF2 = memLocal.arrayFloats[dirArg2-5000]; arg2Float = true;}
                        //TEMPORALES
                        else if (dirArg2 >= 7000 && dirArg2 < 8000) { auxI2 = memLocal.arrayIntsTemporales[dirArg2-7000]; arg2Int = true;}
                        else if (dirArg2 >= 8000 && dirArg2 < 9000) { auxF2 = memLocal.arrayFloatsTemporales[dirArg2-8000]; arg2Float = true;}
                        //CONSTANTES
                        else if (dirArg2 >= 11000 && dirArg2 < 12000) { auxI2 = Integer.parseInt(tablaConst.get(dirArg2)); arg2Int = true;}
                        else if (dirArg2 >= 12000 && dirArg2 < 13000) { auxF2 = Float.parseFloat(tablaConst.get(dirArg2)); arg2Float = true;}

                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (arg1Int && arg2Int) {
                            if (auxI2 != auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA != : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA != : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Int && arg2Float) {
                            if (auxF2 != auxI1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA != : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Int) {
                            if (auxI2 != auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA != : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        else if (arg1Float && arg2Float) {
                            if (auxF2 != auxF1) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; System.out.println( "RESPUESTA < : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); }
                            else { memLocal.arrayBoolsTemporales[dirResultado-10000] = false; System.out.println( "RESPUESTA != : " + memLocal.arrayBoolsTemporales[dirResultado-10000]); } }
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "&"
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////

                    case "&" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;
                        auxB1 = false; auxB2 = false;
                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        auxB1 = memLocal.arrayBoolsTemporales[dirArg1-10000];
                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        auxB2 = memLocal.arrayBoolsTemporales[dirArg2-10000];
                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if(auxB1 && auxB2) { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; }
                        else{ memLocal.arrayBoolsTemporales[dirResultado-10000] = false; }
                        ip++;
                        break;

                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////       Caso "|"
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    /////////////////////////////////////////////////////////////////////////////////////////////

                    case "|" :
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirArg2 = Integer.parseInt(aux.arg2.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxI2 = 0; auxF1 = 0; auxF2 = 0;
                        arg1Int = false; arg1Float = false; arg2Int = false; arg2Float = false;
                        auxB1 = false; auxB2 = false;
                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 1
                        auxB1 = memLocal.arrayBoolsTemporales[dirArg1-10000];
                        ///////////////////////////////////////////////////////////////////////////////////////////// ARG 2
                        auxB2 = memLocal.arrayBoolsTemporales[dirArg2-10000];
                        ///////////////////////////////////////////////////////////////////////////////////////////// RESULTADO
                        if (!auxB1 && !auxB2){ memLocal.arrayBoolsTemporales[dirResultado-10000] = false; }
                        else { memLocal.arrayBoolsTemporales[dirResultado-10000] = true; }
                        ip++;
                        break;


                    case "=":
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxF1 = 0; auxC1 = 'a';
                        arg1Int = false; arg1Float = false; arg1Char = false;

                        // ARRAYS
                        if (dirArg1 >= 14000 && dirArg1 < 15000) { dirArg1 = memLocal.arrayPointersTemporales[dirArg1-14000]; }

                        //CONSTANTES
                        if (dirArg1 >= 11000 && dirArg1 < 14000)
                        {
                            switch (aux.resultado.kind)
                            {
                                case 4:  auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;
                                    break;
                                case 5:  auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;
                                    break;
                                case 6:  auxString = tablaConst.get(dirArg1);
                                    res = auxString.charAt(0);
                                    arg1Char = true;

                                    break;

                                case 38: auxI1 = Integer.parseInt(tablaConst.get(dirArg1)); arg1Int = true;
                                    break;
                                case 39: auxF1 = Float.parseFloat(tablaConst.get(dirArg1)); arg1Float = true;
                                    break;
                                case 41: auxString = tablaConst.get(dirArg1);
                                    auxC1 = auxString.charAt(1);
                                    arg1Char = true;
                                    break;
                                default:  System.out.println("ALgo salio mal " + aux.resultado.kind);
                                    break;
                            }
                        }

                        //ARG1 GLOBALES
                        if (dirArg1 >= 1000 && dirArg1 < 2000) { auxI1 = memGlobal.arrayInts[dirArg1-1000]; arg1Int = true; }
                        if (dirArg1 >= 2000 && dirArg1 < 3000) { auxF1 = memGlobal.arrayFloats[dirArg1-2000]; arg1Float = true; }
                        if (dirArg1 >= 3000 && dirArg1 < 4000) { auxC1 = memGlobal.arrayChars[dirArg1-3000]; arg1Char = true; }

                        //ARG1 LOCALES
                        if (dirArg1 >= 4000 && dirArg1 < 5000) { auxI1 = memLocal.arrayInts[dirArg1-4000]; arg1Int = true; }
                        if (dirArg1 >= 5000 && dirArg1 < 6000) { auxF1 = memLocal.arrayFloats[dirArg1-5000]; arg1Float = true; }
                        if (dirArg1 >= 6000 && dirArg1 < 7000) { auxC1 = memLocal.arrayChars[dirArg1-6000]; arg1Char = true; }

                        //TEMPORALES
                        if (dirArg1 >= 7000 && dirArg1 < 8000) { auxI1 = memLocal.arrayIntsTemporales[dirArg1-7000]; arg1Int = true; }
                        if (dirArg1 >= 8000 && dirArg1 < 9000) { auxF1 = memLocal.arrayFloatsTemporales[dirArg1-8000]; arg1Float = true; }
                        if (dirArg1 >= 9000 && dirArg1 < 10000) { auxC1 = memLocal.arrayCharsTemporales[dirArg1-9000]; arg1Char = true; }


                        //////////////////////////////////////////////////////////////////////////// RESULTADO

                        //ARRAYS
                        if (dirResultado >= 14000 && dirResultado < 15000) { dirResultado =  memLocal.arrayPointersTemporales[dirResultado-14000]; }

                        //ARG1 GLOBALES
                        if (dirResultado >= 1000 && dirResultado < 2000) { memGlobal.arrayInts[dirResultado-1000] = auxI1; }
                        if (dirResultado >= 2000 && dirResultado < 3000) {
                            if (arg1Int) { memGlobal.arrayFloats[dirResultado-2000] = auxI1; }
                            else if(arg1Float) { memGlobal.arrayFloats[dirResultado-2000] = auxF1; } }
                        if (dirResultado >= 3000 && dirResultado < 4000) { memGlobal.arrayChars[dirResultado-3000] = auxC1; }

                        //ARG1 LOCALES
                        if (dirResultado >= 4000 && dirResultado < 5000) { memLocal.arrayInts[dirResultado-4000] = auxI1; }
                        if (dirResultado >= 5000 && dirResultado < 6000) {
                            if (arg1Int) { memGlobal.arrayFloats[dirResultado-5000] = auxI1; }
                            else if(arg1Float) { memGlobal.arrayFloats[dirResultado-5000] = auxF1; } }
                        if (dirResultado >= 6000 && dirResultado < 7000) { memLocal.arrayChars[dirResultado-6000] = auxC1; }

                        //TEMPORALES
                        if (dirResultado >= 7000 && dirResultado < 8000) { memLocal.arrayIntsTemporales[dirResultado-7000] = auxI1; }
                        if (dirResultado >= 8000 && dirResultado < 9000) {
                            if (arg1Int) { memGlobal.arrayFloats[dirResultado-8000] = auxI1; }
                            else if(arg1Float) { memGlobal.arrayFloats[dirResultado-8000] = auxF1; } }
                        if (dirResultado >= 9000 && dirResultado < 10000) { memLocal.arrayCharsTemporales[dirResultado-6000] = auxC1; }


                        ip++;
                        break;
                    default:
                        ip++;
                        break;
                }
        }

    }

    public static String getText()
    {
        return text;
    }

    public static void printNumVarsGlobal()
    {
        System.out.println("MAQUINA VIRTUAL Num vars GLOBAL : " + varArrayGlobal[0] + " " + varArrayGlobal[1] + " " + varArrayGlobal[2] + " " + varArrayGlobal[3] + " ");
    }

}
