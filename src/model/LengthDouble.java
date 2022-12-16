package model;

/**
 * This class represents the double value in animation.
 */
public class LengthDouble extends Length {
  private double len;

  /**
   * The constructor of LengthDouble.
   * Length can be less than 0. 
   * Length is reserved as one digit precision.
   * 
   * @param len the raw double value
   */
  public LengthDouble(double len) {
    super();
    this.len = (double)Math.round(len * 10) / 10;
  }
  
  @Override
  public int hashCode() {
    return (int)(this.len * 10);
  }
  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LengthDouble)) {
      return false;
    }
    LengthDouble other = (LengthDouble)o;
    return Math.abs(other.len - len) < 0.01;
  }
  
  @Override
  public String toString() {
    return String.format("%.1f", len);
  }

  @Override
  public double getVal() {
    return this.len;
  }
  
  @Override
  public Attribute clone() {
    return new LengthDouble(len);
  }

  @Override
  public Attribute getMidAttribute(int a, Attribute x, int b, Attribute y, int mid)
      throws IllegalArgumentException {
    if (b < a || mid < a || mid > b) {
      throw new IllegalArgumentException("Invalid tick vlaues.");
    }
    if (x.getClass() != this.getClass() || y.getClass() != this.getClass()) {
      throw new IllegalArgumentException("Invalid type.");
    }
    double d0 = ((LengthDouble)x).len;
    double d1 = ((LengthDouble)y).len;
    if (a == b && Math.abs(d0 - d1) >= 0.01) {
      throw new IllegalArgumentException("Interval length 0 with different values.");
    }
    if (a == b) {
      return new LengthDouble(d0);
    }
    double val = d0 * ((double)(b - mid) / (b - a)) + d1 * ((double)(mid - a) / (b - a));
    return new LengthDouble(Math.round(val));
  }
}