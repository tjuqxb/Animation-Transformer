package model.intervallist;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * IntervalList implemented using TreeMap.
 * 
 * @param <K> p1
 * @param <V> p2
 */
public class IntervalListTreeMapImpl<K, V> implements IntervalList<K, V> {
  private TreeMap<K, Interval<K, V>> map;

  /**
   * The constructor of IntervalListTreeMapImpl.
   */
  public IntervalListTreeMapImpl() {
    this.map = new TreeMap<K, Interval<K, V>>();
  }

  @Override
  public void insertInterval(Comparable<K> a, V x, Comparable<K> b, V y)
      throws IllegalArgumentException {
    if (a.compareTo((K) b) > 0) {
      throw new IllegalArgumentException("Interval start key is greater than interval end key.");
    }
    if (a.compareTo((K) b) == 0) {
      if (!x.equals(y)) {
        throw new IllegalArgumentException("Start key and end key overlap with different values.");
      }
    }
    K ak = (K) a;
    K bk = (K) b;
    K prev0 = map.floorKey(ak);
    K prev1 = map.lowerKey(bk);
    if (a.compareTo((K) b) != 0
        && ((prev0 == null && prev1 != null) || (prev0 != null && !prev0.equals(prev1)))) {
      throw new IllegalArgumentException("Inserted interval overlap with existing intervals.");
    }
    boolean overlapBorder = false;
    boolean mergePrev = false;
    if (prev0 != null) {
      Interval<K, V> prevInterval = map.get(prev0);
      K prevEnd = prevInterval.getSecond().getKey();
      if (a.compareTo(prevEnd) < 0) {
        throw new IllegalArgumentException("Inserted interval overlap with existing intervals.");
      }
      if (a.compareTo(prevEnd) == 0) {
        overlapBorder = true;
        if (!prevInterval.getSecond().getVal().equals(x)) {
          throw new IllegalArgumentException(
              "Overlap with previous interval end key, but with a different value.");
        } else if (a.compareTo(prev0) == 0) {
          mergePrev = true;
        }
      }
    }
    boolean mergeNext = false;
    K next = map.ceilingKey(prev0);
    if (next != null && b.compareTo(next) == 0) {
      overlapBorder = true;
      Interval<K, V> nextInterval = map.get(next);
      if (!nextInterval.getFirst().getVal().equals(y)) {
        throw new IllegalArgumentException(
            "Overlap with next interval start key, but with a different value.");
      } else if (b.compareTo(nextInterval.getSecond().getKey()) == 0) {
        mergeNext = true;
      }
    }
    Interval<K, V> interval = new Interval<K, V>(new SimpleElement<K, V>((K) a, x),
        new SimpleElement<K, V>((K) b, y));
    if (mergePrev) {
      map.remove(prev0);
    }
    if (mergeNext) {
      map.remove(next);
    }
    if (a.equals(b) && overlapBorder) {
      return;
    }
    map.put(ak, interval);
  }

  @Override
  public List<Element<K, V>> generateSeq() {
    List<Element<K, V>> arr = new ArrayList<>();
    for (K k : map.keySet()) {
      Interval<K, V> interval = map.get(k);
      Element<K, V> first = interval.getFirst();
      if (arr.size() == 0 || (arr.size() > 0 && !arr.get(arr.size() - 1).equals(first))) {
        arr.add(first);
      }
      Element<K, V> second = interval.getSecond();
      if (arr.size() == 0 || (arr.size() > 0 && !arr.get(arr.size() - 1).equals(second))) {
        arr.add(second);
      }
    }
    List<Element<K, V>> ret = new ArrayList<>();
    for (int i = 0; i < arr.size(); i++) {
      if (i == 0 || i == arr.size() - 1) {
        ret.add(arr.get(i));
      } else {
        Element<K, V> cur = arr.get(i);
        Element<K, V> prev = ret.get(ret.size() - 1);
        Element<K, V> next = arr.get(i + 1);
        if (cur.getVal().equals(prev.getVal()) && cur.getVal().equals(next.getVal())) {
          continue;
        }
        ret.add(cur);
      }
    }
    return ret;
  }

  @Override
  public Interval<K, V> get(int index) {
    int acc = 0;
    for (K k : map.keySet()) {
      if (acc == index) {
        return map.get(k);
      }
      acc++;
    }
    return null;
  }
}