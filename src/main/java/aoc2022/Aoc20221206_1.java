package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Aoc20221206_1 {

  public static void main(String[] args) throws IOException {
    int result = findMarker (14);
    System.out.println(result);
  }

  static int findMarker(int markerLength) throws IOException {
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221206_1.class.getResourceAsStream("/1206-device.txt")))) {
      String line = br.readLine();
      for (int i = 0; i < line.length() - markerLength; i++) {
        String substring = line.substring(i, i + markerLength);
        if(isMarker(substring, markerLength)) {
          return markerLength + i;
        }
      }
    }
    return -1;
  }

  static boolean isMarker(String s, int markerLen) {
    Set<Character> set = new HashSet<>();
    for (char c : s.toCharArray()) {
      set.add(c);
    }
    return set.size() == markerLen;
  }
}
