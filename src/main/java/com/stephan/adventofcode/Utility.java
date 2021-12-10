package com.stephan.adventofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Utility {

  public static <T> List<List<T>> transpose(List<List<T>> table) {
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

  public static Function<String, List<String>> splitStringToList(String splitCharacter) {
    return (stringToSplit) -> Arrays.stream(stringToSplit.split(splitCharacter)).toList();
  }

  public static Function<String, List<Character>> splitStringToCharList(String splitCharacter) {
    return (stringToSplit) -> Arrays.stream(stringToSplit.split(splitCharacter)).map(a -> {
      if (a.length() != 1) {
        throw new IllegalStateException();
      }

      return a.charAt(0);
    }).toList();
  }
}
