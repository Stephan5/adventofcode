package com.stephan.adventofcode.y2020.day3;

import com.stephan.adventofcode.DailyChallenge;
import java.util.List;

public class Day3 extends DailyChallenge {

  public Day3() {
    super(2020, 3);
  }

  @Override
  public long part1Result() {
    return getHits(getInputAsStringList(), 3, 1);
  }

  @Override
  public long part2Result() {
    List<Long> hitList = List.of(
        getHits(getInputAsStringList(), 1, 1),
        getHits(getInputAsStringList(), 3, 1),
        getHits(getInputAsStringList(), 5, 1),
        getHits(getInputAsStringList(), 7, 1),
        getHits(getInputAsStringList(), 1, 2)
    );

    return hitList.stream().reduce(1L, (x, y) -> x * y);
  }

  private long getHits(List<String> lines, int traverseRight, int traverseDown) {
    int hits = 0;
    String[][] map = new String[lines.size()][lines.get(0).split("").length];

    for (int i = 0; i < lines.size(); i++) {
      map[i] = lines.get(i).split("");

      if (i % traverseDown != 0) {
        continue;
      }

      int z = (((i / traverseDown) * traverseRight) % map[i].length);

      String square = map[i][z];

      if (square.equals("#")) {
        hits++;
      }
    }
    return hits;
  }
}
