package com.stephan.adventofcode;

import com.stephan.adventofcode.days.Day;
import com.stephan.adventofcode.days.day1.Day1;
import com.stephan.adventofcode.days.day2.Day2;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

  private static final List<Class<? extends Day>> days = List.of(
      Day1.class,
      Day2.class
  );

  public static void main(String[] args) {
    System.out.println("----------------------------------------------");
    System.out.println("             Advent of Code 2021              ");
    System.out.println("----------------------------------------------");

    days.forEach(Main::printResultsForDay);
  }

  private static void printResultsForDay(Class<? extends Day> day) {
    try {
      Day abstractDay = day.getDeclaredConstructor().newInstance();
      System.out.println();
      System.out.printf("Day %s%n", abstractDay.day());
      System.out.println("------");
      System.out.println("Part 1 answer -> " + abstractDay.part1Result());
      System.out.println("Part 2 answer -> " + abstractDay.part2Result());
      System.out.println("----------------------------------------------");
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException("Failed when attempting to instantiate an AbstractDay class", e);
    }
  }
}
