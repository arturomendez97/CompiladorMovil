////////////////////////////////////////////////////////////////////////////////////////////////////
// Proyecto de Clase de Compiladores
// Proyecto Especial de Diseño de CompiladoresCOVID19AD20: En Pareja - Lenguaje Par++
// Elaborado por:
// Jorge Arturo Méndez Vargas - A01176369
// Jorge Adrían Ramos Barrena - A01176234
////////////////////////////////////////////////////////////////////////////////////////////////////
// CLASE CUADRUPLO
// ¿Que hace? - Contiene la estructura de los cuadruplos.
// ¿Que parametros recibe? - El operador, el primer argumento, el segundo argumento, y el resultado del cuadruplo.
// ¿Que genera como salida? - Un cuadruplo.
// Modulos más importantes que hacen uso de este archivo. - TokenAsignaciones, Comp.jj, y MaquinaVirtual.

package com.example.myapplication.TokenAsignaciones;
import java.lang.String;
import java.util.Hashtable;
import com.example.myapplication.parser.Token;


public class Quadruple {
    public String operator;
    public Token arg1;
    public Token arg2;
    public Token resultado;

    //public static void main(String[] args) {
        //Quadruple myObj = new Quadruple();
    //}

    public Quadruple(String op, Token ar1, Token ar2, Token res) {

        operator = op;
        arg1 = ar1;
        arg2 = ar2;
        resultado = res;

    }

    public void print(int num){
        System.out.println(num +". [" + operator + " " + arg1 + " " + arg2 + " " + resultado + "]");
    }
}