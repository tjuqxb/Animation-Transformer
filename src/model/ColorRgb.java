package model;

/**
 * This class represents a RGB color.
 */
public class ColorRgb extends Color {
  private Length r;
  private Length g;
  private Length b;
  
  /**
   * The constructor of RGB color.
   * 
   * @param r the given r
   * @param g the given g
   * @param b the given b
   */
  public ColorRgb(double r, double g, double b) throws IllegalArgumentException {
    if (r < 0 || g < 0 || b < 0) {
      throw new IllegalArgumentException("RGB color attribute should not be less than 0.");
    }
    if (r > 255 || g > 255 || b > 255) {
      throw new IllegalArgumentException("RGB color attribute should not be greater than 255.");
    }
    this.r = new LengthDouble(r);
    this.g = new LengthDouble(g);
    this.b = new LengthDouble(b);
  }

  /**
   * The constructor of RGB color.
   * 
   * @param r the given r
   * @param g the given g
   * @param b the given b
   * @throws IllegalArgumentException If r or g or b is null or is less than 0
   */
  public ColorRgb(Length r, Length g, Length b) throws IllegalArgumentException {
    super();
    if (r == null || g == null || b == null) {
      throw new IllegalArgumentException("Null input.");
    }
    if (r.getVal() < 0 || g.getVal() < 0 || b.getVal() < 0) {
      throw new IllegalArgumentException("RGB color attribute should not be less than 0.");
    }
    if (r.getVal() > 255 || g.getVal() > 255 || b.getVal() > 255) {
      throw new IllegalArgumentException("RGB color attribute should not be greater than 255.");
    }
    this.r = r;
    this.g = g;
    this.b = b;
  }

  @Override
  public int hashCode() {
    return (this.r.hashCode() * 37 + this.g.hashCode()) * 37 + this.b.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ColorRgb)) {
      return false;
    }
    ColorRgb co = (ColorRgb) o;
    return co.r.equals(r) && co.g.equals(g) && co.b.equals(b);
  }

  @Override
  public String toString() {
    return "(" + r.toString() + "," + g.toString() + "," + b.toString() + ")";
  }
  
  @Override
  public Attribute clone() {
    return new ColorRgb((Length)r.clone(), (Length)g.clone(), (Length)b.clone());
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
    Length r1 = (Length)this.r.getMidAttribute(a, ((ColorRgb)x).r, b, ((ColorRgb)y).r, mid); 
    Length g1 = (Length)this.r.getMidAttribute(a, ((ColorRgb)x).g, b, ((ColorRgb)y).g, mid); 
    Length b1 = (Length)this.r.getMidAttribute(a, ((ColorRgb)x).b, b, ((ColorRgb)y).b, mid);
    return new ColorRgb(r1, g1, b1);
  }

  /**
   * Return r.
   * 
   * @return the r
   */
  public Length getR() {
    return r;
  }

  /**
   * Return g.
   * 
   * @return the g
   */
  public Length getG() {
    return g;
  }

  /**
   * Return b.
   * 
   * @return the b
   */
  public Length getB() {
    return b;
  }
  
  
}