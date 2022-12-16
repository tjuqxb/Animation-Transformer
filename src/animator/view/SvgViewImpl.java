package animator.view;

import java.io.IOException;
import java.util.List;

import model.AnimateOutput;
import model.AnimateTime;
import model.Attribute;
import model.ColorRgb;
import model.Position2d;
import model.ShapeOutput;
import model.Shapes;
import model.Size;
import model.SizeOval;
import model.SizeRectangle;
import model.intervallist.Interval;

/**
 * This class is an SVG view implementation.
 */
public class SvgViewImpl implements SvgView {
  private Appendable output;
  private AnimateOutput modelOutput;
  private int tick;

  /**
   * The constructor of SVGViewImpl.
   *
   * @param output the output character stream
   */
  public SvgViewImpl(Appendable output) {
    this.output = output;
    this.modelOutput = null;
    this.tick = 1;
  }

  @Override
  public void display() throws IllegalStateException {
    if (modelOutput != null) {
      String start = "<?xml version=\"1.0\" standalone=\"no\"?>\n"
          + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \n"
          + "  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">" + "\n";
      int[] bounds = modelOutput.getBounds();
      if (bounds[2] == -1) {
        bounds = new int[]{0, 0, 500, 500};
      }
      String bs = "";
      for (int i = 0; i < 4; i++) {
        bs += " " + bounds[i];
      }
      int[] interval = modelOutput.getInterval();
      String str1 = "<svg viewBox=\"" + bs.trim() + "\"\n"
          + "     xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">";
      start += str1 + "\n";
      start += transformShapes(modelOutput.getShapes(), interval) + "\n";
      start += "</svg>";
      try {
        output.append(start);
      } catch (IOException e) {
        throw new IllegalStateException("IO has some problem.");
      }
    }
  }

  /**
   * Transform ShapeOut list and interval to SVG string.
   *
   * @param list ShapeOut list
   * @param interval animation interval (start, end)
   * @return the SVG string
   */
  private String transformShapes(List<ShapeOutput> list, int[] interval) {
    String ret = "";
    boolean first = true;
    for (ShapeOutput shape: list) {
      if (first) {
        ret += getShapeString(shape, interval);
        first = false;
      } else {
        ret += "\n" + getShapeString(shape, interval);
      }
    }
    return ret;
  }

  /**
   * Transform ticks to time (ms).
   *
   * @param ticks the given ticks
   * @return time (ms)
   */
  private int timeHelper(int ticks) {
    return (int)Math.round((double)ticks / tick * 1000);
  }

