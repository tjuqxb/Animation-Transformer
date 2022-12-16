package animator.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import animator.controller.Controller;
import animator.controller.ControllerImpl;
import model.AnimateFrame;

/**
 * The GuiView implementation.
 */
public class GuiViewImpl extends JFrame implements GuiView {
  private AnimatePanel animatePanel;
  private Timer timer;
  private int gap;
  private int width;
  private int height;

  /**
   * The constructor of GuiView.
   */
  public GuiViewImpl() {
    super("Animator");
    setDefaultLookAndFeelDecorated(false);
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (UnsupportedLookAndFeelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.setLocation(0, 0);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.gap = 1000;
    this.timer = null;
    setVisible(true);
    this.repaint();
  }

  @Override
  public void display() {
    if (this.timer != null) {
      this.timer.restart();
    }
  }
  
  @Override
  public void display(Controller c) {
    this.timer = new Timer(gap, (ControllerImpl)c);
    this.timer.start();
  }

  @Override
  public void update(Object o) {
    if (! (o instanceof AnimateFrame)) {
      JOptionPane.showMessageDialog(new JFrame(), "Require AnimtateFrame type.");
      return;
    }
    AnimateFrame frame = (AnimateFrame)o;
    int[] bounds = frame.getBounds();
    if (this.animatePanel == null || this.width != bounds[2] || this.height != bounds[3]) {
      this.animatePanel = new AnimatePanel(bounds);
      this.width = bounds[2];
      this.height = bounds[3];
      this.setContentPane(this.animatePanel);
      pack();
    }
    this.animatePanel.updateShapes(frame);
    this.repaint();
  }

  /**
   * Update tick per second in Animation.
   *
   * @param tickPerSecond updated tick per second
   * @throws IllegalArgumentException if tickPerSecond is less or equal to zero
   */
  @Override
  public void updateFreq(int tickPerSecond) {
    if (tickPerSecond <= 0) {
      JOptionPane.showMessageDialog(new JFrame(), "Speed should be greater than 0.");
      return;
    }
    this.gap = 1000 / tickPerSecond;
    if (this.timer != null) {
      this.timer.setDelay(gap);
    } 
  }
}