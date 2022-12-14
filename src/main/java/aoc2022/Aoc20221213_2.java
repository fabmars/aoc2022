package aoc2022;

import static aoc2022.Aoc20221213_1.parseChunk;
import static aoc2022.Aoc20221213_1.parsePairs;

import aoc2022.Aoc20221213_1.Chunk;
import aoc2022.Aoc20221213_1.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Aoc20221213_2 {

  public static void main(String[] args) throws IOException {
    List<Pair> pairs = parsePairs();
    Chunk div1 = parseChunk("[[2]]");
    Chunk div2 = parseChunk("[[6]]");

    List<Chunk> all = new ArrayList<>();
    pairs.forEach(pair -> all.addAll(pair.chunks()));
    all.add(div1);
    all.add(div2);
    Collections.sort(all);

    System.out.println(indexOf(div1, all) * indexOf(div2, all));
  }

  static int indexOf(Chunk needle, List<Chunk> haystack) {
    return haystack.indexOf(needle) + 1;
  }
}
