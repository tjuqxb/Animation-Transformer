package animator.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import animator.controller.Controller;
import animator.controller.ControllerImpl;
import model.AnimateFrame;

/**
 * This class is an implementation of Playback view.
 * For change speed: using timer and control variable.
 * For start, pause, resume, restart: using controller listener and model.
 * StartButton: start -> pause -> resume(start) (-> end if animation is not looping)
 */
public class PlaybackViewImpl extends JFrame 
    implements PlaybackView, ActionListener, ChangeListener {
  private int tickPerSecond;
  private Timer timer;
  private JSplitPane splitPane;
  private AnimatePanel animatePanel;
  private JPanel buttonPanel;
  private JButton startButton;
  private JButton restartButton;
  private JButton loopingButton;
  private JTextField speedField;
  private JSlider speedSlider;
  private JTextField jTextField;
  private int gap;
  private int width;
  private int height;

  /**
   * The constructor of PlaybackViewImpl.
   */
  public PlaybackViewImpl() {
    super("Playback Animator");
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

    this.splitPane = new JSplitPane();
    getContentPane().setLayout(new GridLayout());  
    getContentPane().add(splitPane); 

    this.buttonPanel = new JPanel(true);
    this.buttonPanel.setBackground(Color.WHITE);
    this.buttonPanel.setLocation(0, 0);
    this.buttonPanel.setLayout(new FlowLayout());

    this.startButton = new JButton("start");
    this.startButton.setName("Start");
    this.buttonPanel.add(this.startButton);

    this.restartButton = new JButton("restart");
    this.restartButton.setName("Restart");
    this.buttonPanel.add(restartButton);

    this.loopingButton = new JButton("looping");
    this.loopingButton.setName("Looping");
    this.buttonPanel.add(loopingButton);

    this.speedSlider = new JSlider(1, 60, 1);
    this.speedSlider.setName("Speed Slider");
    this.speedSlider.addChangeListener(this);
    this.buttonPanel.add(speedSlider);

    this.speedField = new JTextField(5);
    this.buttonPanel.add(speedField);

    JButton speedButton = new JButton("Set speed!");
    speedButton.setName("Speed");
    speedButton.setToolTipText("To set speed higher than 60, "
        + "you can input a number and click this button.");
    speedButton.addActionListener(this);
    buttonPanel.add(speedButton);

    this.jTextField = new JTextField(9);
    this.buttonPanel.add(jTextField);

    this.animatePanel = null;
    this.gap = 1000;

    this.setVisible(true);
  }

  @Override
  public void display() throws IllegalStateException {
    if (this.timer != null) {
      this.timer.restart();
    }
  }

  @Override
  public void display(Controller c) {
    this.startButton.addActionListener((ControllerImpl)c);
    this.restartButton.addActionListener((ControllerImpl)c);
    this.loopingButton.addActionListener((ControllerImpl)c);
    this.animatePanel.addMouseMotionListener(c.getMouseAdapter());
    this.timer = new Timer(gap, (ControllerImpl)c);
    this.timer.start();
  }

  @Override
  public void update(Object o) {
    if (! (o instanceof AnimateFrame)) {
      JOptionPane.showMessageDialog(this, "Require AnimtateFrame type.");
      return;
    }
    AnimateFrame frame = (AnimateFrame)o;
    int[] bounds = frame.getBounds();
    if (this.animatePanel == null || this.width != bounds[2] || this.height != bounds[3]) {
      this.animatePanel = new AnimatePanel(bounds);
      this.width = bounds[2];
      this.height = bounds[3];
      this.buttonPanel.setSize(new Dimension(width, 40));
      this.splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  
      this.splitPane.setDividerLocation(40);                    
      this.splitPane.setTopComponent(buttonPanel);              
      this.splitPane.setBottomComponent(animatePanel);
      this.splitPane.setPreferredSize(getPreferredSize());
      this.pack();
    }
    this.animatePanel.updateShapes(frame);
    boolean hasStarted = frame.hasStarted();
    boolean isPaused = frame.isPaused();
    boolean isLooping = frame.isLooping();
    boolean isFinished = frame.isFinished();
    if (!hasStarted || isFinished) {
      this.startButton.setText("start");
    } else if (isPaused) {
      this.startButton.setText("resume");
    } else {
      this.startButton.setText("pause");
    }
    if (isLooping) {
      this.loopingButton.setText("stop looping");
    } else {
      this.loopingButton.setText("looping");
    }
    this.repaint();
  }

  @Override
  public void updateFreq(int tickPerSecond) {
    if (tickPerSecond <= 0) {
      JOptionPane.showMessageDialog(this, "Speed should be greater than 0.");
      return;
    }
    this.tickPerSecond = tickPerSecond;
    this.gap = 1000 / tickPerSecond;
    this.speedField.setText("" + tickPerSecond);
    this.speedSlider.setValue(tickPerSecond);
    if (this.timer != null) {
      this.timer.setDelay(gap);
      this.timer.restart();
    } 
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Component c = (Component)e.getSource();
    if (c.getName().equals("Speed")) {
      int speed = this.getSpeed();
      this.updateFreq(speed);
      this.speedSlider.setValue(speed);
    } 
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    Component c = (Component)e.getSource();
    if (c.getName().equals("Speed Slider")) {
      this.speedField.setText("" + this.speedSlider.getValue());
      this.updateFreq(this.speedSlider.getValue());
    }
  }

  /**
   * Return the speed parsed from input field.
   * If number is invalid, would return original speed.
   * 
   * @return the parsed speed
   */
  private int getSpeed() {
    String command = this.speedField.getText();
    int ret = this.tickPerSecond;
    try {
      ret = Integer.parseInt(command);
      if (ret <= 0) {
        JOptionPane.showMessageDialog(this, "Speed should be greater than 0.");
      }
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Please enter a number.");
    }
    return ret;
  }

  @Override
  public void displayCoordinates(String str) {
    this.jTextField.setText(str);
  }
}