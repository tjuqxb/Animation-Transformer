package model.intervallist;

import java.util.List;

/**
 * This interface represents a data structure recording animations of one
 * attribute. It is intended to be generic. Basically, it is a sorted 
 * data structure of non-overlapping intervals(allowing border overlapping 
 * with consistent values).
 */
public interface IntervalList<K, V> {

  /**
   * Insert an interval to Interval list.
   * 
   * @param a interval start key
   * @param x interval start value
   * @param b interval end key
   * @param y interval end value
   * @throws IllegalArgumentException 1. When a or b falls into existing intervals
   *                                  (allow end keys overlapping with
   *                                  conditions). 2. Border keys are
   *                                  overlapped(self or with other interval
   *                                  borders) but with different values. 3. a > b
   */
  void insertInterval(Comparable<K> a, V x, Comparable<K> b, V y) throws IllegalArgumentException;

  /**
   * Return a list of element pairs from IntervalList.
   * 
   * @return a list of element pairs from IntervalList
   */
  List<Element<K, V>> generateSeq();

  /**
   * Get the interval with given index.
   * 
   * @param index the given index
   * @return the interval with given index
   */
  Interval<K, V> get(int index);
}