package com.stephan.adventofcode.y2020.day5;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day5 extends DailyChallenge {

  public Day5() {
    super(2020, 5);
  }

  @Override
  public long part1Result() {
    List<String> seatCodes = getInputAsStringList();
    Stream<PlaneSeat> seats = parseSeats(seatCodes);

    return seats.map(PlaneSeat::seatId).max(Comparator.comparing(Function.identity())).orElseThrow();
  }

  @Override
  public long part2Result() {
    List<String> seatCodes = getInputAsStringList();
    Stream<Integer> seatIds = parseSeats(seatCodes).map(PlaneSeat::seatId);

    return findMySeat(seatIds);
  }

  private int findMySeat(Stream<Integer> seatIds) {
    int maxSeatId = (127 * 8) + 7;
    List<Integer> allSeats = new ArrayList<>(IntStream.rangeClosed(0, maxSeatId).boxed().toList());
    seatIds.forEach(allSeats::remove);

    List<Integer> seatCandidates = new ArrayList<>();
    for (int i = 1; i < allSeats.size() - 1; i++) {
      int currentSeat = allSeats.get(i);
      int previousSeat = allSeats.get(i - 1);
      int nextSeat = allSeats.get(i + 1);

      if (currentSeat - 1 != previousSeat && currentSeat + 1 != nextSeat) {
        seatCandidates.add(currentSeat);
      }
    }

    if (seatCandidates.size() != 1) {
      throw new IllegalStateException("Couldn't find seat. Found " + seatCandidates.size() + " candidates!");
    }

    return seatCandidates.get(0);
  }

  private Stream<PlaneSeat> parseSeats(List<String> seatCodes) {
    return seatCodes.stream().map(code -> {
      int row = binarySearch(code.substring(0, 7), 127);
      int col = binarySearch(code.substring(7, 10), 7);
      int seatId = (row * 8) + col;
      return new PlaneSeat(code, row, col, seatId);
    });
  }

  private int binarySearch(String code, int max) {
    int min = 0;
    for (String letter : code.split("")) {
      int diff = max - min;
      if (letter.equals("F") || letter.equals("L")) {
        max -= (Math.ceil(diff) / 2);
      } else if (letter.equals("B") || letter.equals("R")) {
        min += (Math.ceil(diff) / 2);
      } else {
        throw new IllegalStateException("Unexpected letter " + letter);
      }
    }
    return max;
  }

  record PlaneSeat(String code, int row, int col, int seatId) {
  }
}
