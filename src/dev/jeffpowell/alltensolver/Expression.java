package dev.jeffpowell.alltensolver;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Expression {
    public static enum Ops {
        ROOT(' ', (l,r) -> l),
        ADD('+', (l,r) -> l + r),
        SUBTRACT('-', (l,r) -> l - r),
        MULTIPLY('*', (l,r) -> l * r),
        DIVIDE('/', (l,r) -> l / r);
        final char c;
        final BiFunction<Double, Double, Double> fn;
        private Ops(char c, BiFunction<Double, Double, Double> fn) {
            this.c = c;
            this.fn = fn;
        }

        public double eval(double l, double r) {
            return fn.apply(l, r);
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
        double next = operandsLeft.get(0).doubleValue();
        List<Integer> operandsRemaining = removeFromGroup(0, operandsLeft);
        List<Expression> ret = new ArrayList<>();
        for (Ops op : EnumSet.complementOf(EnumSet.of(Ops.ROOT))) {
            ret.add(new Expression(this, start, op, next, operandsRemaining));
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

    public StringBuilder buildString(boolean isStart, StringBuilder b) {
        List<Expression> expressionChain = new ArrayList<>();
        expressionChain.addLast(this);
        Expression e = this;
        do {
            e = e.priorExpression;
            expressionChain.addFirst(e);
        } while (e.operation != Ops.ROOT);

        e = expressionChain.removeFirst();
        b.append(Double.valueOf(e.start).intValue());
        while (!expressionChain.isEmpty()) {
            e = expressionChain.removeFirst();
            b.append(" ").append(e.operation.c).append(" ").append(Double.valueOf(e.nextOperand).intValue());
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
