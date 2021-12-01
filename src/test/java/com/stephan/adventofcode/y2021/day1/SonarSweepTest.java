package com.stephan.adventofcode.y2021.day1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SonarSweepTest {

  private SonarSweep underTest;

  @BeforeEach
  void setUp() {
    underTest = new SonarSweep();
  }

  @Test
  void canCountIncreases() {
    // When
    int result = underTest.countIncreases();

    // Then
    assertThat(result).isEqualTo(7);
  }

  @Test
  void canCountWindowedIncreases() {
    // When
    int result = underTest.countWindowedIncreases();

    // Then
    assertThat(result).isEqualTo(5);
  }
}
