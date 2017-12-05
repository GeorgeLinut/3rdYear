flex flex.lx
C:\GnuWin32\bin\bison.exe -d bison.y
g++ bison.tab.c lex.yy.c -include bison.tab.h
a.exe 
PAUSE