

import java.lang.String;

public class cuboSemantico {
/// Se definen los diferentes operadores y tipos

    String types[] = new String[]{"int", "float", "char"};
    String operators[] = new String[]{"+", "-", "*", "/", "==", "!=", ">=", "<=", "<", ">"};
    String mathOP[] = new String[]{"+", "-", "*", "/"};
    String numTypes[] = new String[]{"int","float"};
    String charOP[] = new String[]{"+"};
    String charTypes[] = new String[]{"char","string"};
    String logOp[] = new String[]{"==", "!=", ">=", "<=", "<", ">"};
    String boolOp[] = new String[]{"==", "!="};
    String logTypes[] = new String[]{"bool"};
    private String[][][] cubo;

    /// private operators: Array<string> = ['+', '-', '*', '/', '<', '>', '==', '!=', '>=', '<='];
    // /private logOp: Array<string> = ['<', '>', '==', '!=', '>=', '<='];
   /// private cubo Object;
   public static void main(String[] args) {
       cuboSemantico cubo = new cuboSemantico();

   }

    void constructor() {


        for (int i = 0; i < this.types.length; i++) {
            this.cubo[i][0][0] = types[i];
            for (int j = 0; j < this.types.length; j++) {
                this.cubo[i][j][0] = types[j];
                for (int k = 0; k < this.operators.length; k++) {
                    this.cubo[i][j][k] = null;
                }
            }
        }
    }
    /// Funcion para aceder al tipo de cualquir renglon
    public String getType(int firstType, int secondType, String op){

       int indice_I;
       int indice_J;
       int indice_K;

        switch (firstType)
        {
            case 4:  indice_I = 0;
                break;
            case 5:  indice_I = 1;
                break;
            case 6:  indice_I = 2;
                break;
            default: indice_I = -1;
                break;
        }

        switch (secondType)
        {
            case 4:  indice_J = 0;
                break;
            case 5:  indice_J = 1;
                break;
            case 6:  indice_J = 2;
                break;
            default: indice_J = -1;
                break;
        }

        switch (op)
        {
            case "+":  indice_K = 0;
                break;
            case "-":  indice_K = 1;
                break;
            case "*":  indice_K = 2;
                break;
            case "/":  indice_K = 3;
                break;
            case "==":  indice_K = 4;
                break;
            case "!=":  indice_K = 5;
                break;
            case ">=":  indice_K = 6;
                break;
            case "<=":  indice_K = 7;
                break;
            case "<":  indice_K = 8;
                break;
            case ">":  indice_K = 9;
                break;
            default: indice_K = -1;
                break;
        }

        return this.cubo[indice_I][indice_J][indice_K];
    }
    /// Funcion para agregar una regla
    public  void insertTypeRule(String firstType, String secondType, String op,String result) {
        ///this.cubo[firstType][secondType][op] = result;
    }

    public  void prepareSemanticRules(String types[], String op[]) {
        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < types.length; j++) {
                for (int k = 0; k < op.length; k++) {
                    if(types[i] == types[j]) {
                        this.insertTypeRule(types[i], types[j], op[k], types[i]);
                    } else {
                        this.insertTypeRule(types[i], types[j], op[k], types[types.length - 1]);
                    }
                }
            }
        }
    }
    /// Reglas
    public  void setRules() {
        /// Tipos con numeros con operadores para numeros
        this.prepareSemanticRules(this.numTypes, this.mathOP);
        /// Tipos de char con operadores de char
        this.prepareSemanticRules(this.charTypes, this.charOP);
        /// Tipos de numeros con operadores log
        this.prepareSemanticRules(this.numTypes, this.logOp);
        /// Tipos de log con aperadores bool
        this.prepareSemanticRules(this.logTypes, this.boolOp);
    }

    /// Prints
    public  void printCombination() {
        for (int i = 0; i < this.types.length; i++) {
            for (int j = 0; j < this.types.length; j++) {
                for (int k = 0; k < this.operators.length; k++) {
                    ///console.log("Type 1: ", this.types[i],  " Type 2: ", this.types[j], " OP: ", this.operators[k]);
                   /// console.log("Produce: ", this.cubo[this.types[i]][this.types[j]][this.operators[k]]);
                }
            }
        }
    }

    

}


