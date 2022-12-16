package model.utils;

import model.AnimateTime;
import model.Attribute;
import model.intervallist.Element;
import model.intervallist.Interval;
import model.intervallist.SimpleElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This is a utility class. But it is not as generic as IntervalList. Methods
 * can be more generic, but with more wording.
 */
public class Utils {

  /**
   * Merge multiple lists according to the order of AnimateTime.
   * 
   * @param map the lists to be merged, with string as name attributes
   * @return a sorted list of Element intervals with name attributes
   */
  public static List<Element<String, Interval<AnimateTime, Attribute>>> mergeMulti(
      Map<String, List<Interval<AnimateTime, Attribute>>> map) {
    PriorityQueue<Element<String, Interval<AnimateTime, Attribute>>> pq = new PriorityQueue<>(
        (a, b) -> {
          return a.getVal().getFirst().getKey().compareTo(b.getVal().getFirst().getKey());
        });
    List<Element<String, Interval<AnimateTime, Attribute>>> ret = new ArrayList<>();
    Map<String, Integer> indexMap = new HashMap<>();
    for (String name : map.keySet()) {
      List<Interval<AnimateTime, Attribute>> list = map.get(name);
      if (list.size() == 0) {
        continue;
      }
      pq.offer(new SimpleElement<String, Interval<AnimateTime, Attribute>>(name, list.get(0)));
      indexMap.put(name, 0);
    }
    while (pq.size() > 0) {
      Element<String, Interval<AnimateTime, Attribute>> ele = pq.poll();
      ret.add(ele);
      String name = ele.getKey();
      Integer next = indexMap.get(name) + 1;
      indexMap.put(name, next);
      List<Interval<AnimateTime, Attribute>> list = map.get(name);
      if (next >= list.size()) {
        continue;
      }
      pq.offer(new SimpleElement<String, Interval<AnimateTime, Attribute>>(name, list.get(next)));
    }
    return ret;
  }
}