package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aoc20221204_1 {

  public static void main(String[] args) throws IOException {
    int total = 0;
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221204_1.class.getResourceAsStream("/1204-assign.txt")))) {
      String line;
      while((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        String[] r1 = parts[0].split("-");
        String[] r2 = parts[1].split("-");
        int r1s = Integer.parseInt(r1[0]);
        int r1e = Integer.parseInt(r1[1]);
        int r2s = Integer.parseInt(r2[0]);
        int r2e = Integer.parseInt(r2[1]);

        if((r1s <= r2s && r1e >= r2e) || (r2s <= r1s && r2e >= r1e)) {
          total++;
        }
      }
    }
    System.out.println(total);
  }
}
