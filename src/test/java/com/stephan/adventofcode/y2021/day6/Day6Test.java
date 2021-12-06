package com.stephan.adventofcode.y2021.day6;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2021.day5.Day5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day6Test {

  private Day6 underTest;


  @BeforeEach
  void setUp() {
    underTest = new Day6();
  }

  @Test
  void canReturnPart1Result() {
    // When
    long result = underTest.part1Result();

    // Then
    assertThat(result).isEqualTo(5_934);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2Result();

    // Then
    assertThat(result).isEqualTo(26_984_457_539L);
  }
}
