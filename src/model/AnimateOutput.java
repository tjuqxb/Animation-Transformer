package model;

import java.util.List;

/**
 * This interface defines output of animation model.
 */
public interface AnimateOutput {
 
  /**
   * Return a string representation (with ticks per second).
   * 
   * @param freq the given frequency
   * @return a string representation (specifying ticks per second)
   */
  String toString(int freq);

  /**
   * Return the names of the shapes.
   * 
   * @return the names
   */
  List<String> getNames();

  /**
   * Return the shapes with animation functions.
   * 
   * @return the shapes
   */
  List<ShapeOutput> getShapes();

  /**
   * Return the bounds of animation.
   * 
   * @return the bounds of animation (left x, top y, width, height)
   */
  int[] getBounds();

  /**
   * Return the (start, end) pair of this animation.
   * 
   * @return the interval (start, end)
   */
  public int[] getInterval();
  
  /**
   * Reset tick to zero.
   */
  void reset();
  
  /**
   * Switch to play animation or pause animation.
   */
  void switchPlay(); 
  
  /**
   * Switch to looping or not looping.
   */
  void switchLooping();
  
  /**
   * Add one tick to animation.
   */
  void next();
  
  /**
   * Return animate frame based on tick.
   * 
   * @return animate frame
   */
  AnimateFrame generateFrame();

  /**
   * Return if the animation has started.
   * 
   * @return if the animation has started
   */
  boolean getHasStarted();

  /**
   * Return if the animation is paused.
   * 
   * @return if the animation is paused
   */
  boolean getIsPaused();

  /**
   * Return if the animation is looping.
   * 
   * @return if the animation is looping
   */
  boolean getIsLooping();

  /**
   * Return if the animation is in finished state.
   * 
   * @return if the animation is in finished state
   */
  boolean getIsFinished();
}