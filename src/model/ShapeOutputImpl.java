package model;

import java.util.List;

import model.intervallist.Interval;

/**
 * The implementation of output shape. If tick is not in range, no attribute
 * would be exported.
 */
public class ShapeOutputImpl implements ShapeOutput {
  private Shapes shape;
  private List<Interval<AnimateTime, Attribute>> colorList;
  private List<Interval<AnimateTime, Attribute>> posList;
  private List<Interval<AnimateTime, Attribute>> sizeList;
  private int[] colorIndex;
  private int[] posIndex;
  private int[] sizeIndex;

  /**
   * The constructor of OutputShapeImpl.
   * 
   * @param shape     the shape type
   * @param colorList the list of color intervals
   * @param posList   the list of position intervals
   * @param sizeList  the list of size intervals
   */
  public ShapeOutputImpl(Shapes shape, List<Interval<AnimateTime, Attribute>> colorList,
      List<Interval<AnimateTime, Attribute>> posList,
      List<Interval<AnimateTime, Attribute>> sizeList) {
    this.shape = shape;
    this.colorList = colorList;
    this.posList = posList;
    this.sizeList = sizeList;
    this.colorIndex = new int[] { 0 };
    this.posIndex = new int[] { 0 };
    this.sizeIndex = new int[] { 0 };
  }

  @Override
  public void reset() {
    // corresponding to restart button
    this.colorIndex = new int[] { 0 };
    this.posIndex = new int[] { 0 };
    this.sizeIndex = new int[] { 0 };
  }

  /**
   * Helper function for get attribute and change index.
   * 
   * @param tick  current tick
   * @param index current index
   * @param list  attribute interval list
   * @return the attribute (can be null)
   */
  private Attribute getAttribute(int tick, int[] index,
      List<Interval<AnimateTime, Attribute>> list) {
    if (list.size() == 0) {
      return null;
    }
    if (index[0] >= list.size()) {
      return null;
    }
    if (tick < list.get(0).getFirst().getKey().getVal()) {
      return null;
    }
    while (tick > list.get(index[0]).getSecond().getKey().getVal()) {
      index[0] = index[0] + 1;
      if (index[0] == list.size()) {
        break;
      }
    }
    if (index[0] >= list.size()) {
      return null;
    }
    Interval<AnimateTime, Attribute> interval = list.get(index[0]);
    int a = (int) interval.getFirst().getKey().getVal();
    Attribute x = interval.getFirst().getVal();
    int b = (int) interval.getSecond().getKey().getVal();
    Attribute y = interval.getSecond().getVal();
    return x.getMidAttribute(a, x, b, y, tick);
  }

  @Override
  public Shapes getShape() {
    return this.shape;
  }

  @Override
  public Attribute getCurColor(int tick) {
    return getAttribute(tick, colorIndex, colorList);
  }

  @Override
  public Attribute getCurPos(int tick) {
    return getAttribute(tick, posIndex, posList);
  }

  @Override
  public Attribute getCurSize(int tick) {
    return getAttribute(tick, sizeIndex, sizeList);
  }

  @Override
  public List<Interval<AnimateTime, Attribute>> getColorList() {
    return this.colorList;
  }

  @Override
  public List<Interval<AnimateTime, Attribute>> getPosList() {
    return this.posList;
  }

  @Override
  public List<Interval<AnimateTime, Attribute>> getSizeList() {
    return this.sizeList;
  }
}