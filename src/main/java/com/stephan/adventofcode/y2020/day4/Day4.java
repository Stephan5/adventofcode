package com.stephan.adventofcode.y2020.day4;

import com.stephan.adventofcode.DailyChallenge;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 implements DailyChallenge {

  private static final String BIRTH_YEAR = "byr";
  private static final String ISSUE_YEAR = "iyr";
  private static final String EXPIRATION_YEAR = "eyr";
  private static final String HEIGHT = "hgt";
  private static final String HAIR_COLOUR = "hcl";
  private static final String EYE_COLOUR = "ecl";
  private static final String PASSPORT_ID = "pid";
  private static final String COUNTRY_ID = "cid";

  @Override
  public int year() {
    return 2020;
  }

  @Override
  public int day() {
    return 4;
  }

  @Override
  public long part1Result() {
    String passportBatch = getInputString();
    Stream<Passport> passports = parsePassports(passportBatch);

    return passports
        .filter(Passport::requiredFieldsPopulated)
        .count();
  }

  @Override
  public long part2Result() {
    String passportBatch = getInputString();
    Stream<Passport> passports = parsePassports(passportBatch);

    List<Passport> passportList = passports.toList();

    return passportList.stream()
        .filter(Passport::requiredFieldsPopulated)
        .filter(Passport::isValid)
        .count();
  }

  private Stream<Passport> parsePassports(String passportBatch) {
    return Arrays.stream(passportBatch.split("\\n\\n"))
        .map(rawPassportInfo -> {

          Map<String, String> map = Arrays.stream(rawPassportInfo.split("\\s|\\n"))
              .map(s -> s.split(":"))
              .collect(Collectors.toMap(key -> key[0], value -> value[1]));

          return new Passport(
              map.get(BIRTH_YEAR),
              map.get(ISSUE_YEAR),
              map.get(EXPIRATION_YEAR),
              map.get(HEIGHT),
              map.get(HAIR_COLOUR),
              map.get(EYE_COLOUR),
              map.get(PASSPORT_ID),
              map.get(COUNTRY_ID)
          );
        });
  }

  record Passport(String birthYear,
                  String issueYear,
                  String expirationYear,
                  String height,
                  String hairColour,
                  String eyeColour,
                  String passportId,
                  String countryId) {

    public boolean requiredFieldsPopulated() {
      return birthYear != null
          && issueYear != null
          && expirationYear != null
          && height != null
          && hairColour != null
          && eyeColour != null
          && passportId != null;
    }

    public boolean isValid() {
      return isBirthYearValid()
          && isIssueYearValid()
          && isExpirationYearValid()
          && isHeightValid()
          && isHairColourValid()
          && isEyeColourValid()
          && isPassportIdValid();
    }

    public boolean isBirthYearValid() {
      try {
        int year = Integer.parseInt(birthYear);
        return year >= 1920 && year <= 2002;
      } catch (NumberFormatException e) {
        return false;
      }
    }

    public boolean isIssueYearValid() {
      try {
        int year = Integer.parseInt(issueYear);
        return year >= 2010 && year <= 2020;
      } catch (NumberFormatException e) {
        return false;
      }
    }

    public boolean isExpirationYearValid() {
      try {
        int year = Integer.parseInt(expirationYear);
        return year >= 2020 && year <= 2030;
      } catch (NumberFormatException e) {
        return false;
      }
    }

    public boolean isHeightValid() {
      try {
        String centimeters = "cm";
        String inches = "in";
        if (height.endsWith(centimeters)) {
          int heightCm = Integer.parseInt(height.replace(centimeters, ""));
          return heightCm >= 150 && heightCm <= 193;
        } else if (height.endsWith(inches)) {
          int heightIn = Integer.parseInt(height.replace(inches, ""));
          return heightIn >= 59 && heightIn <= 76;
        } else {
          return false;
        }
      } catch (NumberFormatException e) {
        return false;
      }
    }

    public boolean isHairColourValid() {
      return hairColour.matches("#[0-9a-f]{6}");
    }

    public boolean isEyeColourValid() {
      return Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(eyeColour);
    }

    public boolean isPassportIdValid() {
      return passportId.matches("\\d{9}");
    }
  }
}
