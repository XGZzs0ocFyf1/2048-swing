package hw3.game;

import java.util.*;
import java.util.stream.Collectors;

public class GameHelper {

    public List<Integer> moveAndMergeEqual(List<Integer> list) {
        List<Integer> output = sort(list);
        for (int i = 0; i < output.size() - 1; i++) {
            int firstElement = output.get(i) == null ? 0 : output.get(i);
            int secondElement = output.get(i + 1) == null ? 0 : output.get(i + 1);

            if (firstElement == secondElement) {
                if (output.get(i) != null) {
                    output.set(i, firstElement + secondElement);
                    output.set(i + 1, null);
                }
            }
        }
        output = sort(output);
        return output;

    }

    private List<Integer> sort(List<Integer> input) {
        return input.stream()
                .sorted((a, b) -> nullComparator(a, b))
                .collect(Collectors.toList());
    }
    private int nullComparator(Integer a, Integer b) {
        if (a == null)
            return 1;
        if (b == null)
            return -1;
        return 0;
    }

}
