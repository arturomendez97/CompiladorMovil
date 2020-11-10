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
    private static Hashtable<String, Integer> tablaConst = new Hashtable<String, Integer>();
    private static ArrayList<Quadruple> cuadruplos = new ArrayList<Quadruple>();
    private static int ip = 0;



    public static void Comienza(Hashtable<String, Tipo_Dir> vGlobal, int[] arrGlobal, Hashtable<String, CustomHash> funcs, Hashtable<String, Integer> constantes, ArrayList<Quadruple> cuads)
    {
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
        while (ip < cuadruplos.size())
        {
            aux = cuadruplos.get(ip);

                switch (aux.operator)
                {
                    case "GOTO" :
                        ip = Integer.parseInt(aux.resultado.image);
                    case "write":
                        break;
                    default: break;
                }
        }

    }

    public static void printNumVarsGlobal()
    {
        System.out.println("MAQUINA VIRTUAL Num vars GLOBAL : " + varArrayGlobal[0] + " " + varArrayGlobal[1] + " " + varArrayGlobal[2] + " " + varArrayGlobal[3] + " ");
    }

}
