package com.stephan.adventofcode.y2021.day1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day1Test {

  private Day1 underTest;


  @BeforeEach
  void setUp() {
    underTest = new Day1();
  }

  @Test
  void canReturnPart1Result() {
    // When
    int result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(7);
  }

  @Test
  void canReturnPart2Result() {
    // When
    int result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(5);
  }
}
