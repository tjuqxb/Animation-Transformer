package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents animate output implementation for generating animations.
 */
public class AnimateOutputImpl implements AnimateOutput {
  private List<String> names;
  private List<ShapeOutput> shapes;
  private int[] bounds;
  private int[] interval;
  private int tick;
  private boolean hasStarted;
  private boolean isPaused;
  private boolean isLooping;
  private AnimateModel model;
  
  /**
   * The constructor of AnimateOutput.
   * 
   * @param names shape names
   * @param shapes shape with animation functions
   * @param bounds animation bounds (left x, top y, width, height)
   * @param interval the (start end) interval
   * @param hasStarted if the initial state auto starts
   * @param model a pointer to original model
   */
  public AnimateOutputImpl(List<String> names, List<ShapeOutput> shapes, 
      int[] bounds, int[] interval, boolean hasStarted ,AnimateModel model) {
    super();
    this.names = names;
    this.shapes = shapes;
    this.bounds = bounds;
    this.interval = interval;
    this.model = model;
    this.tick = 0;
    this.hasStarted = hasStarted;
    this.isPaused = false;
    this.isLooping = false;
  }
  
  @Override
  public String toString() {
    return this.model.toString();
  }
  
  @Override 
  public String toString(int freq) {
    return this.model.toString(freq);
  }

  @Override
  public List<String> getNames() {
    return names;
  }

  @Override
  public List<ShapeOutput> getShapes() {
    return shapes;
  }

  @Override
  public int[] getBounds() {
    return bounds;
  }

  @Override
  public int[] getInterval() {
    return interval;
  }

  @Override
  public void reset() {
    this.tick = 0;
    if (!this.hasStarted) {
      this.hasStarted = true;
    }
    if (this.isPaused) {
      this.isPaused = false;
    }
    for (ShapeOutput shape : shapes) {
      shape.reset();
    }
  }

  @Override
  public void switchPlay() {
    if (this.hasStarted) {
      if (this.getIsFinished()) {
        this.reset();
      } else {
        this.isPaused = !this.isPaused;
      }
    } else {
      this.hasStarted = true;
      this.isPaused = false;
    }
  }

  @Override
  public void switchLooping() {
    this.isLooping = !this.isLooping;
  }

  @Override
  public void next() {
    if (hasStarted && !isPaused) {
      this.tick++;
      if (tick > this.interval[1]) {
        if (isLooping) {
          reset();
        } else {
          this.tick--;
        }
      }
    }
  }
  
  @Override
  public boolean getHasStarted() {
    return hasStarted;
  }

  @Override
  public boolean getIsPaused() {
    return isPaused;
  }

  @Override
  public AnimateFrame generateFrame() {
    List<Attribute[]> list = new ArrayList<>();
    for (ShapeOutput shape: shapes) {
      Attribute[] arr = new Attribute[3];
      arr[0] = shape.getCurColor(tick);
      arr[1] = shape.getCurPos(tick);
      arr[2] = shape.getCurSize(tick);
      list.add(arr);
    }
    return new AnimateFrame(list, hasStarted, isPaused, getIsFinished() , isLooping, bounds);
  }
  
  @Override
  public boolean getIsLooping() {
    return this.isLooping;
  }

  @Override
  public boolean getIsFinished() {
    return this.hasStarted && !this.isLooping && this.tick == interval[1];
  }
}