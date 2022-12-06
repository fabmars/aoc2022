package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Aoc20221205_2 {

  public static void main(String[] args) throws IOException {
    int laneCount = 9;
    int crateHeight = 8;
    List[] lanes = new List[laneCount];

    for (int l = 0; l < laneCount; l++) {
      lanes[l] = new ArrayList<Character>();
    }

    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221205_2.class.getResourceAsStream("/1205-cranes.txt")))) {
      String line;
      for (int h = 0; h < crateHeight; h++) {
        line = br.readLine();
        for (int l = 0; l < laneCount && l*4 < line.length(); l++) {
          String crate = line.substring(l*4, l*4+3).substring(1, 2);
          if(!" ".equals(crate)) {
            lanes[l].add(crate);
          }
        }
      }

      br.readLine();
      br.readLine();

      while((line = br.readLine()) != null) {
        String[] parts = line.substring("move ".length()).split("[a-z ]+");
        int num = Integer.parseInt(parts[0]);
        int from = Integer.parseInt(parts[1]);
        int to = Integer.parseInt(parts[2]);

        List<String> crates = new ArrayList<>();
        for (int n = 0; n < num; n++) {
          crates.add((String)lanes[from - 1].remove(0));
        }
        lanes[to-1].addAll(0, crates);
      }
    }

    for (List lane : lanes) {
      System.out.print(lane.get(0));
    }
  }
}
