package aoc2022;

import static java.lang.Integer.parseInt;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Aoc20221214_1 {

  public static void main(String[] args) throws IOException {
    Grid grid = parseGrid();
    Point ingress = new Point(500, 0);

    int count = 0;
    for(; !grid.finished; count++) {
      grid.pour(ingress);
    }
    System.out.println(count-1); // the last one has fallen in the void already
  }

  static Grid parseGrid() throws IOException {
    var grid = new Grid();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221214_1.class.getResourceAsStream("/1214-sand.txt")))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" -> ");
        Point start = parsePoint(parts[0]);
        for (int i = 1; i < parts.length; i++) {
          Point end = parsePoint(parts[i]);
          if (start.x < end.x) {
            for (int x = start.x; x <= end.x; x++) {
              grid.add(new Point(x, start.y), '#');
            }
          }
          else if (start.x > end.x) {
            for (int x = start.x; x >= end.x; x--) {
              grid.add(new Point(x, start.y), '#');
            }
          } else if(start.y < end.y) {
            for (int y = start.y; y <= end.y; y++) {
              grid.add(new Point(start.x, y), '#');
            }
          } else {
            for (int y = start.y; y >= end.y; y--) {
              grid.add(new Point(start.x, y), '#');
            }
          }
          start = end;
        }
      }
    }
    return grid;
  }

  static Point parsePoint(String s) {
    int i = s.indexOf(',');
    return new Point(parseInt(s.substring(0, i)), parseInt(s.substring(i+1)));
  }


  static class Grid {
    HashMap<Point, Character> grid = new HashMap<>();
    int lowest = 0;
    boolean finished = false;

    void add(Point p, char c) {
      grid.put(p, c);
      if(p.y > lowest) {
        lowest = p.y;
      }
    }

    public void pour(Point sand) {
      while(true) {
        if(sand.y == lowest) { // reached ground
          finished = true;
          break;
        }

        Point down = new Point(sand.x, sand.y+1);
        if(grid.get(down) == null) {
          sand = down;
          continue;
        }

        Point downLeft = new Point(sand.x-1, sand.y+1);
        if(grid.get(downLeft) == null) {
          sand = downLeft;
          continue;
        }

        Point downRight = new Point(sand.x+1, sand.y+1);
        if(grid.get(downRight) == null) {
          sand = downRight;
          continue;
        }

        if(sand.y == 0) { // part 2-specific
          finished = true;
        } else {
          grid.put(sand, 'o'); // not #add
        }
        break;
      }
    }
  }

}

