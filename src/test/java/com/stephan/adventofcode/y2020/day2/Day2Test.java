package com.stephan.adventofcode.y2020.day2;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2020.day1.Day1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Day2Test {

  private Day2 underTest;

  @BeforeEach
  void setUp() {
    underTest = new Day2();
  }

  @Test
  void canReturnPart1Result() {
    // When
    int result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(2);
  }

  @Test
  void canReturnPart2Result() {
    // When
    int result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(1);
  }
}
