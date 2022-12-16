package model;

/**
 * This class represents abstract length.
 */
public abstract class Length implements Attribute {
  /**
   * Get the value of length.
   */
  public abstract double getVal();
  
  @Override
  public String generateActionString(Attribute a0, Attribute a1)
      throws IllegalArgumentException {
    // We can use fine grained control here. Thus, this might be not generic.
    if (a0.getClass() != this.getClass() || a1.getClass() != this.getClass()) {
      throw new IllegalArgumentException("Wrong attribute type.");
    }
    String ret = "changes from ";
    ret += a0.toString();
    ret += " to " + a1.toString();
    return ret;
  }
  
  @Override
  public abstract Attribute clone();
}