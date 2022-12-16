import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.AnimateModel;
import model.AnimateModelImpl;
import model.Color;
import model.ColorRgb;
import model.LengthDouble;
import model.Position;
import model.Position2d;
import model.Shapes;
import model.Size;
import model.SizeOval;
import model.SizeRectangle;

/**
 * JUnit test case for AnimateModel.
 */
public class AnimateModelTest {
  private AnimateModel model;

  /**
   * Set up test variable.
   */
  @Before
  public void setup() {
    this.model = new AnimateModelImpl();
  }

  /**
   * Test invalid attributes for current use case interface.
   */
  @Test
  public void testInvalidAttributes() {
    try {
      new ColorRgb(new LengthDouble(0.0), null, null);
      fail("Should throw excptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      new ColorRgb(new LengthDouble(0.0), new LengthDouble(-1.0), new LengthDouble(1.0));
      fail("Should throw excptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      new Position2d(null, new LengthDouble(-1.0));
      fail("Should throw excptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      new SizeRectangle(null, new LengthDouble(100.0));
      fail("Should throw excptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      new SizeRectangle(new LengthDouble(-100.0), new LengthDouble(100.0));
      fail("Should throw excptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      new SizeOval(null, new LengthDouble(100.0));
      fail("Should throw excptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      new SizeOval(new LengthDouble(-100.0), new LengthDouble(100.0));
      fail("Should throw excptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test add multiple animations for one attribute.
   */
  @Test
  public void testAddMultiAnmationsForOneAttribute() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 100, c1, p1, s1);

    model.addAnimation("R", 10, p0, 50, p0);
    String e0 = "Shapes:\n" + "Name: R\n" + "Type: rectangle\n"
        + "Min corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n"
        + "Appears at t=1\nDisappears at t=100\n\n" + "Name: C\n" + "Type: oval\n"
        + "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6\n" + "Disappears at t=100";
    assertEquals(e0, model.toString());

    Position p2 = new Position2d(new LengthDouble(100.0), new LengthDouble(100.0));
    model.addAnimation("R", 50, p0, 60, p1);
    model.addAnimation("R", 70, p2, 90, p1);
    Size s4 = new SizeOval(new LengthDouble(0.0), new LengthDouble(100.0));
    model.addAnimation("C", 65, s1, 70, s4);

    String e1 = "Shape R moves from (200.0,200.0) to (500.0,100.0) from t=50 to t=60\n"
        + "Shape R moves from (500.0,100.0) to (100.0,100.0) from t=60 to t=70\n"
        + "Shape C scales from X radius: 60.0, Y radius: 30.0 to X radius: 0.0, "
        + "Y radius: 100.0 from t=65 to t=70\n"
        + "Shape R moves from (100.0,100.0) to (500.0,100.0) from t=70 to t=90";
    assertEquals(e0 + "\n\n" + e1, model.toString());
  }

  /**
   * Test add shape fail.
   */
  @Test
  public void testAddShapeFail() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 100, c1, p1, s1);
    try {
      Color c2 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
      Position p2 = new Position2d(new LengthDouble(100.0), new LengthDouble(100.0));
      Size s2 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
      model.addShape(Shapes.OVAL, "C", 6, 100, c2, p2, s2);
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      Color c3 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
      Position p3 = new Position2d(new LengthDouble(100.0), new LengthDouble(100.0));
      Size s3 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
      model.addShape(Shapes.OVAL, "R", 6, 100, c3, p3, s3);
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      Color c3 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
      Position p3 = new Position2d(new LengthDouble(100.0), new LengthDouble(100.0));
      Size s3 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
      model.addShape(Shapes.OVAL, "negTime", -6, 100, c3, p3, s3);
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      Color c3 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
      Position p3 = new Position2d(new LengthDouble(100.0), new LengthDouble(100.0));
      Size s3 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
      model.addShape(Shapes.OVAL, "reverseInterval", 100, 6, c3, p3, s3);
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test add animation fail cases.
   */
  @Test
  public void testAddAnimationFail() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 100, c1, p1, s1);

    Color c2 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(0.0));

    try {
      model.addAnimation("C", 100, c1, 101, c2);
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      model.addAnimation("C", -1, c1, 20, c2);
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      model.addAnimation("C", 50, c1, 30, c2);
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    try {
      model.addAnimation("C", 30, p1, 50, c2);
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }

    model.addAnimation("C", 7, c1, 20, c2);

    try {
      model.addAnimation("C", 19, c2, 50, c1);
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * This is a sample test. Add shape and do simple animation.
   */
  @Test
  public void testSimpleAddAndAnimation() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 100, c1, p1, s1);

    String e0 = "Shapes:\n" + "Name: R\n" + "Type: rectangle\n"
        + "Min corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n"
        + "Appears at t=1\nDisappears at t=100\n\n" + "Name: C\n" + "Type: oval\n"
        + "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6\n" + "Disappears at t=100";
    assertEquals(e0, model.toString());

    // Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50
    Position p2 = new Position2d(new LengthDouble(300.0), new LengthDouble(300.0));
    model.addAnimation("R", 10, p0, 50, p2);

    String e1 = "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50";
    assertEquals(e0 + "\n\n" + e1, model.toString());

    Color c2 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(0.0));
    model.addAnimation("C", 7, c1, 20, c2);

    String e2 = "Shape C changes color from (0.0,0.0,1.0) to (0.0,0.0,0.0) from t=7 to t=20";
    assertEquals(e0 + "\n\n" + e2 + "\n" + e1, model.toString());

    Size s3 = new SizeRectangle(new LengthDouble(100.0), new LengthDouble(100.0));
    model.addAnimation("R", 5, s0, 40, s3);

    String e3 = "Shape R scales from Width: 50.0, "
        + "Height: 100.0 to Width: 100.0, Height: 100.0 from t=5 to t=40";
    assertEquals(e0 + "\n\n" + e3 + "\n" + e2 + "\n" + e1, model.toString());

    Size s4 = new SizeOval(new LengthDouble(0.0), new LengthDouble(100.0));
    model.addAnimation("C", 8, s1, 30, s4);

    String e4 = "Shape C scales from X radius: 60.0, Y radius: 30.0 to X radius: 0.0, "
        + "Y radius: 100.0 from t=8 to t=30";
    assertEquals(e0 + "\n\n" + e3 + "\n" + e2 + "\n" + e4 + "\n" + e1, model.toString());
  }
}