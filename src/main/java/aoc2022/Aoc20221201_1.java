package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Aoc20221201_1 {


  public static void main(String[] args) throws IOException {
    long most = 0;
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221201_1.class.getResourceAsStream("1201-calories.txt")))) {
      String line;
      long current = 0;
      int elf = 0;
      System.out.println("elf: " + elf);
      while((line = br.readLine()) != null) {
        if(line.isEmpty()) {
          if(current > most) {
            most = current;
            System.out.println("new most: " + most);
          }
          current = 0;
          System.out.println("elf: " + ++elf);
        } else {
          current += Long.parseLong(line);
        }
      }

      if(current > most) {
        most = current;
      }

      System.out.println(most);
    }
  }

}
