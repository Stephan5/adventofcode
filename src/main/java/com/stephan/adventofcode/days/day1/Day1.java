package com.stephan.adventofcode.days.day1;

import com.stephan.adventofcode.days.AbstractDay;

public class Day1 extends AbstractDay {
  private final SonarSweep sweep;

  public Day1() {
    sweep = new SonarSweep();
  }

  @Override
  public int day() {
    return 1;
  }

  @Override
  public int part1Result() {
    return sweep.countIncreases();
  }

  @Override
  public int part2Result() {
    return sweep.countWindowedIncreases();
  }
}
