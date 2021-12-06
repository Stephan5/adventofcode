package com.stephan.adventofcode.y2020.day9;

import static org.assertj.core.api.Assertions.assertThat;

import com.stephan.adventofcode.y2020.day8.Day8;
import java.util.Arrays;
import java.util.List;
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
    long result = underTest.part1(5);

    // Then
    assertThat(result).isEqualTo(127);
  }

  @Test
  void canReturnPart2Result() {
    // When
    long result = underTest.part2(5);

    // Then
    assertThat(result).isEqualTo(62);
  }
}
