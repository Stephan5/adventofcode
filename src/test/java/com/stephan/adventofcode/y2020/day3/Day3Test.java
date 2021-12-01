package com.stephan.adventofcode.y2020.day3;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day3Test {

  private Day3 underTest;

  @BeforeEach
  void setUp() {
    underTest = new Day3();
  }

  @Test
  void canReturnPart1Result() {
    // When
    long result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(7);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(336);
  }
}
