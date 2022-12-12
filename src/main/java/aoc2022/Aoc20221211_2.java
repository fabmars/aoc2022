package aoc2022;

import java.io.IOException;

public class Aoc20221211_2 extends Aoc20221211_1 {

  long modAll;

  @Override
  public void parse() throws IOException {
    super.parse();
    this.modAll = monkeys.stream().map(monkey -> monkey.div).reduce(1L, (l1, l2) -> l1 * l2);
  }

  @Override
  protected long dampen(long level) {
    return level % modAll;
  }

  public static void main(String[] args) throws IOException {
    System.out.println(new Aoc20221211_2().run(10000));
  }
}
