/* comp.java */
/* Generated By:JavaCC: Do not edit this line. comp.java */
package com.example.myapplication.parser;
import com.example.myapplication.TokenAsignaciones.TokenAsignaciones;
import com.example.myapplication.TokenAsignaciones.Quadruple;


public class comp implements compConstants {

////////////////////////////////////////////////////////////////////////////
///////////////////////////////Gramática////////////////////////////////////


//////////////PROGRAMA/////////////////
  static final public void Programa() throws ParseException {TokenAsignaciones.SetTables();
        ///cuboSemantico.constructor();
        Token var;
    jj_consume_token(PROGRAMA);
    jj_consume_token(ID);
    jj_consume_token(SEMICOLON);
    Mis_Vars_Global();
    Mis_Func();
    Main();
    jj_consume_token(0);
///cuboSemantico.printCombination();
        System.out.println("Pila OP: " + TokenAsignaciones.returnPilaOP());
        System.out.println("Pila VP: " + TokenAsignaciones.returnPilaVP());
        TokenAsignaciones.emptyPilaOP();
        TokenAsignaciones.emptyPilaVP();
  }

//////////////MIS_VARS_GLOBAL/////////////////
  static final public void Mis_Vars_Global() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VAR:{
      Vars_Global();
      break;
      }
    default:
      jj_la1[0] = jj_gen;
      Empty();
    }
  }

  static final public void Vars_Global() throws ParseException {
    jj_consume_token(VAR);
    Vars2_Global();
  }

  static final public void Vars2_Global() throws ParseException {int td;
        Token var;
    td = Tipo();
    var = jj_consume_token(ID);
TokenAsignaciones.InsertarSimboloGlobal(var, td);
    Dim();
    MasV_Global(td);
    jj_consume_token(SEMICOLON);
    MasT_Global();
  }

  static final public void MasV_Global(int td) throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      var = jj_consume_token(ID);
TokenAsignaciones.InsertarSimboloGlobal(var, td);
      Dim();
      MasV_Global(td);
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      Empty();
    }
  }

  static final public void MasT_Global() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:
    case FLOAT:
    case CHAR:{
      Vars2_Global();
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      Empty();
    }
  }

//////////////MIS_VARS/////////////////
  static final public void Mis_Vars(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VAR:{
      Vars(func);
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      Empty();
    }
  }

  static final public void Vars(Token func) throws ParseException {
    jj_consume_token(VAR);
    Vars2(func);
  }

  static final public void Vars2(Token func) throws ParseException {int td;
        Token var;
        String res;
    td = Tipo();
    var = jj_consume_token(ID);
res = TokenAsignaciones.InsertarSimbolo(var, td, func);

                if(res != " ")
                {
                    {if (true) throw new ParseException(res);}
                }
    Dim();
    MasV(td, func);
    jj_consume_token(SEMICOLON);
    MasT(func);
  }

  static final public void Dim() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BRACKETIZQ:{
      jj_consume_token(BRACKETIZQ);
      jj_consume_token(CTEI);
      jj_consume_token(BRACKETDER);
      MasDim();
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      Empty();
    }
  }

  static final public void MasDim() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BRACKETIZQ:{
      jj_consume_token(BRACKETIZQ);
      jj_consume_token(CTEI);
      jj_consume_token(BRACKETDER);
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      Empty();
    }
  }

  static final public void Dim_Expresion(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BRACKETIZQ:{
      jj_consume_token(BRACKETIZQ);
      Expresion(func);
      jj_consume_token(BRACKETDER);
      MasDim_Expresion(func);
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      Empty();
    }
  }

  static final public void MasDim_Expresion(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BRACKETIZQ:{
      jj_consume_token(BRACKETIZQ);
      Expresion(func);
      jj_consume_token(BRACKETDER);
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      Empty();
    }
  }

  static final public void MasV(int td, Token func) throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      var = jj_consume_token(ID);
TokenAsignaciones.InsertarSimbolo(var, td, func);
      MasV(td ,func);
      break;
      }
    default:
      jj_la1[8] = jj_gen;
      Empty();
    }
  }

  static final public void MasT(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:
    case FLOAT:
    case CHAR:{
      Vars2(func);
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      Empty();
    }
  }

