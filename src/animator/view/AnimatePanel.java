package animator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import model.AnimateFrame;
import model.Attribute;
import model.ColorRgb;
import model.Position2d;
import model.Size;
import model.SizeOval;
import model.SizeRectangle;

/**
 * This Class represents an animation panel.
 */
public class AnimatePanel extends JPanel {
  private int[] bounds;
  private List<Attribute[]> list;

  /**
   * The constructor of Animate panel.
   */
  public AnimatePanel(int[] bounds) {
    super(true);
    this.setLocation(0, 0);
    this.setBackground(Color.white);
    if (bounds[2] == -1) {
      this.setPreferredSize(new Dimension(500, 500));
    } else {
      this.setPreferredSize(new Dimension(bounds[2], bounds[3]));
    }
    this.bounds = bounds;
    this.list = new ArrayList<>();
  }

  /**
   * Update shapes in AnimatePanel.
   *
   * @param frame the output frame from controller
   */
  public void updateShapes(AnimateFrame frame) {
    if (frame.getBounds()[2] != -1) {
      this.bounds = frame.getBounds();
      this.setPreferredSize(new Dimension(bounds[2], bounds[3]));
    }
    this.list = frame.getList();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponents(g);
    int xBase = bounds[2] == -1 ? 0 : bounds[0];
    int yBase = bounds[2] == -1 ? 0 : bounds[1];
    for (Attribute[] arr : list) {
      ColorRgb color = (ColorRgb)arr[0];
      Position2d position = (Position2d)arr[1];
      Size size = (Size)arr[2];
      if (color != null && position != null && size != null) {
        int r1 = (int)Math.round(color.getR().getVal());
        int g1 = (int)Math.round(color.getG().getVal());
        int b1 = (int)Math.round(color.getB().getVal());
        int x1 = (int)Math.round(position.getX().getVal());
        int y1 = (int)Math.round(position.getY().getVal());
        if (size instanceof SizeRectangle) {
          SizeRectangle size1 = (SizeRectangle)size;
          int w1 = (int)Math.round(size1.getWidth().getVal());
          int h1 = (int)Math.round(size1.getHeight().getVal());
          int displayX = x1 - xBase;
          int displayY = y1 - yBase;
          Color orig = g.getColor();
          g.setColor(new Color(r1, g1, b1));
          g.fillRect(displayX, displayY, w1, h1);
          g.setColor(orig);
        } else if (size instanceof SizeOval) { 
          SizeOval size1 = (SizeOval)size;
          int xRadius = (int)Math.round(size1.getxRadius().getVal());
          int yRadius = (int)Math.round(size1.getyRadius().getVal());
          int displayX = x1 - xRadius - xBase;
          int displayY = y1 - yRadius - yBase;
          Color orig = g.getColor();
          g.setColor(new Color(r1, g1, b1));
          g.fillOval(displayX, displayY, 2 * xRadius, 2 * yRadius);
          g.setColor(orig);
        }
      }
    }
  }
}