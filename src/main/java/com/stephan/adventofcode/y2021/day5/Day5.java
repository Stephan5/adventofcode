package com.stephan.adventofcode.y2021.day5;

import com.stephan.adventofcode.DailyChallenge;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Day5 implements DailyChallenge {

  @Override
  public int year() {
    return 2021;
  }

  @Override
  public int day() {
    return 5;
  }

  @Override
  public long part1Result() {
    List<String> coordList = getInputAsStringList();

    List<Integer> allPoints = new ArrayList<>();

    List<Segment> segments = parseLineSegments(coordList, allPoints);

    List<Segment> straightSegments = segments.stream().filter(Segment::isStraightLine).toList();

    Integer largestPoint = allPoints.stream().max(Comparator.comparing(Function.identity())).orElseThrow();
    int plotSize = largestPoint + 1;
    int[][] plot = initPlot(plotSize);

    plotSegments(straightSegments, plot);

    return countOverlaps(plotSize, plot);
  }

  @Override
  public long part2Result() {
    List<String> coordList = getInputAsStringList();

    List<Integer> allPoints = new ArrayList<>();

    List<Segment> segments = parseLineSegments(coordList, allPoints);

    Integer largestPoint = allPoints.stream().max(Comparator.comparing(Function.identity())).orElseThrow();
    int plotSize = largestPoint + 1;
    int[][] plot = initPlot(plotSize);

    plotSegments(segments, plot);

    return countOverlaps(plotSize, plot);
  }

  private int countOverlaps(int plotSize, int[][] plot) {
    int overlapCount = 0;

    for (int i = 0; i < plotSize; i++) {
      for (int j = 0; j < plotSize; j++) {
        if (plot[i][j] >= 2) {
          overlapCount++;
        }
      }
    }
    return overlapCount;
  }

  private void plotSegments(List<Segment> straightSegments, int[][] plot) {
    for (Segment segment : straightSegments) {
      List<Point> pointsOnStraightLine = segment.getPointsOnLine();
      for (Point point : pointsOnStraightLine) {
        plot[point.y()][point.x()] += 1;
      }
    }
  }

  private int[][] initPlot(int plotSize) {
    int[][] array = new int[plotSize][];
    for (int i = 0; i < plotSize; i++) {
      int[] ints = new int[plotSize];
      Arrays.fill(ints, 0);
      array[i] = ints;
    }
    return array;
  }

  private List<Segment> parseLineSegments(List<String> coordList, List<Integer> allPoints) {
    return coordList
        .stream()
        .map(rawLineSegment -> {
          List<String> pointList = Arrays.stream(rawLineSegment.split("\\s->\\s")).toList();

          List<Point> points = pointList.stream().map(a -> {
            String[] split = a.split(",");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            allPoints.add(x);
            allPoints.add(y);
            return new Point(x, y);
          }).toList();

          if (points.size() != 2) {
            throw new IllegalStateException("Expected two points, but found " + points.size());
          }

          return new Segment(points.get(0), points.get(1));
        }).toList();
  }

  record Segment(Point pointA, Point pointB) {

    boolean isStraightLine() {
      return xEqual() || yEqual();
    }

    boolean xEqual() {
      return pointA.x() == pointB.x();
    }

    boolean yEqual() {
      return pointA.y() == pointB.y();
    }

    List<Point> getPointsOnLine() {
      List<Point> points = new ArrayList<>();
      int[] coord = new int[]{pointA.x(), pointA.y(), pointB.x(), pointB.y()};

      points.add(new Point(coord[0], coord[1]));

      int xInc = coord[0] > coord[2] ? -1 : +1;
      int yInc = coord[1] > coord[3] ? -1 : +1;

      if (xEqual()) {
        while (coord[1] != coord[3]) {
          coord[1] += yInc;
          points.add(new Point(coord[0], coord[1]));
        }
      } else {
        if (yEqual()) {
          while (coord[0] != coord[2]) {
            coord[0] += xInc;
            points.add(new Point(coord[0], coord[1]));
          }
        } else {
          while (coord[0] != coord[2]) {
            coord[0] += xInc;
            coord[1] += yInc;
            points.add(new Point(coord[0], coord[1]));
          }
        }
      }
      return points;
    }
  }

  record Point(int x, int y) {
  }
}
