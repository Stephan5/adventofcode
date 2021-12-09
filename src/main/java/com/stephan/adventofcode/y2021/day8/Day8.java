package com.stephan.adventofcode.y2021.day8;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day8 extends DailyChallenge {

  public Day8() {
    super(2021, 8);
  }

  static final List<Segment> segmentData = List.of(
      new Segment(0, Set.of(0, 1, 2, 4, 5, 6)),
      new Segment(1, Set.of(2, 5)),
      new Segment(2, Set.of(0, 2, 3, 4, 6)),
      new Segment(3, Set.of(0, 2, 3, 5, 6)),
      new Segment(4, Set.of(1, 2, 3, 5)),
      new Segment(5, Set.of(0, 1, 3, 5, 6)),
      new Segment(6, Set.of(0, 1, 3, 4, 5, 6)),
      new Segment(7, Set.of(0, 2, 5)),
      new Segment(8, Set.of(0, 1, 2, 3, 4, 5, 6)),
      new Segment(9, Set.of(0, 1, 2, 3, 5, 6))
  );

  @Override
  public long part1Result() {
    List<String> entryList = getInputAsStringList();
    Set<Integer> uniqueSegmentSizes = getSegmentsOfUniqueSize().stream().map(Segment::noOfSegmentsUsed).collect(Collectors.toSet());
    List<Signal> signalList = parseSignals(entryList);

    AtomicInteger count = new AtomicInteger();

    for (Signal signal : signalList) {
      List<UnknownSegment> outputs = signal.outputs();

      for (UnknownSegment output : outputs) {
        if (uniqueSegmentSizes.contains(output.size())) {
          count.incrementAndGet();
        }
      }
    }

    return count.get();
  }

  @Override
  public long part2Result() {
    List<String> entryList = getInputAsStringList();
    List<Signal> signalList = parseSignals(entryList);

    int[] outputNumberList = new int[signalList.size()];

    for (int i = 0; i < signalList.size(); i++) {
      Signal signal = signalList.get(i);

      Map<UnknownSegment, Integer> signalToDisplayMap = decipherSegmentsForSignal(signal.testSignal());
      StringBuilder outputStringBuilder = new StringBuilder();

      for (UnknownSegment output : signal.outputs()) {
        Integer displayNumber = signalToDisplayMap.get(output);
        if (displayNumber == null) {
          throw new IllegalStateException("Number for " + output + " not contained in map: " + signalToDisplayMap);
        }
        outputStringBuilder.append(displayNumber);
      }

      int outputNumber = Integer.parseInt(outputStringBuilder.toString());
      outputNumberList[i] = outputNumber;
    }

    return Arrays.stream(outputNumberList).sum();
  }

  private String commonCharactersInStrings(String a, String b) {
    return Arrays.stream(a.split(""))
        .filter(b::contains)
        .collect(Collectors.joining());
  }

  private Map<UnknownSegment, Integer> decipherSegmentsForSignal(List<UnknownSegment> unknownSegments) {
    List<UnknownSegment> fiveSignal = unknownSegments.stream().filter(a -> a.code().length() == 5).toList();
    List<UnknownSegment> sixSignal = unknownSegments.stream().filter(a -> a.code().length() == 6).toList();

    UnknownSegment one = unknownSegments.stream().filter(a -> a.code().length() == 2).findAny().orElseThrow();
    UnknownSegment seven = unknownSegments.stream().filter(a -> a.code().length() == 3).findAny().orElseThrow();
    UnknownSegment four = unknownSegments.stream().filter(a -> a.code().length() == 4).findAny().orElseThrow();
    UnknownSegment eight = unknownSegments.stream().filter(a -> a.code().length() == 7).findAny().orElseThrow();

    UnknownSegment two = fiveSignal.stream().filter(a -> commonCharactersInStrings(a.code(), four.code()).length() == 2).findAny().orElseThrow();
    UnknownSegment three = fiveSignal.stream().filter(a -> commonCharactersInStrings(a.code(), one.code()).length() == 2).findAny().orElseThrow();
    UnknownSegment five = fiveSignal.stream().filter(a -> !a.code().equals(two.code()) && !a.code().equals(three.code())).findAny().orElseThrow();

    UnknownSegment nine = sixSignal.stream().filter(a -> commonCharactersInStrings(a.code(), three.code()).length() == 5).findAny().orElseThrow();
    UnknownSegment zero = sixSignal.stream().filter(a -> commonCharactersInStrings(a.code(), five.code()).length() == 4).findAny().orElseThrow();
    UnknownSegment six = sixSignal.stream().filter(a -> !a.code().equals(nine.code()) && !a.code().equals(zero.code())).findAny().orElseThrow();

    return Map.of(
        zero, 0,
        one, 1,
        two, 2,
        three, 3,
        four, 4,
        five, 5,
        six, 6,
        seven, 7,
        eight, 8,
        nine, 9
    );
  }

  private List<Signal> parseSignals(List<String> entryList) {
    List<Signal> signalList = new ArrayList<>();

    for (String entry : entryList) {
      String[] splitEntryArray = entry.split("\\|");

      List<UnknownSegment> uniqueSignals = Arrays.stream(splitEntryArray[0].trim().split(" "))
          .map(UnknownSegment::new)
          .toList();

      List<UnknownSegment> outputs = Arrays.stream(splitEntryArray[1].trim().split(" "))
          .map(UnknownSegment::new)
          .toList();

      signalList.add(new Signal(uniqueSignals, outputs));
    }
    return signalList;
  }

  private List<Segment> getSegmentsOfUniqueSize() {
    List<Integer> valueList = segmentData.stream().map(Segment::noOfSegmentsUsed).toList();

    Set<Integer> seenOnce = new HashSet<>();
    Set<Integer> duplicates = new HashSet<>();
    for (Integer value : valueList) {
      if (duplicates.contains(value)) {
        continue;
      }

      if (seenOnce.contains(value)) {
        seenOnce.remove(value);
        duplicates.add(value);
      } else {
        seenOnce.add(value);
      }
    }

    return segmentData
        .stream()
        .filter(segment -> seenOnce.contains(segment.noOfSegmentsUsed()))
        .toList();
  }

  record Signal(List<UnknownSegment> testSignal, List<UnknownSegment> outputs) {
  }

  record Segment(int displayNumber, Set<Integer> segmentsUsed) {
    int noOfSegmentsUsed() {
      return segmentsUsed.size();
    }
  }

  record UnknownSegment(String code) {
    UnknownSegment(String code) {
      this.code = code.chars()
          .sorted()
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
    }

    int size() {
      return code.length();
    }
  }
}
