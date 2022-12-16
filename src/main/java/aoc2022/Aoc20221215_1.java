
package aoc2022;

import static aoc2022.Aoc20221215_1.Cave.manhatttan;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Aoc20221215_1 {

  static final Pattern XY_PATTERN = Pattern.compile("(x|y)=(-?\\d+)");


  public static void main(String[] args) throws IOException {
    Cave cave = parseCave();
    System.out.println(cave.countSpotsWithoutABeaconOnRow(2000000)); // and I knew part 2 wouldn't be so simple....
    System.out.println(cave.countRowExclusions(2000000, Integer.MIN_VALUE, Integer.MAX_VALUE) - 1); // -1 cause there's a beacon on that row
  }


  static Cave parseCave() throws IOException {
    Cave cave = new Cave();
    try(BufferedReader br = new BufferedReader(new InputStreamReader(Aoc20221215_1.class.getResourceAsStream("/1215-sensors.txt")))) {
      String line;
      while ((line = br.readLine()) != null) {
        Matcher matcher = XY_PATTERN.matcher(line);
        matcher.find();
        int sx = parseInt(matcher.group(2));
        matcher.find();
        int sy = parseInt(matcher.group(2));
        matcher.find();
        int bx = parseInt(matcher.group(2));
        matcher.find();
        int by = parseInt(matcher.group(2));

        cave.add(new Pairing(new Point(sx, sy), new Point(bx, by)));
      }
    }
    return cave;
  }


  static class Cave {
    List<Pairing> pairings = new ArrayList<>();

    void add(Pairing pairing) {
      pairings.add(pairing);
    }

    static int manhatttan(Point p1, Point p2) {
      return abs(p1.x - p2.x) + abs(p1.y - p2.y);
    }

    long countSpotsWithoutABeaconOnRow(int row) {
      return pairings.stream().filter(pairing -> pairing.isRowInRange(row)).flatMap(pairing -> pairing.excludedOnRowPoints(row).stream().filter(point -> !point.equals(pairing.beacon))).collect(Collectors.toSet()).size();
    }

    long countRowExclusions(int row, int minCol, int maxCol) {
      return pairings.stream().filter(pairing -> pairing.isRowInRange(row)).map(pairing -> pairing.excludedOnRow(row, minCol, maxCol))
        .collect(RangeList::new, RangeList::add, RangeList::addAll) // once reduced we have either one or two ranges left. If two, that means there's a hole where the hidden beacon is...unless it's on an edge :)
        .stream().mapToLong(Range::length).sum();
    }

    long countColumnExclusions(int col, int minRow, int maxRow) {
      return pairings.stream().filter(pairing -> pairing.isColumnInRange(col)).map(pairing -> pairing.excludedOnColumn(col, minRow, maxRow))
        .collect(RangeList::new, RangeList::add, RangeList::addAll) // likewise
        .stream().mapToLong(Range::length).sum();
    }
  }


  static class Pairing {
    Point sensor, beacon;
    int exclLeft, exclRight, exclTop, exclBottom; // such that left < right and top < bottom

    public Pairing(Point sensor, Point beacon) {
      this.sensor = sensor;
      this.beacon = beacon;

      // computing the exclusion diamond
      int dist = manhatttan(sensor, beacon);
      this.exclLeft = sensor.x - dist;
      this.exclRight = sensor.x + dist;
      this.exclTop = sensor.y - dist;
      this.exclBottom = sensor.y + dist;
    }

    List<Point> excludedOnRowPoints(int row) {
      int gnaw = abs(sensor.y - row);
      return IntStream.rangeClosed(exclLeft + gnaw, exclRight - gnaw).mapToObj(x -> new Point(x, row)).toList();
    }

    Range excludedOnRow(int row, int minCol, int maxCol) {
      int gnaw = abs(sensor.y - row);
      return new Range(max(minCol, exclLeft + gnaw), min(maxCol, exclRight - gnaw));
    }

    Range excludedOnColumn(int col, int minRow, int maxRow) {
      int gnaw = abs(sensor.x - col);
      return new Range(max(minRow, exclTop + gnaw), min(maxRow, exclBottom - gnaw));
    }

    boolean isRowInRange(int row) {
      return exclTop <= row && row <= exclBottom;
    }

    boolean isColumnInRange(int col) {
      return exclLeft <= col && col <= exclRight;
    }
  }

  record Range(int i1, int i2) { // i1 <= i2
    int length() {
      return i2-i1+1; //discrete
    }

    private Range looselyMerge(Range range) {
      return new Range(min(i1, range.i1), max(i2, range.i2));
    }
  }


  static class RangeList implements Iterable<Range> {
    List<Range> ranges = new ArrayList<>();

    /**
     * Trying to reduce on the fly, FFS!
     */
    public void add(Range other) {
      List<Range> merges = new ArrayList<>(); // a list cause we may be able to join with several segments at once, like R2 bridging the gap between R1 and R3
      Iterator<Range> it = ranges.iterator();
      while(it.hasNext()) {
        Range ours = it.next();
        Range merge = ours.looselyMerge(other);
        if(merge.length() <= ours.length() + other.length()) { // means joinable. ALL THAT FOR THAT, DOOOOOD!
          it.remove();
          merges.add(merge);
        }
      }

      if(merges.isEmpty()) {
        ranges.add(other);
      } else {
        Iterator<Range> itm = merges.iterator();
        Range result = itm.next();
        while(itm.hasNext()) {
          result = result.looselyMerge(itm.next());
        }
        ranges.add(result);
      }
    }

    public void addAll(RangeList list) {
      for (Range range : list) {
        add(range);
      }
    }

    @Override
    public Iterator<Range> iterator() {
      return ranges.iterator();
    }

    public Stream<Range> stream() {
      return StreamSupport.stream(spliterator(), false);
    }
  }
}

