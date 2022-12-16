package model;

import model.intervallist.Element;
import model.intervallist.Interval;
import model.intervallist.IntervalList;
import model.intervallist.IntervalListImpl;
import model.intervallist.IntervalListTreeMapImpl;
import model.intervallist.SimpleElement;
import model.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents an abstract animate shape.
 */
public abstract class AbstractAnimateShape implements AnimateShape {
  protected Integer start;
  protected Integer end;
  protected Integer min;
  protected Integer max;
  protected IntervalList<AnimateTime, Attribute> colorList;
  protected IntervalList<AnimateTime, Attribute> posList;
  protected IntervalList<AnimateTime, Attribute> sizeList;
  protected String name;
 

  /**
   * The constructor of abstract animate shape.
   * 
   * @param name  shape name
   * @param start start time
   * @param end   end time
   * @param color initial color
   * @param point initial position
   * @throws IllegalArgumentException If (start, end) is not valid
   */
  public AbstractAnimateShape(String name, int start, int end, Color color, Position point)
      throws IllegalArgumentException {
    if (start < 0 || end < 0) {
      throw new IllegalArgumentException("Border key should be greater than or equal to 0.");
    }
    if (start > end) {
      throw new IllegalArgumentException("Shape start time < end time.");
    }
    this.start = start;
    this.end = end;
    this.min = start;
    this.max = start;
    this.colorList = new IntervalListImpl<>();
    this.posList = new IntervalListImpl<>();
    this.sizeList = new IntervalListImpl<>();
    AnimateTime t0 = new AnimateTimeInt(start);
    colorList.insertInterval(t0, color, t0, color);
    posList.insertInterval(t0, point, t0, point);
    this.name = name;
  }
  
  /**
   * The constructor of abstract animate shape.
   * 
   * @param name the shape name
   */
  public AbstractAnimateShape(String name) {
    this.name = name;
    this.start = null;
    this.end = null;
    this.min = null;
    this.max = null;
    this.colorList = new IntervalListTreeMapImpl<>();
    this.posList = new IntervalListTreeMapImpl<>();
    this.sizeList = new IntervalListTreeMapImpl<>();
  }

  /**
   * Helper method for inserting interval to IntervalList.
   * 
   * @param a interval start time
   * @param x interval start attribute
   * @param b interval end time
   * @param y interval end attribute
   * @throws IllegalArgumentException If a < start || b > end or list insertion
   *                                  causes exception
   */
  protected void addToList(int a, Attribute x, int b, Attribute y,
      IntervalList<AnimateTime, Attribute> list) throws IllegalArgumentException {
    if (start != null && end != null) {
      if (a < start || b > end) {
        throw new IllegalArgumentException("Start key or end key is out of range.");
      }
    }
    AnimateTime t0 = new AnimateTimeInt(a);
    AnimateTime t1 = new AnimateTimeInt(b);
    list.insertInterval(t0, x, t1, y);
    if (min == null) {
      min = a;
      max = b;
    } else {
      if (a < min) {
        min = a;
      }
      if (b > max) {
        max = b;
      }
    }
  }

  @Override
  public void addAttributeChange(int a, Attribute x, int b, Attribute y)
      throws IllegalArgumentException {
    if (!checkAttributes(x, y)) {
      throw new IllegalArgumentException("Wrong attribute type.");
    }
    if (x instanceof Color) {
      addToList(a, x, b, y, colorList);
    } else if (x instanceof Position) {
      addToList(a, x, b, y, posList);
    } else if (x instanceof Size) {
      addToList(a, x, b, y, sizeList);
    }
  }

  @Override
  public void setStart(int a) throws IllegalArgumentException {
    if (a > min) {
      throw new IllegalArgumentException("Set start affect existing intevals.");
    }
    if (a < 0) {
      throw new IllegalArgumentException("Time should be greater than or equal to 0.");
    }
    start = a;
  }

  @Override
  public void setEnd(int b) throws IllegalArgumentException {
    if (b < max) {
      throw new IllegalArgumentException("Set end affect existing intervals.");
    }
    if (b < 0) {
      throw new IllegalArgumentException("Time should be greater than or equal to 0.");
    }
    end = b;
  }

