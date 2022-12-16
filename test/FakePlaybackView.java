import javax.swing.JPanel;

import animator.controller.Controller;
import animator.view.PlaybackView;

/**
 * This view class is a helper class for testing.
 */
public class FakePlaybackView extends JPanel implements PlaybackView {
  
  private String coordinates;
  private int f = 1;
  private Controller controller;
  private Object frame;
  
  @Override
  public void display() throws IllegalStateException {
    // TODO Auto-generated method stub
  }
  
  @Override
  public void display(Controller c) {
    this.controller = c;
  }

  /**
   * Get the coordinates string.
   * 
   * @return the coordinates
   */
  public String getCoordinates() {
    return coordinates;
  }

  /**
   * Get f.
   * 
   * @return the f
   */
  public int getF() {
    return f;
  }

  /**
   * Get controller.
   * 
   * @return the controller
   */
  public Controller getController() {
    return controller;
  }

  /**
   * Get frame.
   * 
   * @return the frame
   */
  public Object getFrame() {
    return frame;
  }

  @Override
  public void update(Object o) throws IllegalArgumentException {
    this.frame = o;
  }

  @Override
  public void updateFreq(int f) throws IllegalArgumentException {
    this.f = f;
  }

  @Override
  public void displayCoordinates(String str) {
    this.coordinates = str;
  }
}