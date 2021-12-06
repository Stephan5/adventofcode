package com.stephan.adventofcode.y2021.day2;

import com.stephan.adventofcode.DailyChallenge;
import java.util.List;

public class Day2 extends DailyChallenge {

  public Day2() {
    super(2021, 2);
  }

  @Override
  public long part1Result() {
    return calculatePositionAndMultiply(false);
  }

  @Override
  public long part2Result() {
    return calculatePositionAndMultiply(true);
  }

  private long calculatePositionAndMultiply(boolean complex) {
    List<String> rawInstructions = getInputAsStringList();
    Position position = new Position(complex);

    rawInstructions
        .stream()
        .map(raw -> new Instruction(Direction.valueOf(raw.split(" ")[0].toUpperCase()), Integer.parseInt(raw.split(" ")[1])))
        .forEach(position::applyInstruction);

    return position.multiplyPosition();
  }

  private enum Direction {
    FORWARD, // move forward
    UP, // decrease depth
    DOWN // increase depth
  }

  private record Instruction(Direction direction, int magnitude) {
  }

  private static class Position {
    private int horizontal;
    private int depth;
    private int aim;
    private final boolean complexInstructionApplication;

    Position(boolean complexInstructionApplication) {
      this.complexInstructionApplication = complexInstructionApplication;
      this.horizontal = 0;
      this.depth = 0;
      this.aim = 0;
    }

    public void applyInstruction(Instruction instruction) {
      if (complexInstructionApplication) {
        applyComplexInstruction(instruction);
      } else {
        applySimpleInstruction(instruction);
      }
    }

    public void applySimpleInstruction(Instruction instruction) {
      switch (instruction.direction()) {
        case FORWARD -> horizontal = horizontal + instruction.magnitude();
        case UP -> depth = depth - instruction.magnitude();
        case DOWN -> depth = depth + instruction.magnitude();
        default -> throw new IllegalStateException("Unexpected value: " + instruction.direction());
      }
    }

    public void applyComplexInstruction(Instruction instruction) {
      switch (instruction.direction()) {
        case FORWARD -> {
          horizontal = horizontal + instruction.magnitude();
          depth = depth + (aim * instruction.magnitude());
        }
        case UP -> aim = aim - instruction.magnitude();
        case DOWN -> aim = aim + instruction.magnitude();
        default -> throw new IllegalStateException("Unexpected value: " + instruction.direction());
      }
    }

    public long multiplyPosition() {
      return (long) horizontal * (long) depth;
    }
  }
}
