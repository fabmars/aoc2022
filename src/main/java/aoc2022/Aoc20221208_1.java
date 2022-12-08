package aoc2022;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Aoc20221208_1 {

  public static void main(String[] args) throws IOException {
    char ground = '0' - 1;
    Set<Point> visible = new HashSet<>();

    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221206_2.class.getResourceAsStream("/1208-trees.txt")))) {
      List<char[]> lines = new ArrayList<>();
      String line;
      while((line = br.readLine()) != null) {
        lines.add(line.toCharArray());
      }
      char[][] trees = lines.toArray(value -> new char[lines.size()][value]);
      final int ymax = trees.length, xmax = trees[0].length;

      // top
      for(int y  = 0; y < ymax; y++) {
        char[] row = trees[y];
        char tallest = ground;
        for(int x  = 0; x < xmax; x++) {
          char height = row[x];
          if(height > tallest) {
            tallest = height;
            visible.add(new Point(x, y));
          }
        }
      }

      // bottom
      for(int y  = 0; y < ymax; y++) {
        char[] row = trees[y];
        char tallest = ground;
        for(int x = xmax-1; x >= 0; x--) {
          char height = row[x];
          if(height > tallest) {
            tallest = height;
            visible.add(new Point(x, y));
          }
        }
      }

      // left
      for(int x = 0; x < xmax; x++) {
        char tallest = ground;
        for(int y  = 0; y < ymax; y++) {
          char height = trees[y][x];
          if(height > tallest) {
            tallest = height;
            visible.add(new Point(x, y));
          }
        }
      }

      // right
      for(int x = 0; x < xmax; x++) {
        char tallest = ground;
        for(int y  = ymax -1; y >= 0; y--) {
          char height = trees[y][x];
          if(height > tallest) {
            tallest = height;
            visible.add(new Point(x, y));
          }
        }
      }
    }

    System.out.println(visible.size());
  }
}
