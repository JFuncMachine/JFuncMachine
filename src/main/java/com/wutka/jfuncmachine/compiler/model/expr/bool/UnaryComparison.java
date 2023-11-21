package com.wutka.jfuncmachine.compiler.model.expr.bool;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.Expression;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Test;
import com.wutka.jfuncmachine.compiler.model.expr.bool.tests.Tests;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Stack;

/** Represents a comparison for a single expression, usually either a binary expression (represented by 0 or 1),
 * or the result of a compare function or instruction.
 */
public class UnaryComparison extends BooleanExpr {
    /** The test to perform */
    public Test test;
    /** The expression to test */
    public Expression expr;
    /** The expression that should be executed if the test is true */
    public BooleanExpr truePath;
    /** The expression that should be executed if the test is false */
    public BooleanExpr falsePath;

    /** Create a new UnaryExpression for a test and expression
     * @param test The test to perform
     * @param expr The expression to be tested
     */
    public UnaryComparison(Test test, Expression expr) {
        super(null, 0);
        this.test = test;
        this.expr = expr;
    }

    /** Create a new UnaryExpression for a test and expression
     * @param test The test to perform
     * @param expr The expression to be tested
     * @param filename The source filename this expression is associated with
     * @param lineNumber The source line number this expression is associated with
     */
    public UnaryComparison(Test test, Expression expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.test = test;
        this.expr = expr;
    }

    public BooleanExpr invert() {
        this.test = test.invert();
        return this;
    }

    public BooleanExpr removeNot() {
        return this;
    }

    public BooleanExpr computeSequence(BooleanExpr trueNext, BooleanExpr falseNext, List<BooleanExpr> tests) {
        this.falsePath = falseNext;
        this.truePath = trueNext;

        tests.add(this);

        return this;
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
    }

    public void generate(ClassGenerator generator, Environment env, BooleanExpr next) {
        if (label != null) {
            generator.instGen.label(label);
        }
        expr.generate(generator, env, false);

        Test generateTest = test;
        BooleanExpr generateTruePath = truePath;

        /* Java if instructions always jump if the test is true, and otherwise just execute the next
           instruction if the test is false. If the next test after this is supposed to occur if the
           comparison is true, invert the test so that the next test is executed when the test is false
           instead of true.
         */
        if (truePath == next) {
            generateTest = test.invert();
            generateTruePath = falsePath;
        }

        if (generateTest instanceof Tests.EQTest || generateTest instanceof Tests.NETest ||
            generateTest instanceof Tests.LTTest || generateTest instanceof Tests.LETest ||
            generateTest instanceof Tests.GTTest || generateTest instanceof Tests.GETest) {
            if (!expr.getType().hasIntRepresentation()) {
                throw generateException("Unary test for EQ,NE,LT,LE,GT,GE requires an int expression");
            }
        }
        int opcode = switch (generateTest) {
            case Tests.IsNullTest t -> Opcodes.IFNULL;
            case Tests.IsNotNullTest t -> Opcodes.IFNONNULL;
            case Tests.IsTrueTest t -> Opcodes.IFNE;
            case Tests.IsFalseTest t -> Opcodes.IFEQ;
            case Tests.EQTest t -> Opcodes.IFEQ;
            case Tests.NETest t -> Opcodes.IFNE;
            case Tests.LTTest t -> Opcodes.IFLT;
            case Tests.LETest t -> Opcodes.IFLE;
            case Tests.GTTest t -> Opcodes.IFGT;
            case Tests.GETest t -> Opcodes.IFGE;
            default -> throw generateException("Invalid test for unary comparison");
        };

        if (generateTruePath.label == null) {
            generateTruePath.label = new Label();
        }
        generator.instGen.rawJumpOpcode(opcode, generateTruePath.label);
    }
}
