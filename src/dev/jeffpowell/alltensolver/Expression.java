package dev.jeffpowell.alltensolver;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Expression {
    public static enum Ops {
        ROOT(' ', (l,r) -> l, false),
        REV_DIVIDE('\\', (l,r) -> r / l, false),
        REV_MULTIPLY('x', (l,r) -> r * l, false),
        DIVIDE('/', (l,r) -> l / r, false),
        MULTIPLY('*', (l,r) -> l * r, false),
        REV_SUBTRACT('~', (l,r) -> r - l, true),
        REV_ADD('t', (l,r) -> r + l, true),
        SUBTRACT('-', (l,r) -> l - r, true),
        ADD('+', (l,r) -> l + r, true);
        final char c;
        final BiFunction<Double, Double, Double> fn;
        final boolean isAddSubtract;
        private Ops(char c, BiFunction<Double, Double, Double> fn, boolean isAddSubtract) {
            this.c = c;
            this.fn = fn;
            this.isAddSubtract = isAddSubtract;
        }

        public double eval(double l, double r) {
            return fn.apply(l, r);
        }

        public boolean requiresParens(Ops nextOperator) {
            return isAddSubtract != nextOperator.isAddSubtract;
        }
    }
    private final Ops operation;
    private final List<Integer> operandsLeft;
    private final Expression priorExpression;
    private double start;
    private double nextOperand;
    
    public Expression(double start, List<Integer> operandsLeft) {
        this.start = start;
        this.operandsLeft = operandsLeft;
        this.priorExpression = null;
        this.operation = Ops.ROOT;
    }

    public Expression(Expression priorExpression, double start, Ops operation, double nextOperand, List<Integer> operandsLeft) {
        this.priorExpression = priorExpression;
        this.start = operation.eval(start, nextOperand);
        this.nextOperand = nextOperand;
        this.operation = operation;
        this.operandsLeft = operandsLeft;
    }

    public List<Expression> iterateSimple() {
        List<Expression> ret = new ArrayList<>();
        for (int i = 0; i < operandsLeft.size(); i++) {
            double next = operandsLeft.get(i).doubleValue();
            List<Integer> operandsRemaining = removeFromGroup(i, operandsLeft);
            for (Ops op : EnumSet.complementOf(EnumSet.of(Ops.ROOT))) {
                ret.add(new Expression(this, start, op, next, operandsRemaining));
            }
        }
        return ret;
    }
    
    public List<Expression> iterateFull() {
        return new ArrayList<>();
    }

    public boolean finished() {
        return operandsLeft.isEmpty();
    }

    public double eval() {
        return start;
    }

    public StringBuilder buildString(StringBuilder b) {
        List<Expression> expressionChain = new ArrayList<>();
        expressionChain.addLast(this);
        Expression e = this;
        do {
            e = e.priorExpression;
            expressionChain.addFirst(e);
        } while (e.operation != Ops.ROOT);

        e = expressionChain.removeFirst();
        b.append(Double.valueOf(e.start).intValue());
        Ops lastOperator = null;
        while (!expressionChain.isEmpty()) {
            e = expressionChain.removeFirst();
            if (lastOperator != null && lastOperator.requiresParens(e.operation)){
                b.insert(0, "(").append(")");
            }
            switch (e.operation) {
                case REV_SUBTRACT -> b.insert(0, " - (").insert(0, Double.valueOf(e.nextOperand).intValue()).append(")");
                case REV_ADD -> b.insert(0, " + ").insert(0, Double.valueOf(e.nextOperand).intValue());
                case REV_MULTIPLY -> b.insert(0, " * ").insert(0, Double.valueOf(e.nextOperand).intValue());
                case REV_DIVIDE -> b.insert(0, " / (").insert(0, Double.valueOf(e.nextOperand).intValue()).append(")");
                default -> b.append(" ").append(e.operation.c).append(" ").append(Double.valueOf(e.nextOperand).intValue());
            }
            lastOperator = e.operation;
        }
        return b;
    }

    public static List<Integer> removeFromGroup(int index, List<Integer> group) {
        return IntStream.range(0, group.size())
            .filter(i -> i != index)
            .mapToObj(group::get)
            .collect(Collectors.toList());
    }
}
