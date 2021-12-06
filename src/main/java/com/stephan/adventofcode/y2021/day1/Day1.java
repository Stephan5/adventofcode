package com.stephan.adventofcode.y2021.day1;

import com.stephan.adventofcode.DailyChallenge;

public class Day1 extends DailyChallenge {
  private final SonarSweep sweep;

  public Day1() {
    super(2021, 1);
    sweep = new SonarSweep();
  }

  @Override
  public long part1Result() {
    return sweep.countIncreases(getInputAsIntegerList());
  }

  @Override
  public long part2Result() {
    return sweep.countWindowedIncreases(getInputAsIntegerList());
  }
}