//////////////MIS_FUNC/////////////////
  static final public void Mis_Func() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MODULE:{
      Funcion();
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      Empty();
    }
  }

  static final public void Funcion() throws ParseException {int td;
        Token func;
        String res;
    jj_consume_token(MODULE);
    td = Func1();
    func = jj_consume_token(ID);
res = TokenAsignaciones.InsertarFuncion(func, td);

                if(res != " ")
                {
                    {if (true) throw new ParseException(res);}
                }
    jj_consume_token(PARENIZQ);
    Func2(func);
    jj_consume_token(PARENDER);
    Func3(func);
    jj_consume_token(CURLYIZQ);
    Func4(func);
    jj_consume_token(CURLYDER);
    Mas_F();
  }

  static final public int Func1() throws ParseException {int td;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:
    case FLOAT:
    case CHAR:
    case VOID:{
      td = Tipo_Retorno();
{if ("" != null) return td;}
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      Empty();
    }
    throw new Error("Missing return statement in function");
  }

  static final public void Func2(Token func) throws ParseException {
    Parametros_Func(func);
  }

  static final public void Func3(Token func) throws ParseException {
    Mis_Vars(func);
  }

  static final public void Func4(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case RETURN:
    case READ:
    case WRITE:
    case IF:
    case WHILE:
    case FOR:
    case CALL:
    case ID:{
      Estatuto(func);
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      Empty();
    }
  }

  static final public void Mas_F() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MODULE:{
      Funcion();
      break;
      }
    default:
      jj_la1[13] = jj_gen;
      Empty();
    }
  }

//////////////TIPO_RETORNO/////////////////
  static final public int Tipo_Retorno() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:{
      jj_consume_token(INT);
{if ("" != null) return 4;}
      break;
      }
    case FLOAT:{
      jj_consume_token(FLOAT);
{if ("" != null) return 5;}
      break;
      }
    case CHAR:{
      jj_consume_token(CHAR);
{if ("" != null) return 6;}
      break;
      }
    case VOID:{
      jj_consume_token(VOID);
{if ("" != null) return 7;}
      break;
      }
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//////////////MAIN/////////////////
  static final public void Main() throws ParseException {Token func;
    func = jj_consume_token(MAIN);
TokenAsignaciones.InsertarMain(func);
    jj_consume_token(PARENIZQ);
    jj_consume_token(PARENDER);
    jj_consume_token(CURLYIZQ);
    Main2(func);
    jj_consume_token(CURLYDER);
  }

  static final public void Main2(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case RETURN:
    case READ:
    case WRITE:
    case IF:
    case WHILE:
    case FOR:
    case CALL:
    case ID:{
      Estatuto(func);
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      Empty();
    }
  }

//////////////PARAMETROS_FUNC/////////////////
  static final public 
void Parametros_Func(Token func) throws ParseException {int td;
    Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:
    case FLOAT:
    case CHAR:{
      td = Tipo();
      var = jj_consume_token(ID);
TokenAsignaciones.InsertarSimbolo(var, td, func);
      Dim_Expresion(func);
      Params_Func(func);
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      Empty();
    }
  }

  static final public void Params_Func(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      Parametros_Func(func);
      break;
      }
    default:
      jj_la1[17] = jj_gen;
      Empty();
    }
  }

//////////////PARAMETROS/////////////////
  static final public 
void Parametros(Token func) throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CALL:
    case PARENIZQ:
    case CTEI:
    case CTEF:
    case ID:{
      Expresion(func);
      Params(func);
      break;
      }
    default:
      jj_la1[18] = jj_gen;
      Empty();
    }
  }

  static final public void Params(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      Parametros(func);
      break;
      }
    default:
      jj_la1[19] = jj_gen;
      Empty();
    }
  }

//////////////LECTURA/////////////////
  static final public void Lectura(Token func) throws ParseException {
    jj_consume_token(READ);
    jj_consume_token(PARENIZQ);
    Lectura2(func);
    jj_consume_token(PARENDER);
    jj_consume_token(SEMICOLON);
  }

  static final public void Lectura2(Token func) throws ParseException {
    Variable(func);
    Lectura3(func);
  }

  static final public void Lectura3(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      Lectura2(func);
      break;
      }
    default:
      jj_la1[20] = jj_gen;
      Empty();
    }
  }

