package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aoc20221203_1 {

  public static void main(String[] args) throws IOException {
    int sum = 0;
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221203_1.class.getResourceAsStream("/1203-rucksack.txt")))) {
      String line;
      while((line = br.readLine()) != null) {
        int half = line.length() / 2;
        char[] half1 = line.substring(0, half).toCharArray();
        char[] half2 = line.substring(half).toCharArray();

        sum += findAdd(half1, half2);
      }
    }
    System.out.println(sum);
  }

  private static int findAdd(char[] half1, char[] half2) {
    for (char c1 : half1) {
      for (char c2 : half2) {
        if(c1 == c2) {
          int add;
          if(c1 >= 'a') {
            add = (c1 - 'a' + 1);
          } else {
            add = (c1 - 'A' + 27);
          }
          return add;
        }
      }
    }
    throw new IllegalArgumentException();
  }
}
