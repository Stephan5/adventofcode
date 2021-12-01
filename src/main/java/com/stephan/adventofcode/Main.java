package com.stephan.adventofcode;

import com.stephan.adventofcode.y2021.day1.Day1;
import com.stephan.adventofcode.y2021.day2.Day2;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

  private static final List<Class<? extends DailyChallenge>> advent2020 = List.of(
      com.stephan.adventofcode.y2020.day1.Day1.class,
      com.stephan.adventofcode.y2020.day2.Day2.class,
      com.stephan.adventofcode.y2020.day3.Day3.class
  );

  private static final List<Class<? extends DailyChallenge>> advent2021 = List.of(
      Day1.class,
      Day2.class
  );

  public static void main(String[] args) {
    System.out.println("----------------------------------------------");
    System.out.println("                Advent of Code                ");
    System.out.println("----------------------------------------------");

    System.out.println();
    System.out.println("-------------------  2020  -------------------");

    advent2020.forEach(Main::printResultsForDay);

    System.out.println();
    System.out.println("-------------------  2021  -------------------");

    advent2021.forEach(Main::printResultsForDay);
  }

  private static void printResultsForDay(Class<? extends DailyChallenge> day) {
    try {
      DailyChallenge dailyChallenge = day.getDeclaredConstructor().newInstance();
      System.out.println();
      System.out.printf("Day %s%n", dailyChallenge.day());
      System.out.println("------");
      System.out.println("Part 1 answer -> " + dailyChallenge.part1Result());
      System.out.println("Part 2 answer -> " + dailyChallenge.part2Result());
      System.out.println("----------------------------------------------");
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException("Failed when attempting to instantiate an AbstractDay class", e);
    }
  }
}
