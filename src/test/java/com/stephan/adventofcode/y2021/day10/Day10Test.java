package com.stephan.adventofcode.y2021.day10;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2021.day9.Day9;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day10Test {

  private Day10 underTest;

  @BeforeEach
  void setUp() {
    underTest = new Day10();
  }

  @Test
  void canReturnPart1Result() {
    // When
    long result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(26397);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(288957);
  }
}
