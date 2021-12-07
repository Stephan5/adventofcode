package com.stephan.adventofcode.y2021.day7;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2021.day6.Day6;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day7Test {

  private Day7 underTest;


  @BeforeEach
  void setUp() {
    underTest = new Day7();
  }

  @Test
  void canReturnPart1Result() {
    // When
    long result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(37);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(168);
  }
}
