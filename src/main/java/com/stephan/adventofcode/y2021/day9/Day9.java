package com.stephan.adventofcode.y2021.day9;

import static com.stephan.adventofcode.Utility.transpose;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day9 extends DailyChallenge {

  public Day9() {
    super(2021, 9);
  }

  @Override
  public long part1Result() {
    Map map = getHeightMap();
    List<Point> points = mapPoints(map);
    List<Point> lowPoints = points.stream().filter(Point::isLowPoint).toList();

    int[] riskLevels = lowPoints
        .stream()
        .map(lowPoint -> lowPoint.height() + 1)
        .mapToInt(Integer::intValue)
        .toArray();

    return Arrays.stream(riskLevels).sum();
  }


  @Override
  public long part2Result() {
    Map map = getHeightMap();
    List<Point> points = mapPoints(map);
    List<Point> lowPoints = points.stream().filter(Point::isLowPoint).toList();

    List<Point> consideredPoints = new ArrayList<>();

    List<Integer> basinSizes = new ArrayList<>();

    for (Point lowPoint : lowPoints) {
      List<Point> basin = mapBasinForLowPoint(points, consideredPoints, lowPoint);
      basinSizes.add(basin.size());
    }

    basinSizes.sort(Collections.reverseOrder());

    return (long) basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
  }

  private List<Point> mapBasinForLowPoint(List<Point> points, List<Point> consideredPoints, Point lowPoint) {
    List<Point> basin = new ArrayList<>();
    basin.add(lowPoint);
    consideredPoints.add(lowPoint);

    findNeighboursInBasin(points, consideredPoints, basin, lowPoint.neighbours());

    return basin;
  }

  private void findNeighboursInBasin(List<Point> points, List<Point> consideredPoints, List<Point> basin, List<NeighbouringPoint> neighbours) {
    for (NeighbouringPoint neighbour : neighbours) {
      if (neighbour.height() == 9) {
        continue;
      }

      Point neighbourPoint = neighbour.getPoint(points);

      if (consideredPoints.contains(neighbourPoint)) {
        continue;
      }

      basin.add(neighbourPoint);
      consideredPoints.add(neighbourPoint);
      findNeighboursInBasin(points, consideredPoints, basin, neighbourPoint.neighbours());
    }
  }

  private Map getHeightMap() {
    List<String> inputList = getInputAsStringList();

    List<List<Integer>> mapX = inputList.stream()
        .map(a -> Arrays.stream(a.split("")).map(Integer::parseInt).toList())
        .toList();

    List<List<Integer>> mapY = transpose(mapX);

    return new Map(mapX, mapY);
  }

  private List<Point> mapPoints(Map map) {
    List<List<Integer>> mapX = map.xMap();
    List<List<Integer>> mapY = map.yMap();

    List<Point> allPoints = new ArrayList<>();

    for (int i = 0; i < mapX.size(); i++) {
      List<Integer> intRow = mapX.get(i);

      for (int j = 0; j < intRow.size(); j++) {
        List<Integer> intCol = mapY.get(j);

        int centre1 = intCol.get(i);

        NeighbouringPoint up = i != 0 ? new NeighbouringPoint(j, i - 1, intCol.get(i - 1)) : null;
        NeighbouringPoint down = i != intCol.size() - 1 ? new NeighbouringPoint(j, i + 1, intCol.get(i + 1)) : null;

        int centre2 = intRow.get(j);

        NeighbouringPoint left = j != 0 ? new NeighbouringPoint(j - 1, i, intRow.get(j - 1)) : null;
        NeighbouringPoint right = j != intRow.size() - 1 ? new NeighbouringPoint(j + 1, i, intRow.get(j + 1)) : null;

        if (centre1 != centre2) {
          throw new IllegalStateException("Expected center ints to be equal");
        }

        List<NeighbouringPoint> neighbours = Stream.of(up, down, left, right).filter(Objects::nonNull).toList();

        allPoints.add(new Point(j, i, centre1, neighbours));
      }
    }
    return allPoints;
  }

  record Map(List<List<Integer>> xMap, List<List<Integer>> yMap) {
  }

  record Point(int x, int y, int height, List<NeighbouringPoint> neighbours) {

    boolean isLowPoint() {
      List<NeighbouringPoint> shorterNeighbours = neighbours.stream()
          .filter(neighbouringPoint -> neighbouringPoint.height() < height).toList();
      return shorterNeighbours.isEmpty();
    }
  }

  record NeighbouringPoint(int x, int y, int height) {
    Point getPoint(List<Point> points) {
      List<Point> potentialPoints = points.stream().filter(point -> point.x() == x && point.y() == y).toList();

      if (potentialPoints.size() != 1) {
        throw new IllegalStateException("Expected a single point to be found, but found " + potentialPoints.size());
      }
      return new Point(x, y, height, potentialPoints.get(0).neighbours());
    }
  }
}
