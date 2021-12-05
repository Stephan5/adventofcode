package com.stephan.adventofcode.y2021.day5;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2021.day4.Day4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day5Test {

  private Day5 underTest;


  @BeforeEach
  void setUp() {
    underTest = new Day5();
  }

  @Test
  void canReturnPart1Result() {
    // When
    long result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(5);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(12);
  }
}
