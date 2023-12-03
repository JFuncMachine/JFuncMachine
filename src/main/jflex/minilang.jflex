package org.jfuncmachine.jfuncmachine.examples.minilang;

import java_cup.runtime.Symbol;

%%

%class Lexer
%cup
%implements sym

%%

"print"     { return sym(PRINT); }
"not"         { return sym(NOT); }
"and"         { return sym(AND); }
"or"         { return sym(OR); }
"true"     { return sym(TRUE); }
"false"        { return sym(FALSE); }
[A-Za-z_][A-Za-z0-9_]+  return sym(ID,yytext()); }
[0-9]+          { return sym(NUMBER,Integer.valueOf(yytext())); }
\"[^\"]*\"          { return sym(STRING,yytext()); }
'-'             { return sym(MINUS); }
'*'             { return sym(MUL); }
'/'             { return sym(DIV); }
'+'             { return sym(ADD); }
"&"             { return sym(BITAND); }
"|"             { return sym(BITOR); }
"^"             { return sym(BITXOR); }
"<<"             { return sym(BITLSL); }
">>"             { return sym(BITLSR); }
">>>"             { return sym(BITASR); }
"("             { return sym(LPAREN); }
")"             { return sym(RPAREN); }
"\\"             { return sym(BACKSLASH); }
"->"             { return sym(ARROW); }
"++"             { return sym(PLUSPLUS); }
"="             { return sym(EQUAL); }
"!="             { return sym(NE); }
"<"             { return sym(LT); }
"<="             { return sym(LE); }
">"             { return sym(GT); }
">="             { return sym(GE); }
[\ \t\b\f\r\n]+ { /* eat whitespace */ }
"//"[^\n]*      { /* one-line comment */ }
.               { throw new Error("Unexpected character ["+yytext()+"]"); }