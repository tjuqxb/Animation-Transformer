package model;

/**
 * This class represents a time type wrapped integer type.
 */
public class AnimateTimeInt implements AnimateTime {
  private int t;
  
  /**
   * The constructor of AnimateTimeInt.
   * Does not check t range here as this is not specified in requirements.
   * 
   * @param t the given time value
   * @throws IllegalArgumentException if given time value is less than 0
   */
  public AnimateTimeInt(int t) throws IllegalArgumentException {
    if (t < 0) {
      throw new IllegalArgumentException("Time is greater than or equal to 0.");
    }
    this.t = t;
  }
 
  @Override
  public int compareTo(AnimateTime o) {
    double time = o.getVal();
    if (Math.abs(time - t) < 0.05) {
      return 0;
    }
    return t < time ? -1 : 1;
  }
  
  @Override
  public boolean equals(Object o) {
    if (! (o instanceof AnimateTimeInt)) {
      return false;
    }
    AnimateTimeInt other = (AnimateTimeInt)o;
    return Math.abs(other.getVal() - t) < 0.05;
  }
  
  @Override
  public int hashCode() {
    return this.t;
  }

  @Override
  public double getVal() {
    return (double)this.t;
  }
  
  @Override
  public String toString() {
    return "" + this.t;
  }
  
  @Override
  public AnimateTime clone() {
    return new AnimateTimeInt(t);
  }
}