package com.stephan.adventofcode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class InputService {

  static String getInput(int year, int day) {
    String resource = String.format("/y%d/day%d/input.txt", year, day);
    try (InputStream stream = InputService.class.getResourceAsStream(resource)) {
      assert stream != null;
      byte[] bytes = stream.readAllBytes();
      return new String(bytes);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to find input file");
    }
  }

  static List<Integer> getInputAsIntegerList(int year, int day) {
    String string = getInput(year, day);
    return Arrays.stream(string.split("\\n")).map(Integer::parseInt).toList();
  }

  static List<Integer> getSingleLineCsvIntegerInputAsIntegerList(int year, int day) {
    String string = getInput(year, day);
    return Arrays.stream(string.replaceAll("\\n", "").split(",")).map(Integer::parseInt).toList();
  }

  static List<String> getInputAsStringList(int year, int day) {
    String string = getInput(year, day);
    return Arrays.stream(string.split("\\n")).toList();
  }
}
