package model;

/**
 * This interface represents an animate model.
 * Can add more methods such as set shape existing start and end time.
 */
public interface AnimateModel {

  /**
   * Add an initial shape to animation.
   * 
   * @param type  the type of shape
   * @param name  the name of shape
   * @param start the start time
   * @param end   the end time
   * @param color the initial color
   * @param point the initial position
   * @param size  the initial size
   * @throws IllegalArgumentException If 1.name already exists 2.size type is not
   *                                  compatible with type string
   */
  void addShape(Shapes type, String name, int start, 
      int end, Color color, Position point, Size size) throws IllegalArgumentException;
  
  /**
   * Add an initial shape to animation.
   * 
   * @param type the shape type
   * @param name the shape name
   * @throws IllegalArgumentException If name already exists 
   */
  void addShape(Shapes type, String name) throws IllegalArgumentException;

  /**
   * Add color change action to one shape.
   * 
   * @param name shape name
   * @param a    start time
   * @param x    start color
   * @param b    end time
   * @param y    end color
   * @throws IllegalArgumentException if 1. name does not exist 2.Attribute type
   *                                  is incorrect 3.interval is invalid 4.interval
   *                                  overlap with existing intervals(allow borders overlap
   *                                  with non-conflicting values)
   */
  void addAnimation(String name, int a, Attribute x, int b, Attribute y)
      throws IllegalArgumentException;
  
  
  /**
   * Adds a transformation to shape.
   * Assuming (x, y) is the top left corner.
   * 
   * @param name The name of the shape
   * @param t1   The start time of this transformation
   * @param x1   The initial x-position of the shape
   * @param y1   The initial y-position of the shape
   * @param w1   The initial width of the shape
   * @param h1   The initial height of the shape
   * @param r1   The initial red color-value of the shape
   * @param g1   The initial green color-value of the shape
   * @param b1   The initial blue color-value of the shape
   * @param t2   The end time of this transformation
   * @param x2   The final x-position of the shape
   * @param y2   The final y-position of the shape
   * @param w2   The final width of the shape
   * @param h2   The final height of the shape
   * @param r2   The final red color-value of the shape
   * @param g2   The final green color-value of the shape
   * @param b2   The final blue color-value of the shape
   * @throws IllegalArgumentException 1.name does not exist 
   *                                  2.calling addAnimation(name, a, x, b, y) throws exception
   */
  void addAnimation(String name,
      int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) 
          throws IllegalArgumentException;
  
  /**
   * Set the bounds for animation.
   * 
   * @param x the leftmost x value
   * @param y the topmost y value
   * @param width the width of the bounding box
   * @param height the height of the bounding box
   * @throws IllegalArgumentException if width or height is less than 0
   */
  void setBounds(int x, int y, int width, int height) throws IllegalArgumentException;
  
  /**
   * Helper method copying bounds.
   * 
   * @return the copied bounds
   */
  int[] getBounds();
  
  /**
   * Generate the output object to be imported to view.
   * 
   * @param started whether the animation has started or not
   * @return AnimateOuptput object
   */
  AnimateOutput generateOutput(boolean started);
  
  /**
   * Generate string representation with tick per second configuration.
   * 
   * @param f the given frequency
   * @return the string representation
   */
  String toString(int f) throws IllegalArgumentException;
}