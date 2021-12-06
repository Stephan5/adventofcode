package com.stephan.adventofcode.y2020.day8;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Day8 extends DailyChallenge {

  public Day8() {
    super(2020, 8);
  }

  @Override
  public long part1Result() {
    List<String> rawInstructionStringList = getInputAsStringList();
    List<Instruction> instructions = parseInstructions(rawInstructionStringList);

    AtomicInteger acc = new AtomicInteger();
    Integer attempt = 0;
    executeProgram(acc, instructions, attempt, null);

    return acc.get();
  }

  @Override
  public long part2Result() {
    List<String> rawInstructionStringList = getInputAsStringList();
    List<Instruction> instructions = parseInstructions(rawInstructionStringList);

    List<Integer> positionsToTry = new ArrayList<>(instructions
        .stream()
        .filter(a -> !a.type().equals(InstructionType.acc))
        .map(Instruction::position)
        .toList());

    AtomicInteger acc = new AtomicInteger();
    Integer attempt = 0;
    executeProgram(acc, instructions, attempt, positionsToTry);

    return acc.get();
  }

  private List<Instruction> parseInstructions(List<String> rawInstructionStringList) {
    List<Instruction> instructions = new ArrayList<>();

    for (int i = 0; i < rawInstructionStringList.size(); i++) {
      String instruction = rawInstructionStringList.get(i);
      String[] strings = instruction.split(" ");
      instructions.add(new Instruction(InstructionType.valueOf(strings[0]), Integer.parseInt(strings[1]), i));
    }
    return instructions;
  }

  private void executeProgram(AtomicInteger acc, List<Instruction> instructions, Integer attempt, List<Integer> positionsToTry) {
    acc.set(0);
    int currentPosition = 0;
    List<Integer> visitedPositions = new ArrayList<>();

    attempt++;

    boolean positionAlteredForAttempt = false;

    while (currentPosition != instructions.size()) {
      if (visitedPositions.contains(currentPosition)) {
        if (positionsToTry == null) {
          return;
        }
        if (positionsToTry.isEmpty()) {
          throw new IllegalStateException("Ran out of changes to attempt!");
        }

        executeProgram(acc, instructions, attempt, positionsToTry);
        break;
      }
      Instruction instruction = instructions.get(currentPosition).copy();

      if (attempt > 1 && !positionAlteredForAttempt && positionsToTry.contains(currentPosition)) {
        if (instruction.type.equals(InstructionType.jmp)) {
          instruction.setType(InstructionType.nop);
        } else if (instruction.type.equals(InstructionType.nop)) {
          instruction.setType(InstructionType.jmp);
        }
        positionAlteredForAttempt = true;
        positionsToTry.remove((Object) currentPosition);
      }

      visitedPositions.add(currentPosition);

      switch (instruction.type()) {
        case acc -> {
          acc.addAndGet(instruction.magnitude());
          currentPosition += 1;
        }
        case jmp -> currentPosition += instruction.magnitude();
        case nop -> currentPosition += 1;
        default -> throw new IllegalStateException("Unexpected value: " + instruction.type());
      }

      if (currentPosition < 0) {
        throw new IllegalStateException("current position must be a non-negative integer");
      }
    }
  }

  static final class Instruction {
    private InstructionType type;
    private final int magnitude;
    private final int position;

    Instruction(InstructionType type, int magnitude, int position) {
      this.type = type;
      this.magnitude = magnitude;
      this.position = position;
    }

    public InstructionType type() {
      return type;
    }

    public int magnitude() {
      return magnitude;
    }

    public int position() {
      return position;
    }

    public void setType(InstructionType type) {
      this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj == null || obj.getClass() != this.getClass()) return false;
      var that = (Instruction) obj;
      return Objects.equals(this.type, that.type) &&
          this.magnitude == that.magnitude &&
          this.position == that.position;
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, magnitude, position);
    }

    @Override
    public String toString() {
      return "Instruction[" +
          "type=" + type + ", " +
          "magnitude=" + magnitude + ", " +
          "position=" + position + ']';
    }

    public Instruction copy() {
      return new Instruction(type, magnitude, position);
    }
  }

  enum InstructionType {
    acc,
    jmp,
    nop
  }
}
