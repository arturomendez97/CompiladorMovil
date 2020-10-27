package com.example.myapplication.TokenAsignaciones;
import java.lang.String;
import java.util.Hashtable;
import com.example.myapplication.parser.Token;


public class Quadruple {
    Object operator;
    Object arg1;
    Object arg2;
    Object resultado;

    public static void main(String[] args) {
        Quadruple myObj = new Quadruple();
    }

    void constructor(Object operator, Object arg1, Object arg2, Object Resultado) {

        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.resultado = resultado;

    }
}