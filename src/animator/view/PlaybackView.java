package animator.view;

import animator.controller.Controller;

/**
 * This interface represents a playback view.
 */
public interface PlaybackView extends View {
  /**
   * Display the result from model.
   * 
   * @param c the Controller
   */
  void display(Controller c);
  
  /**
   * Display mouse coordinates.
   * 
   * @param str coordinates string
   */
  void displayCoordinates(String str);
}
