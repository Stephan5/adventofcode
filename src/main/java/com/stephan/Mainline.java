package com.stephan;

import com.stephan.days.day1.Day1;
import java.util.Scanner;

public class Mainline {

  public static void main(String[] args) throws NoSuchMethodException {
    System.out.println("Advent of Code 2021");
    System.out.println("Which day would you like to calculate the results for?");

    Scanner inputReader = new Scanner(System.in);

    switch (inputReader.nextLine()) {
      case "1" -> Day1.main();
      default -> throw new NoSuchMethodException("That is not a valid day");
    }
  }
}
