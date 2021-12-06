package com.stephan.adventofcode.y2020.day1;

import com.stephan.adventofcode.DailyChallenge;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day1 extends DailyChallenge {

  private static final int TARGET = 2020;

  public Day1() {
    super(2020, 1);
  }

  @Override
  public long part1Result() {
    List<Integer> expenses = getInputAsIntegerList();
    Set<Integer> exponents = new HashSet<>();

    for (Integer i : expenses) {
      for (Integer j : expenses) {
        if (i + j == TARGET) {
          exponents.add(i);
          exponents.add(j);
        }
      }
    }

    if (exponents.size() != 2) {
      throw new IllegalStateException("Expected to find two entries that sum to 2020 but found " + exponents.size());
    }

    return exponents.stream().reduce(1, (x, y) -> x * y);
  }

  @Override
  public long part2Result() {
    List<Integer> expenses = getInputAsIntegerList();
    Set<Integer> exponents = new HashSet<>();

    for (Integer i : expenses) {
      for (Integer j : expenses) {
        for (Integer k : expenses) {
          if (i + j + k == TARGET) {
            exponents.add(i);
            exponents.add(j);
            exponents.add(k);
          }
        }
      }
    }

    if (exponents.size() != 3) {
      throw new IllegalStateException("Expected to find three entries that sum to 2020 but found " + exponents.size());
    }

    return exponents.stream().reduce(1, (x, y) -> x * y);
  }
}
