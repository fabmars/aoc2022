package aoc2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Aoc20221211_1 {

  List<Monkey> monkeys;

  long run(int rounds) throws IOException {
    parse();
    rounds(rounds);
    return monkeyBusiness();
  }

  void parse() throws IOException {
    var monkeys = new ArrayList<Monkey>();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221211_1.class.getResourceAsStream("/1211-monkeys.txt")))) {
      while (br.readLine() != null) { // monkey num
        List<Long> items = Arrays.stream(br.readLine().split(": ")[1].split("[ ,]+")).map(Long::parseLong).collect(Collectors.toList());

        String line = br.readLine();
        String[] operation = line.split(": ")[1].split("[ =]+");
        String arith = operation[2];
        String operand = operation[3];

        long div = Long.parseLong(lastPartAfter(br.readLine(), " "));

        int trueMonkey = Integer.parseInt(lastPartAfter(br.readLine(), " "));
        int falseMonkey = Integer.parseInt(lastPartAfter(br.readLine(), " "));

        br.readLine();
        monkeys.add(new Monkey(items, arith, operand, div, trueMonkey, falseMonkey));
      }
    }
    this.monkeys = monkeys;
  }

  void rounds(int rounds) {
    for (int r = 0; r < rounds; r++) {
      round();
    }
  }

  void round() {
    for (Monkey monkey : monkeys) {
      Iterator<Long> it = monkey.items.iterator();
      while(it.hasNext()) {
        Long oldLevel = it.next();
        long newLevel = monkey.inspect(oldLevel);
        long dampenedLevel = dampen(newLevel);
        int destMonkey = monkey.decide(dampenedLevel);
        it.remove();
        monkeys.get(destMonkey).receive(dampenedLevel);
      }
    }
  }

  long dampen(long level) {
    return level / 3;
  }


  long monkeyBusiness() {
    Collections.sort(monkeys);
    return monkeys.get(0).inspections * monkeys.get(1).inspections;
  }

  private static String lastPartAfter(String str, String sep) {
    return str.substring(str.lastIndexOf(sep) + sep.length());
  }

  static final class Monkey implements Comparable<Monkey> {
    List<Long> items;
    long inspections;
    String arith;
    String operand;
    long div;
    int trueMonkey;
    int falseMonkey;

    public Monkey(List<Long> items, String arith, String operand, long div, int trueMonkey, int falseMonkey) {
      this.items = new ArrayList<>(items); // mutable
      this.arith = arith;
      this.operand = operand;
      this.div = div;
      this.trueMonkey = trueMonkey;
      this.falseMonkey = falseMonkey;
    }

    public long inspect(long level) {
      inspections++;

      switch (arith) {
        case "+":
          return level + evalOperand(level);
        case "*":
          return level * evalOperand(level);
        default:
          throw new IllegalArgumentException(arith);
      }
    }

    private long evalOperand(long old) {
      return "old".equals(operand) ? old : Long.parseLong(operand);
    }

    public int decide(long level) {
      if (level % div == 0) {
        return trueMonkey;
      } else {
        return falseMonkey;
      }
    }

    public void receive(long level) {
      items.add(level);
    }

    @Override
    public int compareTo(Monkey m) {
      return Long.compare(m.inspections, inspections); // desc
    }
  }

  public static void main(String[] args) throws IOException {
    System.out.println(new Aoc20221211_1().run(20));
  }
}
