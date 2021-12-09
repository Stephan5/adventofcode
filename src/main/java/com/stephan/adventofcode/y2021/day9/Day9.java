package com.stephan.adventofcode.y2021.day9;

import static com.stephan.adventofcode.Utility.transpose;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 extends DailyChallenge {

  public Day9() {
    super(2021, 9);
  }

  @Override
  public long part1Result() {
    Graph graph = getHeightGraph();
    List<Point> points = mapPoints(graph);
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
    Graph graph = getHeightGraph();
    List<Point> points = mapPoints(graph);
    List<Point> lowPoints = points.stream().filter(Point::isLowPoint).toList();

    List<BasePoint> consideredPoints = new ArrayList<>();

    Map<BasePoint, List<BasePoint>> neighbourMap = points.stream()
        .filter(a -> a.height() != 9) // we don't care about the neighbours of points of height 9!
        .collect(Collectors.toMap(point -> new BasePoint(point.x(), point.y(), point.height()), Point::neighbours));

    List<Integer> basinSizes = new ArrayList<>();

    for (Point lowPoint : lowPoints) {
      AtomicInteger basinSize = new AtomicInteger();
      mapBasinForLowPointAndGetSize(basinSize, neighbourMap, consideredPoints, lowPoint);
      basinSizes.add(basinSize.get());
    }

    basinSizes.sort(Collections.reverseOrder());
    return (long) basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
  }

  private void mapBasinForLowPointAndGetSize(AtomicInteger basinSize, Map<BasePoint, List<BasePoint>> neighbourMap, List<BasePoint> consideredPoints, Point lowPoint) {
    consideredPoints.add(lowPoint);
    findNeighboursInBasin(neighbourMap, consideredPoints, lowPoint.neighbours(), basinSize);
  }

  private void findNeighboursInBasin(Map<BasePoint, List<BasePoint>> neighbourMap, List<BasePoint> consideredPoints, List<BasePoint> neighbours, AtomicInteger basinSize) {
    for (BasePoint neighbour : neighbours) {
      if (neighbour.height() == 9 || consideredPoints.contains(neighbour)) {
        continue;
      }

      consideredPoints.add(neighbour);
      basinSize.incrementAndGet();
      findNeighboursInBasin(neighbourMap, consideredPoints, neighbour.findNeighbours(neighbourMap), basinSize);
    }
  }

  private Graph getHeightGraph() {
    List<String> inputList = getInputAsStringList();

    List<List<Integer>> mapX = inputList.stream()
        .map(a -> Arrays.stream(a.split("")).map(Integer::parseInt).toList())
        .toList();

    List<List<Integer>> mapY = transpose(mapX);

    return new Graph(mapX, mapY);
  }

  private List<Point> mapPoints(Graph graph) {
    List<List<Integer>> mapX = graph.xMap();
    List<List<Integer>> mapY = graph.yMap();

    List<Point> allPoints = new ArrayList<>();

    for (int i = 0; i < mapX.size(); i++) {
      List<Integer> intRow = mapX.get(i);

      for (int j = 0; j < intRow.size(); j++) {
        List<Integer> intCol = mapY.get(j);

        int centre1 = intCol.get(i);

        BasePoint up = i != 0 ? new BasePoint(j, i - 1, intCol.get(i - 1)) : null;
        BasePoint down = i != intCol.size() - 1 ? new BasePoint(j, i + 1, intCol.get(i + 1)) : null;

        int centre2 = intRow.get(j);

        BasePoint left = j != 0 ? new BasePoint(j - 1, i, intRow.get(j - 1)) : null;
        BasePoint right = j != intRow.size() - 1 ? new BasePoint(j + 1, i, intRow.get(j + 1)) : null;

        if (centre1 != centre2) {
          throw new IllegalStateException("Expected center ints to be equal");
        }

        List<BasePoint> neighbours = Stream.of(up, down, left, right).filter(Objects::nonNull).toList();

        allPoints.add(new Point(j, i, centre1, neighbours));
      }
    }
    return allPoints;
  }

  record Graph(List<List<Integer>> xMap, List<List<Integer>> yMap) {
  }

  static class Point extends BasePoint {
    private final int height;
    private final List<BasePoint> neighbours;

    Point(int x, int y, int height, List<BasePoint> neighbours) {
      super(x, y, height);
      this.height = height;
      this.neighbours = neighbours;
    }

    boolean isLowPoint() {
      List<BasePoint> shorterNeighbours = neighbours.stream()
          .filter(basePoint -> basePoint.height() < height).toList();
      return shorterNeighbours.isEmpty();
    }

    public List<BasePoint> neighbours() {
      return neighbours;
    }

  }

  static class BasePoint {
    private final int x;
    private final int y;
    private final int height;

    BasePoint(int x, int y, int height) {
      this.x = x;
      this.y = y;
      this.height = height;
    }

    public int x() {
      return x;
    }

    public int y() {
      return y;
    }

    public int height() {
      return height;
    }

    List<BasePoint> findNeighbours(Map<BasePoint, List<BasePoint>> neighbourMap) {
      List<BasePoint> neighbours = neighbourMap.get(this);

      if (neighbours == null) {
        throw new IllegalStateException("Expected to find neighbours for this point");
      }

      return neighbours;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      BasePoint basePoint = (BasePoint) o;
      return x == basePoint.x && y == basePoint.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }
}
