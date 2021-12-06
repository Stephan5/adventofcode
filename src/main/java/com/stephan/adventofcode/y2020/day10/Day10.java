package com.stephan.adventofcode.y2020.day10;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Day10 extends DailyChallenge {

  public Day10() {
    super(2020, 10);
  }

  @Override
  public long part1Result() {
    List<Integer> adapterList = getOrderedJoltageList();

    List<Integer> joltDiff = new ArrayList<>();

    for (int i = 1; i < adapterList.size(); i++) {
      int i1 = adapterList.get(i) - adapterList.get(i - 1);

      if (!Set.of(1, 2, 3).contains(i1)) {
        throw new IllegalStateException();
      }

      joltDiff.add(i1);
    }

    long oneJoltDiffCount = joltDiff.stream().filter(diff -> diff.equals(1)).count();
    long threeJoltDiffCount = joltDiff.stream().filter(diff -> diff.equals(3)).count();
    return oneJoltDiffCount * threeJoltDiffCount;
  }

  @Override
  public long part2Result() {
    int[] list = getInputAsIntegerList().stream().mapToInt(Integer::intValue).toArray();
    Arrays.sort(list);

    long[] sums = new long[list[list.length - 1] + 1];
    sums[0] = 1;
    for (int j : list) {
      long x = j >= 3 ? sums[j - 3] : 0;
      long y = j >= 2 ? sums[j - 2] : 0;
      long z = j >= 1 ? sums[j - 1] : 0;
      sums[j] = x + y + z;
    }

    return sums[sums.length - 1];
  }

  private List<Integer> getOrderedJoltageList() {
    List<Integer> adapterList = new ArrayList<>(getInputAsIntegerList());

    Collections.sort(adapterList);

    adapterList.add(0, 0);

    Integer maxAdapter = adapterList.stream().max(Comparator.comparing(Function.identity())).orElseThrow();
    int deviceJoltage = maxAdapter + 3;
    adapterList.add(deviceJoltage);
    return adapterList;
  }
}
