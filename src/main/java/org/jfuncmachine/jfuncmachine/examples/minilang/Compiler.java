package org.jfuncmachine.jfuncmachine.examples.minilang;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.model.Access;
import org.jfuncmachine.jfuncmachine.compiler.model.ClassDef;
import org.jfuncmachine.jfuncmachine.compiler.model.ClassField;
import org.jfuncmachine.jfuncmachine.compiler.model.MethodDef;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.conv.ToUnit;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ArrayType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.Field;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.examples.minilang.expr.*;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.Parser;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprDouble;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprInt;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprItem;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprList;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprString;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprSymbol;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.ModelMapper;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.SexprToModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Compiler {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please supply an input filename");
            return;
        }

        try {
            SexprItem item = Parser.parseFile(args[0], true, new MinilangSymbolMatcher());

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

            Object[] exprs = SexprToModel.sexprsToModel(sexprList,
                    new ModelMapper("org.jfuncmachine.jfuncmachine.examples.minilang"));

            Expression mainExpr = null;

            List<MethodDef> methods = new ArrayList<>();

            for (Object exprObj: exprs) {
                if (exprObj instanceof Func) {
                    Func func = (Func) exprObj;
                    func.unify();
                    MethodDef method = func.generate();
                    methods.add(method);
                } else if (exprObj instanceof Expression) {
                    Expression expr = (Expression) exprObj;
                    if (mainExpr == null) {
                        throw new MinilangException(
                                String.format("%s %d: There can only be one top-level main expression",
                                        expr.filename, expr.lineNumber));
                    }
                    mainExpr = expr;
                }
            }

            if (!mainExpr.getType().equals(SimpleTypes.UNIT)) {
                mainExpr = new ToUnit(mainExpr, mainExpr.filename, mainExpr.lineNumber);
            }

            MethodDef main = new MethodDef("main", Access.PUBLIC + Access.STATIC,
                    new Field[] {
                            new Field("args", new ArrayType(SimpleTypes.STRING))
                    }, SimpleTypes.UNIT, mainExpr);
            methods.add(main);

            ClassDef classDef = new ClassDef("", className, Access.PUBLIC,
                    methods.toArray(new MethodDef[0]), new ClassField[0], new String[0]);

            ClassGenerator generator = new ClassGenerator();
            generator.generate(classDef, "out");

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
