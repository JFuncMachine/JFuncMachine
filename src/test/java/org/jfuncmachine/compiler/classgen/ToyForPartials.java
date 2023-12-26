package org.jfuncmachine.compiler.classgen;

public class ToyForPartials implements ToyPartialsInterface{
    int foo;
    String bar;
    double baz;

    public ToyForPartials() {

    }

    public ToyForPartials(int foo, String bar, double baz) {
        this.foo = foo;
        this.bar = bar;
        this.baz = baz;
    }

    public int addThree(int a, int b, int c) {
        return a + b + c;
    }

    public static int addThreeStatic(int a, int b, int c) {
        return a + b + c;
    }

    public Object addThree$$TC$$(int a, int b, int c) {
        return a + b + c;
    }

    public static Object addThreeStatic$$TC$$(int a, int b, int c) {
        return a + b + c;
    }
}
