package animator.util;

import model.AnimateModel;
import model.AnimateModelImpl;
import model.Shapes;

/**
 * The implementation of AnimationBuilder interface.
 */
public final class AnimationBuilderImpl implements AnimationBuilder<AnimateModel> {
  private AnimateModel model;
  
  @Override
  public AnimateModel build() {
    this.model = new AnimateModelImpl();
    return this.model;
  }

  @Override
  public AnimationBuilder<AnimateModel> setBounds(int x, int y, int width, int height) {
    this.model.setBounds(x, y, width, height);
    return this;
  }

  @Override
  public AnimationBuilder<AnimateModel> declareShape(String name, String type) {
    if (type.equalsIgnoreCase("rectangle")) {
      this.model.addShape(Shapes.RECTANGLE, name);
    } else if (type.equalsIgnoreCase("ellipse")) {
      this.model.addShape(Shapes.OVAL, name);
    }
    return this;
  }

  @Override
  public AnimationBuilder<AnimateModel> addMotion(String name, int t1, int x1, int y1, int w1,
      int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
      int b2) {
    this.model.addAnimation(name, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2);
    return this;
  }
}