package model;

/**
 * This class represents abstract position in animation.
 */
public abstract class Position implements Attribute {
  @Override
  public String generateActionString(Attribute a0, Attribute a1)
      throws IllegalArgumentException {
    // We can use fine grained control here. Thus, this might not be generic.
    if (a0.getClass() != this.getClass() || a1.getClass() != this.getClass()) {
      throw new IllegalArgumentException("Wrong attribute type.");
    }
    String ret = "moves from ";
    ret += a0.toString();
    ret += " to " + a1.toString();
    return ret;
  }
  
  @Override
  public abstract Attribute clone();
}
