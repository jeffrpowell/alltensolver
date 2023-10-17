package dev.jeffpowell.alltensolver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<List<Integer>> inputGroups = generateInputGroups(args[0], args[1], args[2], args[3]);
        Solver s = new Solver(inputGroups, System.out::println);
        s.solve();
    }

    private static List<List<Integer>> generateInputGroups(String... inputs) {
        List<List<Integer>> inputGroups = new ArrayList<>();
        List<Integer> unsorted = Stream.of(
            Integer.parseInt(inputs[0]),
            Integer.parseInt(inputs[1]),
            Integer.parseInt(inputs[2]),
            Integer.parseInt(inputs[3])
        ).collect(Collectors.toList());
        unsorted.sort(Comparator.naturalOrder());
        int i0 = unsorted.get(0);
        int i1 = unsorted.get(1);
        int i2 = unsorted.get(2);
        int i3 = unsorted.get(3);
        int i01 = Integer.parseInt(inputs[0]+inputs[1]);
        int i02 = Integer.parseInt(inputs[0]+inputs[2]);
        int i03 = Integer.parseInt(inputs[0]+inputs[3]);
        int i10 = Integer.parseInt(inputs[1]+inputs[0]);
        int i12 = Integer.parseInt(inputs[1]+inputs[2]);
        int i13 = Integer.parseInt(inputs[1]+inputs[3]);
        int i20 = Integer.parseInt(inputs[2]+inputs[0]);
        int i21 = Integer.parseInt(inputs[2]+inputs[1]);
        int i23 = Integer.parseInt(inputs[2]+inputs[3]);
        int i30 = Integer.parseInt(inputs[3]+inputs[0]);
        int i31 = Integer.parseInt(inputs[3]+inputs[1]);
        int i32 = Integer.parseInt(inputs[3]+inputs[2]);
        inputGroups.add(List.of(i23, i01));
        add(inputGroups, List.of(i32, i01));
        add(inputGroups, List.of(i13, i02));
        add(inputGroups, List.of(i31, i02));
        add(inputGroups, List.of(i12, i03));
        add(inputGroups, List.of(i21, i03));
        add(inputGroups, List.of(i30, i12));
        add(inputGroups, List.of(i20, i13));
        add(inputGroups, List.of(i10, i23));
        add(inputGroups, List.of(i01, i2, i3));
        add(inputGroups, List.of(i02, i1, i3));
        add(inputGroups, List.of(i03, i1, i2));
        add(inputGroups, List.of(i10, i2, i3));
        add(inputGroups, List.of(i12, i0, i3));
        add(inputGroups, List.of(i13, i0, i2));
        add(inputGroups, List.of(i20, i1, i3));
        add(inputGroups, List.of(i21, i0, i3));
        add(inputGroups, List.of(i23, i0, i1));
        add(inputGroups, List.of(i30, i1, i2));
        add(inputGroups, List.of(i31, i0, i2));
        add(inputGroups, List.of(i32, i0, i1));
        inputGroups.add(List.of(i0, i1, i2, i3));
        return inputGroups;
    }

    private static void add(List<List<Integer>> inputGroups, List<Integer> group) {
        if (!inputGroups.contains(group)) {
            inputGroups.add(group);
        }
    }
    
}
