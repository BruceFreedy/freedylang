package me.brucefreedy.freedylang.lang.regex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class Regex {

    @Getter
    private static final ArrayList<Rex> rex = new ArrayList<>();

    @ToString
    @AllArgsConstructor
    public static class Rex {
        int size;
        ArrayList<String> rex;
    }

    public static void addRegex(String regex) {
        if (regex.isEmpty()) return;
        int size = regex.length();
        int count = 1;
        for (Rex rex : rex) {
            if (rex.size == size) {
                if (!rex.rex.contains(regex)) rex.rex.add(regex);
                return;
            }
            if (size > rex.size) break;
            count = rex.size;
        }
        Rex element = new Rex(size, new ArrayList<>());
        element.rex.add(regex);
        rex.add(count - 1, element);
    }

    @AllArgsConstructor
    public static class RexResult {
        public int size;
        public int index;
    }

    /**
     * return lowest index of containing rex in source
     */
    private static int rex(ArrayList<String> rex, String source) {
        return rex.stream().mapToInt(source::indexOf).filter(i -> i != -1).min().orElse(-1);
    }

    public static RexResult rex(String source) {
        return rex.stream()
                .map(rex -> new RexResult(rex.size, rex(rex.rex, source)))
                .filter(rex -> rex.index != -1)
                .min(Comparator.comparingInt(o -> o.index))
                .orElse(new RexResult(0, -1));
    }

}
