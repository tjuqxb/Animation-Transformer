package model;

/**
 * This class represents abstract size in animation.
 */
public abstract class Size implements Attribute {
  @Override
  public String generateActionString(Attribute a0, Attribute a1)
      throws IllegalArgumentException {
    // We can use fine grained control here. Thus, this is not generic.
    if (a0.getClass() != this.getClass() || a1.getClass() != this.getClass()) {
      throw new IllegalArgumentException("Wrong attribute type.");
    }
    String ret = "scales from ";
    ret += a0.toString();
    ret += " to " + a1.toString();
    return ret;
  }
  
  @Override
  public abstract Attribute clone();
}