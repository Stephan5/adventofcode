package com.stephan.adventofcode.y2021.day8;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2021.day7.Day7;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day8Test {

  private Day8 underTest;

  @BeforeEach
  void setUp() {
    underTest = new Day8();
  }

  @Test
  void canReturnPart1Result() {
    // When
    long result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(26);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(61229);
  }
}
