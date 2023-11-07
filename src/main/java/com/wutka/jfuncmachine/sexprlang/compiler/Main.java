package com.wutka.jfuncmachine.sexprlang.compiler;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.model.ClassDef;
import com.wutka.jfuncmachine.sexprlang.parser.Parser;
import com.wutka.jfuncmachine.sexprlang.parser.SexprItem;
import com.wutka.jfuncmachine.sexprlang.parser.SexprList;
import com.wutka.jfuncmachine.sexprlang.translate.SexprToModel;

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
