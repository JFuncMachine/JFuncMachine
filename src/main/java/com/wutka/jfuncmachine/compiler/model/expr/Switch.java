package com.wutka.jfuncmachine.compiler.model.expr;

import com.wutka.jfuncmachine.compiler.classgen.ClassGenerator;
import com.wutka.jfuncmachine.compiler.classgen.Environment;
import com.wutka.jfuncmachine.compiler.classgen.Label;
import com.wutka.jfuncmachine.compiler.model.expr.boxing.Unbox;
import com.wutka.jfuncmachine.compiler.model.types.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Switch extends Expression {
    public final Expression expr;
    public final SwitchCase[] cases;
    public final Expression defaultCase;
    public final int gapThreshold;
    protected boolean useTableSwitch;
    protected final Map<Integer,SwitchCase> caseMap;

    public Switch(Expression expr, SwitchCase[] cases, Expression defaultCase) {
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


    public Switch(Expression expr, SwitchCase[] cases, Expression defaultCase,
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

    public Switch(Expression expr, SwitchCase[] cases, Expression defaultCase, int gapThreshold) {
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
            if (!switchType.equals(cases[i].expr.getType())) {
                throw cases[i].generateException("Switch expressions must all be the same type");
            }
        }

        caseMap = new TreeMap<>();
        computeSwitchType();
    }


    public Switch(Expression expr, SwitchCase[] cases, Expression defaultCase, int gapThreshold,
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
        for (int i=0; i < cases.length; i++) {
            caseMap.put(i, cases[i]);
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

    public void findCaptured(Environment env) {
        expr.findCaptured(env);
        for (SwitchCase switchCase: cases) {
            switchCase.expr.findCaptured(env);
        }
    }

    @Override
    public void generate(ClassGenerator generator, Environment env, boolean inTailPosition) {

        Type exprType = expr.getType();

        boolean autobox = false;

        if (exprType instanceof ObjectType t) {
            String tclass = t.className;
            if (!(tclass.equals("java.lang.Boolean") || tclass.equals("java.lang.Byte") ||
                    tclass.equals("java.lang.Char") || tclass.equals("java.lang.Integer") ||
                    tclass.equals("java.lang.Long") || tclass.equals("java.lang.Short"))) {
                throw generateException("Switch expression must be some type of int");
            }
            if (!generator.options.autobox) {
                throw generateException("Switch is a boxed integer, but autoboxing is not enabled");
            }
            autobox = true;

        } else if (!((exprType instanceof BooleanType) || (exprType instanceof CharType) ||
                (exprType instanceof IntType) || (exprType instanceof LongType) ||
                (exprType instanceof ShortType))) {
            throw generateException("Switch expression must be some type of int");
        }

        if (autobox) {
            new Unbox(expr, SimpleTypes.INT).generate(generator, env, false);
        } else {
            expr.generate(generator, env, false);
        }

        Label switchEndLabel = new Label();
        int minValue = cases[0].value;
        int maxValue = cases[0].value;

        Map<Integer, SwitchCase> caseMap = new TreeMap<>();

        for (SwitchCase switchCase: cases) {
            caseMap.put(switchCase.value, switchCase);

            if (switchCase.value < minValue) minValue = switchCase.value;
            if (switchCase.value > maxValue) maxValue = switchCase.value;
        }

        Label defaultLabel = switchEndLabel;
        if (defaultCase != null) {
            defaultLabel = new Label();
        }

        if (useTableSwitch) {
            List<Label> switchLabels = new ArrayList<>();

            for (int i=minValue; i <= maxValue; i++) {
                switchLabels.add(new Label());
            }

            generator.instGen.tableswitch(minValue, maxValue, defaultLabel,
                    switchLabels.toArray(new Label[0]));

            for (int i=minValue; i <= maxValue; i++) {
                generator.instGen.label(switchLabels.get(i));

                if (caseMap.containsKey(i)) {
                    SwitchCase switchCase = caseMap.get(i);
                    switchCase.expr.generate(generator, env, inTailPosition);
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

            generator.instGen.lookupswitch(defaultLabel, labelKeys, switchLabels.toArray(new Label[0]));

            pos = 0;
            for (SwitchCase switchCase : cases) {
                generator.instGen.label(switchLabels.get(pos++));
                switchCase.expr.generate(generator, env, inTailPosition);
            }
        }

        if (defaultCase != null) {
            generator.instGen.label(defaultLabel);
            defaultCase.generate(generator, env, inTailPosition);
        }

        generator.instGen.label(switchEndLabel);
    }
}
