package aoc2022;

import static java.lang.Math.abs;

import java.io.IOException;
import java.util.ArrayList;

public class Aoc20221210_2 {

  public static final int LINE_LENGTH = 40;

  public static void main(String[] args) throws IOException {
    var xxx = Aoc20221210_1.execIntructions(1);
    var pixels = new ArrayList<Character>();
    for (int cycle = 0; cycle < xxx.size(); cycle++) {
      boolean overlaps = overlaps(cycle % LINE_LENGTH, xxx.get(cycle));
      pixels.add(overlaps ? '#' : '.');
    }

    for(int x = 0; x < xxx.size(); x = x + LINE_LENGTH) {
      pixels.subList(x, x + LINE_LENGTH).forEach(System.out::print);
      System.out.println();
    }
  }

  static boolean overlaps(int pos, int x) {
    return abs(pos-x) <= 1;
  }
}
