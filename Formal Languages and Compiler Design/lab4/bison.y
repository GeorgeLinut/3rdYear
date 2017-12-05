%{
#include <iostream>
#include <stdio.h>
using namespace std;

int yyerror(const char *s);
extern int yylex(void);
extern FILE *yyin;
extern void show();
%}


%union{
  char* 	sval;
}


%start	input 

%token	<sval> IDENTIFIER_TOKEN
%token  <sval> CONSTANT_TOKEN
%token ASSIGN_TOKEN
%token TYPE_TOKEN
%token IF_TOKEN
%token DOREPEAT_TOKEN
%token READ_TOKEN
%token WRITE_TOKEN
%token DONE_TOKEN


%nonassoc LOWER_THAN_ELSE_TOKEN
%nonassoc ELSE_TOKEN

%left	PLUS MINUS
%left	MULT DIV
%left   BOOL_OP

%%

input: var_declarations statements
	 ;

var_declarations: var_decl_statement {cout << "VAR_DECL_STATEMENT" << '\n';}
				| var_decl_statement var_declarations {cout << "VAR_DECL_STATEMENT VAR_DECLARATIONS" << '\n';}

var_decl_statement: TYPE_TOKEN list_identifier ';' {cout << "VAR TOKEN LIST_IDEN" << '\n';}
				  ;

list_identifier: IDENTIFIER_TOKEN ',' list_identifier {cout << "IDENTIFIER_TOKEN, list_identifier" << '\n';}
			   | IDENTIFIER_TOKEN {cout << "IDENTIFIER_TOKEN" << '\n';}
			   ;

statements: statement {cout << "STATEMENT" << '\n';}
		 | statement statements {cout << "STATEMENT STATEMENTS" << '\n';}
		 ;

statement: assign_statement {cout << "ASSIGN_STATEMENT" << '\n';}
		 | conditional_statement {cout << "CONDITIONAL_STATEMENT" << '\n';}
		 | dorepeat_statement {cout << "DOREPEAT_STATEMENT" << '\n';}
		 | read_statement {cout << "SCANIO" << '\n';}
		 | write_statement {cout << "PUTIO" << '\n';}
		 ;


assign_statement: IDENTIFIER_TOKEN ASSIGN_TOKEN value ';' {cout << "assign_stmt\n";} 
           ;

value: IDENTIFIER_TOKEN {cout << "value - IDENTIFIER_TOKEN\n";}
     | arith_exp{cout << "value - ARITH\n";}
     | CONSTANT_TOKEN{cout << "value - CONST\n";}
     ;

arith_exp: value PLUS value {cout << "PLUS\n"; } 
         | value MINUS value{cout << "MINUS\n"; }
         | value MULT value{cout << "MULT\n"; }
         | value DIV value{cout << "DIV\n"; }
         ;

conditional_statement: IF_TOKEN bool_exp ':' statements DONE_TOKEN ';'    %prec LOWER_THAN_ELSE_TOKEN ; 
               |  IF_TOKEN bool_exp ':' statements DONE_TOKEN ';' ELSE_TOKEN ':' statements DONE_TOKEN ';'
               ;

bool_exp: value BOOL_OP value
        ;

dorepeat_statement: DOREPEAT_TOKEN bool_exp ':' statements DONE_TOKEN ';'
        	 ;

read_statement: READ_TOKEN '(' IDENTIFIER_TOKEN ')' ';'
         	  ;

write_statement: WRITE_TOKEN '(' value ')' ';'
          	   | WRITE_TOKEN '(' bool_exp ')' ';'


%%
int main(int, char**) {
    // open a file handle to a particular file:
    FILE *myfile = fopen("ex3.txt", "r");
    // make sure it is valid:
    if (!myfile) {
        cout << "I can't open file!" << endl;
        return -1;
    }
    // set flex to read from it instead of defaulting to STDIN:
    yyin = myfile; 
    // parse through the input until there is no more:
    do {
        yyparse();
    } while (!feof(yyin));
    show();
}
int yyerror(const char *s) {
    cout << "Parse error!  Message: " << s << endl;
    exit(-1);
}