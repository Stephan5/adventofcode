package com.stephan.adventofcode.y2021.day10;

import static com.stephan.adventofcode.Utility.splitStringToCharList;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day10 extends DailyChallenge {

  public Day10() {
    super(2021, 10);
  }

  @Override
  public long part1Result() {
    List<String> navSubsystemLines = getInputAsStringList();

    List<List<Character>> individualChars = navSubsystemLines
        .stream()
        .map(splitStringToCharList(""))
        .toList();

    List<ProcessedLine> processedLines = processLines(individualChars);

    return processedLines
        .stream()
        .filter(ProcessedLine::isCorrupted)
        .map(corruptedLine -> ChunkBoundary.getScoreForIllegalCharacter(corruptedLine.getFirstIllegalCharacter()))
        .mapToInt(Integer::intValue)
        .sum();
  }

  @Override
  public long part2Result() {
    List<String> navSubsystemLines = getInputAsStringList();

    List<List<Character>> individualChars = navSubsystemLines
        .stream()
        .map(splitStringToCharList(""))
        .toList();

    List<ProcessedLine> processedLines = processLines(individualChars);

    List<ProcessedLine> incompleteLines = processedLines.stream().filter(ProcessedLine::isIncomplete).toList();

    List<Long> scores = new ArrayList<>();

    for (ProcessedLine incompleteLine : incompleteLines) {
      List<Character> restOfLine = autoCompleteLine(incompleteLine.openChunks());
      AtomicLong totalScore = new AtomicLong();

      restOfLine.forEach(character -> totalScore.set((totalScore.get() * 5) + ChunkBoundary.getScoreForAutoCompletedCharacter(character)));

      scores.add(totalScore.get());
    }

    Collections.sort(scores);
    return getMiddleScore(scores);
  }

  private long getMiddleScore(List<Long> scores) {
    int size = scores.size();

    if (size % 2 == 0) {
      throw new IllegalStateException("Expected odd number of elements");
    }

    int middleIndex = (int) Math.floor((float) size / 2);
    return scores.get(middleIndex);
  }

  private List<Character> autoCompleteLine(List<Character> openedChunksAtPointOfIllegal) {
    List<Character> restOfLine = new ArrayList<>();

    for (int i = openedChunksAtPointOfIllegal.size() - 1; i >= 0; i--) {
      Character lastOpenedChunk = openedChunksAtPointOfIllegal.get(i);
      char terminator = ChunkBoundary.getExpectedTerminatorForOpener(lastOpenedChunk);
      restOfLine.add(terminator);
    }

    return restOfLine;
  }

  private List<ProcessedLine> processLines(List<List<Character>> individualChars) {
    List<ProcessedLine> processedLines = new ArrayList<>();

    for (int i = 0; i < individualChars.size(); i++) {
      processedLines.add(processLine(individualChars.get(i), i));
    }
    return processedLines;
  }

  private ProcessedLine processLine(List<Character> line, int currentLineNumber) {
    List<Character> openedChunks = new ArrayList<>();
    List<Character> illegalCharacters = new ArrayList<>();

    for (char character : line) {
      if (ChunkBoundary.isChunkOpeningCharacter(character)) {
        openedChunks.add(character);
      } else if (ChunkBoundary.isChunkTerminatingCharacter(character)) {
        int index = openedChunks.size() - 1;
        Character lastOpenedChunk = openedChunks.get(index);
        if (ChunkBoundary.matching(lastOpenedChunk, character)) {
          openedChunks.remove(index);
        } else {
          illegalCharacters.add(character);
        }
      }
    }
    return new ProcessedLine(currentLineNumber, illegalCharacters, openedChunks);
  }

  enum ChunkBoundary {
    ROUND('(', ')', 3, 1),
    SQUARE('[', ']', 57, 2),
    CURLY('{', '}', 1197, 3),
    ANGLE('<', '>', 25137, 4),
    ;

    private final char openingCharacter;
    private final char terminatingCharacter;
    private final int compilerScore;
    private final int completionScore;

    ChunkBoundary(char openingCharacter, char terminatingCharacter, int compilerScore, int completionScore) {
      this.openingCharacter = openingCharacter;
      this.terminatingCharacter = terminatingCharacter;
      this.compilerScore = compilerScore;
      this.completionScore = completionScore;
    }

    public static int getScoreForIllegalCharacter(char character) {
      if (!isChunkTerminatingCharacter(character)) {
        throw new IllegalStateException();
      }

      return getChunkForTerminator(character).getCompilerScore();
    }

    public static int getScoreForAutoCompletedCharacter(char character) {
      if (!isChunkTerminatingCharacter(character)) {
        throw new IllegalStateException();
      }

      return getChunkForTerminator(character).getCompletionScore();
    }

    public int getCompilerScore() {
      return compilerScore;
    }

    public int getCompletionScore() {
      return completionScore;
    }

    public char getOpeningCharacter() {
      return openingCharacter;
    }

    public char getTerminatingCharacter() {
      return terminatingCharacter;
    }

    public static ChunkBoundary getChunkForOpener(char openingCharacter) {
      List<ChunkBoundary> chunks = Arrays.stream(values()).filter(a -> a.getOpeningCharacter() == openingCharacter).toList();
      if (chunks.size() != 1) {
        throw new IllegalStateException();
      }

      return chunks.get(0);
    }

    public static ChunkBoundary getChunkForTerminator(char terminatingCharacter) {
      List<ChunkBoundary> chunks = Arrays.stream(values()).filter(a -> a.getTerminatingCharacter() == terminatingCharacter).toList();
      if (chunks.size() != 1) {
        throw new IllegalStateException();
      }

      return chunks.get(0);
    }

    public static char getExpectedTerminatorForOpener(char openingCharacter) {
      ChunkBoundary chunkForOpener = getChunkForOpener(openingCharacter);
      return chunkForOpener.getTerminatingCharacter();
    }

    public static boolean matching(char openingCharacter, char terminatingCharacter) {
      return getExpectedTerminatorForOpener(openingCharacter) == terminatingCharacter;
    }

    private static boolean isChunkOpeningCharacter(char character) {
      Set<Character> characterSet = Arrays.stream(values()).map(ChunkBoundary::getOpeningCharacter).collect(Collectors.toSet());
      return characterSet.contains(character);
    }

    private static boolean isChunkTerminatingCharacter(char character) {
      Set<Character> characterSet = Arrays.stream(values()).map(ChunkBoundary::getTerminatingCharacter).collect(Collectors.toSet());
      return characterSet.contains(character);
    }
  }

  record ProcessedLine(int lineNumber, List<Character> illegalCharacters, List<Character> openChunks) {
    boolean isCorrupted() {
      return !illegalCharacters.isEmpty();
    }

    boolean isIncomplete() {
      return illegalCharacters.isEmpty() && !openChunks.isEmpty();
    }

    boolean isComplete() {
      return openChunks.isEmpty() && illegalCharacters.isEmpty();
    }

    char getFirstIllegalCharacter() {
      if (!isCorrupted()) {
        throw new IllegalStateException();
      }

      return illegalCharacters.get(0);
    }
  }
}
