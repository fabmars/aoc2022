
package aoc2022;

import static aoc2022.Aoc20221215_1.parseCave;

import aoc2022.Aoc20221215_1.Cave;
import java.awt.Point;
import java.io.IOException;

public class Aoc20221215_2 {

  public static void main(String[] args) throws IOException {

    Cave cave = parseCave();
    Point hidden = new Point();

    long start = System.currentTimeMillis();

    int maxRow = 4000000, maxCol = maxRow;
    int width = maxCol+1, height = maxRow+1;

    for (int row = 0; row <= maxRow; row++) {
      long count = cave.countRowExclusions(row, 0, maxCol);
      if(count != width) {
        hidden.y = row;
        break;
      }
    }
    for (int col = 0; col <= maxCol; col++) {
      long count = cave.countColumnExclusions(col, 0, maxRow);
      if(count != height) {
        hidden.x = col;
        break;
      }
    }

    long time = System.currentTimeMillis() - start;

    System.out.println("Hidden in : " + hidden);
    System.out.println("Frequency: " + (hidden.x * 4000000L + hidden.y));
    System.out.printf("Solved in: %d ms", time);
  }
}

