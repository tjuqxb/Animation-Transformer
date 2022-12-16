package model;

/**
 *  This class represents a concrete animation rectangle.
 */
public class AnimateRectangle extends AbstractAnimateShape {

  /**
   * The constructor of AnimateRectangle.
   * 
   * @param name shape name
   * @param start start time
   * @param end end time
   * @param color initial color
   * @param point initial position
   * @param size initial size
   * @throws IllegalArgumentException if 1. super() throws exception 2. size is of wrong type
   */
  public AnimateRectangle(String name, int start, int end, Color color, Position point, Size size)
      throws IllegalArgumentException {
    super(name, start, end, color, point);
    if (!(size instanceof SizeRectangle)) {
      throw new IllegalArgumentException("Wrong initial size type for rectangle.");
    }
    AnimateTime t0 = new AnimateTimeInt(start);
    this.sizeList.insertInterval(t0, size, t0, size);
  }
  
  /**
   * The constructor of AnimateRectangle.
   * 
   * @param name the shape name
   */
  public AnimateRectangle(String name) {
    super(name);
  }

  @Override
  public boolean checkAttributes(Attribute x, Attribute y) {
    return (x instanceof Position && y instanceof Position) 
        || (x instanceof Color && y instanceof Color) 
        || (x instanceof SizeRectangle && y instanceof SizeRectangle);
  }

  @Override
  public String getTypeString() {
    return "rectangle";
  }

  @Override
  public String getTypeSpecificInitString() {
    String ret = "";
    ret += "Min corner: " + this.posList.get(0).getFirst().getVal().toString() + ", ";
    ret += this.sizeList.get(0).getFirst().getVal().toString() + ", ";
    ret += "Color: " + this.colorList.get(0).getFirst().getVal().toString();
    return ret;
  }

  @Override
  public Shapes getShape() {
    return Shapes.RECTANGLE;
  }
}