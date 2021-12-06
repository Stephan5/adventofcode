package com.stephan.adventofcode.y2020.day7;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day7 implements DailyChallenge {

  private static final String SHINY_GOLD = "shiny gold";


  @Override
  public int year() {
    return 2020;
  }

  @Override
  public int day() {
    return 7;
  }

  @Override
  public long part1Result() {
    List<String> bagRules = getInputAsStringList();
    List<Bag> bags = parseBags(bagRules);

    Map<String, List<InnerBag>> bagMap = bags.stream()
        .collect(Collectors.toMap(Bag::colour, Bag::permittedContents));

    Set<String> outerBagColourSet = new HashSet<>();
    for (Bag bag : bags) {
      inner(bag.colour(), bagMap, outerBagColourSet, bag.permittedContents());
    }

    return outerBagColourSet.size();
  }

  @Override
  public long part2Result() {
    List<String> bagRules = getInputAsStringList();
    List<Bag> bags = parseBags(bagRules);

    Map<String, List<InnerBag>> bagMap = bags.stream()
        .collect(Collectors.toMap(Bag::colour, Bag::permittedContents));

    List<InnerBag> innerBags = bagMap.get(SHINY_GOLD);

    AtomicInteger atomicInteger = new AtomicInteger();
    inner2(bagMap, innerBags, atomicInteger, 1);

    return atomicInteger.get();
  }

  private void inner2(Map<String, List<InnerBag>> bagMap, List<InnerBag> innerBags, AtomicInteger count, int multiplier) {
    for (InnerBag innerBag : innerBags) {
      int delta = innerBag.numberRequired() * multiplier;
      count.addAndGet(delta);
      inner2(bagMap, bagMap.get(innerBag.colour()), count, delta);
    }
  }

  private List<Bag> parseBags(List<String> bagRules) {
    return bagRules
        .stream()
        .map(bagRule -> {
          String[] contains = bagRule.split(" bags contain ");

          String contents = contains[1];

          String[] split = contents.split(", ");

          List<InnerBag> innerBags = new ArrayList<>();
          Arrays.stream(split)
              .forEach(requirement -> {
                if (requirement.equals("no other bags.")) {
                  return;
                }
                String count = requirement.substring(0, 1);
                int i = Integer.parseInt(count);

                String colour = requirement.replace(" bags", "")
                    .replace(" bag", "")
                    .replace(".", "")
                    .replaceAll("\\d\\s", "");

                innerBags.add(new InnerBag(colour, i));
              });
          return new Bag(contains[0], innerBags);
        })
        .toList();
  }

  private void inner(String outermostBagColour, Map<String, List<InnerBag>> bagMap, Set<String> outerBagColourSet, List<InnerBag> innerBags) {
    for (InnerBag innerBag : innerBags) {
      if (innerBag.colour().equals(SHINY_GOLD)) {
        outerBagColourSet.add(outermostBagColour);
      } else {
        List<InnerBag> nextBagDown = bagMap.get(innerBag.colour());
        inner(outermostBagColour, bagMap, outerBagColourSet, nextBagDown);
      }
    }
  }

  record Bag(String colour, List<InnerBag> permittedContents) {
  }

  record InnerBag(String colour, int numberRequired) {
  }
}
