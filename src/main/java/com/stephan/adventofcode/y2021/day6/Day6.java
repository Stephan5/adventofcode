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

    int days = 80;

    return simulateLampfishProcreation(lampfishList, days);
  }

  @Override
  public long part2Result() {
    String inputString = getInputString();
    int[] lampfishList = parseInitialLampfishList(inputString);

    int days = 256;

    return simulateLampfishProcreation(lampfishList, days);
  }

  private long simulateLampfishProcreation(int[] initialLampfish, int days) {
    // So instead of representing each lampfish in an array, we represent the numbers of lampfish at each stage of procreation in the `procreationStage` array.
    long[] procreationStage = new long[9];

    for (int j : initialLampfish) {
      procreationStage[j]++;
    }

    long births;

    for (int i = 0; i < days; i++) {
      // Set `births` to the number of fish at `0`
      births = procreationStage[0];
      // Move each one position forward
      procreationStage[0] = procreationStage[1];
      procreationStage[1] = procreationStage[2];
      procreationStage[2] = procreationStage[3];
      procreationStage[3] = procreationStage[4];
      procreationStage[4] = procreationStage[5];
      procreationStage[5] = procreationStage[6];
      // After giving birth, existing fish are reset to position 6
      procreationStage[6] = births + procreationStage[7];
      procreationStage[7] = procreationStage[8];
      // New births are set to position 8
      procreationStage[8] = births;
    }

    return Arrays.stream(procreationStage).sum();
  }

  private int[] parseInitialLampfishList(String inputString) {
    String removedNewLines = inputString.replace("\n", "");

    return Arrays.stream(removedNewLines.split(","))
        .mapToInt(Integer::parseInt)
        .toArray();
  }
}
