package model;

import model.intervallist.Element;
import model.intervallist.Interval;
import model.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a concrete animation model.
 */
public class AnimateModelImpl implements AnimateModel {
  private Map<String, AnimateShape> shapeMap;
  private List<String> names;
  private int[] bounds;
  private Integer animateStart;
  private Integer animateEnd;

  /**
   * The constructor of AnmianteModelImpl.
   */
  public AnimateModelImpl() {
    this.shapeMap = new HashMap<>();
    this.bounds = new int[4];
    this.names = new ArrayList<>();
    this.animateStart = null;
    this.animateEnd = null;
    Arrays.fill(bounds, 0);
  }

  @Override
  public void addShape(Shapes type, String name, int start, int end, Color color, Position point,
      Size size) throws IllegalArgumentException {
    if (shapeMap.containsKey(name)) {
      throw new IllegalArgumentException("Shape name already exists.");
    }
    switch (type) {
      case RECTANGLE:
        AnimateShape shape1 = new AnimateRectangle(name, start, end, color, point, size);
        shapeMap.put(name, shape1);
        names.add(name);
        updateStartEnd(start, end);
        break;
      case OVAL:
        AnimateShape shape2 = new AnimateOval(name, start, end, color, point, size);
        shapeMap.put(name, shape2);
        names.add(name);
        updateStartEnd(start, end);
        break;
      default:
        break;
    }
  }
  
  @Override
  public void addShape(Shapes type, String name) throws IllegalArgumentException {
    if (shapeMap.containsKey(name)) {
      throw new IllegalArgumentException("Shape name already exists.");
    }
    switch (type) {
      case RECTANGLE:
        AnimateShape shape1 = new AnimateRectangle(name);
        shapeMap.put(name, shape1);
        names.add(name);
        break;
      case OVAL:
        AnimateShape shape2 = new AnimateOval(name);
        shapeMap.put(name, shape2);
        names.add(name);
        break;
      default:
        break;
    }
  }
  
  /**
   * Update the start and end if conditions meet.
   * 
   * @param start the given start
   * @param end the given end
   */
  private void updateStartEnd(int start, int end) {
    if (this.animateStart == null || start < this.animateStart) {
      this.animateStart = start;
    } 
    if (this.animateEnd == null || end > this.animateEnd) {
      this.animateEnd = end;
    }
  }

  @Override
  public void addAnimation(String name, int a, Attribute x, int b, Attribute y)
      throws IllegalArgumentException {
    if (!shapeMap.containsKey(name)) {
      throw new IllegalArgumentException("Shape name does not exist.");
    }
    AnimateShape shape = shapeMap.get(name);
    shape.addAttributeChange(a, x, b, y);
    updateStartEnd(a, b);
  }
  
  @Override
  public void addAnimation(String name, int t1, int x1, int y1, int w1, int h1, int r1, int g1,
      int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2)
      throws IllegalArgumentException {
    if (!shapeMap.containsKey(name)) {
      throw new IllegalArgumentException("Shape name does not exist.");
    }
    ColorRgb c1 = new ColorRgb(new LengthDouble(r1), new LengthDouble(g1), new LengthDouble(b1));
    ColorRgb c2 = new ColorRgb(new LengthDouble(r2), new LengthDouble(g2), new LengthDouble(b2));
    addAnimation(name, t1, c1, t2, c2);
    AnimateShape shape = shapeMap.get(name);
    if (shape.getShape() == Shapes.RECTANGLE) {
      Position2d p1 = new Position2d(new LengthDouble(x1), new LengthDouble(y1));
      Position2d p2 = new Position2d(new LengthDouble(x2), new LengthDouble(y2));
      addAnimation(name, t1, p1, t2, p2);
      SizeRectangle s1 = new SizeRectangle(new LengthDouble(w1), new LengthDouble(h1));
      SizeRectangle s2 = new SizeRectangle(new LengthDouble(w2), new LengthDouble(h2));
      addAnimation(name, t1, s1, t2, s2);
    } else if (shape.getShape() == Shapes.OVAL) {
      int rx1 = w1 / 2;
      int ry1 = h1 / 2;
      int rx2 = w2 / 2;
      int ry2 = h2 / 2;
      SizeOval s1 = new SizeOval(new LengthDouble(rx1), new LengthDouble(ry1));
      SizeOval s2 = new SizeOval(new LengthDouble(rx2), new LengthDouble(ry2));
      addAnimation(name, t1, s1, t2, s2);
      int cx1 = x1 + rx1;
      int cy1 = y1 + ry1;
      int cx2 = x2 + rx2;
      int cy2 = y2 + ry2;
      Position2d p1 = new Position2d(new LengthDouble(cx1), new LengthDouble(cy1));
      Position2d p2 = new Position2d(new LengthDouble(cx2), new LengthDouble(cy2));
      addAnimation(name, t1, p1, t2, p2);
    }
  }

