package com.stephan.adventofcode.y2021.day3;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2021.day2.Day2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day3Test {

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
    assertThat(result).isEqualTo(198);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(230);
  }
}
