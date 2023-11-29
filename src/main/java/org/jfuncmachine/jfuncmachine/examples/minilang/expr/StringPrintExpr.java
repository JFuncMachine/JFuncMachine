package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.CallJavaMethod;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.javainterop.GetJavaStaticField;
import org.jfuncmachine.jfuncmachine.compiler.model.types.ObjectType;
import org.jfuncmachine.jfuncmachine.compiler.model.types.SimpleTypes;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.StringType;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.UnitType;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

public class StringPrintExpr extends Expr {
    public final Expr expr;
    public StringPrintExpr(Expr expr, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
    }

    @Override
    public void unify(TypeHolder other, Environment<TypeHolder> env) throws UnificationException {
        TypeHolder unitType = new TypeHolder(new UnitType(filename, lineNumber));
        other.unify(unitType);
        TypeHolder stringType = new TypeHolder(new StringType(filename, lineNumber));
        expr.unify(stringType, env);
    }

    public Expression generate() {
        return new CallJavaMethod("java.io.PrintStream", "println",
                // Get the PrintStream object from System.out, that is the object
                // that we will be calling println on
                SimpleTypes.UNIT, new GetJavaStaticField("java.lang.System", "out",
                new ObjectType("java.io.PrintStream")),
                // Load up the arguments to println, which is just one, that is a string constant
                new Expression[] { expr.generate() });
    }
}
