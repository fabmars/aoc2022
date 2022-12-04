package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Aoc20221201_2 {


  public static void main(String[] args) throws IOException {
    Map<Integer, Long> elves = new HashMap<>();

    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221201_2.class.getResourceAsStream("1201-calories.txt")))) {
      String line;
      long calories = 0;
      int elf = 0;
      while((line = br.readLine()) != null) {
        if(line.isEmpty()) {
          elves.put(elf++, calories);
          calories = 0;
        } else {
          calories += Long.parseLong(line);
        }
      }
    }

    ArrayList<Long> list = new ArrayList<>(elves.values());
    Collections.sort(list);
    Collections.reverse(list);
    System.out.println(list.get(0) + list.get(1) + list.get(2));
  }

}