  /**
   * Transform a ShapeOut object to SVG string.
   *
   * @param shape the shape
   * @param interval animation interval
   * @return svg string
   */
  private String getShapeString(ShapeOutput shape, int[] interval) {
    String ret = "";
    List<Interval<AnimateTime, Attribute>> colorList = shape.getColorList();
    List<Interval<AnimateTime, Attribute>> posList = shape.getPosList();
    List<Interval<AnimateTime, Attribute>> sizeList = shape.getSizeList();
    if (colorList.size() == 0 ||
        posList.size() == 0 ||
        sizeList.size() == 0) {
      return ret;
    }
    String initString = "";
    int startTick = (int)colorList.get(0).getFirst().getKey().getVal();
    int startMs = timeHelper(startTick);
    int endTick = (int)colorList.get(colorList.size() - 1).getSecond().getKey().getVal();
    int endMs = timeHelper(endTick);
    ColorRgb c0 = (ColorRgb)colorList.get(0).getFirst().getVal();
    Position2d p0 = (Position2d)posList.get(0).getFirst().getVal();
    int x0 = (int)p0.getX().getVal();
    int y0 = (int)p0.getY().getVal();
    int r0 = (int)c0.getR().getVal();
    int g0 = (int)c0.getG().getVal();
    int b0 = (int)c0.getB().getVal();
    String appearString = "    <animate attributeName=\"visibility\" attributeType=\"XML\"\n"
        + "             begin=\"0ms\" dur=\"" + startMs +
        "ms\" fill=\"freeze\" from=\"hidden\" to=\"visible\" />";
    if (shape.getShape() == Shapes.RECTANGLE) {
      SizeRectangle s0 = (SizeRectangle)sizeList.get(0).getFirst().getVal();
      int w0 = (int)s0.getWidth().getVal();
      int h0 = (int)s0.getHeight().getVal();
      initString = "  <rect  x=\"" + x0 + "\" y=\"" + y0 + "\" width=\"" + w0 +
          "\" height=\"" + h0 + "\"\n"
          + "        fill=\"rgb(" + r0 + "," + g0 + "," + b0 + ")\"  visibility=\"hidden\">";

    } else if (shape.getShape() == Shapes.OVAL) {
      SizeOval s0 = (SizeOval)sizeList.get(0).getFirst().getVal();
      int xRadius = (int)s0.getxRadius().getVal();
      int yRadius = (int)s0.getyRadius().getVal();
      initString = "  <ellipse  cx=\"" + x0 + "\" cy=\"" + y0 + "\" rx=\"" + xRadius +
          "\" ry=\"" + yRadius + "\"\n"
          + "        fill=\"rgb(" + r0 + "," + g0 + "," + b0 + ")\"  visibility=\"hidden\">";
    }
    ret += initString + "\n";
    ret += appearString + "\n";
    String strColor = getAnimateString(shape.getShape(), colorList);
    String strPos = getAnimateString(shape.getShape(), posList);
    String strSize = getAnimateString(shape.getShape(), sizeList);
    ret += strColor + "\n";
    ret += strPos + "\n";
    ret += strSize + "\n";
    // Add disappear string if the shape disappears and its end < animation end
    if (endTick < modelOutput.getInterval()[1]) {
      String disString = "    <animate attributeName=\"visibility\" attributeType=\"XML\"\n"
          + "             begin=\"" + endMs + "ms\" dur=\"" + 1 +
          "ms\" fill=\"freeze\" from=\"visible\" to=\"hidden\" />";
      ret += disString + "\n";
    }
    String endString = "";
    if (shape.getShape() == Shapes.RECTANGLE) {
      endString = "  </rect>";
    } else if (shape.getShape() == Shapes.OVAL) {
      endString = "  </ellipse>";
    }
    ret += endString;
    return ret;
  }

