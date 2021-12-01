package com.stephan.adventofcode.y2021.day1;

import com.stephan.adventofcode.DailyChallenge;

public class Day1 implements DailyChallenge {
  private final SonarSweep sweep;

  public Day1() {
    sweep = new SonarSweep();
  }

  @Override
  public int year() {
    return 2021;
  }

  @Override
  public int day() {
    return 1;
  }

  @Override
  public int part1Result() {
    return sweep.countIncreases(getInputAsIntegerList());
  }

  @Override
  public int part2Result() {
    return sweep.countWindowedIncreases(getInputAsIntegerList());
  }
}
