package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Aoc20221210_1 {

  public static void main(String[] args) throws IOException {
    var xxx = execIntructions(1);
    int strength = IntStream.iterate(20, cycle -> cycle <= 220, cycle -> cycle + 40).map(cycle -> cycle * xxx.get(cycle - 1)).sum();
    System.out.println(strength);
  }

  static ArrayList<Integer> execIntructions(int x) throws IOException {
    var values = new ArrayList<Integer>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221210_1.class.getResourceAsStream("/1210-signal.txt")))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(" ");
        switch (parts[0]) { // instruction
          case "noop" -> values.add(x);
          case "addx" -> {
            int operand = Integer.parseInt(parts[1]);
            values.add(x); values.add(x);
            x = x + operand;
          }
        }
      }
    }
    return values;
  }
}