//////////////ESCRITURA/////////////////
  static final public void Escritura(Token func) throws ParseException {
    jj_consume_token(WRITE);
    jj_consume_token(PARENIZQ);
    Escritura2(func);
    jj_consume_token(PARENDER);
    jj_consume_token(SEMICOLON);
  }

  static final public void Escritura2(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMILLA:{
      Letrero();
      Escritura3(func);
      break;
      }
    case CALL:
    case PARENIZQ:
    case CTEI:
    case CTEF:
    case ID:{
      Expresion(func);
      Escritura3(func);
      break;
      }
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void Escritura3(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case COMMA:{
      jj_consume_token(COMMA);
      Escritura2(func);
      break;
      }
    default:
      jj_la1[22] = jj_gen;
      Empty();
    }
  }

  static final public void Letrero() throws ParseException {
    jj_consume_token(COMILLA);
    jj_consume_token(ID);
    jj_consume_token(COMILLA);
  }

//////////////ESTATUTO DE DECISION/////////////////
  static final public void Estatuto_De_Decision(Token func) throws ParseException {
    jj_consume_token(IF);
    jj_consume_token(PARENIZQ);
    Expresion(func);
    jj_consume_token(PARENDER);
    jj_consume_token(THEN);
    jj_consume_token(CURLYIZQ);
    EDD2(func);
    jj_consume_token(CURLYDER);
    EDD3(func);
  }

  static final public void EDD2(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case RETURN:
    case READ:
    case WRITE:
    case IF:
    case WHILE:
    case FOR:
    case CALL:
    case ID:{
      Estatuto(func);
      break;
      }
    default:
      jj_la1[23] = jj_gen;
      Empty();
    }
  }

  static final public void EDD3(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ELSE:{
      jj_consume_token(ELSE);
      jj_consume_token(CURLYIZQ);
      EDD2(func);
      jj_consume_token(CURLYDER);
      break;
      }
    default:
      jj_la1[24] = jj_gen;
      Empty();
    }
  }

//////////////ASIGNACION/////////////////
  static final public 
void Asignacion(Token func) throws ParseException {Token var;
Token var2;
    Variable(func);
    jj_consume_token(ASIGNACION);
    Expresion(func);
    jj_consume_token(SEMICOLON);
  }

/*

void Asignacion2(Token func, int tipo2) :
{}
{
        Asignacion(func, tipo2)
     |  Empty()
}

//// throw new ParseException(res);
//// res = TokenAsignaciones.checkAsing(v1, v2, func);

/*
void Asignacion( Token func) :
{
	int td;
	Token var;
	Token var2;
}
{
    var = Variable()
    <ASIGNACION> var2= <ID>
    Asignacion2(var, var2, func)
}

void Asignacion2(Token v1, Token v2, Token func) :
{
	Token v3;
	String res;
	boolean imp = false;
}
{
        <PARENIZQ>Parametros(func)<PARENDER>
        {
        res = TokenAsignaciones.checkAsing(v1, v2, func);
        if(res != " ")
        {
            throw new ParseException(res);
            imp = true;
        }
        }

        Asignacion3(func)

    |   Expresion(func)
        v3 = Asignacion3(func)
        {
         res = TokenAsignaciones.checkAsing(v2, v3, func);
                if(res != " ")
        		{
        			System.out.println(res);
        			imp = true;
        		}
                res = TokenAsignaciones.checkAsing(v1, v2, func);
                if(res != " ")
        		{
        			System.out.println(res);
        			imp = true;
        		}
        }

}

Token Asignacion3(Token func) :
{
    Token v1;
}
{
        <SEMICOLON>
        v1 = Asignacion4(func)
        {
         return v1;
        }

}
Token Asignacion4(Token func) :
{
    Token v1;
    Token v2;
    String res;
    boolean imp = false;
}
{
        v1 = Expresion(func)
        v2 = Asignacion3(func)
        {
        res = TokenAsignaciones.checkAsing(v1, v2, func);

        		if(res != " ")
        		{
        			System.out.println(res);
        			imp = true;
        		}
                return v1;
        }
      | Empty() {return func;}
}*/

