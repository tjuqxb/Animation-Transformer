package animator.view;

import animator.controller.Controller;

/**
 * The GUI view interface.
 */
public interface GuiView extends View {
  /**
   * Display the result from model.
   * 
   * @param c the Controller
   */
  void display(Controller c);
}