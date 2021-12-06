package com.stephan.adventofcode.y2021.day6;

import com.stephan.adventofcode.DailyChallenge;
import java.util.Arrays;

public class Day6 implements DailyChallenge {

  @Override
  public int year() {
    return 2021;
  }

  @Override
  public int day() {
    return 6;
  }

  @Override
  public long part1Result() {
    String inputString = getInputString();
    int[] lampfishList = parseInitialLampfishList(inputString);
    return simulateLampfishProcreation(lampfishList, 80);
  }

  @Override
  public long part2Result() {
    String inputString = getInputString();
    int[] lampfishList = parseInitialLampfishList(inputString);
    return simulateLampfishProcreation(lampfishList, 256);
  }

  private long simulateLampfishProcreation(int[] initialLampfish, int days) {
    // So instead of representing each lampfish in an array, we represent the numbers of lampfish at each stage of procreation in the `lifecycle` array.
    long[] lifecycle = new long[9];

    for (int j : initialLampfish) {
      lifecycle[j]++;
    }

    long gestationComplete;

    for (int i = 0; i < days; i++) {
      // Set `gestationComplete` to the number of fish at `0`
      gestationComplete = lifecycle[0];
      // Move each one position forward
      lifecycle[0] = lifecycle[1];
      lifecycle[1] = lifecycle[2];
      lifecycle[2] = lifecycle[3];
      lifecycle[3] = lifecycle[4];
      lifecycle[4] = lifecycle[5];
      lifecycle[5] = lifecycle[6];
      // After giving birth, existing fish are reset to position 6
      lifecycle[6] = gestationComplete + lifecycle[7];
      lifecycle[7] = lifecycle[8];
      // Newly born fish are set to position 8
      lifecycle[8] = gestationComplete;
    }

    return Arrays.stream(lifecycle).sum();
  }

  private int[] parseInitialLampfishList(String inputString) {
    String removedNewLines = inputString.replace("\n", "");

    return Arrays.stream(removedNewLines.split(","))
        .mapToInt(Integer::parseInt)
        .toArray();
  }
}