//////////////VARIABLE/////////////////
  static final public Token Variable(Token func) throws ParseException {Token var;
    String res;
    boolean imp = false;
    var = jj_consume_token(ID);
res = TokenAsignaciones.checkVariable(var, func);

        if(res != " ")
        {
            {if (true) throw new ParseException(res);}
            imp = true;
        }

        TokenAsignaciones.pushPilaVP(var);
        System.out.println(TokenAsignaciones.returnPilaVP());
    Dim_Expresion(func);
{if ("" != null) return var;}
    throw new Error("Missing return statement in function");
  }

//////////////TIPO/////////////////
  static final public int Tipo() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case INT:{
      jj_consume_token(INT);
{if ("" != null) return 4;}
      break;
      }
    case FLOAT:{
      jj_consume_token(FLOAT);
{if ("" != null) return 5;}
      break;
      }
    case CHAR:{
      jj_consume_token(CHAR);
{if ("" != null) return 6;}
      break;
      }
    default:
      jj_la1[25] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//////////////RETORNO/////////////////
  static final public void Retorno(Token func) throws ParseException {
    jj_consume_token(RETURN);
    jj_consume_token(PARENIZQ);
    Expresion(func);
    jj_consume_token(PARENDER);
    jj_consume_token(SEMICOLON);
  }

//////////////CONDICIONAL/////////////////
  static final public void Condicional(Token func) throws ParseException {
    jj_consume_token(WHILE);
    jj_consume_token(PARENIZQ);
    Expresion(func);
    jj_consume_token(PARENDER);
    jj_consume_token(DO);
    jj_consume_token(CURLYIZQ);
    Condicional2(func);
    jj_consume_token(CURLYDER);
  }

  static final public void Condicional2(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case RETURN:
    case READ:
    case WRITE:
    case IF:
    case WHILE:
    case FOR:
    case CALL:
    case ID:{
      Estatuto(func);
      break;
      }
    default:
      jj_la1[26] = jj_gen;
      Empty();
    }
  }

//////////////NO_CONDICIONAL/////////////////
  static final public void No_condicional(Token func) throws ParseException {
    jj_consume_token(FOR);
    Variable(func);
    jj_consume_token(ASIGNACION);
    Expresion(func);
    jj_consume_token(TO);
    Expresion(func);
    jj_consume_token(DO);
    jj_consume_token(CURLYIZQ);
    No_condicional2(func);
    jj_consume_token(CURLYDER);
  }

  static final public void No_condicional2(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case RETURN:
    case READ:
    case WRITE:
    case IF:
    case WHILE:
    case FOR:
    case CALL:
    case ID:{
      Estatuto(func);
      break;
      }
    default:
      jj_la1[27] = jj_gen;
      Empty();
    }
  }

//////////////Llamada/////////////////
  static final public Token Llamada(Token func) throws ParseException {Token var;
    String res;
        boolean imp = false;
    jj_consume_token(CALL);
    var = jj_consume_token(ID);
res = TokenAsignaciones.checkFuncion(var);

        if(res != " ")
        {
            {if (true) throw new ParseException(res);}
            imp = true;
        }
    jj_consume_token(PARENIZQ);
    Llamada2(func);
    jj_consume_token(PARENDER);
{if ("" != null) return var;}
    throw new Error("Missing return statement in function");
  }

  static final public void Llamada2(Token func) throws ParseException {
    Parametros(func);
  }

//////////////ESTATUTO/////////////////
  static final public void Estatuto(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case ID:{
      Asignacion(func);
      Estatuto2(func);
      break;
      }
    case READ:{
      Lectura(func);
      Estatuto2(func);
      break;
      }
    case WRITE:{
      Escritura(func);
      Estatuto2(func);
      break;
      }
    case RETURN:{
      Retorno(func);
      Estatuto2(func);
      break;
      }
    case IF:{
      Estatuto_De_Decision(func);
      Estatuto2(func);
      break;
      }
    case WHILE:{
      Condicional(func);
      Estatuto2(func);
      break;
      }
    case FOR:{
      No_condicional(func);
      Estatuto2(func);
      break;
      }
    case CALL:{
      Llamada(func);
      jj_consume_token(SEMICOLON);
      Estatuto2(func);
      break;
      }
    default:
      jj_la1[28] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void Estatuto2(Token func) throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case RETURN:
    case READ:
    case WRITE:
    case IF:
    case WHILE:
    case FOR:
    case CALL:
    case ID:{
      Estatuto(func);
      break;
      }
    default:
      jj_la1[29] = jj_gen;
      Empty();
    }
  }

