grammar Minilang;

functionDef: ID ID* '=' expr;

expr: constant
    | '-' expr
    | expr '*' expr
    | expr '/' expr
    | expr '+' expr
    | expr '-' expr
    | expr '&' expr
    | expr '|' expr
    | expr '^' expr
    | expr '<<' expr
    | expr '>>' expr
    | expr '>>>' expr
    | ID '(' expr* ')'
    | '\\' expr* '->' expr
    | expr '++' expr
    | 'print' expr
    | ID
    ;

boolExpr : expr '=' expr
    | expr '!=' expr
    | expr '>' expr
    | expr '>=' expr
    | expr '<' expr
    | expr '<=' expr
    | 'not' boolExpr
    | boolExpr 'and' boolExpr
    | boolExpr 'or' boolExpr
    | 'true'
    | 'false'
    ;

constant: INT | STRING;

ID: [A-Za-z_][A-Za-z0-9_]* ;
INT: [0-9]+;
STRING: ["][^"]*["];
