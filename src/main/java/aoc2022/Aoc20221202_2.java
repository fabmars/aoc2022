package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Aoc20221202_2 {

  public static Map<String, Integer> abcMap = Map.of("A", 1, "B", 2, "C", 3);


  public static void main(String[] args) throws IOException {
    Scores total = new Scores(0, 0);
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221202_2.class.getResourceAsStream("/1202-rpc.txt")))) {
      String line;
      while((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        String d1 = parts[0];
        String r2 = parts[1];

        Scores draw = Scores.of(d1, r2);
        total = total.add(draw);
      }
    }

    System.out.println(total.s2);
  }


  static class Scores {
    int s1;
    int s2;

    public Scores(int s1, int s2) {
      this.s1 = s1;
      this.s2 = s2;
    }

    static Scores of(String d1, String res) {
      int s1 = abcMap.get(d1);
      int s2 = 0;

      if("X".equals(res)) { // I lose
        if("A".equals(d1)) { // rock > cissors
          s2 = 3;
        } else if("B".equals(d1)) { // paper > rock
          s2 = 1;
        } else {
          s2 = 2;
        }
      } else if("Y".equals(res)) { // draw
        if("A".equals(d1)) { // rock
          s2 = 3+1;
        } else if("B".equals(d1)) { // paper
          s2 = 3+2;
        } else {
          s2 = 3+3;
        }
      } else if("Z".equals(res)) { // I win
        if("A".equals(d1)) { // rock < paper
          s2 = 6+2;
        } else if("B".equals(d1)) { // paper < cissors
          s2 = 6+3;
        } else {
          s2 = 6+1;
        }
      }

      return new Scores(s1, s2);
    }

    public Scores add(Scores ss) {
      return new Scores(s1 + ss.s1, s2 + ss.s2);
    }
  }
}
