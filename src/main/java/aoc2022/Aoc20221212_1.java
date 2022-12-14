package aoc2022;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Aoc20221212_1 {

  public static void main(String[] args) throws IOException {
    System.out.println(walk('S'));
  }

  static int walk(char target) throws IOException {
    Grid grid = new Grid();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221212_1.class.getResourceAsStream("/1212-steps.txt")))) {
      String line;
      while((line = br.readLine()) != null) {
        grid.add(line.toCharArray());
      }
    }
    return grid.walk(target);
  }


  static class Grid {
    final List<char[]> grid = new ArrayList<>();


    public void add(char[] chars) {
      grid.add(chars);
    }

    public char cell(Point p) {
      return grid.get(p.y)[p.x];
    }

    static int elevation(char c) {
      if(c == 'S') {
        c = 'a';
      } else if(c == 'E') {
        c = 'z';
      }
      return c; // not an int, but still an int, whatever...
    }

    private List<Point> options(Point pos) {
      char from = cell(pos);

      var options = new ArrayList<Point>();
      if(pos.x > 0) {
        Point option = new Point(pos.x - 1, pos.y);
        if(descendable(from, cell(option))) {
          options.add(option);
        }
      }
      if(pos.x < grid.get(pos.y).length-1) {
        Point option = new Point(pos.x + 1, pos.y);
        if(descendable(from, cell(option))) {
          options.add(option);
        }
      }
      if(pos.y > 0) {
        Point option = new Point(pos.x, pos.y - 1);
        if(descendable(from, cell(option))) {
          options.add(option);
        }
      }
      if(pos.y < grid.size()-1) {
        Point option = new Point(pos.x, pos.y + 1);
        if(descendable(from, cell(option))) {
          options.add(option);
        }
      }
      return options;
    }

    private boolean descendable(char from, char to) {
      return elevation(from) - elevation(to) <= 1;
    }

    int walk(char target) {
      var toVisit = new ArrayList<Point>();
      var distances = new HashMap<Point, Integer>();
      for (int y = 0; y < grid.size(); y++) {
        char[] chars = grid.get(y);
        for (int x = 0; x < chars.length; x++) {
          if(chars[x] == 'E') {
            Point end = new Point(x, y);
            toVisit.add(end);
            distances.put(end, 0);
          } else {
            distances.put(new Point(x, y), Integer.MAX_VALUE);
          }
        }
      }

      while(!toVisit.isEmpty()) {
        Point point = toVisit.remove(0);
        int newDist = distances.get(point) + 1;
        for (Point option : options(point)) {
          if (cell(option) == target) {
            return newDist;
          } else {
            if(newDist < distances.get(option)) {
              distances.put(option, newDist);
              toVisit.add(option);
            }
          }
        }
      }
      throw new IllegalArgumentException();
    }
  }
}