  /**
   * Generate initial shape strings (the constructor string).
   * 
   * @param sMap input map with name as key and sequence as value
   * @param f the given frequency
   * @return the constructor string of all the shapes in this animation
   */
  private String generateConstructorString(
      Map<String, List<Interval<AnimateTime, Attribute>>> sMap, Integer f) {
    String ret = "";
    List<String> names = new ArrayList<>(sMap.keySet());
    Collections.sort(names, (a, b) -> {
      AnimateTime t1 = sMap.get(a).get(0).getFirst().getKey();
      AnimateTime t2 = sMap.get(b).get(0).getFirst().getKey();
      return t1.compareTo(t2);
    });
    for (String name : names) {
      ret += shapeMap.get(name).generateInitString(f) + "\n\n";
    }
    return ret.trim();
  }

  @Override
  public void setBounds(int x, int y, int width, int height) throws IllegalArgumentException {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException();
    }
    bounds[0] = x;
    bounds[1] = y;
    bounds[2] = width;
    bounds[3] = height;
  }

  /**
   * Generate animate output shapes to be imported to view.
   * 
   * @param started whether the animation has started or not
   * @return a list of generated output shapes
   */
  private List<ShapeOutput> generateOutputShapes() {
    List<ShapeOutput> ret = new ArrayList<>();
    for (String name: names) {
      ret.add(shapeMap.get(name).generateOutputShape());
    }
    return ret;
  }

  @Override
  public int[] getBounds() {
    int[] ret = new int[4];
    for (int i = 0; i < 4; i++) {
      ret[i] = bounds[i];
    }
    return ret;
  }

  @Override
  public AnimateOutputImpl generateOutput(boolean started) {
    return new AnimateOutputImpl(names, generateOutputShapes(), bounds, new int[]{
        animateStart == null ? 0 : animateStart, 
        animateEnd == null ? 0 : animateEnd}, started, this);
  }


  @Override
  public String toString() {
    String ret = "Shapes:\n";
    Map<String, List<Interval<AnimateTime, Attribute>>> sMap = new HashMap<>();
    for (String name : shapeMap.keySet()) {
      List<Interval<AnimateTime, Attribute>> list = shapeMap.get(name).getSeq();
      sMap.put(name, list);
    }
    ret += generateConstructorString(sMap, null) + "\n\n";
    List<Element<String, Interval<AnimateTime, Attribute>>> sList = Utils.mergeMulti(sMap);
    for (Element<String, Interval<AnimateTime, Attribute>> t : sList) {
      String name = t.getKey();
      AnimateShape shape = shapeMap.get(name);
      Interval<AnimateTime, Attribute> val = t.getVal();
      Attribute a0 = val.getFirst().getVal();
      Attribute a1 = val.getSecond().getVal();
      if (!a0.equals(a1)) {
        ret += shape.generateActionIntervalString(val, null) + "\n";
      }
    }
    return ret.trim();
  }
  
  @Override
  public String toString(int f) throws IllegalArgumentException {
    if (f <= 0) {
      throw new IllegalArgumentException();
    }
    String ret = "Shapes:\n";
    Map<String, List<Interval<AnimateTime, Attribute>>> sMap = new HashMap<>();
    for (String name : shapeMap.keySet()) {
      List<Interval<AnimateTime, Attribute>> list = shapeMap.get(name).getSeq();
      sMap.put(name, list);
    }
    ret += generateConstructorString(sMap, f) + "\n\n";
    List<Element<String, Interval<AnimateTime, Attribute>>> sList = Utils.mergeMulti(sMap);
    for (Element<String, Interval<AnimateTime, Attribute>> t : sList) {
      String name = t.getKey();
      AnimateShape shape = shapeMap.get(name);
      Interval<AnimateTime, Attribute> val = t.getVal();
      Attribute a0 = val.getFirst().getVal();
      Attribute a1 = val.getSecond().getVal();
      if (!a0.equals(a1)) {
        ret += shape.generateActionIntervalString(val, f) + "\n";
      }
    }
    return ret.trim();
  }
}