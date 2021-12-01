package com.stephan.adventofcode.y2020.day2;

import com.stephan.adventofcode.DailyChallenge;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day2 implements DailyChallenge {

  @Override
  public int year() {
    return 2020;
  }

  @Override
  public int day() {
    return 2;
  }

  @Override
  public long part1Result() {
    List<String> rawPasswordList = getInputAsStringList();
    AtomicInteger matchCount = new AtomicInteger();

    Stream<Password> passwords = parsePasswordPolicy(rawPasswordList);

    passwords.forEach(password -> {
      long count = password.getPassword().chars().filter(ch -> ch == password.getRequiredLetter()).count();
      if (count >= password.getFirstNumber() && count <= password.getSecondNumber()) {
        matchCount.incrementAndGet();
      }
    });

    return matchCount.get();
  }

  @Override
  public long part2Result() {
    List<String> rawPasswordList = getInputAsStringList();
    AtomicInteger matchCount = new AtomicInteger();

    Stream<Password> passwords = parsePasswordPolicy(rawPasswordList);

    passwords.forEach(password -> {
      if (getCharAtPosition(password.getPassword(), password.getFirstNumber()) == password.getRequiredLetter()
          ^ getCharAtPosition(password.getPassword(), password.getSecondNumber()) == password.getRequiredLetter()) {
        matchCount.incrementAndGet();
      }
    });

    return matchCount.get();
  }

  private char getCharAtPosition(String password, int position) {
    int index = position - 1;
    String substring = password.substring(index, index + 1);
    return substring.charAt(0);
  }

  private Stream<Password> parsePasswordPolicy(List<String> rawPasswordList) {
    return rawPasswordList
        .stream()
        .map(passwordString -> {
          String[] split = passwordString.split("\\s");

          String reqLim = split[0];
          String[] splitReqLims = reqLim.split("-");
          String minLim = splitReqLims[0];
          String maxLim = splitReqLims[1];

          String reqLetter = split[1].substring(0, 1);

          String password = split[2].strip();
          return new Password(password, reqLetter.charAt(0), Integer.parseInt(minLim), Integer.parseInt(maxLim));
        });
  }

  static class Password {
    private final String password;
    private final Character requiredLetter;
    private final int firstNumber;
    private final int secondNumber;

    Password(String password, Character requiredLetter, int firstNumber, int secondNumber) {
      this.password = password;
      this.requiredLetter = requiredLetter;
      this.firstNumber = firstNumber;
      this.secondNumber = secondNumber;
    }

    public String getPassword() {
      return password;
    }

    public Character getRequiredLetter() {
      return requiredLetter;
    }

    public int getFirstNumber() {
      return firstNumber;
    }

    public int getSecondNumber() {
      return secondNumber;
    }
  }
}
