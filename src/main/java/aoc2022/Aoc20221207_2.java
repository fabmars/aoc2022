package aoc2022;

import aoc2022.Aoc20221207_1.Dir;
import java.io.IOException;

public class Aoc20221207_2 {

  public static void main(String[] args) throws IOException {

    Dir root = Aoc20221207_1.readScript();
    int freeSpace = 70000000 - root.size;
    int needed = 30000000 - freeSpace;

    int bestFit = findSmallestAbove(root, needed, root.size);
    System.out.println(bestFit);
  }

  private static int findSmallestAbove(Dir dir, int minSize, int bestFit) {
    if(dir.size >= minSize && dir.size < bestFit) {
      bestFit = dir.size;
    }

    for (Dir sub : dir.subs) {
      bestFit = findSmallestAbove(sub, minSize, bestFit);
    }
    return bestFit;
  }
}
