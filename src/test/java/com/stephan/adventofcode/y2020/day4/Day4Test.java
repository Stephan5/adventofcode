package com.stephan.adventofcode.y2020.day4;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2020.day3.Day3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day4Test {

  private Day4 underTest;

  @BeforeEach
  void setUp() {
    underTest = new Day4();
  }

  @Test
  void canReturnPart1Result() {
    // When
    long result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(2);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(2);
  }
}
