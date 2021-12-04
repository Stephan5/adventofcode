package com.stephan.adventofcode;

import com.stephan.adventofcode.y2021.day1.Day1;
import com.stephan.adventofcode.y2021.day2.Day2;
import com.stephan.adventofcode.y2021.day3.Day3;
import com.stephan.adventofcode.y2021.day4.Day4;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

  private static final List<Class<? extends DailyChallenge>> advent2020 = List.of(
      com.stephan.adventofcode.y2020.day1.Day1.class,
      com.stephan.adventofcode.y2020.day2.Day2.class,
      com.stephan.adventofcode.y2020.day3.Day3.class,
      com.stephan.adventofcode.y2020.day4.Day4.class,
      com.stephan.adventofcode.y2020.day5.Day5.class,
      com.stephan.adventofcode.y2020.day6.Day6.class
  );

  private static final List<Class<? extends DailyChallenge>> advent2021 = List.of(
      Day1.class,
      Day2.class,
      Day3.class,
      Day4.class
  );

  public static void main(String[] args) {
    printHeader();
    printResultsForYear(2020, advent2020);
    printResultsForYear(2021, advent2021);
  }

  private static void printHeader() {
    System.out.println("----------------------------------------------");
    System.out.println("                Advent of Code                ");
    System.out.println("----------------------------------------------");
  }

  private static void printResultsForYear(int year, List<Class<? extends DailyChallenge>> classList) {
    System.out.println();
    System.out.println("-------------------  " + year + "  -------------------");
    classList.forEach(Main::printResultsForDay);
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
