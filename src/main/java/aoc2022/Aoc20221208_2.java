package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Aoc20221208_2 {

  public static void main(String[] args) throws IOException {
    int best = 0;
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221208_2.class.getResourceAsStream("/1208-trees.txt")))) {
      List<char[]> lines = new ArrayList<>();
      String line;
      while ((line = br.readLine()) != null) {
        lines.add(line.toCharArray());
      }
      char[][] trees = lines.toArray(value -> new char[lines.size()][value]);
      final int ymax = trees.length, xmax = trees[0].length;

      for (int y = 1; y < ymax-1; y++) { // edges have a zero scenic score
        for (int x = 1; x < xmax-1; x++) {
          int score = getScenicScore(trees, x, y, xmax, ymax);
          if (score > best) {
            best = score;
          }
        }
      }
    }
    System.out.println(best);
  }

  private static int getScenicScore(char[][] trees, int x, int y, int xmax, int ymax) {
    char consideration = trees[y][x];
    int left = 0, right = 0, top = 0, bottom = 0;

    // top
    for(int j = y-1; j >= 0; j--) {
      top++;
      if(trees[j][x] >= consideration) {
        break;
      }
    }

    // bottom
    for(int j = y+1; j < ymax; j++) {
      bottom++;
      if(trees[j][x] >= consideration) {
        break;
      }
    }

    // left
    for(int i = x-1; i >= 0; i--) {
      left++;
      if(trees[y][i] >= consideration) {
        break;
      }
    }

    // right
    for(int i = x+1; i < xmax; i++) {
      right++;
      if(trees[y][i] >= consideration) {
        break;
      }
    }

    //System.out.println(String.format("[%s, %s]: %d, %d, %d, %d", x, y, left, right, top, bottom));
    return left * right * top * bottom;
  }
}
