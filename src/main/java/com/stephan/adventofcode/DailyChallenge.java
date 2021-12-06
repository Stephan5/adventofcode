package com.stephan.adventofcode;

import java.util.List;

public abstract class DailyChallenge {

  private final int year;
  private final int day;

  protected DailyChallenge(int year, int day) {
    this.year = year;
    this.day = day;
  }

  public int getDay() {
    return day;
  }

  abstract public long part1Result();

  abstract public long part2Result();

  public String getInputString() {
    return InputService.getInput(year, day);
  }

  public List<Integer> getInputAsIntegerList() {
    return InputService.getInputAsIntegerList(year, day);
  }

  public List<String> getInputAsStringList() {
    return InputService.getInputAsStringList(year, day);
  }

}
