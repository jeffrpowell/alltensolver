package dev.jeffpowell.alltensolver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dev.jeffpowell.alltensolver.Expression.Ops;

public class ComplexExpression {
    
    private final double first;
    private final Ops firstAdd;
    private final double second;
    private final Ops middleMultiply;
    private final double third;
    private final Ops secondAdd;
    private final double fourth;
    private final double res;

    public ComplexExpression(double first, Ops firstAdd, double second, Ops middleMultiply, double third, Ops secondAdd,
            double fourth) {
        this.first = first;
        this.firstAdd = firstAdd;
        this.second = second;
        this.middleMultiply = middleMultiply;
        this.third = third;
        this.secondAdd = secondAdd;
        this.fourth = fourth;
        this.res = middleMultiply.eval(firstAdd.eval(first, second), secondAdd.eval(third, fourth));
    }

    //searches a limited space of the form (a +- b) */ (c +- d), which is not reachable by the iterateSimple() method
    public static List<ComplexExpression> generate(List<Integer> group) {
        double a = group.get(0).doubleValue();
        double b = group.get(1).doubleValue();
        double c = group.get(2).doubleValue();
        double d = group.get(3).doubleValue();
        return Stream.of(
            new ComplexExpression(a, Ops.ADD, b, Ops.MULTIPLY, c, Ops.ADD, d),
            new ComplexExpression(a, Ops.ADD, b, Ops.MULTIPLY, c, Ops.SUBTRACT, d),
            new ComplexExpression(a, Ops.SUBTRACT, b, Ops.MULTIPLY, c, Ops.SUBTRACT, d),
            new ComplexExpression(a, Ops.SUBTRACT, b, Ops.MULTIPLY, c, Ops.ADD, d),
            new ComplexExpression(a, Ops.ADD, b, Ops.DIVIDE, c, Ops.ADD, d),
            new ComplexExpression(a, Ops.ADD, b, Ops.DIVIDE, c, Ops.SUBTRACT, d),
            new ComplexExpression(a, Ops.SUBTRACT, b, Ops.DIVIDE, c, Ops.SUBTRACT, d),
            new ComplexExpression(a, Ops.SUBTRACT, b, Ops.DIVIDE, c, Ops.ADD, d),

            new ComplexExpression(a, Ops.ADD, b, Ops.MULTIPLY, d, Ops.ADD, c),
            new ComplexExpression(a, Ops.ADD, b, Ops.MULTIPLY, d, Ops.SUBTRACT, c),
            new ComplexExpression(a, Ops.SUBTRACT, b, Ops.MULTIPLY, d, Ops.SUBTRACT, c),
            new ComplexExpression(a, Ops.SUBTRACT, b, Ops.MULTIPLY, d, Ops.ADD, c),
            new ComplexExpression(a, Ops.ADD, b, Ops.DIVIDE, d, Ops.ADD, c),
            new ComplexExpression(a, Ops.ADD, b, Ops.DIVIDE, d, Ops.SUBTRACT, c),
            new ComplexExpression(a, Ops.SUBTRACT, b, Ops.DIVIDE, d, Ops.SUBTRACT, c),
            new ComplexExpression(a, Ops.SUBTRACT, b, Ops.DIVIDE, d, Ops.ADD, c),

            new ComplexExpression(a, Ops.ADD, c, Ops.MULTIPLY, b, Ops.ADD, d),
            new ComplexExpression(a, Ops.ADD, c, Ops.MULTIPLY, b, Ops.SUBTRACT, d),
            new ComplexExpression(a, Ops.SUBTRACT, c, Ops.MULTIPLY, b, Ops.SUBTRACT, d),
            new ComplexExpression(a, Ops.SUBTRACT, c, Ops.MULTIPLY, b, Ops.ADD, d),
            new ComplexExpression(a, Ops.ADD, c, Ops.DIVIDE, b, Ops.ADD, d),
            new ComplexExpression(a, Ops.ADD, c, Ops.DIVIDE, b, Ops.SUBTRACT, d),
            new ComplexExpression(a, Ops.SUBTRACT, c, Ops.DIVIDE, b, Ops.SUBTRACT, d),
            new ComplexExpression(a, Ops.SUBTRACT, c, Ops.DIVIDE, b, Ops.ADD, d),

            new ComplexExpression(a, Ops.ADD, c, Ops.MULTIPLY, d, Ops.ADD, b),
            new ComplexExpression(a, Ops.ADD, c, Ops.MULTIPLY, d, Ops.SUBTRACT, b),
            new ComplexExpression(a, Ops.SUBTRACT, c, Ops.MULTIPLY, d, Ops.SUBTRACT, b),
            new ComplexExpression(a, Ops.SUBTRACT, c, Ops.MULTIPLY, d, Ops.ADD, b),
            new ComplexExpression(a, Ops.ADD, c, Ops.DIVIDE, d, Ops.ADD, b),
            new ComplexExpression(a, Ops.ADD, c, Ops.DIVIDE, d, Ops.SUBTRACT, b),
            new ComplexExpression(a, Ops.SUBTRACT, c, Ops.DIVIDE, d, Ops.SUBTRACT, b),
            new ComplexExpression(a, Ops.SUBTRACT, c, Ops.DIVIDE, d, Ops.ADD, b),

            new ComplexExpression(a, Ops.ADD, d, Ops.MULTIPLY, b, Ops.ADD, c),
            new ComplexExpression(a, Ops.ADD, d, Ops.MULTIPLY, b, Ops.SUBTRACT, c),
            new ComplexExpression(a, Ops.SUBTRACT, d, Ops.MULTIPLY, b, Ops.SUBTRACT, c),
            new ComplexExpression(a, Ops.SUBTRACT, d, Ops.MULTIPLY, b, Ops.ADD, c),
            new ComplexExpression(a, Ops.ADD, d, Ops.DIVIDE, b, Ops.ADD, c),
            new ComplexExpression(a, Ops.ADD, d, Ops.DIVIDE, b, Ops.SUBTRACT, c),
            new ComplexExpression(a, Ops.SUBTRACT, d, Ops.DIVIDE, b, Ops.SUBTRACT, c),
            new ComplexExpression(a, Ops.SUBTRACT, d, Ops.DIVIDE, b, Ops.ADD, c),

            new ComplexExpression(a, Ops.ADD, d, Ops.MULTIPLY, c, Ops.ADD, b),
            new ComplexExpression(a, Ops.ADD, d, Ops.MULTIPLY, c, Ops.SUBTRACT, b),
            new ComplexExpression(a, Ops.SUBTRACT, d, Ops.MULTIPLY, c, Ops.SUBTRACT, b),
            new ComplexExpression(a, Ops.SUBTRACT, d, Ops.MULTIPLY, c, Ops.ADD, b),
            new ComplexExpression(a, Ops.ADD, d, Ops.DIVIDE, c, Ops.ADD, b),
            new ComplexExpression(a, Ops.ADD, d, Ops.DIVIDE, c, Ops.SUBTRACT, b),
            new ComplexExpression(a, Ops.SUBTRACT, d, Ops.DIVIDE, c, Ops.SUBTRACT, b),
            new ComplexExpression(a, Ops.SUBTRACT, d, Ops.DIVIDE, c, Ops.ADD, b),

            new ComplexExpression(b, Ops.ADD, a, Ops.MULTIPLY, c, Ops.ADD, d),
            new ComplexExpression(b, Ops.ADD, a, Ops.MULTIPLY, c, Ops.SUBTRACT, d),
            new ComplexExpression(b, Ops.SUBTRACT, a, Ops.MULTIPLY, c, Ops.SUBTRACT, d),
            new ComplexExpression(b, Ops.SUBTRACT, a, Ops.MULTIPLY, c, Ops.ADD, d),
            new ComplexExpression(b, Ops.ADD, a, Ops.DIVIDE, c, Ops.ADD, d),
            new ComplexExpression(b, Ops.ADD, a, Ops.DIVIDE, c, Ops.SUBTRACT, d),
            new ComplexExpression(b, Ops.SUBTRACT, a, Ops.DIVIDE, c, Ops.SUBTRACT, d),
            new ComplexExpression(b, Ops.SUBTRACT, a, Ops.DIVIDE, c, Ops.ADD, d),

            new ComplexExpression(b, Ops.ADD, a, Ops.MULTIPLY, d, Ops.ADD, c),
            new ComplexExpression(b, Ops.ADD, a, Ops.MULTIPLY, d, Ops.SUBTRACT, c),
            new ComplexExpression(b, Ops.SUBTRACT, a, Ops.MULTIPLY, d, Ops.SUBTRACT, c),
            new ComplexExpression(b, Ops.SUBTRACT, a, Ops.MULTIPLY, d, Ops.ADD, c),
            new ComplexExpression(b, Ops.ADD, a, Ops.DIVIDE, d, Ops.ADD, c),
            new ComplexExpression(b, Ops.ADD, a, Ops.DIVIDE, d, Ops.SUBTRACT, c),
            new ComplexExpression(b, Ops.SUBTRACT, a, Ops.DIVIDE, d, Ops.SUBTRACT, c),
            new ComplexExpression(b, Ops.SUBTRACT, a, Ops.DIVIDE, d, Ops.ADD, c),

            new ComplexExpression(b, Ops.ADD, c, Ops.MULTIPLY, a, Ops.ADD, d),
            new ComplexExpression(b, Ops.ADD, c, Ops.MULTIPLY, a, Ops.SUBTRACT, d),
            new ComplexExpression(b, Ops.SUBTRACT, c, Ops.MULTIPLY, a, Ops.SUBTRACT, d),
            new ComplexExpression(b, Ops.SUBTRACT, c, Ops.MULTIPLY, a, Ops.ADD, d),
            new ComplexExpression(b, Ops.ADD, c, Ops.DIVIDE, a, Ops.ADD, d),
            new ComplexExpression(b, Ops.ADD, c, Ops.DIVIDE, a, Ops.SUBTRACT, d),
            new ComplexExpression(b, Ops.SUBTRACT, c, Ops.DIVIDE, a, Ops.SUBTRACT, d),
            new ComplexExpression(b, Ops.SUBTRACT, c, Ops.DIVIDE, a, Ops.ADD, d),

            new ComplexExpression(b, Ops.ADD, c, Ops.MULTIPLY, d, Ops.ADD, a),
            new ComplexExpression(b, Ops.ADD, c, Ops.MULTIPLY, d, Ops.SUBTRACT, a),
            new ComplexExpression(b, Ops.SUBTRACT, c, Ops.MULTIPLY, d, Ops.SUBTRACT, a),
            new ComplexExpression(b, Ops.SUBTRACT, c, Ops.MULTIPLY, d, Ops.ADD, a),
            new ComplexExpression(b, Ops.ADD, c, Ops.DIVIDE, d, Ops.ADD, a),
            new ComplexExpression(b, Ops.ADD, c, Ops.DIVIDE, d, Ops.SUBTRACT, a),
            new ComplexExpression(b, Ops.SUBTRACT, c, Ops.DIVIDE, d, Ops.SUBTRACT, a),
            new ComplexExpression(b, Ops.SUBTRACT, c, Ops.DIVIDE, d, Ops.ADD, a),

            new ComplexExpression(b, Ops.ADD, d, Ops.MULTIPLY, a, Ops.ADD, c),
            new ComplexExpression(b, Ops.ADD, d, Ops.MULTIPLY, a, Ops.SUBTRACT, c),
            new ComplexExpression(b, Ops.SUBTRACT, d, Ops.MULTIPLY, a, Ops.SUBTRACT, c),
            new ComplexExpression(b, Ops.SUBTRACT, d, Ops.MULTIPLY, a, Ops.ADD, c),
            new ComplexExpression(b, Ops.ADD, d, Ops.DIVIDE, a, Ops.ADD, c),
            new ComplexExpression(b, Ops.ADD, d, Ops.DIVIDE, a, Ops.SUBTRACT, c),
            new ComplexExpression(b, Ops.SUBTRACT, d, Ops.DIVIDE, a, Ops.SUBTRACT, c),
            new ComplexExpression(b, Ops.SUBTRACT, d, Ops.DIVIDE, a, Ops.ADD, c),

            new ComplexExpression(b, Ops.ADD, d, Ops.MULTIPLY, c, Ops.ADD, a),
            new ComplexExpression(b, Ops.ADD, d, Ops.MULTIPLY, c, Ops.SUBTRACT, a),
            new ComplexExpression(b, Ops.SUBTRACT, d, Ops.MULTIPLY, c, Ops.SUBTRACT, a),
            new ComplexExpression(b, Ops.SUBTRACT, d, Ops.MULTIPLY, c, Ops.ADD, a),
            new ComplexExpression(b, Ops.ADD, d, Ops.DIVIDE, c, Ops.ADD, a),
            new ComplexExpression(b, Ops.ADD, d, Ops.DIVIDE, c, Ops.SUBTRACT, a),
            new ComplexExpression(b, Ops.SUBTRACT, d, Ops.DIVIDE, c, Ops.SUBTRACT, a),
            new ComplexExpression(b, Ops.SUBTRACT, d, Ops.DIVIDE, c, Ops.ADD, a),

            new ComplexExpression(c, Ops.ADD, a, Ops.MULTIPLY, b, Ops.ADD, d),
            new ComplexExpression(c, Ops.ADD, a, Ops.MULTIPLY, b, Ops.SUBTRACT, d),
            new ComplexExpression(c, Ops.SUBTRACT, a, Ops.MULTIPLY, b, Ops.SUBTRACT, d),
            new ComplexExpression(c, Ops.SUBTRACT, a, Ops.MULTIPLY, b, Ops.ADD, d),
            new ComplexExpression(c, Ops.ADD, a, Ops.DIVIDE, b, Ops.ADD, d),
            new ComplexExpression(c, Ops.ADD, a, Ops.DIVIDE, b, Ops.SUBTRACT, d),
            new ComplexExpression(c, Ops.SUBTRACT, a, Ops.DIVIDE, b, Ops.SUBTRACT, d),
            new ComplexExpression(c, Ops.SUBTRACT, a, Ops.DIVIDE, b, Ops.ADD, d),

            new ComplexExpression(c, Ops.ADD, a, Ops.MULTIPLY, d, Ops.ADD, b),
            new ComplexExpression(c, Ops.ADD, a, Ops.MULTIPLY, d, Ops.SUBTRACT, b),
            new ComplexExpression(c, Ops.SUBTRACT, a, Ops.MULTIPLY, d, Ops.SUBTRACT, b),
            new ComplexExpression(c, Ops.SUBTRACT, a, Ops.MULTIPLY, d, Ops.ADD, b),
            new ComplexExpression(c, Ops.ADD, a, Ops.DIVIDE, d, Ops.ADD, b),
            new ComplexExpression(c, Ops.ADD, a, Ops.DIVIDE, d, Ops.SUBTRACT, b),
            new ComplexExpression(c, Ops.SUBTRACT, a, Ops.DIVIDE, d, Ops.SUBTRACT, b),
            new ComplexExpression(c, Ops.SUBTRACT, a, Ops.DIVIDE, d, Ops.ADD, b),

            new ComplexExpression(c, Ops.ADD, b, Ops.MULTIPLY, a, Ops.ADD, d),
            new ComplexExpression(c, Ops.ADD, b, Ops.MULTIPLY, a, Ops.SUBTRACT, d),
            new ComplexExpression(c, Ops.SUBTRACT, b, Ops.MULTIPLY, a, Ops.SUBTRACT, d),
            new ComplexExpression(c, Ops.SUBTRACT, b, Ops.MULTIPLY, a, Ops.ADD, d),
            new ComplexExpression(c, Ops.ADD, b, Ops.DIVIDE, a, Ops.ADD, d),
            new ComplexExpression(c, Ops.ADD, b, Ops.DIVIDE, a, Ops.SUBTRACT, d),
            new ComplexExpression(c, Ops.SUBTRACT, b, Ops.DIVIDE, a, Ops.SUBTRACT, d),
            new ComplexExpression(c, Ops.SUBTRACT, b, Ops.DIVIDE, a, Ops.ADD, d),

            new ComplexExpression(c, Ops.ADD, b, Ops.MULTIPLY, d, Ops.ADD, a),
            new ComplexExpression(c, Ops.ADD, b, Ops.MULTIPLY, d, Ops.SUBTRACT, a),
            new ComplexExpression(c, Ops.SUBTRACT, b, Ops.MULTIPLY, d, Ops.SUBTRACT, a),
            new ComplexExpression(c, Ops.SUBTRACT, b, Ops.MULTIPLY, d, Ops.ADD, a),
            new ComplexExpression(c, Ops.ADD, b, Ops.DIVIDE, d, Ops.ADD, a),
            new ComplexExpression(c, Ops.ADD, b, Ops.DIVIDE, d, Ops.SUBTRACT, a),
            new ComplexExpression(c, Ops.SUBTRACT, b, Ops.DIVIDE, d, Ops.SUBTRACT, a),
            new ComplexExpression(c, Ops.SUBTRACT, b, Ops.DIVIDE, d, Ops.ADD, a),

            new ComplexExpression(c, Ops.ADD, d, Ops.MULTIPLY, a, Ops.ADD, b),
            new ComplexExpression(c, Ops.ADD, d, Ops.MULTIPLY, a, Ops.SUBTRACT, b),
            new ComplexExpression(c, Ops.SUBTRACT, d, Ops.MULTIPLY, a, Ops.SUBTRACT, b),
            new ComplexExpression(c, Ops.SUBTRACT, d, Ops.MULTIPLY, a, Ops.ADD, b),
            new ComplexExpression(c, Ops.ADD, d, Ops.DIVIDE, a, Ops.ADD, b),
            new ComplexExpression(c, Ops.ADD, d, Ops.DIVIDE, a, Ops.SUBTRACT, b),
            new ComplexExpression(c, Ops.SUBTRACT, d, Ops.DIVIDE, a, Ops.SUBTRACT, b),
            new ComplexExpression(c, Ops.SUBTRACT, d, Ops.DIVIDE, a, Ops.ADD, b),

            new ComplexExpression(c, Ops.ADD, d, Ops.MULTIPLY, b, Ops.ADD, a),
            new ComplexExpression(c, Ops.ADD, d, Ops.MULTIPLY, b, Ops.SUBTRACT, a),
            new ComplexExpression(c, Ops.SUBTRACT, d, Ops.MULTIPLY, b, Ops.SUBTRACT, a),
            new ComplexExpression(c, Ops.SUBTRACT, d, Ops.MULTIPLY, b, Ops.ADD, a),
            new ComplexExpression(c, Ops.ADD, d, Ops.DIVIDE, b, Ops.ADD, a),
            new ComplexExpression(c, Ops.ADD, d, Ops.DIVIDE, b, Ops.SUBTRACT, a),
            new ComplexExpression(c, Ops.SUBTRACT, d, Ops.DIVIDE, b, Ops.SUBTRACT, a),
            new ComplexExpression(c, Ops.SUBTRACT, d, Ops.DIVIDE, b, Ops.ADD, a),

            new ComplexExpression(d, Ops.ADD, a, Ops.MULTIPLY, b, Ops.ADD, c),
            new ComplexExpression(d, Ops.ADD, a, Ops.MULTIPLY, b, Ops.SUBTRACT, c),
            new ComplexExpression(d, Ops.SUBTRACT, a, Ops.MULTIPLY, b, Ops.SUBTRACT, c),
            new ComplexExpression(d, Ops.SUBTRACT, a, Ops.MULTIPLY, b, Ops.ADD, c),
            new ComplexExpression(d, Ops.ADD, a, Ops.DIVIDE, b, Ops.ADD, c),
            new ComplexExpression(d, Ops.ADD, a, Ops.DIVIDE, b, Ops.SUBTRACT, c),
            new ComplexExpression(d, Ops.SUBTRACT, a, Ops.DIVIDE, b, Ops.SUBTRACT, c),
            new ComplexExpression(d, Ops.SUBTRACT, a, Ops.DIVIDE, b, Ops.ADD, c),

            new ComplexExpression(d, Ops.ADD, a, Ops.MULTIPLY, c, Ops.ADD, b),
            new ComplexExpression(d, Ops.ADD, a, Ops.MULTIPLY, c, Ops.SUBTRACT, b),
            new ComplexExpression(d, Ops.SUBTRACT, a, Ops.MULTIPLY, c, Ops.SUBTRACT, b),
            new ComplexExpression(d, Ops.SUBTRACT, a, Ops.MULTIPLY, c, Ops.ADD, b),
            new ComplexExpression(d, Ops.ADD, a, Ops.DIVIDE, c, Ops.ADD, b),
            new ComplexExpression(d, Ops.ADD, a, Ops.DIVIDE, c, Ops.SUBTRACT, b),
            new ComplexExpression(d, Ops.SUBTRACT, a, Ops.DIVIDE, c, Ops.SUBTRACT, b),
            new ComplexExpression(d, Ops.SUBTRACT, a, Ops.DIVIDE, c, Ops.ADD, b),

            new ComplexExpression(d, Ops.ADD, b, Ops.MULTIPLY, a, Ops.ADD, c),
            new ComplexExpression(d, Ops.ADD, b, Ops.MULTIPLY, a, Ops.SUBTRACT, c),
            new ComplexExpression(d, Ops.SUBTRACT, b, Ops.MULTIPLY, a, Ops.SUBTRACT, c),
            new ComplexExpression(d, Ops.SUBTRACT, b, Ops.MULTIPLY, a, Ops.ADD, c),
            new ComplexExpression(d, Ops.ADD, b, Ops.DIVIDE, a, Ops.ADD, c),
            new ComplexExpression(d, Ops.ADD, b, Ops.DIVIDE, a, Ops.SUBTRACT, c),
            new ComplexExpression(d, Ops.SUBTRACT, b, Ops.DIVIDE, a, Ops.SUBTRACT, c),
            new ComplexExpression(d, Ops.SUBTRACT, b, Ops.DIVIDE, a, Ops.ADD, c),

            new ComplexExpression(d, Ops.ADD, b, Ops.MULTIPLY, c, Ops.ADD, a),
            new ComplexExpression(d, Ops.ADD, b, Ops.MULTIPLY, c, Ops.SUBTRACT, a),
            new ComplexExpression(d, Ops.SUBTRACT, b, Ops.MULTIPLY, c, Ops.SUBTRACT, a),
            new ComplexExpression(d, Ops.SUBTRACT, b, Ops.MULTIPLY, c, Ops.ADD, a),
            new ComplexExpression(d, Ops.ADD, b, Ops.DIVIDE, c, Ops.ADD, a),
            new ComplexExpression(d, Ops.ADD, b, Ops.DIVIDE, c, Ops.SUBTRACT, a),
            new ComplexExpression(d, Ops.SUBTRACT, b, Ops.DIVIDE, c, Ops.SUBTRACT, a),
            new ComplexExpression(d, Ops.SUBTRACT, b, Ops.DIVIDE, c, Ops.ADD, a),

            new ComplexExpression(d, Ops.ADD, c, Ops.MULTIPLY, a, Ops.ADD, b),
            new ComplexExpression(d, Ops.ADD, c, Ops.MULTIPLY, a, Ops.SUBTRACT, b),
            new ComplexExpression(d, Ops.SUBTRACT, c, Ops.MULTIPLY, a, Ops.SUBTRACT, b),
            new ComplexExpression(d, Ops.SUBTRACT, c, Ops.MULTIPLY, a, Ops.ADD, b),
            new ComplexExpression(d, Ops.ADD, c, Ops.DIVIDE, a, Ops.ADD, b),
            new ComplexExpression(d, Ops.ADD, c, Ops.DIVIDE, a, Ops.SUBTRACT, b),
            new ComplexExpression(d, Ops.SUBTRACT, c, Ops.DIVIDE, a, Ops.SUBTRACT, b),
            new ComplexExpression(d, Ops.SUBTRACT, c, Ops.DIVIDE, a, Ops.ADD, b),

            new ComplexExpression(d, Ops.ADD, c, Ops.MULTIPLY, b, Ops.ADD, a),
            new ComplexExpression(d, Ops.ADD, c, Ops.MULTIPLY, b, Ops.SUBTRACT, a),
            new ComplexExpression(d, Ops.SUBTRACT, c, Ops.MULTIPLY, b, Ops.SUBTRACT, a),
            new ComplexExpression(d, Ops.SUBTRACT, c, Ops.MULTIPLY, b, Ops.ADD, a),
            new ComplexExpression(d, Ops.ADD, c, Ops.DIVIDE, b, Ops.ADD, a),
            new ComplexExpression(d, Ops.ADD, c, Ops.DIVIDE, b, Ops.SUBTRACT, a),
            new ComplexExpression(d, Ops.SUBTRACT, c, Ops.DIVIDE, b, Ops.SUBTRACT, a),
            new ComplexExpression(d, Ops.SUBTRACT, c, Ops.DIVIDE, b, Ops.ADD, a)
        ).collect(Collectors.toList());
    }

    public double eval() {
        return res;
    }

    public StringBuilder buildString(StringBuilder b) {
        return b.append("(")
            .append(d2i(first))
            .append(" ")
            .append(firstAdd.c)
            .append(" ")
            .append(d2i(second))
        .append(") ")
            .append(middleMultiply.c)
        .append(" (")
            .append(d2i(third))
            .append(" ")
            .append(secondAdd.c)
            .append(" ")
            .append(d2i(fourth))
        .append(")");

    }

    private static int d2i (double d) {
        return Double.valueOf(d).intValue();
    }
}
