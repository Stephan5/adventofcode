#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end

import com.stephan.adventofcode.DailyChallenge;

public class Day${DAY_NUMBER} implements DailyChallenge {

  @Override
  public int year() {
    return ${YEAR_NUMBER};
  }

  @Override
  public int day() {
    return ${DAY_NUMBER};
  }

  @Override
  public long part1Result() {
    return 0;
  }

  @Override
  public long part2Result() {
    return 0;
  }
}
