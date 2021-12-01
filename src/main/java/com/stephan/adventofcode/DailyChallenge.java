package com.stephan.adventofcode;

import java.util.List;

public interface DailyChallenge {
  int year();

  int day();

  long part1Result();

  long part2Result();

  default String getInputString() {
    return InputService.getInput(year(), day());
  }

  default List<Integer> getInputAsIntegerList() {
    return InputService.getInputAsIntegerList(year(), day());
  }

  default List<String> getInputAsStringList() {
    return InputService.getInputAsStringList(year(), day());
  }

}
