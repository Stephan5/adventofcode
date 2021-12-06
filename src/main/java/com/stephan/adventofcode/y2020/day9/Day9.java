package com.stephan.adventofcode.y2020.day9;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Day9 implements DailyChallenge {

  @Override
  public int year() {
    return 2020;
  }

  @Override
  public int day() {
    return 9;
  }

  @Override
  public long part1Result() {
    int preambleSize = 25;
    return part1(preambleSize);
  }

  long part1(int preambleSize) {
    List<Long> cypher = Arrays.stream(getInputString().split("\\n")).map(Long::parseLong).toList();
    return findWeakness(cypher, preambleSize);
  }

  @Override
  public long part2Result() {
    int preambleSize = 25;
    return part2(preambleSize);
  }

  long part2(int preambleSize) {
    List<Long> cypher = Arrays.stream(getInputString().split("\\n")).map(Long::parseLong).toList();
    Long weakness = findWeakness(cypher, preambleSize);
    return findContiguousSetToMatchWeakness(cypher, weakness);
  }

  private long findContiguousSetToMatchWeakness(List<Long> cypher, Long weakness) {
    List<Long> numbersUnderConsideration = new ArrayList<>();

    for (Long number : cypher) {
      numbersUnderConsideration.add(number);
      Long sum = sum(numbersUnderConsideration);

      while (sum > weakness) {
        numbersUnderConsideration.remove(0);
        sum = sum(numbersUnderConsideration);
      }

      if (sum.equals(weakness)) {
        break;
      }
    }

    Long smallestInContiguousRange = numbersUnderConsideration.stream().min(Comparator.comparing(Function.identity())).orElseThrow();
    Long largestInContiguousRange = numbersUnderConsideration.stream().max(Comparator.comparing(Function.identity())).orElseThrow();

    return smallestInContiguousRange + largestInContiguousRange;
  }

  private Long sum(List<Long> listOfNumbersToSum) {
    return listOfNumbersToSum.stream().reduce(0L, Long::sum);
  }

  Long findWeakness(List<Long> allNumbers, int preambleSize) {
    List<Long> previous = new ArrayList<>();
    List<Long> remainingNumbers = new ArrayList<>(allNumbers);
    for (int i = 0; i < preambleSize; i++) {
      previous.add(allNumbers.get(i));
      remainingNumbers.remove(allNumbers.get(i));
    }

    for (int i = 0; i < remainingNumbers.size(); i++) {
      Long numberToSumTo = remainingNumbers.get(0);

      for (int j = 0; j < preambleSize; j++) {
        for (int k = 0; k < preambleSize; k++) {
          if (j == k) {
            continue;
          }

          long sum = previous.get(j) + previous.get(k);
          if (sum == numberToSumTo) {
            previous.remove(0);
            previous.add(remainingNumbers.get(0));
            remainingNumbers.remove(0);
          }
        }
      }

    }
    return remainingNumbers.get(0);
  }
}
