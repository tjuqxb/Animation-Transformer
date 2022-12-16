package model;

/**
 * This class represents a 2d position in animation.
 */
public class Position2d extends Position {
  private Length x;
  private Length y;
  
  /**
   * The constructor of a two dimensional point.
   * 
   * @param x x coordinate
   * @param y y coordinate
   */
  public Position2d(double x, double y) {
    this.x = new LengthDouble(x);
    this.y = new LengthDouble(y);
  }
  
  /**
   * The constructor of a two dimensional point.
   * 
   * @param x x coordinate
   * @param y y coordinate
   * @throws IllegalArgumentException if x or y is null
   */
  public Position2d(Length x, Length y) throws IllegalArgumentException {
    if (x == null || y == null) {
      throw new IllegalArgumentException();
    }
    this.x = x;
    this.y = y;
  }

  @Override
  public int hashCode() {
    return this.x.hashCode() * 37 + this.y.hashCode();
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (! (o instanceof Position2d)) {
      return false;
    }
    Position2d other = (Position2d) o;
    return other.x.equals(this.x) && other.y.equals(this.y);
  }
  
  @Override
  public String toString() {
    return "(" + x.toString() + "," + y.toString() + ")";
  }
  
  @Override
  public Attribute clone() {
    return new Position2d((Length)x.clone(), (Length)y.clone());
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
    Length x1 = (Length)this.x.getMidAttribute(a, ((Position2d)x).x, b, ((Position2d)y).x, mid);
    Length y1 = (Length)this.y.getMidAttribute(a, ((Position2d)x).y, b, ((Position2d)y).y, mid);
    return new Position2d(x1, y1);
  }

  /**
   * Return x.
   * 
   * @return the x
   */
  public Length getX() {
    return x;
  }

  /**
   * Return y.
   * 
   * @return the y
   */
  public Length getY() {
    return y;
  }
  
  
}