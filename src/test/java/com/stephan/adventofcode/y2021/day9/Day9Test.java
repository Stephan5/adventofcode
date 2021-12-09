package com.stephan.adventofcode.y2021.day9;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2021.day8.Day8;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day9Test {

  private Day9 underTest;

  @BeforeEach
  void setUp() {
    underTest = new Day9();
  }

  @Test
  void canReturnPart1Result() {
    // When
    long result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(15);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(1134);
  }
}
