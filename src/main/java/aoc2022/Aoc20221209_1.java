package aoc2022;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Aoc20221209_1 {

  public static void main(String[] args) throws IOException {
    Set<Point> visited = new HashSet<>();
    Point head = new Point(), tail = new Point();

    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221209_1.class.getResourceAsStream("/1209-ropes.txt")))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        String dir = parts[0];
        int times = Integer.parseInt(parts[1]);
        for (int i = 0; i < times; i++) {
          switch (dir) {
            case "R":
              head.translate(1, 0);
              tail = extend(head, tail);
              break;
            case "L":
              head.translate(-1, 0);
              tail = extend(head, tail);
              break;
            case "D":
              head.translate(0, 1);
              tail = extend(head, tail);
              break;
            case "U":
              head.translate(0, -1);
              tail = extend(head, tail);
              break;
          }
          visited.add(tail);
        }
      }
    }
    System.out.println(visited.size());
  }

  static Point extend(Point head, Point tail) {
    if(isStretched(head, tail)) {
      int mx = move(head.x - tail.x), my = move(head.y - tail.y);
      tail = new Point(tail); tail.translate(mx, my);
    }
    return tail;
  }

  private static boolean isStretched(Point head, Point tail) {
    return head.distanceSq(tail) > 2;
  }

  private static int move(int d) {
    if(d != 0) {
      return d > 0 ? (d+1)/2 : (d-1)/2;
    } else {
      return 0;
    }
  }
}
