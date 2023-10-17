package dev.jeffpowell.alltensolver;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

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
        List<Integer> groupOf4 = Collections.emptyList();
        for (List<Integer> group : inputGroups) {
            if (group.size() == 4) {
                simpleSearchForListOf4(group);
                groupOf4 = group;
            }
            else {
                fullSearch(group);
            }
        }
        fullSearch(groupOf4);
    };

    private void simpleSearchForListOf4(List<Integer> group) {
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
                    callback.accept(e.buildString(true, new StringBuilder(resInt).append(" = ")).toString());
                    if (solved.size() == 10) {
                        break;
                    }
                }
            }
            else {
                e.iterateSimple().forEach(q::push);
            }
        }
    }

    private void fullSearch(List<Integer> group) {
        Deque<Expression> q = new ArrayDeque<>();
        for (int i = 0; i < group.size(); i++) {
            q.push(new Expression(group.get(i).doubleValue(), Expression.removeFromGroup(i, group)));
        }
        while(!q.isEmpty()) {
            Expression e = q.pop();
            if (e.finished()) {
                double res = e.eval();
                int resInt = Double.valueOf(res).intValue();
                if (res > 0.9999 && res < 10.0001 && solved.add(resInt)) {
                    callback.accept(e.buildString(true, new StringBuilder(resInt).append(" = ")).toString());
                    if (solved.size() == 10) {
                        break;
                    }
                }
            }
            else {
                e.iterateFull().forEach(q::push);
            }
        }
    }
}
