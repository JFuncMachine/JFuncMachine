package org.jfuncmachine.compiler.model.expr;

import org.jfuncmachine.compiler.classgen.ClassGenerator;
import org.jfuncmachine.compiler.classgen.Environment;
import org.jfuncmachine.compiler.classgen.Label;
import org.jfuncmachine.compiler.model.expr.boxing.Unbox;
import org.jfuncmachine.compiler.model.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** A switch expression whose cases are all integers
 *
 * This class maps directly to either Java's tableswitch or lookupswitch instructions,
 * meaning that the switch items must be integers. Other switches must currently be
 * implemented with if statements, although switches on classes and enumerations will
 * be supported by JFuncMachine eventually.
 *
 * A table switch is a simple jump table that is good when the case values are roughly
 * consecutive. A lookup switch performs a binary search on a sorted array of case values
 * to determine which case value to execute.
 */
public class IntSwitch extends Expression {
    /** The expression generating the value to be switched on */
    public final Expression expr;
    /** A case containing a numeric value and an expression to be executed if the switch expression
     * equals the case value
     */
    public final IntSwitchCase[] cases;
    /** The expression to be executed if none of the cases match the switch value */
    public final Expression defaultCase;
    /** The maximum number of empty spaces between switch values allowed before the switch
     * is converted to a lookup switch.
     */
    public final int gapThreshold;
    /** If true, this switch is using the tableswitch instruction */
    protected boolean useTableSwitch;
    /** A sorted map containing the cases sorted by value */
    protected final TreeMap<Integer, IntSwitchCase> caseMap;

    /** Create a Switch expression
     * @param expr The expression generating the value to be switched on
     * @param cases A case containing a numeric value and an expression to be executed if the switch expression
     *              equals the case value
     * @param defaultCase The expression to be executed if none of the cases match the switch value
     */
    public IntSwitch(Expression expr, IntSwitchCase[] cases, Expression defaultCase) {
        super(null, 0);
        this.expr = expr;
        this.cases = cases;
        this.defaultCase = defaultCase;
        this.gapThreshold = 10;

        if (cases.length == 0) {
            throw generateException("Switch must contain at least one case");
        }

        Type switchType = cases[0].expr.getType();
        for (int i=1; i < cases.length; i++) {
            if (!switchType.equals(cases[i].expr.getType())) {
                throw cases[i].generateException("Switch expressions must all be the same type");
            }
        }

        caseMap = new TreeMap<>();
        computeSwitchType();
    }


    /** Create a Switch expression
     * @param expr The expression generating the value to be switched on
     * @param cases A case containing a numeric value and an expression to be executed if the switch expression
     *              equals the case value
     * @param defaultCase The expression to be executed if none of the cases match the switch value
     * @param filename The name of the source file where this switch is defined
     * @param lineNumber The line number in the source file where this switch starts
     */
    public IntSwitch(Expression expr, IntSwitchCase[] cases, Expression defaultCase,
                     String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.cases = cases;
        this.defaultCase = defaultCase;
        this.gapThreshold = 10;

        if (cases.length == 0) {
            throw generateException("Switch must contain at least one case");
        }

        Type switchType = cases[0].expr.getType();
        for (int i=1; i < cases.length; i++) {
            if (!switchType.equals(cases[i].expr.getType())) {
                throw cases[i].generateException("Switch expressions must all be the same type");
            }
        }

        caseMap = new TreeMap<>();
        computeSwitchType();
    }

    /** Create a Switch expression
     * @param expr The expression generating the value to be switched on
     * @param cases A case containing a numeric value and an expression to be executed if the switch expression
     *              equals the case value
     * @param defaultCase The expression to be executed if none of the cases match the switch value
     * @param gapThreshold The maximum number of empty spaces between switch values allowed before the switch
     *                     is converted to a lookup switch.
     */
    public IntSwitch(Expression expr, IntSwitchCase[] cases, Expression defaultCase, int gapThreshold) {
        super(null, 0);
        this.expr = expr;
        this.cases = cases;
        this.defaultCase = defaultCase;
        this.gapThreshold = gapThreshold;

        if (cases.length == 0) {
            throw generateException("Switch must contain at least one case");
        }

        Type switchType = cases[0].expr.getType();
        for (int i=1; i < cases.length; i++) {
            if (!switchType.sameJavaType(cases[i].expr.getType())) {
                throw cases[i].generateException("Switch expressions must all be the same type");
            }
        }

        caseMap = new TreeMap<>();
        computeSwitchType();
    }

