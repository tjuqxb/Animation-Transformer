package model;

/**
 * This interface represents animation time.
 */
public interface AnimateTime extends Comparable<AnimateTime> {
  /**
   * Return the time value.
   * 
   * @return the time value
   */
  double getVal();
  
  /**
   * Clone an animate time object.
   * 
   * @return the cloned object
   */
  AnimateTime clone();
}