package com.stephan.adventofcode.y2021.day1;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class SonarSweep {

  private final List<Integer> sweepList;

  SonarSweep() {
    this.sweepList = getInput();
  }

  int countIncreases() {
    return countListIncreases(sweepList);
  }

  int countWindowedIncreases() {
    return countListIncreases(sumOverWindow(sweepList));
  }

  private int countListIncreases(List<Integer> integerList) {
    AtomicInteger increaseCount = new AtomicInteger();

    for (int i = 0; i < integerList.size(); i++) {
      Integer current = integerList.get(i);
      Integer previous = null;

      if (i > 0) {
        previous = integerList.get(i - 1);
      }

      if (previous != null && current > previous) {
        increaseCount.incrementAndGet();
      }
    }
    return increaseCount.get();
  }

  private List<Integer> sumOverWindow(List<Integer> integerList) {
    List<Integer> sumList = new ArrayList<>();

    for (int i = 0; i < integerList.size(); i++) {
      Integer current = integerList.get(i);
      Integer previous = null;
      Integer next = null;
      if (i > 0) {
        previous = integerList.get(i - 1);
      }

      if (i < (integerList.size() - 1)) {
        next = integerList.get(i + 1);
      }

      if (previous == null || next == null) {
        continue;
      }

      List<Integer> set = List.of(previous, current, next);

      int sum = set.stream().mapToInt(Integer::intValue).sum();

      sumList.add(sum);
    }
    return sumList;
  }

  private static List<Integer> getInput() {
    try (InputStream stream = SonarSweep.class.getResourceAsStream("/y2021/day1/input.txt")) {
      assert stream != null;
      byte[] bytes = stream.readAllBytes();
      return Arrays.stream(new String(bytes).split("\\n")).map(Integer::parseInt).toList();
    } catch (IOException e) {
      throw new IllegalStateException("Failed to find input file");
    }
  }
}
