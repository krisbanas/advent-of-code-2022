package com.github.krisbanas.solutions;

import com.github.krisbanas.toolbox.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day22 {

    public static void main(String[] args) {
        new Day22();
    }

    public Day22() {
        System.out.println(part1());
        System.out.println(part2());
    }

    public Object part1() {
        return FileReader.readAsLongList("22.txt")
                .stream().mapToLong(this::calculateSecret)
                .sum();
    }

    public Object part2() {
        return FileReader.readAsLongList("22.txt")
                .stream()
                .map(this::sequenceToProfitMap)
                .flatMap(profitMap -> profitMap.entrySet().stream())
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingInt(Map.Entry::getValue)))
                .values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();
    }

    private long calculateSecret(long secret) {
        long x = secret;
        for (int i = 0; i < 2_000; i++) {
            x = prune(mix(x, x * 64));
            x = prune(mix(x, x / 32));
            x = prune(mix(x, x * 2048));
        }
        return x;
    }

    private Map<String, Integer> sequenceToProfitMap(long initialSecretNumber) {
        long x = initialSecretNumber;
        List<Long> result = new ArrayList<>();
        for (int i = 0; i < 2_000; i++) {
            x = prune(mix(x, x * 64));
            x = prune(mix(x, x / 32));
            x = prune(mix(x, x * 2048));
            result.add(x);
        }

        List<Integer> prices = new ArrayList<>();
        prices.add((int) (initialSecretNumber % 10));

        IntStream.range(0, 2_000)
                .mapToObj(i -> (int) (result.get(i) % 10))
                .forEach(prices::add);

        List<Integer> diffs = IntStream.range(0, 2_000)
                .mapToObj(i -> prices.get(i + 1) - prices.get(i))
                .collect(Collectors.toList());

        Map<String, Integer> sequenceTargetMap = new HashMap<>();
        IntStream.range(4, diffs.size()).forEach(i -> {
            String key = diffsToSequence(diffs.subList(i - 4, i));
            if (sequenceTargetMap.containsKey(key)) return;
            sequenceTargetMap.put(key, prices.get(i));
        });
        return sequenceTargetMap;
    }

    private String diffsToSequence(List<Integer> diffs) {
        return diffs.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }

    private static long mix(long start, long secret) {
        return start ^ secret;
    }

    private static long prune(long start) {
        return start % 16777216;
    }
}
