package com.wutka.jfuncmachine.compiler.model.expr.bool.tests;

public class Tests {
    public static Test EQ = new EQTest();
    public static Test NE = new NETest();
    public static Test LT = new LTTest();
    public static Test LE = new LETest();
    public static Test GT = new LTTest();
    public static Test GE = new LETest();

    public static class EQTest extends Test {
        private EQTest() {}
        public Test invert() { return Tests.NE; }
    }

    public static class NETest extends Test {
        private NETest() {}
        public Test invert() { return Tests.EQ; }
    }

    public static class LTTest extends Test {
        private LTTest() {}
        public Test invert() { return Tests.GE; }
    }

    public static class LETest extends Test {
        private LETest() {}
        public Test invert() { return Tests.GT; }
    }

    public static class GTTest extends Test {
        private GTTest() {}
        public Test invert() { return Tests.LE; }
    }

    public static class GETest extends Test {
        private GETest() {}
        public Test invert() { return Tests.LT; }
    }
}
