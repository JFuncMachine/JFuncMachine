package com.wutka.jfuncmachine.compiler.classgen;

import com.wutka.jfuncmachine.compiler.model.Class;
import com.wutka.jfuncmachine.compiler.model.Field;
import com.wutka.jfuncmachine.compiler.model.Method;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BasicClassGeneration {

    @Test
    public void testEmptyClass() {
        Class newClass = new Class("com.wutka.test", "EmptyClass",
                Class.Access.Public,
                new Method[0], new Field[0],
                "empty.test", 1);


        ClassGenerator gen = new ClassGenerator();

        try {
            gen.generate(newClass, "testclasspath");
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
