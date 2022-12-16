package animator.view;

/**
 * This interface represents a view in animator.
 * TextView also supports updateFreq() as t can be displayed
 * by time units in this design. If tick per second is not 
 * specified, it would use original ticks to indicate time unit.
 */
public interface View {
  /**
   * Display the result from model.
   * 
   * @throws IllegalStateException if IO problem occurs
   */
  public void display() throws IllegalStateException;
  
  /**
   * Update the view.
   * 
   * @param o the input updated object
   * @throws IllegalArgumentException if o is of right type
   */
  public void update(Object o) throws IllegalArgumentException;
  
  /**
   * Update the number of ticks in one second.
   * 
   * @param f the number of ticks in one second
   * @throws IllegalArgumentException if f is not valid.
   */
  public void updateFreq(int f) throws IllegalArgumentException;
}