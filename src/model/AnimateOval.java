package model;

/**
 * This class represents a concrete animation oval.
 */
public class AnimateOval extends AbstractAnimateShape {

  /**
   * The constructor of AnimateOval.
   * 
   * @param name shape name
   * @param start start time
   * @param end end time
   * @param color initial color
   * @param point initial position
   * @param size initial size
   * @throws IllegalArgumentException if 1. super() throws exception 2. size is of wrong type
   */
  public AnimateOval(String name, int start, int end, Color color, Position point, Size size)
      throws IllegalArgumentException {
    super(name, start, end, color, point);
    if (!(size instanceof SizeOval)) {
      throw new IllegalArgumentException("Wrong initial size type for oval.");
    }
    AnimateTime t0 = new AnimateTimeInt(start);
    this.sizeList.insertInterval(t0, size, t0, size);
  }
  
  /**
   * The constructor of AnimateOval.
   * 
   * @param name the shape name
   */
  public AnimateOval(String name) {
    super(name);
  }

  @Override
  public boolean checkAttributes(Attribute x, Attribute y) {
    return (x instanceof Position && y instanceof Position) 
        || (x instanceof Color && y instanceof Color) 
        || (x instanceof SizeOval && y instanceof SizeOval);
  }

  @Override
  public String getTypeString() {
    return "oval";
  }

  @Override
  public String getTypeSpecificInitString() {
    String ret = "";
    ret += "Center: " + this.posList.get(0).getFirst().getVal().toString() + ", ";
    ret += this.sizeList.get(0).getFirst().getVal().toString() + ", ";
    ret += "Color: " + this.colorList.get(0).getFirst().getVal().toString();
    return ret;
  }

  @Override
  public Shapes getShape() {
    return Shapes.OVAL;
  }
  
}