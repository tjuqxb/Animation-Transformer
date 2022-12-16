package model;

/**
 * This interface indicates one attribute of a shape, such as height, width, color, etc.
 */
public interface Attribute {
  /**
   * Generate attribute change action string.
   * 
   * @param a the first attribute
   * @param b the second attribute
   * @return attribute change action string
   */
  String generateActionString(Attribute a, Attribute b) throws IllegalArgumentException;
  
  /**
   * Clone the attribute.
   * 
   * @return cloned attribute
   */
  public Attribute clone();
  
  /**
   * Get the attribute at one point in one interval.
   * 
   * @param a the interval start
   * @param x the start attribute
   * @param b the interval end
   * @param y the end attribute
   * @param mid the point tick
   * @return the attribute at mid
   * @throws IllegalArgumentException if b < a or mid is not in [a, b]
   */
  public Attribute getMidAttribute(int a, Attribute x, int b, Attribute y, int mid) 
      throws IllegalArgumentException;
}