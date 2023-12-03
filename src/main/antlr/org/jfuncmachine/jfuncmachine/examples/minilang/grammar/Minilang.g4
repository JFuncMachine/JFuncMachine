grammar Minilang;

@header {
package org.jfuncmachine.jfuncmachine.examples.minilang;
import org.jfuncmachine.jfuncmachine.examples.minilang.expr.*;
}

@parser::members {
  private String filename;

  public MinilangParser(String parseFilename) throws java.io.IOException {
    super(new CommonTokenStream(new MinilangLexer(new ANTLRFileStream(parseFilename))));
    filename = parseFilename;
  }
}
functionDef: ID ID* '=' expr;

expr returns [Expr value] : constant
    | '-' expr { $value = new IntUnaryExpr(IntUnaryExpr.ExprType.Neg, $2.value, filename, $1.getLine()); }
    | expr '*' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.Mul, $1.value, $3.value, $1.filename, $1.getLine()); }
    | expr '/' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.Div, $1.value, $3.value, $1.filename, $1.getLine()); }
    | expr '+' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.Add, $1.value, $3.value, $1.filename, $1.getLine()); }
    | expr '-' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.Sub, $1.value, $3.value, $1.filename, $1.getLine()); }
    | expr '&' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.And, $1.value, $3.value, $1.filename, $1.getLine()); }
    | expr '|' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.Or, $1.value, $3.value, $1.filename, $1.getLine()); }
    | expr '^' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.Xor, $1.value, $3.value, $1.filename, $1.getLine()); }
    | expr '<<' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.Shl, $1.value, $3.value, $1.filename, $1.getLine()); }
    | expr '>>' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.Lshr, $1.value, $3.value, $1.filename, $1.getLine()); }
    | expr '>>>' expr { $value = new IntBinaryExpr(IntBinaryExpr.ExprType.Ashr, $1.value, $3.value, $1.filename, $1.getLine()); }
    | ID '(' expr* ')' { $value = new FunctionCallExpr(ID.text, $3, $1.filename, $1.getLine()); }
    | '\\' expr* '->' expr
    | expr '++' expr
    | 'print' expr
    | ID
    ;

boolExpr : expr '=' expr #BoolEQ
    | expr '!=' expr #BoolNE
    | expr '>' expr #BoolGT
    | expr '>=' expr #BoolGE
    | expr '<' expr #BoolLT
    | expr '<=' expr #BoolLE
    | 'not' boolExpr #BoolNot
    | boolExpr 'and' boolExpr #BoolAnd
    | boolExpr 'or' boolExpr #BoolOr
    | 'true' #BoolTrue
    | 'false' #BoolFalse
    ;

constant returns [Expr value] : INT  { return new IntConstantExpr(Integer.valueOf(INT.text)); }
    | STRING { return new StringConstantExpr(STRING.text); }
    ;

ID: [A-Za-z_][A-Za-z0-9_]* ;
INT: [0-9]+;
STRING: ["][^"]*["];
