package model.intervallist;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of IntervalList using ArrayList.
 *
 * @param <K> p1
 * @param <V> p2
 */
public class IntervalListImpl<K, V> implements IntervalList<K, V> {
  private List<Interval<K, V>> list;

  /**
   * The constructor of IntervalListImpl.
   */
  public IntervalListImpl() {
    this.list = new ArrayList<Interval<K, V>>();
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
    // Find previous interval.
    int index1 = findPrevIndex(a);
    int index2 = findPrevIndex(b);
    if (index1 != index2) {
      throw new IllegalArgumentException("Inserted interval overlap with existing intervals.");
    }
    // Check next interval.
    int nextIndex = index1 + 1;
    if (nextIndex < list.size()) {
      K nextStartK = list.get(nextIndex).getFirst().getKey();
      if (b.compareTo(nextStartK) > 0) {
        throw new IllegalArgumentException("Inserted interval overlap with existing intervals.");
      }
    }
    // Check overlap condition.
    if (index1 != -1 && a.compareTo(list.get(index1).getSecond().getKey()) == 0) {
      if (!x.equals(list.get(index1).getSecond().getVal())) {
        throw new IllegalArgumentException(
            "Overlap with previous interval end key, but with a different value.");
      }
    }
    if (nextIndex < list.size() && b.compareTo(list.get(nextIndex).getFirst().getKey()) == 0) {
      if (!y.equals(list.get(nextIndex).getFirst().getVal())) {
        throw new IllegalArgumentException(
            "Overlap with next interval start key, but with a different value.");
      }
    }
    Element<K, V> e0 = new SimpleElement<K, V>((K) a, x);
    Element<K, V> e1 = new SimpleElement<K, V>((K) b, y);
    Interval<K, V> ele = new Interval<>(e0, e1);
    list.add(index1 + 1, ele);
  }

  /**
   * Find previous interval's index (previous interval end key <= a). If no
   * elements in list <= a, will return -1.
   * 
   * @param a the given K a.
   * @return the previous interval's index (if not exists, return -1)
   */
  private int findPrevIndex(Comparable<K> a) {
    if (list.size() == 0) {
      return -1;
    }
    if (a.compareTo(list.get(0).getSecond().getKey()) < 0) {
      return -1;
    }
    int s = 0;
    int e = list.size() - 1;
    while (s < e) {
      int mid = (s + e + 1) >> 1;
      K midEndKey = list.get(mid).getSecond().getKey();
      if (a.compareTo(midEndKey) < 0) {
        e = mid - 1;
      } else {
        s = mid;
      }
    }
    return s;
  }

  @Override
  public List<Element<K, V>> generateSeq() {
    List<Element<K, V>> arr = new ArrayList<>();
    for (int i = 0; i < list.size(); i++) {
      for (int j = 0; j < 2; j++) {
        Element<K, V> element;
        if (j == 0) {
          element = list.get(i).getFirst();
        } else {
          element = list.get(i).getSecond();
        }
        if (arr.size() > 0) {
          Element<K, V> prevElement = arr.get(arr.size() - 1);
          if (!element.equals(prevElement)) {
            arr.add(element);
          }
        } else {
          arr.add(element);
        }
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
    return list.get(index);
  }
}