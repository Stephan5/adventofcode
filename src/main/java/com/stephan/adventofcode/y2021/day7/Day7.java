package com.stephan.adventofcode.y2021.day7;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Day7 extends DailyChallenge {

  public Day7() {
    super(2021, 7);
  }

  @Override
  public long part1Result() {
    return getTotalFuelRequired(false);
  }

  @Override
  public long part2Result() {
    return getTotalFuelRequired(true);
  }

  private Integer getTotalFuelRequired(boolean useCrabEngineeringPrinciples) {
    List<Integer> crabPositions = getSingleLineCsvIntegerInputAsIntegerList();

    int minCrab = crabPositions.stream().min(Comparator.comparing(Function.identity())).orElseThrow();
    int maxCrab = crabPositions.stream().max(Comparator.comparing(Function.identity())).orElseThrow();

    List<Integer> meetingPoints = new ArrayList<>();

    for (int i = minCrab; i <= maxCrab; i++) {
      meetingPoints.add(i);
    }

    List<Integer> fuelSums = new ArrayList<>();
    for (Integer meetingPoint : meetingPoints) {
      List<Integer> fuelRequiredForMeeting = new ArrayList<>();

      for (Integer crabPosition : crabPositions) {
        int distance = Math.abs(crabPosition - meetingPoint);

        int fuelRequired;

        if (useCrabEngineeringPrinciples) {
          fuelRequired = getCrabFuelCalculation(distance);
        } else {
          fuelRequired = distance;
        }
        fuelRequiredForMeeting.add(fuelRequired);
      }

      int fuelSum = fuelRequiredForMeeting.stream().mapToInt(Integer::intValue).sum();
      fuelSums.add(fuelSum);
    }

    return fuelSums.stream().min(Comparator.comparing(Function.identity())).orElseThrow();
  }

  private int getCrabFuelCalculation(int distance) {
    return (distance * (distance + 1)) / 2;
  }
}
