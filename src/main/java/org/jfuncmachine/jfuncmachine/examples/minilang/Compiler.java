package org.jfuncmachine.jfuncmachine.examples.minilang;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.ClassDef;
import org.jfuncmachine.jfuncmachine.compiler.model.ClassField;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.examples.minilang.expr.*;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.Parser;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprDouble;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprInt;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprItem;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprList;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprString;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprSymbol;

import java.io.File;
import java.util.ArrayList;

public class Compiler {
    public static Expr parseExpression(SexprItem item) {
        return switch(item) {
            case SexprSymbol sym -> new SymbolExpr(sym.value, sym.filename, sym.lineNumber);
            case SexprInt sexprInt -> new IntConstantExpr(sexprInt.value, sexprInt.filename, sexprInt.lineNumber);
            case SexprDouble sexprDouble ->
                    throw new RuntimeException(String.format(
                            "%s %d: Double values not supported", sexprDouble.filename, sexprDouble.lineNumber));
            case SexprString sexprString ->
                    new StringConstantExpr(sexprString.value, sexprString.filename, sexprString.lineNumber);
            case SexprList sexprList -> parseExpressionList(sexprList);
        };

    }

    public static Expr parseExpressionList(SexprList sexprList) {
        if (sexprList.value.size() < 1) {
            throw new RuntimeException(String.format(
                    "%s %d: Empty expression list", sexprList.filename, sexprList.lineNumber));
        }
        if (!(sexprList.value.get(0) instanceof SexprSymbol sym)) {
            throw new RuntimeException(String.format(
                    "%s %d: Expected symbol at head of list, not %s", sexprList.filename, sexprList.lineNumber,
                    sexprList.value.get(0)));
        }

        return switch (sym.value) {
            case "and" -> new BoolBinaryExpr(BoolBinaryExpr.ExprType.And,
                    parseExpression(sexprList.value.get(1)),
                    parseExpression(sexprList.value.get(2)), sexprList.filename, sexprList.lineNumber);
            case "or" -> new BoolBinaryExpr(BoolBinaryExpr.ExprType.Or,
                    parseExpression(sexprList.value.get(1)),
                    parseExpression(sexprList.value.get(2)), sexprList.filename, sexprList.lineNumber);
            case "equal" -> new BoolComparison(BoolComparison.CompType.Equal,
                    parseExpression(sexprList.value.get(1)),
                    parseExpression(sexprList.value.get(2)), sexprList.filename, sexprList.lineNumber);
            case "notequal" -> new BoolComparison(BoolComparison.CompType.NotEqual,
                    parseExpression(sexprList.value.get(1)),
                    parseExpression(sexprList.value.get(2)), sexprList.filename, sexprList.lineNumber);
            case "lessthan" -> new BoolComparison(BoolComparison.CompType.LessThan,
                    parseExpression(sexprList.value.get(1)),
                    parseExpression(sexprList.value.get(2)), sexprList.filename, sexprList.lineNumber);
            case "lessorequal" -> new BoolComparison(BoolComparison.CompType.LessOrEqual,
                    parseExpression(sexprList.value.get(1)),
                    parseExpression(sexprList.value.get(2)), sexprList.filename, sexprList.lineNumber);
            case "greaterthan" -> new BoolComparison(BoolComparison.CompType.GreaterThan,
                    parseExpression(sexprList.value.get(1)),
                    parseExpression(sexprList.value.get(2)), sexprList.filename, sexprList.lineNumber);
            case "greaterorequal" -> new BoolComparison(BoolComparison.CompType.GreaterOrEqual,
                    parseExpression(sexprList.value.get(1)),
                    parseExpression(sexprList.value.get(2)), sexprList.filename, sexprList.lineNumber);
            case "not" -> new BoolNotExpr(parseExpression(sexprList.value.get(1)),
                     sexprList.filename, sexprList.lineNumber);
            default -> null;
        };
    }
    public static Func parseDefinition(SexprList defList) {
        return null;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please supply an input filename");
            return;
        }

        try {
            SexprItem item = Parser.parseFile(args[0], true);

            if (!(item instanceof SexprList sexprList)) {
                System.out.println("Internal error, parser did not return a list");
                return;
            }

            File argFile = new File(args[0]);
            String className = argFile.getName();
            if (!Character.isUpperCase(className.charAt(0))) {
                className = Character.toUpperCase(className.charAt(0))+className.substring(1);
            }

            ArrayList<Func> funcs = new ArrayList<>();
            boolean parsedBody = false;

            for (SexprItem listItem: sexprList.value) {
                if (!(listItem instanceof SexprList defList)) {
                    if (parsedBody) {
                        System.out.println(String.format(
                                "Found non-list expression %s, but have already parsed the main body",
                                listItem));
                        return;
                    }
                    Expr mainBody = parseExpression(listItem);
                    Func mainFunc = new Func("main", new Field[0], mainBody, mainBody.filename, mainBody.lineNumber);
                    funcs.add(mainFunc);
                } else {
                    if (defList.value.size() == 0) {
                        System.out.println(String.format("%s %d: Empty list given as definition",
                                defList.filename, defList.lineNumber));
                        return;
                    }
                    SexprItem firstItem = defList.value.get(0);
                    if (firstItem instanceof SexprSymbol sym) {
                        if (sym.value.equals("define")) {
                            Func newFunc = parseDefinition(defList);
                            funcs.add(newFunc);
                        }
                    } else {
                        Expr mainBody = parseExpression(defList);
                        Func mainFunc = new Func("main", new Field[0], mainBody, mainBody.filename, mainBody.lineNumber);
                        funcs.add(mainFunc);
                    }
                }
            }

            for (Func func: funcs) {
                func.unify();
            }

/*
            ClassDef classDef = new ClassDef("", className, Access.PUBLIC,
                    methods.toArray(new MethodDef[0]), new ClassField[0], new String[0]);

            ClassGenerator generator = new ClassGenerator();
            generator.generate(classDef, "out");
            System.out.printf("Class %s generated to out directory", className);
 */

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
