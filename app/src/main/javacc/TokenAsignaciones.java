import java.io.PrintStream;
import java.util.Hashtable;
import java.lang.String;
import java.util.ArrayList;

class TokenAsignaciones
{
	  //Variable para validar asignaciones a caracteres(ichr)
	  public static int segunda = 0;
	  //Tabla que almacenara los tokens declarados globalmente
	  private static Hashtable tablaCarsGlobal = new Hashtable();

	  //Tabla que almacenara las funciones declaradas
	  private static Hashtable tablaFunc = new Hashtable();
	  
	  //Listas que guardaran los tipos compatibles de las variables
	  private static ArrayList<Integer> intComp = new ArrayList();
	  private static ArrayList<Integer> decComp = new ArrayList();
	  //private static ArrayList<Integer> strComp = new ArrayList();
	  private static ArrayList<Integer> chrComp = new ArrayList();
	  
												//variable		//tipoDato
	public static void InsertarSimbolo(Token identificador, int tipo, Token nombreFuncion)
	{
		//En este metodo se agrega a la tabla de tokens el identificador que esta siendo declarado junto con su tipo de dato
		try 
		{
			tabla = tablaFunc.get(nombreFuncion.image);
			tabla.put(identificador.image, tipo);

		}
		catch(Exception e)
		{
			//Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
			return "Error: La funcion " + identificador.image + " No ha sido declarada \r\nLinea: "
		}

	}

	public static void InsertarSimboloGlobal(Token identificador, int tipo)
	{
		//En este metodo se agrega a la tabla de tokens el identificador que esta siendo declarado junto con su tipo de dato
		tablaVarsGlobal.put(identificador.image, tipo);

	}

	public static void InsertarFuncion(Token identificador, int tipo)
	{
		//En este metodo se agrega a la tabla de funciones el identificador que esta siendo declarado junto con su tipo de dato
		private static Hashtable tabla = new Hashtable();
		tabla.put(identificador.image, tipo, tabla);
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
		intComp.add(37);
		
		decComp.add(4);
		decComp.add(5);
		decComp.add(37);
		decComp.add(38);
		
		chrComp.add(6);
		chrComp.add(42);
		
		//strComp.add(47);
		//strComp.add(51);
	}
 
	public static String checkAsing(Token TokenIzq, Token TokenAsig, Token nombreFuncion)
	{
		//variables en las cuales se almacenara el tipo de dato del identificador y de las asignaciones (n1(tipoIdent1) = 2(tipoIdent2) + 3(tipoIdent2))
		int tipoIdent1;
		int tipoIdent2;		
							/* De la tabla obtenemos el tipo de dato del identificador  
								asi como, si el token enviado es diferente a algun tipo que no se declara como los numeros(37), los decimales(38),
								caracteres(42)
								entonces tipoIdent1 = tipo_de_dato, ya que TokenAsig es un dato*/
		if(TokenIzq.kind != 37 && TokenIzq.kind != 38)		
		{
			try 
			{
				tabla = tablaFunc.get(nombreFuncion.image);
				try 
				{
					//Si el TokenIzq.image existe dentro de la tabla de tokens, entonces tipoIdent1 toma el tipo de dato con el que TokenIzq.image fue declarado
					tipoIdent1 = (Integer)tabla.get(TokenIzq.image);	
				}
				catch(Exception e)
				{
					//Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
					return "Error: El identificador " + TokenIzq.image + " No ha sido declarado \r\nLinea: " + TokenIzq.beginLine;
				}
			}
			catch(Exception e)
			{
				//Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
				return "Error: La funcion " + identificador.image + " No ha sido declarada \r\nLinea: "
			}
		}
		else 		
			tipoIdent1 = 0;
			
		//TokenAsig.kind != 37 && TokenAsig.kind != 38 && TokenAsig.kind != 42
		if(TokenAsig.kind == 39)	
		{
			/*Si el tipo de dato que se esta asignando, es algun identificador(kind == 39) 
			se obtiene su tipo de la tabla de tokens para poder hacer las comparaciones*/
			try 
			{
				tabla = tablaFunc.get(nombreFuncion.image);
				try
				{
					tipoIdent2 = (Integer)tabla.get(TokenAsig.image);
				}
				catch(Exception e)
				{
					//si el identificador no existe manda el error
					return "Error: El identificador " + TokenAsig.image + " No ha sido declarado \r\nLinea: " + TokenIzq.beginLine;
				}
			}
			catch(Exception e)
			{
				//Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
				return "Error: La funcion " + identificador.image + " No ha sido declarada \r\nLinea: "
			}
		}
				//Si el dato es entero(37) o decimal(38) o caracter(42)
				//tipoIdent2 = tipo_del_dato
		else if(TokenAsig.kind == 37 || TokenAsig.kind == 38 || TokenAsig.kind == 42)
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
		try 
		{
			tabla = tablaFunc.get(nombreFuncion.image);
			try
			{
				//Intenta obtener el token a verificar(checkTok) de la tabla de los tokens
				int tipoIdent1 = (Integer)tabla.get(checkTok.image);
				return " ";
			}
			catch(Exception e)
			{
				//Si no lo puede obtener, manda el error
				return "Error: El identificador " + checkTok.image + " No ha sido declarado \r\nLinea: " + checkTok.beginLine;
			}
		}
		catch(Exception e)
		{
			//Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
			return "Error: La funcion " + identificador.image + " No ha sido declarada \r\nLinea: "
		}
	}

	/*Metodo que verifica si una funcion ha sido declarada
		ej cuando hace una llamada*/ 
		public static String checkFuncion(Token nombreFuncion)
		{
			try 
			{
				tabla = tablaFunc.get(nombreFuncion.image);
				return " ";
			}
			catch(Exception e)
			{
				//Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens, el token no ha sido declarado, y se manda un error
				return "Error: La funcion " + identificador.image + " No ha sido declarada \r\nLinea: "
			}
		}

 }
  
  
  
  
  
  
  