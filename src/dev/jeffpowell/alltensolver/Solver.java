package dev.jeffpowell.alltensolver;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class Solver {
   
    private final List<List<Integer>> inputGroups;
    private final Consumer<String> callback;
    private final Set<Integer> solved;

    public Solver(List<List<Integer>> inputGroups, Consumer<String> callback) {
        this.inputGroups = inputGroups;
        this.callback = callback;
        this.solved = new HashSet<>();
    }

    public void solve() {
        inputGroups.forEach(this::simpleSearch);
        inputGroups.stream()
            .filter(g -> g.size() == 4).forEach(this::fullSearch);
    };

    private void simpleSearch(List<Integer> group) {
        search(group, Expression::iterate);
    }
    
    private void fullSearch(List<Integer> group) {
        searchComplex(group);
    }

    private void search(List<Integer> group, Function<Expression, List<Expression>> expressionIterator) {
        Deque<Expression> q = new ArrayDeque<>();
        for (int i = 0; i < group.size(); i++) {
            q.push(new Expression(group.get(i).doubleValue(), Expression.removeFromGroup(i, group)));
        }
        while(!q.isEmpty()) {
            Expression e = q.pop();
            if (e.finished()) {
                double res = e.eval();
                int resInt = Double.valueOf(res).intValue();
                if (Math.floor(res) == res && res > 0.9999 && res < 10.0001 && solved.add(resInt)) {
                    callback.accept(e.buildString(new StringBuilder()).insert(0, " = ").insert(0, resInt).toString());
                    if (solved.size() == 10) {
                        break;
                    }
                }
            }
            else {
                expressionIterator.apply(e).forEach(q::push);
            }
        }
    }

    private void searchComplex(List<Integer> group) {
        Deque<ComplexExpression> q = new ArrayDeque<>();
        ComplexExpression.generate(group).forEach(q::push);
        while(!q.isEmpty()) {
            ComplexExpression e = q.pop();
            double res = e.eval();
            int resInt = Double.valueOf(res).intValue();
            if (Math.floor(res) == res && res > 0.9999 && res < 10.0001 && solved.add(resInt)) {
                callback.accept(e.buildString(new StringBuilder()).insert(0, " = ").insert(0, resInt).toString());
                if (solved.size() == 10) {
                    break;
                }
            }
        }
    }
}