  /**
   * This class will transform the results from IntervalList.generateSeq() to
   * interval list. 
   * It would make a deep copy of the IntervalList.
   * It would also handle situations: start is less than min 
   * or end is greater than max.
   * 
   * @param list the element list generated from IntervalList.generateSeq()
   * @return processed interval list
   */
  private List<Interval<AnimateTime, Attribute>> copyIntervals(
      List<Element<AnimateTime, Attribute>> arr) {
    if (arr.size() == 0) {
      return new ArrayList<>();
    }
    if (start != null || min != null) {
      if (min == null || (start != null && start < min)) {
        min = start;
      }
      if (min < arr.get(0).getKey().getVal()) {
        arr.add(0, new SimpleElement<AnimateTime, Attribute>(new AnimateTimeInt(min),
            arr.get(0).getVal().clone()));
      }
    }
    if (end != null || max != null) {
      if (max == null || (end != null && end > max)) {
        max = end;
      }
      if (max > arr.get(arr.size() - 1).getKey().getVal()) {
        arr.add(new SimpleElement<AnimateTime, Attribute>(new AnimateTimeInt(max),
            arr.get(arr.size() - 1).getVal().clone()));
      }
    }
    List<Element<AnimateTime, Attribute>> arr2 = new ArrayList<>();
    for (int i = 0; i < arr.size(); i++) {
      if (i == 0 || i == arr.size() - 1) {
        arr2.add(arr.get(i));
      } else {
        Element<AnimateTime, Attribute> cur = arr.get(i);
        Element<AnimateTime, Attribute> prev = arr2.get(arr2.size() - 1);
        Element<AnimateTime, Attribute> next = arr.get(i + 1);
        if (cur.getVal().equals(prev.getVal()) && cur.getVal().equals(next.getVal())) {
          continue;
        }
        arr2.add(cur);
      }
    }
    
    List<Interval<AnimateTime, Attribute>> ret = new ArrayList<>();
    for (int i = 0; i < arr2.size() - 1; i++) {
      SimpleElement<AnimateTime, Attribute> first = 
          new SimpleElement<>(arr2.get(i).getKey().clone(), arr2.get(i).getVal().clone());
      SimpleElement<AnimateTime, Attribute> second = 
          new SimpleElement<>(arr2.get(i + 1).getKey().clone(), arr2.get(i + 1).getVal().clone());
      Interval<AnimateTime, Attribute> pair = new Interval<>(first, second);
      ret.add(pair);
    }
    return ret;
  }

  @Override
  public List<Interval<AnimateTime, Attribute>> getSeq() {
    List<Interval<AnimateTime, Attribute>> arr1 = copyIntervals(colorList.generateSeq());
    List<Interval<AnimateTime, Attribute>> arr2 = copyIntervals(posList.generateSeq());
    List<Interval<AnimateTime, Attribute>> arr3 = copyIntervals(sizeList.generateSeq());
    Map<String, List<Interval<AnimateTime, Attribute>>> map = new HashMap<>();
    map.put("arr1", arr1);
    map.put("arr2", arr2);
    map.put("arr3", arr3);
    List<Interval<AnimateTime, Attribute>> ret = new ArrayList<>();
    List<Element<String, Interval<AnimateTime, Attribute>>> res = Utils.mergeMulti(map);
    for (Element<String, Interval<AnimateTime, Attribute>> ele : res) {
      ret.add(ele.getVal());
    }
    return ret;
  }
  
  @Override
  public ShapeOutput generateOutputShape() {
    List<Interval<AnimateTime, Attribute>> arr1 = copyIntervals(colorList.generateSeq());
    List<Interval<AnimateTime, Attribute>> arr2 = copyIntervals(posList.generateSeq());
    List<Interval<AnimateTime, Attribute>> arr3 = copyIntervals(sizeList.generateSeq());
    return new ShapeOutputImpl(getShape(), arr1, arr2, arr3);
  }

  @Override
  public String generateInitString(Integer f) {
    String ret = "";
    ret += "Name: " + this.name + "\n";
    ret += "Type: " + this.getTypeString() + "\n";
    ret += this.getTypeSpecificInitString() + "\n";
    int startTick = -1;
    int endTick = -1;
    if (start != null) {
      startTick = start;
    } else if (min != null) {
      startTick = min;
    }
    if (end != null) {
      endTick = end;
    } else if (max != null) {
      endTick = max;
    }
    if (f == null) {
      ret += "Appears at t=" + startTick + "\n";
      ret += "Disappears at t=" + endTick;
    } else {
      ret += "Appears at t=" + generateTimeString(startTick, f) + "\n";
      ret += "Disappears at t=" + generateTimeString(endTick, f);
    }
    return ret;
  }
  
  /**
   * Generate time string for ticks.
   * 
   * @param ticks the given tick number
   * @param ticks per second
   * @return time string
   */
  private String generateTimeString(int ticks, int f) {
    int res = (int)Math.round((double)ticks / f * 1000);
    return res + "ms";
  }

  @Override
  public String generateActionIntervalString(Interval<AnimateTime, Attribute> val, Integer f)
      throws IllegalArgumentException {
    Element<AnimateTime, Attribute> e0 = val.getFirst();
    Element<AnimateTime, Attribute> e1 = val.getSecond();
    Attribute a0 = e0.getVal();
    Attribute a1 = e1.getVal();
    AnimateTime t0 = e0.getKey();
    AnimateTime t1 = e1.getKey();
    int tt0 = (int)e0.getKey().getVal();
    int tt1 = (int)e1.getKey().getVal();
    if (!checkAttributes(a0, a1)) {
      throw new IllegalArgumentException("Wrong attribute type.");
    }
    String ret = "Shape " + this.name + " ";
    ret += a0.generateActionString(a0, a1);
    if (f == null) {
      ret += " from t=" + t0.toString() + " to t=" + t1.toString();
    } else {
      ret += " from t=" + generateTimeString(tt0, f) + " to t=" + generateTimeString(tt1, f);
    }
    return ret;
  }
}