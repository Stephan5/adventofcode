package com.stephan.adventofcode.y2021.day3;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day3 extends DailyChallenge {

  public Day3() {
    super(2021, 3);
  }

  @Override
  public long part1Result() {
    List<String> binaryNumberList = getInputAsStringList();

    List<List<Integer>> listOfLists = binaryNumberList.stream().map(a -> Arrays.stream(a.split("")).map(Integer::parseInt).toList()).toList();

    List<List<Integer>> transposedList = transpose(listOfLists);

    List<Integer> gammaList = new ArrayList<>();
    List<Integer> epsilonList = new ArrayList<>();

    transposedList
        .forEach(row -> {
          BinaryMode binaryMode = binaryMode(row);

          switch (binaryMode) {
            case ZERO -> {
              gammaList.add(0);
              epsilonList.add(1);
            }
            case ONE -> {
              gammaList.add(1);
              epsilonList.add(0);
            }
            case SAME -> throw new IllegalStateException("Same number of ones and zeroes in row!");
            default -> throw new IllegalStateException("Unexpected value: " + binaryMode);
          }
        });

    String gammaBinaryString = mergeIntegerArrayToString(gammaList);
    int gammaDecimal = convertBinaryStringToDecimal(gammaBinaryString);

    String epsilonBinaryString = mergeIntegerArrayToString(epsilonList);
    int epsilonDecimal = convertBinaryStringToDecimal(epsilonBinaryString);

    return (long) gammaDecimal * epsilonDecimal;
  }

  private int convertBinaryStringToDecimal(String gammaBinary) {
    return Integer.parseInt(gammaBinary, 2);
  }

  @Override
  public long part2Result() {
    List<String> binaryNumberList = getInputAsStringList();
    List<List<Integer>> listOfLists = binaryNumberList.stream().map(a -> Arrays.stream(a.split("")).map(Integer::parseInt).toList()).toList();

    Function<BinaryMode, Integer> oxyFunction = binaryMode -> {
      switch (binaryMode) {
        case ZERO -> {
          return 0;
        }
        case ONE, SAME -> {
          return 1;
        }
        default -> throw new IllegalStateException("Unexpected value: " + binaryMode);
      }
    };

    Function<BinaryMode, Integer> co2Function = binaryMode -> {
      switch (binaryMode) {
        case ZERO -> {
          return 1;
        }
        case ONE, SAME -> {
          return 0;
        }
        default -> throw new IllegalStateException("Unexpected value: " + binaryMode);
      }
    };

    int oxyRatingDecimal = getRating(listOfLists, oxyFunction);
    int co2RatingDecimal = getRating(listOfLists, co2Function);

    return (long) oxyRatingDecimal * co2RatingDecimal;
  }

  private int getRating(List<List<Integer>> listOfLists, Function<BinaryMode, Integer> resolveModeToInteger) {

    List<List<Integer>> rowsUnderConsiderationForRating = new ArrayList<>(listOfLists);

    for (int col = 0; col < listOfLists.get(0).size(); col++) {
      List<Integer> currentBitPosition = new ArrayList<>();

      for (List<Integer> integerList : rowsUnderConsiderationForRating) {
        currentBitPosition.add(integerList.get(col));
      }

      Integer bitToFilterFor = resolveModeToInteger.apply(binaryMode(currentBitPosition));

      for (List<Integer> listOfList : listOfLists) {

        if (rowsUnderConsiderationForRating.size() == 1) {
          break;
        }

        if (!bitToFilterFor.equals(listOfList.get(col))) {
          rowsUnderConsiderationForRating.remove(listOfList);
        }
      }
    }

    if (rowsUnderConsiderationForRating.size() != 1) {
      throw new IllegalStateException();
    }
    return convertBinaryStringToDecimal(mergeIntegerArrayToString(rowsUnderConsiderationForRating.get(0)));
  }


  private String mergeIntegerArrayToString(List<Integer> array) {
    return array.stream().map(Object::toString).collect(Collectors.joining());
  }

  private BinaryMode binaryMode(List<Integer> integerList) {
    long ones = integerList.stream().filter(digit -> digit.equals(1)).count();
    long zeroes = integerList.stream().filter(digit -> digit.equals(0)).count();

    if (ones > zeroes) {
      return BinaryMode.ONE;
    } else if (zeroes > ones) {
      return BinaryMode.ZERO;
    } else {
      return BinaryMode.SAME;
    }
  }

  static <T> List<List<T>> transpose(List<List<T>> table) {
    List<List<T>> transposed = new ArrayList<>();
    int rowSize = table.get(0).size();
    for (int i = 0; i < rowSize; i++) {
      List<T> col = new ArrayList<>();
      for (List<T> row : table) {
        col.add(row.get(i));
      }
      transposed.add(col);
    }
    return transposed;
  }

  enum BinaryMode {
    ZERO,
    ONE,
    SAME
  }
}
