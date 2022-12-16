package model;

import java.util.List;

import model.intervallist.Interval;

/**
 * The output model for animation (the input for views).
 */
public interface ShapeOutput {
  /**
   * Reset index to zero.
   */
  void reset();
  
  /**
   * Return the shape type.
   * 
   * @return the shape type
   */
  Shapes getShape();
  
  /**
   * Return the color of current tick.
   * 
   * @param tick current tick
   * @return the color of current tick
   */
  Attribute getCurColor(int tick);
  
  /**
   * Return the position of current tick.
   * 
   * @param tick current tick
   * @return the position of current tick
   */
  Attribute getCurPos(int tick);
  
  /**
   * Return the size of current tick.
   * 
   * @param tick current tick
   * @return the size of current tick
   */
  Attribute getCurSize(int tick);
  
  /**
   * Get the color list of this shape.
   * 
   * @return the color list of this shape
   */
  List<Interval<AnimateTime, Attribute>> getColorList();
  
  /**
   * Get the position list of this shape.
   * 
   * @return the position list of this shape
   */
  List<Interval<AnimateTime, Attribute>> getPosList();
  
  /**
   * Get the size list of this shape.
   * 
   * @return the position list of this shape
   */
  List<Interval<AnimateTime, Attribute>> getSizeList();
}