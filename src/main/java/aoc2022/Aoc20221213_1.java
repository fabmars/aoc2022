package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PrimitiveIterator.OfInt;

public class Aoc20221213_1 {

  public static void main(String[] args) throws IOException {
    int sum = 0;
    List<Pair> pairs = parsePairs();
    for (int i = 0; i < pairs.size(); i++) {
      if(pairs.get(i).compare()) {
        sum = sum + i+1;
      }
    }
    System.out.println(sum);
  }

  static ArrayList<Pair> parsePairs() throws IOException {
    var pairs = new ArrayList<Pair>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221213_1.class.getResourceAsStream("/1213-pairs.txt")))) {
      String line;
      while ((line = br.readLine()) != null) {

        Chunk chunk1 = parseChunk(line);
        Chunk chunk2 = parseChunk(br.readLine());
        pairs.add(new Pair(chunk1, chunk2));

        br.readLine();
      }
    }
    return pairs;
  }

  static Chunk parseChunk(String line) {
    OfInt it = line.chars().iterator();
    it.next(); // [
    return parseChunk(it);
  }

  /**
   * The oldschool way, why not?
   */
  static Chunk parseChunk(OfInt it) {
    var result = new Chunk();
    Integer num = null;

    while(it.hasNext()) {
      char c = (char)it.nextInt();

      if('[' == c) {
        result.subs.add(parseChunk(it));
      } else if(']' == c) {
        if(num != null) {
          result.subs.add(new Chunk(num));
        }
        break;
      } else if(',' == c) {
        if(num != null) {
          result.subs.add(new Chunk(num));
          num = null;
        }
      } else if('0' <= c && c <= '9') {
        int digit = Character.digit(c, 10);
        if(num == null) {
          num = digit;
        } else {
          num = num * 10 + digit;
        }
      } else {
        throw new IllegalArgumentException(Character.toString(c));
      }
    }
    return result;
  }

  record Pair(Chunk left, Chunk right) {

    public boolean compare() {
      return left.compareTo(right) < 0;
    }

    public List<Chunk> chunks() {
      return List.of(left, right);
    }
  }

  static class Chunk implements Comparable<Chunk> {
    Integer num;
    List<Chunk> subs;

    Chunk() {
      subs = new ArrayList<>();
    }

    Chunk(Integer num) {
      this.num = num;
    }

    public Chunk(List<Chunk> subs) {
      this.subs = subs;
    }

    boolean isInt() {
      return num != null;
    }

    @Override
    public int compareTo(Chunk right) {
      Chunk left = this;

      if(left.isInt() && right.isInt()) { // both ints
        return Integer.compare(left.num, right.num);
      } else if(!left.isInt() && !right.isInt()) { // both arrays
        Iterator<Chunk> itl = left.subs.iterator();
        Iterator<Chunk> itr = right.subs.iterator();

        while(true) {
          if(itl.hasNext() && itr.hasNext()) {
            int comp = itl.next().compareTo(itr.next());
            if(comp != 0) {
              return comp;
            }
          } else if(!itl.hasNext() && itr.hasNext()) {
            return -1;
          } else if(itl.hasNext()) {
            return 1;
          } else {
            return 0;
          }
        }
      } else if(left.isInt()) { // heterogenous
        return new Chunk(List.of(left)).compareTo(right);
      } else {
        return left.compareTo(new Chunk(List.of(right)));
      }
    }
  }
}