  /**
   * Get animate string from shape.
   *
   * @param shape the shape
   * @param list the list of attribute intervals
   * @return the animate string
   */
  private String getAnimateString(Shapes shape, List<Interval<AnimateTime, Attribute>> list) {
    String ret = "";
    boolean first = true;
    for (Interval<AnimateTime, Attribute> interval : list) {
      Attribute a0 = interval.getFirst().getVal();
      int t0 = (int)interval.getFirst().getKey().getVal();
      int time0 = timeHelper(t0);
      Attribute a1 = interval.getSecond().getVal();
      int t1 = (int)interval.getSecond().getKey().getVal();
      int time1 = timeHelper(t1);
      int dur = time1 - time0;
      String item = "";
      if (a0 instanceof ColorRgb) {
        ColorRgb c0 = (ColorRgb)a0;
        ColorRgb c1 = (ColorRgb)a1;
        int r0 = (int)c0.getR().getVal();
        int g0 = (int)c0.getG().getVal();
        int b0 = (int)c0.getB().getVal();
        int r1 = (int)c1.getR().getVal();
        int g1 = (int)c1.getG().getVal();
        int b1 = (int)c1.getB().getVal();
        item = "    <animate attributeName=\"fill\" attributeType=\"XML\"\n"
            + "             begin=\"" + time0 + "ms\" dur=\"" + dur +
            "ms\" fill=\"freeze\" from=\"rgb(" + r0 + "," + g0 + "," + b0
            + ")\" to=\"rgb(" + r1 + "," + g1 + "," + b1 + ")\" />";
      } else if (a0 instanceof Position2d) {
        Position2d p0 = (Position2d)a0;
        Position2d p1 = (Position2d)a1;
        int x0 = (int)p0.getX().getVal();
        int y0 = (int)p0.getY().getVal();
        int x1 = (int)p1.getX().getVal();
        int y1 = (int)p1.getY().getVal();
        if (shape == Shapes.RECTANGLE) {
          String temp0 = "    <animate attributeName=\"x\" attributeType=\"XML\"\n"
              + "             begin=\"" + time0 + "ms\" dur=\"" + dur +
              "ms\" fill=\"freeze\" " + "from=\"" + x0 + "\" to=\"" + x1 + "\" />";
          String temp1 = "    <animate attributeName=\"y\" attributeType=\"XML\"\n"
              + "             begin=\"" + time0 + "ms\" dur=\"" + dur +
              "ms\" fill=\"freeze\" " + "from=\"" + y0 + "\" to=\"" + y1 + "\" />";
          item = temp0 + "\n" + temp1;
        } else if (shape == Shapes.OVAL) {
          String temp0 = "    <animate attributeName=\"cx\" attributeType=\"XML\"\n"
              + "             begin=\"" + time0 + "ms\" dur=\"" + dur +
              "ms\" fill=\"freeze\" " + "from=\"" + x0 + "\" to=\"" + x1 + "\" />";
          String temp1 = "    <animate attributeName=\"cy\" attributeType=\"XML\"\n"
              + "             begin=\"" + time0 + "ms\" dur=\"" + dur +
              "ms\" fill=\"freeze\" " + "from=\"" + y0 + "\" to=\"" + y1 + "\" />";
          item = temp0 + "\n" + temp1;
        }
      } else if (a0 instanceof Size) {
        if (a0 instanceof SizeRectangle) {
          SizeRectangle s0 = (SizeRectangle)a0;
          int w0 = (int)s0.getWidth().getVal();
          int h0 = (int)s0.getHeight().getVal();
          SizeRectangle s1 = (SizeRectangle)a1;
          int w1 = (int)s1.getWidth().getVal();
          int h1 = (int)s1.getHeight().getVal();
          String temp0 = "    <animate attributeName=\"width\" attributeType=\"XML\"\n"
              + "             begin=\"" + time0 + "ms\" dur=\"" + dur +
              "ms\" fill=\"freeze\" " + "from=\"" + w0 + "\" to=\"" + w1 + "\" />";
          String temp1 = "    <animate attributeName=\"height\" attributeType=\"XML\"\n"
              + "             begin=\"" + time0 + "ms\" dur=\"" + dur +
              "ms\" fill=\"freeze\" " + "from=\"" + h0 + "\" to=\"" + h1 + "\" />";
          item = temp0 + "\n" + temp1;
        } else if (a0 instanceof SizeOval) {
          SizeOval s0 = (SizeOval)a0;
          int rx0 = (int)s0.getxRadius().getVal();
          int ry0 = (int)s0.getyRadius().getVal();
          SizeOval s1 = (SizeOval)a1;
          int rx1 = (int)s1.getxRadius().getVal();
          int ry1 = (int)s1.getyRadius().getVal();
          String temp0 = "    <animate attributeName=\"rx\" attributeType=\"XML\"\n"
              + "             begin=\"" + time0 + "ms\" dur=\"" + dur +
              "ms\" fill=\"freeze\" " + "from=\"" + rx0 + "\" to=\"" + rx1 + "\" />";
          String temp1 = "    <animate attributeName=\"ry\" attributeType=\"XML\"\n"
              + "             begin=\"" + time0 + "ms\" dur=\"" + dur +
              "ms\" fill=\"freeze\" " + "from=\"" + ry0 + "\" to=\"" + ry1 + "\" />";
          item = temp0 + "\n" + temp1;
        }
      }
      if (first) {
        ret += item;
        first = false;
      } else {
        ret += "\n" + item;
      }
    }
    return ret;
  }

  @Override
  public void update(Object o) throws IllegalArgumentException {
    if (!(o instanceof AnimateOutput)) {
      throw new IllegalArgumentException("Require AnimaterOutput type.");
    }
    this.modelOutput = (AnimateOutput)o;
  }

  @Override
  public void updateFreq(int f) throws IllegalArgumentException {
    if (f <= 0) {
      throw new IllegalArgumentException("Speed should be greater than 0.");
    }
    this.tick = f;
  }
}