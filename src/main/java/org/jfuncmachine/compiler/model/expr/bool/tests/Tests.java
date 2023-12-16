package org.jfuncmachine.compiler.model.expr.bool.tests;

/** A container for the various kinds of tests that can be done for an if instruction. */
public class Tests {
    /** The test for equality */
    public static Test EQ = new EQTest();
    /** The test for non-equality */
    public static Test NE = new NETest();
    /** The test for less-than */
    public static Test LT = new LTTest();
    /** The test for less-or-equal */
    public static Test LE = new LETest();
    /** The test for greater-than */
    public static Test GT = new GTTest();
    /** The test for greater-or-equal */
    public static Test GE = new GETest();
    /** The test for null */
    public static Test IsNull = new IsNullTest();
    /** The test for not-null */
    public static Test IsNotNull = new IsNotNullTest();
    /** The test for true */
    public static Test IsTrue = new IsTrueTest();
    /** The test for false */
    public static Test IsFalse = new IsFalseTest();
    /** The test for equality ignoring case (specifically for strings) */
    public static Test EQ_IgnoreCase = new EQIgnoreCaseTest();
    /** The test for non-equality ignoring case (specifically for strings) */
    public static Test NE_IgnoreCase = new NEIgnoreCaseTest();
    /** The test for less-than ignoring case (specifically for strings) */
    public static Test LT_IgnoreCase = new LTIgnoreCaseTest();
    /** The test for less-or-equal ignoring case (specifically for strings) */
    public static Test LE_IgnoreCase = new LEIgnoreCaseTest();
    /** The test for greater-than ignoring case (specifically for strings) */
    public static Test GT_IgnoreCase = new GTIgnoreCaseTest();
    /** The test for greater-or-equal ignoring case (specifically for strings) */
    public static Test GE_IgnoreCase = new GEIgnoreCaseTest();

    /** The built-in EQ test */
    public static class EQTest extends Test {
        private EQTest() {}
        public Test invert() { return Tests.NE; }
    }

    /** The built-in NE test */
    public static class NETest extends Test {
        private NETest() {}
        public Test invert() { return Tests.EQ; }
    }

    /** The built-in LT test */
    public static class LTTest extends Test {
        private LTTest() {}
        public Test invert() { return Tests.GE; }
    }

    /** The built-in LE test */
    public static class LETest extends Test {
        private LETest() {}
        public Test invert() { return Tests.GT; }
    }

    /** The built-in GT test */
    public static class GTTest extends Test {
        private GTTest() {}
        public Test invert() { return Tests.LE; }
    }

    /** The built-in GE test */
    public static class GETest extends Test {
        private GETest() {}
        public Test invert() { return Tests.LT; }
    }

    /** The built-in is-null test */
    public static class IsNullTest extends Test {
        private IsNullTest() {}
        public Test invert() { return Tests.IsNotNull; }
    }

    /** The built-in is-not-null test */
    public static class IsNotNullTest extends Test {
        private IsNotNullTest() {}
        public Test invert() { return Tests.IsNull; }
    }

    /** The built-in is-true test */
    public static class IsTrueTest extends Test {
        private IsTrueTest() {}
        public Test invert() { return Tests.IsFalse; }
    }

    /** The built-in is-false test */
    public static class IsFalseTest extends Test {
        private IsFalseTest() {}
        public Test invert() { return Tests.IsTrue; }
    }

    /** The built-in EQ-ignore-case test */
    public static class EQIgnoreCaseTest extends Test {
        private EQIgnoreCaseTest() {}
        public Test invert() { return Tests.NE_IgnoreCase; }
    }

    /** The built-in NE-ignore-case test */
    public static class NEIgnoreCaseTest extends Test {
        private NEIgnoreCaseTest() {}
        public Test invert() { return Tests.EQ_IgnoreCase; }
    }

    /** The built-in LT-ignore-case test */
    public static class LTIgnoreCaseTest extends Test {
        private LTIgnoreCaseTest() {}
        public Test invert() { return Tests.GE_IgnoreCase; }
    }

    /** The built-in LE-ignore-case test */
    public static class LEIgnoreCaseTest extends Test {
        private LEIgnoreCaseTest() {}
        public Test invert() { return Tests.GT_IgnoreCase; }
    }

    /** The built-in GT-ignore-case test */
    public static class GTIgnoreCaseTest extends Test {
        private GTIgnoreCaseTest() {}
        public Test invert() { return Tests.LE_IgnoreCase; }
    }

    /** The built-in GE-ignore-case test */
    public static class GEIgnoreCaseTest extends Test {
        private GEIgnoreCaseTest() {}
        public Test invert() { return Tests.LT_IgnoreCase; }
    }

    private Tests() {}
}
