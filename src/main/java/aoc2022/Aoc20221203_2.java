package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aoc20221203_2 {

  public static void main(String[] args) throws IOException {
    int sum = 0;
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221203_2.class.getResourceAsStream("/1203-rucksack.txt")))) {

      String line1;
      char[] chars1, chars2, chars3;
      while((line1 = br.readLine()) != null) {
        chars1 = line1.toCharArray();
        chars2 = br.readLine().toCharArray();
        chars3 = br.readLine().toCharArray();

        sum += findAdd(chars1, chars2, chars3);
      }
    }
    System.out.println(sum);
  }

  private static int findAdd(char[] chars1, char[] chars2, char[] chars3) {
    for (char c1 : chars1) {
      for (char c2 : chars2) {
        for (char c3 : chars3) {
          if(c1 == c2 && c2 == c3) {
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
    }
    throw new IllegalArgumentException();
  }
}
