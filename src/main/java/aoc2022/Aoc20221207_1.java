package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Aoc20221207_1 {

  public static void main(String[] args) throws IOException {
    Dir root = readScript();
    System.out.println(sumFiltered(root, 100000));
  }

  public static Dir readScript() throws IOException {
    Dir root = null;

    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221207_1.class.getResourceAsStream("/1207-filesystem.txt")))) {
      Dir current = null;
      String line;
      while((line = br.readLine()) != null) {

        if (line.startsWith("$ cd")) {
          String dirname = line.substring(5);
          if ("/".equals(dirname)) {
            if(root == null) {
              root = new Dir(null,"/");
            }
            current = root;
          } else if ("..".equals(dirname)) {
            current = current.parent;
          } else {
            Dir sub = new Dir(current, dirname);
            current.subs.add(sub);
            current = sub;
          }
        } else if (line.startsWith("$ ls")) {
          current.addSize(dirSize(br));
        } else {
          throw new IllegalArgumentException(line);
        }
      }
    }
    return root;
  }

  private static int sumFiltered(Dir dir, int maxSize) {
    int sum = (dir.size <= maxSize) ? dir.size : 0;
    for (Dir sub : dir.subs) {
      sum += sumFiltered(sub, maxSize);
    }
    return sum;
  }

  private static int dirSize(BufferedReader br) throws IOException {
    int size = 0;
    String line;
    while((line = br.readLine()) != null) {
      if(line.startsWith("$")) {
        br.reset();
        break;
      } else {
        br.mark(1024);
        if(line.startsWith("dir")) {
          //nothing
        } else {
          String[] parts = line.split(" ");
          size += Integer.parseInt(parts[0]);
        }
      }
    }
    return size;
  }


  public static class Dir {
    Dir parent;
    String name; // not even used!
    int size;
    List<Dir> subs = new ArrayList<>();

    public Dir(Dir parent, String name) {
      this.parent = parent;
      this.name = name;
    }

    public void addSize(int size) {
      this.size += size;
      if(parent != null) {
        parent.addSize(size);
      }
    }
  }
}
