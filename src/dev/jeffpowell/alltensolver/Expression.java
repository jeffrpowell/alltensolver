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
        this.operation = operation;
        this.operandsLeft = operandsLeft;
    }

    public List<Expression> iterateSimple() {
        return iterate(EnumSet.of(Ops.ADD, Ops.SUBTRACT));
    }
    
    public List<Expression> iterateFull() {
        return iterate(EnumSet.allOf(Ops.class));
    }

    private List<Expression> iterate(EnumSet<Ops> ops) {
        double next = operandsLeft.get(0).doubleValue();
        List<Integer> operandsRemaining = removeFromGroup(0, operandsLeft);
        List<Expression> ret = new ArrayList<>();
        for (Ops op : ops) {
            ret.add(new Expression(this, start, op, next, operandsRemaining));
        }
        return ret;
    }

    public boolean finished() {
        return operandsLeft.isEmpty();
    }

    public double eval() {
        return start;
        // return pointerToStart.nextExpression.eval(pointerToStart.start);
    }

    // private double eval(double run) {
    //     run = operation.eval(run, nextOperand);
    //     if (finished()) {
    //         return run;
    //     }
    //     else {
    //         return nextExpression.eval(run);
    //     }
    // }

    public StringBuilder buildString(boolean isStart, StringBuilder b) {
        List<Expression> expressionChain = Stream.iterate(this, e -> e.operation != Ops.ROOT, e -> e.priorExpression)
            .collect(Collectors.toList()).reversed();
        Expression e = expressionChain.removeFirst();
        b.append(e.start);
        while (!expressionChain.isEmpty()) {
            e = expressionChain.removeFirst();
            b.append(" ").append(e.operation.c).append(" ").append(nextOperand);
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
