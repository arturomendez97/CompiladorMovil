package com.example.myapplication.TokenAsignaciones;


import android.os.Environment;

import com.example.myapplication.parser.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;


class MemoriaGlobal {
    int[] arrayInts;
    float[] arrayFloats;
    char[] arrayChars;

    MemoriaGlobal(int numInts, int numFloats, int numChars){

        arrayInts = new int[numInts];
        arrayFloats = new float[numFloats];
        arrayChars = new char[numChars];
    }
}

class MemoriaLocal {
    int[] arrayInts;
    float[] arrayFloats;
    char[] arrayChars;
    boolean[] arrayBools;

    int[] arrayIntsTemporales;
    float[] arrayFloatsTemporales;
    char[] arrayCharsTemporales;
    boolean[] arrayBoolsTemporales;


    MemoriaLocal(int numInts, int numFloats, int numChars, int numBools, int numIntsTemporales, int numFloatsTemporales, int numCharsTemporales, int numBoolsTemporales){
        arrayInts = new int[numInts];
        arrayFloats = new float[numFloats];
        arrayChars = new char[numChars];
        arrayBools = new boolean[numBools];

        arrayIntsTemporales = new int[numIntsTemporales];
        arrayFloatsTemporales = new float[numFloatsTemporales];
        arrayCharsTemporales = new char[numCharsTemporales];
        arrayBoolsTemporales = new boolean[numBoolsTemporales];
    }
}

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

    private static Hashtable<String, Tipo_Dir> tablaVarsGlobal = new Hashtable();
    private static int[] varArrayGlobal = new int[]{0, 0, 0, 0};
    //Tabla que almacenara las funciones declaradas
    private static Hashtable<String,CustomHash> tablaFunc = new Hashtable<String,CustomHash>();
    private static Hashtable<Integer, String> tablaConst = new Hashtable<Integer, String>();
    private static ArrayList<Quadruple> cuadruplos = new ArrayList<Quadruple>();
    private static int ip = 0;
    private static String text = "";

    private static Stack<MemoriaLocal> pilaMemorias = new Stack<MemoriaLocal>();




    public static void Comienza(Hashtable<String, Tipo_Dir> vGlobal, int[] arrGlobal, Hashtable<String, CustomHash> funcs, Hashtable<Integer, String> constantes, ArrayList<Quadruple> cuads)
    {
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
        MemoriaLocal memLocal = new MemoriaLocal(tabla.varArray[0], tabla.varArray[1], tabla.varArray[2], tabla.varArray[3], tabla.varArray[4], tabla.varArray[5], tabla.varArray[6], tabla.varArray[7]);


        //RECORRE CUADRUPLOS

        int contCuadruplo = 0;

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

        boolean arg1Int = false, arg1Float = false, arg2Int = false, arg2Float = false, arg1Char = false, arg2Char = false;

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

                        //CONSTANTES
                        if (dirArg1 >= 11000)
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

                        ip++;
                        break;
                    case "GOSUB" :
                        contCuadruplo = ip;
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        ip = dirResultado;
                        break;

                    case "ENDFUNC" :
                        //Rregresar a la memoria anterior
                        memLocal = pilaMemorias.pop();
                        ip = contCuadruplo+1;
                        break;
                    case "ERA" :
                        // Guardar memoria local actual en el stack y crear una nueva
                        auxString = aux.resultado.image;
                        CustomHash tablaAux;
                        tablaAux = tablaFunc.get(auxString);
                        MemoriaLocal memLocal2 = new MemoriaLocal(tablaAux.varArray[0], tablaAux.varArray[1], tablaAux.varArray[2], tablaAux.varArray[3], tablaAux.varArray[4], tablaAux.varArray[5], tablaAux.varArray[6], tablaAux.varArray[7]);
                        pilaMemorias.push(memLocal);
                        memLocal = memLocal2;
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
                        // GLOBALES
                        if (dirResultado >= 1000 && dirResultado < 2000) { intValue = memGlobal.arrayInts[dirResultado-1000]; text += Integer.toString(intValue); }
                        else if (dirResultado >= 2000 && dirResultado < 3000) { floatValue = memGlobal.arrayFloats[dirResultado-2000]; text += Float.toString(floatValue); }
                        else if (dirResultado >= 3000 && dirResultado < 4000) { charValue = memGlobal.arrayChars[dirResultado-3000]; text += charValue; }
                        //LOCALES
                        if (dirResultado >= 4000 && dirResultado < 5000) { intValue = memLocal.arrayInts[dirResultado-4000]; text += Integer.toString(intValue); }
                        else if (dirResultado >= 5000 && dirResultado < 6000) { floatValue = memLocal.arrayFloats[dirResultado-5000]; text += Float.toString(floatValue); }
                        else if (dirResultado >= 6000 && dirResultado < 7000) { charValue = memLocal.arrayChars[dirResultado-6000]; text += charValue; }
                        // CONSTANTES
                        else if (dirResultado >= 13000) { auxString = tablaConst.get(dirResultado); constChar = auxString.charAt(1); text += constChar; }
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
                        if (arg1Int && arg2Int) { memLocal.arrayIntsTemporales[dirResultado-7000] = auxI2+auxI1; System.out.println( "RESPUESTA + : " + memLocal.arrayIntsTemporales[dirResultado-7000]);}
                        else if (arg1Int && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2+auxI1; System.out.println( "RESPUESTA + : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        else if (arg1Float && arg2Int) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxI2+auxF1; System.out.println( "RESPUESTA + : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
                        else if (arg1Float && arg2Float) { memLocal.arrayFloatsTemporales[dirResultado-8000] = auxF2+auxF1; System.out.println( "RESPUESTA + : " + memLocal.arrayFloatsTemporales[dirResultado-8000]);}
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


                    case "=":
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        auxI1 = 0; auxF1 = 0; auxC1 = 'a';
                        arg1Int = false; arg1Float = false; arg1Char = false;

                        //CONSTANTES
                        if (dirArg1 >= 11000)
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
