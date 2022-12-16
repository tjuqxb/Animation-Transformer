package model;

import java.util.List;

import model.intervallist.Interval;

/**
 * This interface indicates an animate shape.
 */
public interface AnimateShape {
  
  /**
   * Add change attribute action to this shape.
   * 
   * @param a start time
   * @param x start attribute
   * @param b end time 
   * @param y end attribute
   * @throws IllegalArgumentException 1. a or b is not in valid range
   *                                  2. IntervalList insertion error
   *                                  3. attribute is of wrong sub-type
   */
  void addAttributeChange(int a, Attribute x, int b, Attribute y);
  
  /**
   * Check if two attribute type belongs to this shape.
   * And they should be of the same type.
   * 
   * @param x the given attribute
   * @param y the given attribute
   * @return if two attribute belongs to this shape and they of the same type
   */
  boolean checkAttributes(Attribute x, Attribute y);
  
  /**
   * Set the start time of this shape animation.
   * 
   * @param start the start time
   * @throws IllegalArgumentException If 1. start time >= end time
   *                                     2. start time > all interval's smallest start time
   */
  void setStart(int start) throws IllegalArgumentException;
  
  /**
   * Set the end time of this shape animation.
   * 
   * @param end the end time
   * @throws IllegalArgumentException If 1. start time >= end time
   *                                     2. end time < all interval's largest end time
   */
  void setEnd(int end) throws IllegalArgumentException;
  
  /**
   * Return a sorted list of Element, Element records (time, attribute) pairs for text animation.
   * Attach name string as mark.
   * 
   * @return a sorted list of Element
   */
  List<Interval<AnimateTime, Attribute>> getSeq();
  
  /**
   * Generate initial String(type, name, etc.)
   * 
   * @param f the given frequency
   * @return the initial string
   */
  String generateInitString(Integer f);
  
  /**
   * Return the type string.
   * 
   * @return the type string
   */
  String getTypeString();
  
  /**
   * Return the shape type.
   * 
   * @return the shape type
   */
  Shapes getShape();
  
  /**
   * Return type specific initial string.
   * 
   * @return type specific initial string
   */
  String getTypeSpecificInitString();
  
  /**
   * Return an action string based on given Interval.
   * 
   * @param val the given given Interval
   * @param f the given frequency
   * @return action string
   */
  String generateActionIntervalString(Interval<AnimateTime, Attribute> val, Integer f) 
      throws IllegalArgumentException;
  
  /**
   * Generate the OutputShape object, which can
   * be seen as a copy of AnimateShape with
   * animation purpose functions.
   * 
   * @return the generated OutputShape
   */
  ShapeOutput generateOutputShape();
}