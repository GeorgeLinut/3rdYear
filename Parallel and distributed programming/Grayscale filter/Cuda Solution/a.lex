%{
#include<stdlib.h>
#include<string.h>
#include<math.h>

typedef struct{
	int id1;
	int id2;
}PIF;

typedef struct{
	char name[10];
}TSiden;

int pifLength = 0;
int constLength = 0;
int identifierLength = 0;
int identifierCode = 0;
int constCode = 1;
PIF program[300];

TSiden TSidentifier[30];

TSiden TSconstants[30];

void addPIF(int id1,int id2){
	program[pifLength].id1 = id1;
	program[pifLength].id2 = id2;
	++pifLength;
}

void addConst(char* atom){
	int found = 0;
	int i ;
	for(int i=0;i<constLength;++i){
		if(strcmp(TSconstants[i].name, atom) == 0){
			found = 1;
			addPIF(constCode,i);
		}
	}
	if(found == 0){
		strcpy(TSconstants[constLength].name,atom);
		addPIF(constCode,constLength);
		++constLength;
	}
}

void addIdentifier(char *atom){
	int found = 0;
	int i =0;
	while(i < identifierLength){
		int res = strcmp(TSidentifier[i].name,atom);
		if(res == 0){
			found = 1;
			addPIF(identifierCode,i);
		}
		++i;
	}
	if(found == 0){
		strcpy(TSidentifier[identifierLength].name,atom);
		addPIF(identifierCode,identifierLength);
		identifierLength++;
	}
}

void showPIF(){
	printf("PIF:\n");
	int t;
	for(t = 0; t < pifLength;++t){
		if(program[t].id1 != identifierCode && program[t].id1 != constCode){
			printf(" %d|- ",program[t].id1);
		}
		else{
			printf(" %d|%d ",program[t].id1,program[t].id2);
		}
	}
}

void showConst(){
	printf("Constants: \n");
	for(int i=0;i<constLength;++i){
		printf(" %s|%d",TSconstants[i].name,i);
	}
	printf("\n");
}

void showId(){
    printf("Identifier\n");
    for(int i = 0 ;i<identifierLength;++i)
        printf(" %s|%d", TSidentifier[i].name , i);
    printf("\n");
}

void show(){
	showConst();
	showId();
	showPIF();
	printf("\n WENT OK!\n");
}
%}

%option noyywrap
WITHZERO		[0-9]
DIGIT			[1-9]
DIGITS			{DIGIT}{WITHZERO}*|0
ID 				[a-z]


%%
{DIGITS}		{addConst(yytext);int i; yylval.sval = new char[yyleng]; for(i=0;i<yyleng;i++){ yylval.sval[i] = yytext[i]; } return CONSTANT_TOKEN;}

done					{addPIF(5,0); return DONE_TOKEN;}
doif					{addPIF(6,0); return IF_TOKEN;}
dorepeat				{addPIF(7,0); return DOREPEAT_TOKEN;}
int 					{addPIF(8,0); return TYPE_TOKEN;}
float 					{addPIF(9,0); return TYPE_TOKEN;}
scanIO 					{addPIF(10,0); return READ_TOKEN;}
putIO 					{addPIF(11,0); return WRITE_TOKEN;}
\( 						{addPIF(12,0); return yytext[0];}
\)						{addPIF(13,0); return yytext[0];}
\, 						{addPIF(14,0); return yytext[0];}
\+ 						{addPIF(15,0); return PLUS;}
\*						{addPIF(16,0); return MULT;}
\/						{addPIF(17,0); return DIV;}
\-						{addPIF(18,0); return MINUS;}
\=						{addPIF(19,0); return ASSIGN_TOKEN;}
\>						{addPIF(20,0); return BOOL_OP;}
\>=						{addPIF(21,0); return BOOL_OP;}
\==						{addPIF(22,0); return BOOL_OP;}
\!=						{addPIF(23,0); return BOOL_OP;}
\<						{addPIF(24,0); return BOOL_OP;}
\<=						{addPIF(25,0); return BOOL_OP;}
\.						{addPIF(26,0); return yytext[0];}
\;						{addPIF(27,0); return yytext[0];}
\:						{addPIF(30,0); return yytext[0];}
\%						{addPIF(31,0); return yytext[0];}
else					{addPIF(32,0); return ELSE_TOKEN;}
{ID}+					{addIdentifier(yytext);int i; yylval.sval = new char[yyleng]; for(i=0;i<yyleng;i++){ yylval.sval[i] = yytext[i]; } return IDENTIFIER_TOKEN;}

[ \t\n]+					;/* eat up whitespace */

. printf("ERROR\n");

%%