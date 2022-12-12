package aoc2022;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Aoc20221209_2 {

  public static void main(String[] args) throws IOException {

    Set<Point> visited1 = iLikeToMoveItMoveIt(2);
    System.out.println("part1: " + visited1.size());

    Set<Point> visited2 = iLikeToMoveItMoveIt(10);
    System.out.println("part2: " + visited2.size());
  }

  public static Set<Point> iLikeToMoveItMoveIt(int knotCount) throws IOException {
    Set<Point> visited = new HashSet<>();
    List<Point> knots = IntStream.range(0, knotCount).mapToObj(operand -> new Point()).toList();

    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221209_2.class.getResourceAsStream("/1209-ropes.txt")))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        String dir = parts[0];
        int times = Integer.parseInt(parts[1]);
        Point tail = null;
        for (int i = 0; i < times; i++) {
          switch (dir) {
            case "R":
              tail = stretchRope(knots, 1, 0);
              visited.add(tail);
              break;
            case "L":
              tail = stretchRope(knots, -1, 0);
              visited.add(tail);
              break;
            case "D":
              tail = stretchRope(knots, 0, 1);
              visited.add(tail);
              break;
            case "U":
              tail = stretchRope(knots, 0, -1);
              visited.add(tail);
              break;
          }
          visited.add(tail);
        }
      }
    }
    return visited;
  }

  static Point stretchRope(List<Point> knots, int dx, int dy) {
    knots.get(0).translate(dx, dy);

    Point head = null, tail = null;
    for (Point knot : knots) {
      tail = knot;
      if(head != null) {
        extend(head, tail);
      }
      head = tail;
    }
    return new Point(tail);
  }

  static void extend(Point head, Point tail) {
    if(isStretched(head, tail)) {
      int mx = move(head.x - tail.x), my = move(head.y - tail.y);
      tail.translate(mx, my);
    }
  }

  static boolean isStretched(Point head, Point tail) {
    return head.distanceSq(tail) > 2;
  }

  static int move(int d) {
    if(d != 0) {
      return d > 0 ? (d+1)/2 : (d-1)/2;
    } else {
      return 0;
    }
  }
}
