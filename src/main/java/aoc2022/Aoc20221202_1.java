package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class Aoc20221202_1 {

  public static Map<String, String> abcMap = Map.of("A", "R", "B", "P", "C", "C");
  public static Map<String, String> xyzMap = Map.of("X", "R", "Y", "P", "Z", "C");
  public static Map<String, Integer> rcpMap = Map.of("R", 1, "P", 2, "C", 3);


  public static void main(String[] args) throws IOException {
    Scores total = new Scores(0, 0);
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221202_1.class.getResourceAsStream("/1202-rpc.txt")))) {
      String line;
      while((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        String d1 = parts[0];
        String d2 = parts[1];

        Scores draw = Scores.of(d1, d2);
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

    static Scores of(String d1, String d2) {
      String rpc1 = abcMap.get(d1);
      String rpc2 = xyzMap.get(d2);

      int s1 = rcpMap.get(rpc1), s2 = rcpMap.get(rpc2);

      if(rpc1.equals(rpc2)) {
        s1 += 3;
        s2 += 3;
      } else if("R".equals(rpc1)) {
        if("P".equals(rpc2)) {
          s2 += 6;
        } else {
          s1 += 6;
        }
      } else if("P".equals(rpc1)) {
        if("R".equals(rpc2)) {
          s1 += 6;
        } else {
          s2 += 6;
        }
      } else if("C".equals(rpc1)) {
        if("R".equals(rpc2)) {
          s2 += 6;
        } else {
          s1 += 6;
        }
      }

      return new Scores(s1, s2);
    }

    public Scores add(Scores ss) {
      return new Scores(s1 + ss.s1, s2 + ss.s2);
    }
  }
}
