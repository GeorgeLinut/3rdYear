Language Specification:
1 .Language Definition:
1.1 Alphabet:
1.1.a. Upper (A-Z) and lower case letters (a-z) of the English alphabet
c. Decimal digits (0-9);
Lexic:
a.Special symbols, representing:
- operators + - * / := < <= = >=
- separators [ ] { } : ; space
- reserved words:
 public static void main String read print while if else int double Array print
 
b.identifiers
<variableID> ::= <word>|<word>[<value>]
<word> ::= <letter><word>
<letter> ::= "a"|"b"|...|"Y"|"Z"

<program> ::= "class" <mainClassName> "{" <mainFunction> "}"
<mainClassName> ::= <word>
<mainFunction> ::= "public" "static" "void" "main" "(" "String[]" "args" ")" "{" <statementList> "}"

//Statements

<statementList> ::= <statement> | <statement> <statementList> 
<statement> ::= <inputStmt> | <outputStmt> | <assignmentStmt> | <loopStmt> | <declarationStmt> | <ifStmt>
<inputStmt> ::= "read" "(" <variableID> ")"";"
<outputStmt> ::= "print" "("<expr> ")"";"
<assignmentStmt> ::= <variableID> "=" <expr> ";"
<loopStmt> ::= "while" "("<boolExpr>")" "{"<statementList>"}"
<declarationStmt> ::= <type> <listOfVariables> ";"
<ifStmt> ::=  "if" "("<boolExpr>")" "{"<statementList>"}" "else" "{" <statementList> "}"

//Expressions

<expr> ::= <expr><operator><expr>
<operator> ::= "+"|"*"|"/"|"-" 
<expr> ::= <number>|<variableID>
<value> ::= <number>|<number>[.<number>]
<number> ::= <zero>|<nonZero><digits>
<nonZero> ::= "1"|"2"|...|"9"
<zero> ::= "0"
<digit> ::= <zero>|<nonZero>
<digits> ::= <digit>|<digit><digits>
<boolExpr> ::= <expr> <boolOperator> <expr>
<boolOperator> ::= "!="|"=="|">"|"<"|">="|"<="

<listOfAttributes> ::= <attribute>";" | <attribute>";" <listOfAttributes>
<attribute> ::= <accesModifier> <type> <word> 
<type> ::= <simpleType>|<userDefinedType>
<simpleType> ::= "int"|"double"
<userDefinedType> ::= "Array<" <simpleType> ">"
<accesModifier> ::= "private"|"public"
<listOfVariables> ::= <variableID>"," <listOfVariables>
<variableID> ::= <word>|<word>[<value>]
<word> ::= <letter><word>
<letter> ::= "a"|"b"|...|"Y"|"Z"






Program #1:
	class Main{
		public static void main(String[] args){
			int radius, area, circumference;
			double PI;
			PI=3.14;
			read(radius);
			area = PI*radius*radius;
			circumference = 2*PI*r;
			print(area);
			print(circumference);
		}
	}

//Prim's algorithm
Program #2:
	class Main{
		public static void main(String[] args){
			int num1, num2;
			read(num1);
			read(num2);
			if(num1 != 0){
				if(num2 != 0){
					while(num1 != num2){
						if(num1>num2)
							{ num1=num1-num2; }
						else
							{ num2=num2-num1; }
						}
					print(num1);
				}
				else{
					print(num2);
				}
			}
			else{
				print(num1);
			}	
		}
	}


Program #3:
	class Main{
		public static void main(String[] args){
			int n, sum, current;
			read(n);
			sum=0;
			while(n > 0){
				read(current);
				sum=sum+current;
				n=n-1;	
			}
			print(sum);
		}
	}