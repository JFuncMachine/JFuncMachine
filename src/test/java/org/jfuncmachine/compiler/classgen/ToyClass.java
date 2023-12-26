package org.jfuncmachine.compiler.classgen;

public class ToyClass implements ToyInterface {
    public static String staticString = "Foo";
    public int memberInt;

    public ToyClass() {

    }

    public ToyClass(int member) {
        this.memberInt = member;
    }

    public static String addStatic(String str) {
        return str + staticString;
    }

    public static Object addStatic$$TC$$(String str) {
        return str + staticString;
    }

    public int addMember(int x) {
        return x + memberInt;
    }

    public Object addMember$$TC$$(int x) {
        return x + memberInt;
    }
}