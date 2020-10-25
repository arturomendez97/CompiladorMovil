export default class cuboSemantico {
/// Se definen los diferentes operadores y tipos 
    private types: Array<string> = ['int', 'float', 'string', 'char', 'bool'];
    private operators: Array<string> = ['+', '-', '*', '/', '<', '>', '==', '!=', '>=', '<='];
    private mathOP: Array<string> = ['+', '-', '*', '/'];
    private numTypes: Array<string> = ['int', 'float'];
    private charOP: Array<string> = ['+'];
    private charTypes: Array<string> = ['char', 'string'];
    private logOp: Array<string> = ['<', '>', '==', '!=', '>=', '<='];
    private boolOp: Array<string> = ['==', '!=' ];
    private logTypes: Array<string> = ['bool'];

    private cubo: Object;

    constructor() {
        this.cubo = new Object; ///Creacion del cubo

        for (let i = 0; i < this.types.length; i++) {
            this.cubo[this.types[i]] = new Object;
            for (let j = 0; j < this.types.length; j++) {
                this.cubo[this.types[i]][this.types[j]] = new Object;
                for (let k = 0; k < this.operators.length; k++) {
                    this.cubo[this.types[i]][this.types[j]][this.operators[k]] = null;
                }
            }
        }
    }
    /// Funcion para aceder al tipo de cualquir renglon
    getType(firstType: string, secondType:string, op: string):string {
        return this.cubo[firstType][secondType][op];  
    }
    /// Funcion para agregar una regla 
    insertTypeRule(firstType: string, secondType:string, op: string, result: string) {
        this.cubo[firstType][secondType][op] = result;
    }

    prepareSemanticRules(types:Array<string>, op:Array<string>) {
        for (let i = 0; i < types.length; i++) {
            for (let j = 0; j < types.length; j++) {
                for (let k = 0; k < op.length; k++) {
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
    setRules() {
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
    printCombination() {
        for (let i = 0; i < this.types.length; i++) {
            for (let j = 0; j < this.types.length; j++) {
                for (let k = 0; k < this.operators.length; k++) {
                    console.log("Type 1: ", this.types[i],  " Type 2: ", this.types[j], " OP: ", this.operators[k]);
                    console.log("Produce: ", this.cubo[this.types[i]][this.types[j]][this.operators[k]]);
                }
            }
        }
    }

    printCubo() {
        console.log(this.cubo);
    }

}


let test = {
    int: {
      int: {
        '+': 'int',
        '-': 'int',
        '*': 'int',
        '/': 'int',
        '<': 'bool',
        '>': 'bool',
        '==': 'bool',
        '!=': 'bool',
        '>=': 'bool',
        '<=': 'bool'
      },
      float: {
        '+': 'float',
        '-': 'float',
        '*': 'float',
        '/': 'float',
        '<': 'bool',
        '>': 'bool',
        '==': 'bool',
        '!=': 'bool',
        '>=': 'bool',
        '<=': 'bool'
      },
      string: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      char: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      bool: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      }
    },
    float: {
      int: {
        '+': 'float',
        '-': 'float',
        '*': 'float',
        '/': 'float',
        '<': 'bool',
        '>': 'bool',
        '==': 'bool',
        '!=': 'bool',
        '>=': 'bool',
        '<=': 'bool'
      },
      float: {
        '+': 'float',
        '-': 'float',
        '*': 'float',
        '/': 'float',
        '<': 'bool',
        '>': 'bool',
        '==': 'bool',
        '!=': 'bool',
        '>=': 'bool',
        '<=': 'bool'
      },
      string: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      char: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      bool: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      }
    },
    string: {
      int: {
        '+': 'string',
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      float: {
        '+': 'string',
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      string: {
        '+': 'string',
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      char: {
        '+': 'string',
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      bool: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      }
    },
    char: {
      int: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      float: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      string: {
        '+': 'string',
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      char: {
        '+': 'string',
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      bool: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      }
    },
    bool: {
      int: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      float: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      string: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      char: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': null,
        '!=': null,
        '>=': null,
        '<=': null
      },
      bool: {
        '+': null,
        '-': null,
        '*': null,
        '/': null,
        '<': null,
        '>': null,
        '==': 'bool',
        '!=': 'bool',
        '>=': null,
        '<=': null
      }
    }
  }