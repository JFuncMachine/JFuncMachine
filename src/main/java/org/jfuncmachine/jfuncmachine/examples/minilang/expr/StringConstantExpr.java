package org.jfuncmachine.jfuncmachine.examples.minilang.expr;

import org.jfuncmachine.jfuncmachine.compiler.model.expr.Expression;
import org.jfuncmachine.jfuncmachine.compiler.model.expr.constants.StringConstant;
import org.jfuncmachine.jfuncmachine.examples.minilang.Environment;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.StringType;
import org.jfuncmachine.jfuncmachine.examples.minilang.types.Type;
import org.jfuncmachine.jfuncmachine.sexprlang.translate.ModelItem;
import org.jfuncmachine.jfuncmachine.util.unification.TypeHolder;
import org.jfuncmachine.jfuncmachine.util.unification.UnificationException;

@ModelItem(isStringConstant = true)
public class StringConstantExpr extends StringExpr {
    public final String value;
    public StringConstantExpr(String value, String filename, int lineNumber) {
        super(filename, lineNumber);
        this.value = value;
    }

    @Override
    public void unify(TypeHolder<Type> other, Environment<TypeHolder<Type>> env) throws UnificationException {
        TypeHolder<Type> stringType = new TypeHolder<>(new StringType(filename, lineNumber));
        other.unify(stringType);
        type.unify(stringType);
    }

    public Expression generate() {
        return new StringConstant(value);
    }
}
