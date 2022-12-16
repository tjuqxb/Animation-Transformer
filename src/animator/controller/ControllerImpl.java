package animator.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.Timer;

import animator.view.GuiView;
import animator.view.PlaybackView;
import animator.view.View;
import model.AnimateModel;
import model.AnimateOutput;

/**
 * This class is an implementation of controller.
 */
public class ControllerImpl implements Controller, ActionListener {
  private View view;
  private AnimateOutput modelOutput;
  private MouseAdapter mouseAdapter;

  /**
   * The constructor of ControllerImpl.
   *
   * @param view the view
   */
  public ControllerImpl(View view) {
    this.view = view;
    if (view instanceof PlaybackView) {
      this.mouseAdapter = new MousePosListener((PlaybackView) view);
    }
  }

  @Override
  public void tranformAnimation(AnimateModel animateModel, Integer tick)
      throws IllegalArgumentException {
    if (animateModel == null) {
      throw new IllegalArgumentException("Model is null.");
    }
    if (tick != null && tick <= 0) {
      throw new IllegalArgumentException("Speed should be greater than 0.");
    }
    try {
      if (view instanceof PlaybackView) {
        this.modelOutput = animateModel.generateOutput(false);
      } else {
        this.modelOutput = animateModel.generateOutput(true);
      }
      if (tick != null && tick > 1) {
        view.updateFreq(tick);
      }
      if (view instanceof PlaybackView || view instanceof GuiView) {
        view.update(this.modelOutput.generateFrame());
        if (view instanceof PlaybackView) {
          ((PlaybackView) view).display(this);
        } else {
          ((GuiView) view).display(this);
        }
      } else {
        view.update(this.modelOutput);
        view.display();
      }
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() instanceof Component) {
      Component c = (Component) e.getSource();
      if (c.getName().equals("Restart")) {
        this.modelOutput.reset();
      } else if (c.getName().equals("Start")) {
        this.modelOutput.switchPlay();
      } else if (c.getName().equals("Looping")) {
        this.modelOutput.switchLooping();
      }
      this.view.update(this.modelOutput.generateFrame());
    } else if (e.getSource() instanceof Timer) {
      this.modelOutput.next();
      this.view.update(this.modelOutput.generateFrame());
    }
  }

  @Override
  public MouseAdapter getMouseAdapter() {
    return this.mouseAdapter;
  }
}