package com.stephan.adventofcode.y2021.day4;

import static com.stephan.adventofcode.Utility.transpose;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4 extends DailyChallenge {

  public Day4() {
    super(2021, 4);
  }

  @Override
  public long part1Result() {
    String bingoSheet = getInputString();

    List<String> bingoList = new ArrayList<>(Arrays.stream(bingoSheet.split("\\n\\n")).toList());

    String drawnNumberString = bingoList.get(0);
    List<Integer> drawnNumberList = Arrays.stream(drawnNumberString.split(","))
        .map(Integer::parseInt)
        .toList();
    bingoList.remove(0);

    List<BingoBoard> bingoBoards = parseBingoBoards(bingoList);

    return playBingo(bingoBoards, drawnNumberList);
  }

  @Override
  public long part2Result() {
    String bingoSheet = getInputString();

    List<String> bingoList = new ArrayList<>(Arrays.stream(bingoSheet.split("\\n\\n")).toList());

    String drawnNumberString = bingoList.get(0);
    List<Integer> drawnNumberList = Arrays.stream(drawnNumberString.split(","))
        .map(Integer::parseInt)
        .toList();
    bingoList.remove(0);

    List<BingoBoard> bingoBoards = parseBingoBoards(bingoList);


    return playAndFindLastWinner(bingoBoards, drawnNumberList);
  }

  private List<BingoBoard> parseBingoBoards(List<String> bingoList) {
    return bingoList
        .stream()
        .map(board -> Arrays.stream(board.split("\\n"))
            .map(boardRow -> {
              String singleSpaced = boardRow.trim().replace("\s\s", "\s");
              List<String> stream = Arrays.stream(singleSpaced.split("\\s")).toList();
              return stream.stream()
                  .map(Integer::parseInt)
                  .map(BingoNumber::new)
                  .toList();
            }).toList())
        .map(BingoBoard::new)
        .toList();
  }

  private long playBingo(List<BingoBoard> bingoBoards, List<Integer> drawnNumberList) {
    for (Integer drawnNumber : drawnNumberList) {
      markNumberOnBoards(bingoBoards, drawnNumber);

      for (BingoBoard board : bingoBoards) {
        if (checkForWinner(board)) {
          return processWinner(drawnNumber, board);
        }
      }
    }

    throw new IllegalStateException("No winners for this round of bingo, please play again!");
  }

  private long playAndFindLastWinner(List<BingoBoard> bingoBoards, List<Integer> drawnNumberList) {
    for (Integer drawnNumber : drawnNumberList) {
      markNumberOnBoards(bingoBoards, drawnNumber);

      for (BingoBoard board : bingoBoards) {
        if (checkForWinner(board)) {
          if (bingoBoards.stream().allMatch(BingoBoard::isWinner)) {
            return processWinner(drawnNumber, board);
          }
        }
      }
    }

    throw new IllegalStateException("No winners for this round of bingo, please play again!");
  }

  private void markNumberOnBoards(List<BingoBoard> parsedBingoBoards, Integer drawnNumber) {
    for (BingoBoard bingoBoard : parsedBingoBoards) {
      List<List<BingoNumber>> board = bingoBoard.getNumbers();
      for (List<BingoNumber> bingoRow : board) {
        for (BingoNumber bingoNumber : bingoRow) {
          if (bingoNumber.getNumber() == drawnNumber) {
            bingoNumber.mark();
          }
        }
      }
    }
  }

  private long processWinner(Integer drawnNumber, BingoBoard bingoBoard) {
    AtomicInteger unmarkedSum = new AtomicInteger();
    bingoBoard.getNumbers().forEach(row ->
        row.forEach(number -> {
          if (!number.isMarked()) {
            unmarkedSum.addAndGet(number.getNumber());
          }
        }));


    return (long) unmarkedSum.get() * drawnNumber;
  }

  private boolean checkForWinner(BingoBoard bingoBoard) {
    List<List<BingoNumber>> boardNumbers = List.copyOf(bingoBoard.getNumbers());
    // Check rows for winners
    for (List<BingoNumber> bingoRow : boardNumbers) {
      boolean completeRow = bingoRow.stream().allMatch(BingoNumber::isMarked);

      if (completeRow) {
        bingoBoard.setWinner();
        return true;
      }
    }

    List<List<BingoNumber>> transposedBoard = transpose(boardNumbers);

    // Check columns for winners
    for (List<BingoNumber> bingoColumn : transposedBoard) {
      boolean completeColumn = bingoColumn.stream().allMatch(BingoNumber::isMarked);

      if (completeColumn) {
        bingoBoard.setWinner();
        return true;
      }
    }

    return false;
  }

  static class BingoBoard {
    private final List<List<BingoNumber>> numbers;
    private boolean winner;

    BingoBoard(List<List<BingoNumber>> numbers) {
      this.numbers = numbers;
      this.winner = false;
    }

    public List<List<BingoNumber>> getNumbers() {
      return numbers;
    }

    public boolean isWinner() {
      return winner;
    }

    void setWinner() {
      this.winner = true;
    }
  }

  static class BingoNumber {
    private final int number;
    private boolean marked;

    BingoNumber(int number) {
      this.number = number;
      this.marked = false;
    }

    int getNumber() {
      return number;
    }

    boolean isMarked() {
      return marked;
    }

    void mark() {
      this.marked = true;
    }
  }
}