//////////////EXPRESION/////////////////
  static final public Token Expresion(Token func) throws ParseException {Token var;
    var = T_Exp(func);
    Expresion2(func);
if (TokenAsignaciones.checkPilaOP("|"))
            {
                TokenAsignaciones.pushPilaVP(TokenAsignaciones.popPilaOP());
            }
        {if ("" != null) return var;}
    throw new Error("Missing return statement in function");
  }

  static final public void Expresion2(Token func) throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case OR:{
      var = jj_consume_token(OR);
      Expresion(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    default:
      jj_la1[30] = jj_gen;
      Empty();
    }
  }

//////////////T_Exp/////////////////
  static final public Token T_Exp(Token func) throws ParseException {Token var;
    var = G_Exp(func);
    T_Exp2(func);
if (TokenAsignaciones.checkPilaOP("&"))
                {

                    TokenAsignaciones.pushPilaVP(TokenAsignaciones.popPilaOP());
                }
            {if ("" != null) return var;}
    throw new Error("Missing return statement in function");
  }

  static final public void T_Exp2(Token func) throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case AND:{
      var = jj_consume_token(AND);
      T_Exp(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    default:
      jj_la1[31] = jj_gen;
      Empty();
    }
  }

//////////////G_Exp/////////////////
  static final public Token G_Exp(Token func) throws ParseException {Token var;
    var = M_Exp(func);
    G_Exp2(func);
if (TokenAsignaciones.checkPilaOP("<") | TokenAsignaciones.checkPilaOP(">") | TokenAsignaciones.checkPilaOP("==") | TokenAsignaciones.checkPilaOP("!=") | TokenAsignaciones.checkPilaOP(">=") | TokenAsignaciones.checkPilaOP("<="))
               {
                   TokenAsignaciones.pushPilaVP(TokenAsignaciones.popPilaOP());
               }
           {if ("" != null) return var;}
    throw new Error("Missing return statement in function");
  }

  static final public void G_Exp2(Token func) throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MENOSQUE:{
      var = jj_consume_token(MENOSQUE);
      M_Exp(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    case MASQUE:{
      var = jj_consume_token(MASQUE);
      M_Exp(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    case IGUAL:{
      var = jj_consume_token(IGUAL);
      M_Exp(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    case NOIGUAL:{
      var = jj_consume_token(NOIGUAL);
      M_Exp(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    case MAYORIGUAL:{
      var = jj_consume_token(MAYORIGUAL);
      M_Exp(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    case MENORIGUAL:{
      var = jj_consume_token(MENORIGUAL);
      M_Exp(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    default:
      jj_la1[32] = jj_gen;
      Empty();
    }
  }

//////////////M_Exp/////////////////
  static final public Token M_Exp(Token func) throws ParseException {Token var;
    var = T(func);
    M_Exp2(func);
if (TokenAsignaciones.checkPilaOP("+") | TokenAsignaciones.checkPilaOP("-"))
                   {
                       TokenAsignaciones.pushPilaVP(TokenAsignaciones.popPilaOP());
                   }
               {if ("" != null) return var;}
    throw new Error("Missing return statement in function");
  }

  static final public void M_Exp2(Token func) throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MAS:{
      var = jj_consume_token(MAS);
      M_Exp(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    case MENOS:{
      var = jj_consume_token(MENOS);
      M_Exp(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    default:
      jj_la1[33] = jj_gen;
      Empty();
    }
  }

//////////////T(TERMINO)/////////////////
  static final public Token T(Token func) throws ParseException {Token var;
    var = F(func);
    T2(func);
if (TokenAsignaciones.checkPilaOP("*") | TokenAsignaciones.checkPilaOP("/"))
                       {

                           creaCuadruplo();
                       }
                   {if ("" != null) return var;}
    throw new Error("Missing return statement in function");
  }

// Esta función hace pop de la pila de operadores, y dos pops de la pila del vector polaco. Con esto llama al cubo Semántico para crear
// un temporal del tipo correspondiente. Crea el cuadruplo con esos 4 elementos, y guarda el temporal en la pila del vector polaco.
  static final public void creaCuadruplo() throws ParseException {Token op;
     Token arg1;
     Token arg2;
     Token temporal;
op = TokenAsignaciones.popPilaOP();
    arg1 = TokenAsignaciones.popPilaVP();
    arg2 = TokenAsignaciones.popPilaVP();
    temporal = op.newToken(op.kind);
    System.out.println("image: " + arg1 + " type: " + TokenAsignaciones.getTypeGlobal(arg1));
    System.out.println("image: " + arg2 + " type: " + TokenAsignaciones.getTypeGlobal(arg2));

    temporal.kind = TokenAsignaciones.getCuboType(TokenAsignaciones.getTypeGlobal(arg1), TokenAsignaciones.getTypeGlobal(arg2), op.image);

    System.out.println("temporal.kind: " + temporal.kind);

    if(temporal.kind == 0)
    {
        {if (true) throw new ParseException("Los argumentos: " + arg1.image + " y " + arg2.image + " no son compatibles.");}
    }

    temporal.image = String.valueOf(TokenAsignaciones.getContLocal(temporal.kind));
    TokenAsignaciones.pushPilaVP(temporal);
    Quadruple quad = new Quadruple(op, arg1, arg2, temporal);
    quad.print();
  }

  static final public void T2(Token func) throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MULT:{
      var = jj_consume_token(MULT);
      T(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    case DIV:{
      var = jj_consume_token(DIV);
      T(func);
TokenAsignaciones.pushPilaOP(var); System.out.println(TokenAsignaciones.returnPilaOP());
      break;
      }
    default:
      jj_la1[34] = jj_gen;
      Empty();
    }
  }

//////////////F(FACTOR)/////////////////
  static final public Token F(Token func) throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case PARENIZQ:{
      jj_consume_token(PARENIZQ);
      var = Expresion(func);
      jj_consume_token(PARENDER);
{if ("" != null) return var;}
      break;
      }
    case ID:{
      var = Variable(func);
{if ("" != null) return var;}
      break;
      }
    case CALL:{
      var = Llamada(func);
{if ("" != null) return var;}
      break;
      }
    case CTEI:
    case CTEF:{
      var = F2();
{if ("" != null) return var;}
      break;
      }
    default:
      jj_la1[35] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Token F2() throws ParseException {Token var;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CTEI:{
      var = jj_consume_token(CTEI);
{if ("" != null) return var;}
      break;
      }
    case CTEF:{
      var = jj_consume_token(CTEF);
{if ("" != null) return var;}
      break;
      }
    case ID:{
      var = jj_consume_token(ID);
{if ("" != null) return var;}
      break;
      }
    default:
      jj_la1[36] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

//////////////VACIO/////////////////
  static final public void Empty() throws ParseException {

  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public compTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[37];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x8,0x1000000,0x70,0x8,0x0,0x0,0x0,0x0,0x1000000,0x70,0x100,0xf0,0xa9e00,0x100,0xf0,0xa9e00,0x70,0x1000000,0x180000,0x1000000,0x1000000,0x180000,0x1000000,0xa9e00,0x4000,0x70,0xa9e00,0xa9e00,0xa9e00,0xa9e00,0x0,0x0,0x0,0x18000000,0x60000000,0x180000,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x400,0x400,0x400,0x400,0x0,0x0,0x0,0x0,0x100,0x0,0x0,0x100,0x0,0x0,0x1c0,0x0,0x0,0x11c0,0x0,0x100,0x0,0x0,0x100,0x100,0x100,0x100,0x20,0x10,0x600f,0x0,0x0,0x1c0,0x1c0,};
   }

  /** Constructor with InputStream. */
  public comp(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public comp(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new compTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public comp(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new compTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
      jj_input_stream = new SimpleCharStream(stream, 1, 1);
   } else {
      jj_input_stream.ReInit(stream, 1, 1);
   }
   if (token_source == null) {
      token_source = new compTokenManager(jj_input_stream);
   }

    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public comp(compTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(compTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 37; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[52];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 37; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 52; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
