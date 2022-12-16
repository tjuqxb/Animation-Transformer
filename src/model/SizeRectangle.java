package model;

/**
 * This class indicates size attribute of an animate rectangle shape.
 */
public class SizeRectangle extends Size {
  Length width;
  Length height;
  
  /**
   * The constructor of Rectangle size.
   * 
   * @param width the width of rectangle
   * @param height the height of rectangle
   * @throws IllegalArgumentException if width < 0 or height < 0
   */
  public SizeRectangle(double width, double height) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("Rectangle size should be greater than or equal to 0.");
    }
    this.width = new LengthDouble(width);
    this.height = new LengthDouble(height);
  }
  
  /**
   * The constructor of Rectangle size.
   * 
   * @param width the width of rectangle
   * @param height the height of rectangle
   * @throws IllegalArgumentException if width is null or < 0, height is null or < 0
   */
  public SizeRectangle(Length width, Length height) throws IllegalArgumentException {
    super();
    if (width == null || height == null) {
      throw new IllegalArgumentException("Null input.");
    }
    if (width.getVal() < 0 || height.getVal() < 0) {
      throw new IllegalArgumentException("Rectangle size should be greater than or equal to 0.");
    }
    this.width = width;
    this.height = height;
  }
  
  @Override
  public Attribute clone() {
    return new SizeRectangle((Length)width.clone(), (Length)height.clone());
  }
  
  @Override
  public int hashCode() {
    return width.hashCode() * 37 + height.hashCode();
  }
  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SizeRectangle)) {
      return false;
    }
    SizeRectangle other = (SizeRectangle) o;
    return other.width.equals(width) && other.height.equals(height);
  }
  
  @Override
  public String toString() {
    return "Width: " + this.width.toString() + ", " + "Height: " + this.height.toString(); 
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
    Length w = (Length)this.width.getMidAttribute(a, ((SizeRectangle)x).width, 
        b, ((SizeRectangle)y).width, mid);
    Length h = (Length)this.height.getMidAttribute(a, ((SizeRectangle)x).height, 
        b, ((SizeRectangle)y).height, mid);
    return new SizeRectangle(w, h);
  }

  /**
   * Return width.
   * 
   * @return the width
   */
  public Length getWidth() {
    return width;
  }

  /**
   * Return height.
   * 
   * @return the height
   */
  public Length getHeight() {
    return height;
  }
  
  
}