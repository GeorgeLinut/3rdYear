
%{
#include <iostream>
#include <stdio.h>
#include <string.h>
using namespace std;

int yyerror(const char *s);
extern int yylex(void);
extern FILE *yyin;
char* BYTES = "2";
FILE *fout = fopen("result.asm", "w");

extern void show();
%}


%union{
  char* 	sval;
}


%start	program 

%type <sval> express
%type <sval> var_declarations
%type <sval> var_decl_statement
%type <sval> statements
%type <sval> statement
%type <sval> list_identifier
%type <sval> assign_statement
%type <sval> read_statement
%type <sval> write_statement
%type <sval> conditional_statement


%token	<sval> IDENTIFIER_TOKEN
%token  <sval> CONSTANT_TOKEN
%token ASSIGN_TOKEN
%token TYPE_TOKEN
%token IF_TOKEN
%token DOREPEAT_TOKEN
%token PACKAGE_TOKEN
%token READ_TOKEN
%token WRITE_TOKEN
%token DONE_TOKEN


%nonassoc LOWER_THAN_ELSE_TOKEN
%nonassoc ELSE_TOKEN

%left	PLUS MINUS
%left	MULT DIV
%left   BOOL_OP

%%

program: var_declarations ';' statements ';'  {printf("STARTED\n");fprintf(fout, "; Input:\n; ESI = pointer to the string to convert\n; ECX = number of digits in the string (must be > 0)\n; Output:\n; EAX = integer value\nstring_to_int:\n\txor ebx,ebx    ; clear ebx\n.next_digit:\n\tmovzx eax,byte[esi]\n\tinc esi\n\tsub al,'0'    ; convert from ASCII to number\n\timul ebx,10\n\tadd ebx,eax   ; ebx = ebx*10 + eax\n\tloop .next_digit  ; while (--ecx)\n\tmov eax,ebx\n\tret\n\n; Input:\n; EAX = integer value to convert\n; ESI = pointer to buffer to store the string in (must have room for at least 10 bytes)\n; Output:\n; EAX = pointer to the first character of the generated string\nint_to_string:\n\tadd esi,9\n\tmov byte [esi],STRING_TERMINATOR\n\n\tmov ebx,10\n.next_digit:\n\txor edx,edx         ; Clear edx prior to dividing edx:eax by ebx\n\tdiv ebx             ; eax /= 10\n\tadd dl,'0'          ; Convert the remainder to ASCII \n\tdec esi             ; store characters in reverse order\n\tmov [esi],dl\n");
  fprintf(fout, "\ttest eax,eax\n\tjnz .next_digit ; Repeat until eax==0\n\tmov eax,esi\n\tret\nsection .text\n\tglobal _start\n\textern _scanf\n\textern _printf\n_start:\n%s\n\tmov eax,1\n\tmov ebx, 0\n\tint 80h\nsection .data\n%s\n\tSTRING_TERMINATOR equ 0\nsection .bss\n\tbuffer resb 5\n", $3, $1);fprintf(stderr,"Got here!");}
  | var_declarations ';' {}
;

var_declarations: var_decl_statement {printf("HEre_decl\n");$$ = $1;} | var_declarations ';' var_decl_statement {char* tmpVal = new char[strlen($1) + strlen($3) + 1]; strcpy(tmpVal, $1); strcat(tmpVal, $3); $$ = tmpVal;}
;

var_decl_statement: TYPE_TOKEN list_identifier {printf("here_single_decl\n");$$ = $2; }
;

list_identifier: IDENTIFIER_TOKEN  {
    printf("list_iden\n");
  char* tmpVal = new char[strlen($1) + strlen(" DW 0\n") + 3];
  strcpy(tmpVal, "\t");
  strcat(tmpVal, $1);
  strcat(tmpVal, " DW 0\n");
  $$ = tmpVal;
}
  | IDENTIFIER_TOKEN ',' list_identifier {
    char* tmpVal = new char[strlen($1) + strlen(" DW 0\n") + 3 + strlen($3)];
    strcpy(tmpVal, "\t");
    strcat(tmpVal, $1);
    strcat(tmpVal, " DW 0\n");
    strcat(tmpVal, $3);
    $$ = tmpVal;
  }
;

statements: statement {printf("at statements\n");$$ = $1;} | statements ';' statement {
    printf("HEEEEE\n");
  char* tmpVal = new char[strlen($1) + strlen($3) + 1];
  strcpy(tmpVal, $1);
  strcat(tmpVal, $3);
  $$ = tmpVal;
}
;

statement: assign_statement {printf("statement\n");$$ = $1;}
  | read_statement {printf("Statement\n");$$ = $1;}
  | write_statement {$$ = $1;}
  | dorepeat_statement {$$ = "dorepeat_statement";}
  | conditional_statement {$$ = "conditional_statement";}
;

