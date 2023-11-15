package com.wutka.jfuncmachine.compiler.model.expr.bool.tests;

public class Tests {
    public static Test EQ = new EQTest();
    public static Test NE = new NETest();
    public static Test LT = new LTTest();
    public static Test LE = new LETest();
    public static Test GT = new LTTest();
    public static Test GE = new LETest();
    public static Test IsNull = new IsNullTest();
    public static Test IsNotNull = new IsNotNullTest();
    public static Test IsTrue = new IsTrueTest();
    public static Test IsFalse = new IsFalseTest();

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

    public static class IsNullTest extends Test {
        private IsNullTest() {}
        public Test invert() { return Tests.IsNotNull; }
    }

    public static class IsNotNullTest extends Test {
        private IsNotNullTest() {}
        public Test invert() { return Tests.IsNull; }
    }

    public static class IsTrueTest extends Test {
        private IsTrueTest() {}
        public Test invert() { return Tests.IsFalse; }
    }

    public static class IsFalseTest extends Test {
        private IsFalseTest() {}
        public Test invert() { return Tests.IsTrue; }
    }
}
