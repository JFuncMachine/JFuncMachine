package org.jfuncmachine.jfuncmachine.sexprlang.compiler;

import org.jfuncmachine.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.jfuncmachine.compiler.model.ClassDef;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.Parser;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprItem;
import org.jfuncmachine.jfuncmachine.sexprlang.parser.SexprList;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.SexprToModel;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please supply an input filename");
            return;
        }

        try {
            SexprItem item = Parser.parseFile(args[0]);

            if (item instanceof SexprList list) {
                ClassDef classDef = SexprToModel.translateClass(list);
                ClassGenerator generator = new ClassGenerator();
                generator.generate(classDef, "out");
            } else {
                System.out.println("File should contain a list s-expression at the top level");
            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
