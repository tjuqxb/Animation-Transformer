package model;

/**
 * This class indicates size attribute of an animate Oval shape.
 */
public class SizeOval extends Size {
  Length xRadius;
  Length yRadius;
  
  /**
   * The constructor of SizeOval.
   * 
   * @param xr x radius
   * @param yr y radius
   * @throws IllegalArgumentException if xRadius or yRadius is null or is less than 0
   */
  public SizeOval(double xr, double yr) throws IllegalArgumentException {
    if (xr < 0 || yr < 0) {
      throw new IllegalArgumentException("Radius should be greater or equal to 0.");
    }
    this.xRadius = new LengthDouble(xr);
    this.yRadius = new LengthDouble(yr);
  }
  
  /**
   * The constructor of SizeOval.
   * 
   * @param xRadius x radius
   * @param yRadius y radius
   * @throws IllegalArgumentException if xRadius or yRadius is null or is less than 0
   */
  public SizeOval(Length xRadius, Length yRadius) throws IllegalArgumentException {
    super();
    if (xRadius == null || yRadius == null) {
      throw new IllegalArgumentException("Null input.");
    }
    if (xRadius.getVal() < 0 || yRadius.getVal() < 0) {
      throw new IllegalArgumentException("Radius should be greater or equal to 0.");
    }
    this.xRadius = xRadius;
    this.yRadius = yRadius;
  }
  
  @Override
  public int hashCode() {
    return xRadius.hashCode() * 37 + yRadius.hashCode();
  }
  
  @Override
  public Attribute clone() {
    return new SizeOval((Length)xRadius.clone(), (Length)yRadius.clone());
  }
  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SizeOval)) {
      return false;
    }
    SizeOval other = (SizeOval) o;
    return other.xRadius.equals(xRadius) && other.yRadius.equals(yRadius);
  }
  
  @Override
  public String toString() {
    return "X radius: " + this.xRadius.toString() + ", " + "Y radius: " + this.yRadius.toString(); 
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
    Length xr = (Length)xRadius.getMidAttribute(a, ((SizeOval)x).xRadius, 
        b, ((SizeOval)y).xRadius, mid);
    Length yr = (Length)yRadius.getMidAttribute(a, ((SizeOval)x).yRadius, 
        b, ((SizeOval)y).yRadius, mid);
    return new SizeOval(xr, yr);
  }

  /**
   * Return xRadius.
   * 
   * @return the xRadius
   */
  public Length getxRadius() {
    return xRadius;
  }

  /**
   * Return y Radius.
   * 
   * @return the yRadius
   */
  public Length getyRadius() {
    return yRadius;
  }
  
  
}
