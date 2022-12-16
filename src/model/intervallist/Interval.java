package model.intervallist;

/**
 * This class represents an interval.
 * 
 * @param <K> generic type K for element
 * @param <V> generic type V for element
 */
public class Interval<K, V> {
  private Element<K, V> first;
  private Element<K, V> second;
  
  /**
   * The constructor of interval.
   * 
   * @param first first element
   * @param second second element
   */
  public Interval(Element<K, V> first, Element<K, V> second) {
    super();
    this.first = first;
    this.second = second;
  }

  /**
   * Return the first element.
   * 
   * @return the first
   */
  public Element<K, V> getFirst() {
    return first;
  }

  /**
   * Return the second element.
   * 
   * @return the second
   */
  public Element<K, V> getSecond() {
    return second;
  }
}