    /** Create a Switch expression
     * @param expr The expression generating the value to be switched on
     * @param cases A case containing a numeric value and an expression to be executed if the switch expression
     *              equals the case value
     * @param defaultCase The expression to be executed if none of the cases match the switch value
     * @param gapThreshold The maximum number of empty spaces between switch values allowed before the switch
     *                     is converted to a lookup switch.
     * @param filename The name of the source file where this switch is defined
     * @param lineNumber The line number in the source file where this switch starts
     */
    public IntSwitch(Expression expr, IntSwitchCase[] cases, Expression defaultCase, int gapThreshold,
                     String filename, int lineNumber) {
        super(filename, lineNumber);
        this.expr = expr;
        this.cases = cases;
        this.defaultCase = defaultCase;
        this.gapThreshold = gapThreshold;

        if (cases.length == 0) {
            throw generateException("Switch must contain at least one case");
        }

        Type switchType = cases[0].expr.getType();
        for (int i=1; i < cases.length; i++) {
            if (!switchType.equals(cases[i].expr.getType())) {
                throw cases[i].generateException("Switch expressions must all be the same type");
            }
        }

        caseMap = new TreeMap<>();
        computeSwitchType();
    }

    protected void computeSwitchType() {
        if (expr.getType().getJVMTypeRepresentation() != 'I') return;

        for (int i=0; i < cases.length; i++) {
            caseMap.put(cases[i].value, cases[i]);
        }
        int gapSum = 0;
        int last = 0;
        boolean isFirst = true;

        useTableSwitch = true;

        for (int key: caseMap.keySet()) {
            if (isFirst) {
                last = key;
                isFirst = false;
            } else {
                gapSum += key-last-1;
                if (gapSum > gapThreshold) {
                    useTableSwitch = false;
                }
            }
        }
    }

    public Type getType() {
        return cases[0].expr.getType();
    }

    @Override
    public void reset() {
        expr.reset();
        for (IntSwitchCase intSwitchCase : cases) {
            intSwitchCase.expr.reset();
        }
    }

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
        for (IntSwitchCase intSwitchCase : cases) {
            intSwitchCase.expr.findCaptured(env);
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {

        Type exprType = expr.getType();

        if ((exprType.getJVMTypeRepresentation() == 'I') || (exprType.isBoxType() && generator.options.autobox)) {
            boolean autobox = exprType.isBoxType();

            if (autobox) {
                new Unbox(expr, SimpleTypes.INT).generate(generator, env, false);
            } else {
                expr.generate(generator, env, false);
            }

            Label switchEndLabel = new Label();
            int minValue = cases[0].value;
            int maxValue = cases[0].value;

            Map<Integer, IntSwitchCase> caseMap = new TreeMap<>();

            for (IntSwitchCase intSwitchCase : cases) {
                caseMap.put(intSwitchCase.value, intSwitchCase);

                if (intSwitchCase.value < minValue) minValue = intSwitchCase.value;
                if (intSwitchCase.value > maxValue) maxValue = intSwitchCase.value;
            }

            Label defaultLabel = switchEndLabel;
            if (defaultCase != null) {
                defaultLabel = new Label();
            }

            if (useTableSwitch) {
                List<Label> switchLabels = new ArrayList<>();

                for (int i = minValue; i <= maxValue; i++) {
                    switchLabels.add(new Label());
                }

                generator.instGen.lineNumber(lineNumber);
                generator.instGen.tableswitch(minValue, maxValue, defaultLabel,
                        switchLabels.toArray(new Label[0]));

                for (int i = minValue; i <= maxValue; i++) {
                    generator.instGen.label(switchLabels.get(i - minValue));

                    if (caseMap.containsKey(i)) {
                        IntSwitchCase intSwitchCase = caseMap.get(i);
                        intSwitchCase.expr.generate(generator, env, inTailPosition);
                        generator.instGen.gotolabel(switchEndLabel);
                    } else {
                        generator.instGen.gotolabel(defaultLabel);
                    }
                }
            } else {
                List<Label> switchLabels = new ArrayList<>();

                int[] labelKeys = new int[caseMap.size()];
                int pos = 0;
                for (Integer key : caseMap.keySet()) {
                    switchLabels.add(new Label());
                    labelKeys[pos++] = key;
                }

                generator.instGen.lineNumber(lineNumber);
                generator.instGen.lookupswitch(defaultLabel, labelKeys, switchLabels.toArray(new Label[0]));

                pos = 0;
                for (Integer key : caseMap.keySet()) {
                    IntSwitchCase intSwitchCase = caseMap.get(key);
                    generator.instGen.label(switchLabels.get(pos++));
                    intSwitchCase.expr.generate(generator, env, inTailPosition);
                    generator.instGen.gotolabel(switchEndLabel);
                }
            }

            generator.instGen.label(defaultLabel);
            if (defaultCase != null) {
                defaultCase.generate(generator, env, inTailPosition);

            }

            generator.instGen.label(switchEndLabel);
        } else {
            throw generateException("Switch expression must be some type of int");
        }
    }
}
