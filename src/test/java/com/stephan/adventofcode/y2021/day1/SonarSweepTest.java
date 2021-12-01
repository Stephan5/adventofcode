package com.stephan.adventofcode.y2021.day1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

import java.util.List;
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
    // Given
    List<Integer> integerList = List.of(1, 2, 1, 2, 3, 1);

    // When
    int result = underTest.countIncreases(integerList);

    // Then
    assertThat(result).isEqualTo(3);
  }

  @Test
  void canCountWindowedIncreases() {
    // Given
    List<Integer> integerList = List.of(1, 2, 1, 2, 3, 1);

    // When
    int result = underTest.countWindowedIncreases(integerList);

    // Then
    assertThat(result).isEqualTo(2);
  }
}
