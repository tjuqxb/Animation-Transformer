package animator.controller;

import java.awt.event.MouseAdapter;

import model.AnimateModel;

/**
 * The controller interface.
 */
public interface Controller {
  
  /**
   * Transform input to animation and display (or output to file).
   * 
   * @model the animate model
   * @tick the animate tick
   * @throws IllegalArgumentException if model is null or tick is null or < 0
   */
  void tranformAnimation(AnimateModel model, Integer tick) throws IllegalArgumentException;
  
  /**
   * Return the controller's delegated MouseAdapter.
   * 
   * @return the controller's delegated MouseAdapter
   */
  MouseAdapter getMouseAdapter();
}