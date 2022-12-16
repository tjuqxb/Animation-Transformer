package model;

/**
 * This class indicates abstract color in animation.
 */
public abstract class Color implements Attribute, Cloneable {
  @Override
  public String generateActionString(Attribute a0, Attribute a1)
      throws IllegalArgumentException {
    // We can use fine grained control here. Thus, this might not be generic.
    if (a0.getClass() != this.getClass() || a1.getClass() != this.getClass()) {
      throw new IllegalArgumentException("Wrong attribute type.");
    }
    String ret = "changes color from ";
    ret += a0.toString();
    ret += " to " + a1.toString();
    return ret;
  }
  
  @Override
  public abstract Attribute clone();
}