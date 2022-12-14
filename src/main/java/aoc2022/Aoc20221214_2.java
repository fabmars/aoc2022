package aoc2022;

import static aoc2022.Aoc20221214_1.parseGrid;

import aoc2022.Aoc20221214_1.Grid;
import java.awt.Point;
import java.io.IOException;

public class Aoc20221214_2 {

  public static void main(String[] args) throws IOException {
    Grid grid = parseGrid();
    Point ingress = new Point(500, 0);

    int floor = grid.lowest+2;
    // adding a floor the length of twice the side of a square triangle :)
    for(int x = ingress.x - floor; x <= ingress.x + floor; x++) {
      grid.add(new Point(x, floor), 'F');
    }

    int count = 0;
    for(; !grid.finished; count++) {
      grid.pour(ingress);
    }
    System.out.println(count);
  }
}

