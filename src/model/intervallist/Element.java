package model.intervallist;

/**
 * This interface represents one of two border points of an interval.
 */
public interface Element<K, V> {
  /**
   * Return the key of Element.
   * Key is used to sort.
   *  
   * @return the key of Element
   */
  K getKey();
  
  /**
   * Return the value of the Element.
   * 
   * @return the value of the Element
   */
  V getVal();
}