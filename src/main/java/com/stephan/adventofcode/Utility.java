package com.stephan.adventofcode;

import java.util.ArrayList;
import java.util.List;

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
}
