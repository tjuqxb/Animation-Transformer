package model;

import java.util.List;

/**
 * This class represents one animate frame with necessary information.
 * This class only has getters and is a data object.
 */
public class AnimateFrame {
  private int[] bounds;
  private List<Attribute[]> list;
  private boolean isPaused;
  private boolean isFinished;
  private boolean isLooping;
  private boolean hasStarted;
  
  /**
   * The constructor of AnimateFrame.
   * 
   * @param list  a list of Attribute[], order: color, position, size, 
   * @param isPaused is in pause state or not
   * @param isFinished is in finished state or not
   * @param isLooping is in looping state or not
   * @param bounds the bounds of animation
   */
  public AnimateFrame(List<Attribute[]> list, boolean hasStarted, 
      boolean isPaused, boolean isFinished,
      boolean isLooping, int[] bounds) {
    super();
    this.list = list;
    this.hasStarted = hasStarted;
    this.isPaused = isPaused;
    this.isFinished = isFinished;
    this.isLooping = isLooping;
    this.bounds = bounds;
  }

  /**
   * Return the bounds.
   * 
   * @return the bounds
   */
  public int[] getBounds() {
    return bounds;
  }

  /**
   * Return the list of Attribute[].
   * 
   * @return the list
   */
  public List<Attribute[]> getList() {
    return list;
  }

  /**
   * Return if it is paused.
   * 
   * @return the isPaused
   */
  public boolean isPaused() {
    return isPaused;
  }

  /**
   * Return if it is finished.
   * 
   * @return the isFinished
   */
  public boolean isFinished() {
    return isFinished;
  }

  /**
   * Return if it is looping.
   * 
   * @return the isLooping
   */
  public boolean isLooping() {
    return isLooping;
  }
  
  /**
   * Return if it has started.
   * 
   * @return the hasStarted
   */
  public boolean hasStarted() {
    return hasStarted;
  }
}