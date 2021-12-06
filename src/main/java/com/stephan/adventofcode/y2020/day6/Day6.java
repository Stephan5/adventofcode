package com.stephan.adventofcode.y2020.day6;

import com.stephan.adventofcode.DailyChallenge;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 extends DailyChallenge {

  private static final Set<String> fullAlphabet = Set.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");

  public Day6() {
    super(2020, 6);
  }

  @Override
  public long part1Result() {
    String allAnswerString = getInputString();

    Stream<Integer> answerCountStream = groupAnswers(allAnswerString)
        .map(groupMembersAnswers -> {
          String allInGroupTogether = groupMembersAnswers.replace("\n", "");
          String[] individualAnswersForGroup = allInGroupTogether.split("");
          Set<String> uniqueAnswersForGroup = Arrays.stream(individualAnswersForGroup).collect(Collectors.toSet());
          return uniqueAnswersForGroup.size();
        });

    return sum(answerCountStream);
  }

  @Override
  public long part2Result() {
    String allAnswerString = getInputString();

    Stream<Integer> answerCountStream = groupAnswers(allAnswerString)
        .map(groupMembersAnswers -> {
          List<String> groupMembersAnswerList = Arrays.stream(groupMembersAnswers.split("\\n")).toList();
          List<Set<String>> listOfAnswerSets = groupMembersAnswerList
              .stream()
              .map(a -> Arrays.stream(a.split(""))
                  .collect(Collectors.toSet()))
              .toList();

          Set<String> fullSet = new HashSet<>(fullAlphabet);

          listOfAnswerSets.forEach(fullSet::retainAll);
          return fullSet.size();
        });

    return sum(answerCountStream);
  }



  private Stream<String> groupAnswers(String allAnswerString) {
    return Arrays.stream(allAnswerString.split("\\n\\n"));
  }

  private int sum(Stream<Integer> answerCountStream) {
    return answerCountStream.mapToInt(Integer::intValue).sum();
  }
}
