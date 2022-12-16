package animator.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import animator.view.PlaybackView;

/**
 * This class is an example class of mouse handling.
 * Moving the mouse, and the view display would change.
 */
public class MousePosListener extends MouseAdapter {

  private PlaybackView view;

  /**
   * The constructor of MouseDisplayer.
   *
   * @param view the given play-back view
   */
  public MousePosListener(PlaybackView view) {
    this.view = view;
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    // will display x and y coordinates.
    int x = e.getX();
    int y = e.getY();
    String str = "x: " + x + "  y: " + y;
    this.view.displayCoordinates(str);
  }
}