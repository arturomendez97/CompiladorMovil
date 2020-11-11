package com.example.myapplication.TokenAsignaciones;


import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;


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


    MemoriaLocal(int numInts, int numFloats, int numChars, int numBools){

        arrayInts = new int[numInts];
        arrayFloats = new float[numFloats];
        arrayChars = new char[numChars];
        arrayBools = new boolean[numBools];
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

        //RECORRE CUADRUPLOS

        Quadruple aux;
        int constInt;
        float constFloat;
        char constChar;
        boolean constBool;
        String auxString;


        int dirResultado;
        int dirArg1;

        int intValue;
        float floatValue;
        char charValue;
        boolean boolValue;

        while (ip < cuadruplos.size())
        {
            aux = cuadruplos.get(ip);

                switch (aux.operator)
                {
                    case "GOTO" :
                        ip = Integer.parseInt(aux.resultado.image);
                        break;
                    case "write":
                        dirResultado = Integer.parseInt(aux.resultado.image);

                        if (dirResultado >= 1000 && dirResultado < 2000)
                        {
                            intValue = memGlobal.arrayInts[dirResultado-1000];
                            text += Integer.toString(intValue);
                        }
                        else if (dirResultado >= 2000 && dirResultado < 3000)
                        {
                            floatValue = memGlobal.arrayFloats[dirResultado-2000];
                            text += Float.toString(floatValue);
                        }
                        else if (dirResultado >= 3000 && dirResultado < 4000)
                        {
                            charValue = memGlobal.arrayChars[dirResultado-3000];
                            text += charValue;
                        }
                        else if (dirResultado >= 13000)
                        {
                            auxString = tablaConst.get(dirResultado);
                            constChar = auxString.charAt(1);
                            text += constChar;
                        }
                        text += "\r\n";
                        ip++;

                        break;
                    case "=":
                        dirResultado = Integer.parseInt(aux.resultado.image);
                        dirArg1 = Integer.parseInt(aux.arg1.image);
                        if (Integer.parseInt(aux.arg1.image) >= 11000)
                        {


                            switch (aux.resultado.kind)
                            {
                                case 4:  constInt = Integer.parseInt(tablaConst.get(dirArg1));
                                    memGlobal.arrayInts[dirResultado-1000] = constInt;
                                    break;
                                case 5:  constFloat = Float.parseFloat(tablaConst.get(dirArg1));
                                    memGlobal.arrayFloats[dirResultado-2000] = constFloat;
                                    break;
                                case 6:  auxString = tablaConst.get(dirArg1);
                                    constChar = auxString.charAt(0);
                                    memGlobal.arrayFloats[dirResultado-3000] = constChar;
                                    break;

                                case 38: constInt = Integer.parseInt(tablaConst.get(dirArg1));
                                    memGlobal.arrayInts[dirResultado-1000] = constInt;
                                    break;
                                case 39: constFloat = Float.parseFloat(tablaConst.get(dirArg1));
                                    memGlobal.arrayFloats[dirResultado-2000] = constFloat;

                                    break;
                                case 41: auxString = tablaConst.get(dirArg1);
                                    constChar = auxString.charAt(1);
                                    memGlobal.arrayChars[dirResultado-3000] = constChar;
                                    break;
                                default:  System.out.println("ALgo salio mal " + aux.resultado.kind);
                                    break;
                            }

                        }
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