assign_statement: IDENTIFIER_TOKEN ASSIGN_TOKEN express {
    printf("GOT TO ASSIGNMENT\n");
  char* tmpVal = new char[strlen($1) + strlen($3) + 2 + strlen("\tMOV , eax\n")];
  strcpy(tmpVal, $3);
  strcat(tmpVal, "\tMOV [");
  strcat(tmpVal, $1);
  strcat(tmpVal, "], eax\n");
  $$ = tmpVal;}
;

express: IDENTIFIER_TOKEN {
    printf("HEREEEE");
  char* tmpVal = new char[strlen($1) + strlen("\tMOV eax, []\n") + 1];
  strcpy(tmpVal, "\tMOV eax, [");
  strcat(tmpVal, $1);
  strcat(tmpVal, "]\n");
  $$ = tmpVal;
}
  | CONSTANT_TOKEN {
    char* tmpVal = new char[strlen($1) + strlen("\tMOV eax, \n") + 1];
    strcpy(tmpVal, "\tMOV eax, ");
    strcat(tmpVal, $1); strcat(tmpVal, "\n");
    $$ = tmpVal;
  }
  | IDENTIFIER_TOKEN operator express {
    printf("HERe at expression");
    char* tmpVal = new char[strlen($3) + 2 + strlen("\tADD eax, []\n") + strlen($1)];
    strcat(tmpVal, $3);
    strcat(tmpVal, "\tADD eax, [");
    strcat(tmpVal, $1);
    strcat(tmpVal, "]\n");
    $$ = tmpVal;
  }
  | CONSTANT_TOKEN operator express {
    char* tmpVal = new char[strlen($3) + 2 + strlen("\tADD eax, \n") + strlen($1)];
    strcat(tmpVal, $3);
    strcat(tmpVal, "\tADD eax, ");
    strcat(tmpVal, $1);
    strcat(tmpVal, "\n");
    $$ = tmpVal;
  }
;
operator: PLUS | MINUS | MULT | DIV
;

conditional_statement: IF_TOKEN '(' bool_exp ')' ':' statements DONE_TOKEN ';' {printf("Conditional statement\n");}

bool_exp: IDENTIFIER_TOKEN BOOL_OP IDENTIFIER_TOKEN {printf("Logical expression\n");} | IDENTIFIER_TOKEN BOOL_OP CONSTANT_TOKEN {printf("Logical expression\n");} | CONSTANT_TOKEN BOOL_OP IDENTIFIER_TOKEN {printf("Logical expression\n");} | CONSTANT_TOKEN BOOL_OP CONSTANT_TOKEN {printf("Logical expression\n");}
;

dorepeat_statement: DOREPEAT_TOKEN '(' bool_exp ')'':' statements DONE_TOKEN ';'  {printf("Loop statement\n");}
;

read_statement: READ_TOKEN '(' IDENTIFIER_TOKEN ')' {
    printf("Statement read\n");
  char* tmpVal = new char[3 * strlen($3) + strlen("\tmov eax, 3\n\tmov ebx, 0\n\tmov ecx, \n\tmov edx, 5\n\tint 80h\n\tlea esi, []\n\tmov ecx,4\n\tcall string_to_int\n\tmov [], eax\n") + 1];
  strcpy(tmpVal, "\tmov eax, 3\n\tmov ebx, 0\n\tmov ecx, ");
  strcat(tmpVal, $3);
  strcat(tmpVal, "\n\tmov edx, ");
  strcat(tmpVal, BYTES);
  strcat(tmpVal, "\n\tint 80h");
  strcat(tmpVal, "\n\tlea esi, [");
  strcat(tmpVal, $3);
  strcat(tmpVal, "]\n\tmov ecx, ");
  strcat(tmpVal, BYTES);
  strcat(tmpVal, "\n\tcall string_to_int\n\tmov [");
  strcat(tmpVal, $3);
  strcat(tmpVal, "], eax\n");
  $$ = tmpVal;
}
;

write_statement: WRITE_TOKEN '(' express ')'  {char* tmpVal = new char[3*strlen($3) + strlen("\tlea esi, eax\n\tcall int_to_string\n\tmov ecx, eax\tmov eax, 3\n\tmov ebx, 2\n\tmov edx, 5\n\tint 80h\n") + 1];
  strcpy(tmpVal, $3);
  strcat(tmpVal, "\tmov [buffer], eax\n\tlea esi, [buffer]\n\tcall int_to_string");
  strcat(tmpVal, "\n\tmov ecx, eax\n\tmov eax, 4\n\tmov ebx, 1");
  strcat(tmpVal, "\n\tmov edx, ");
  strcat(tmpVal, BYTES);
  strcat(tmpVal, "\n\tint 80h\n");
  $$ = tmpVal;
}
;
%%

int main(int, char**) {
    // open a file handle to a particular file:
    FILE *myfile = fopen("1.txt", "r");
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