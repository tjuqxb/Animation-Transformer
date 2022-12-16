import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.AnimateFrame;
import model.AnimateModel;
import model.AnimateModelImpl;
import model.AnimateOutput;
import model.AnimateTime;
import model.Attribute;
import model.Color;
import model.ColorRgb;
import model.LengthDouble;
import model.Position;
import model.Position2d;
import model.ShapeOutput;
import model.Shapes;
import model.Size;
import model.SizeOval;
import model.SizeRectangle;
import model.intervallist.Interval;

/**
 * JUnit test for AnimateOutput.
 */
public class AnimateOutputTest {
  private AnimateModel model;


  /**
   * Set up test variable.
   */
  @Before
  public void setup() {
    this.model = new AnimateModelImpl();
  }

  /**
   * Test AnimateOutput initial state.
   */
  @Test
  public void testInitialState() {
    AnimateOutput output = this.model.generateOutput(true);
    assertTrue(output.getHasStarted());
    assertFalse(output.getIsPaused());
    assertFalse(output.getIsLooping());
    assertTrue(output.getIsFinished());

    output = this.model.generateOutput(false);
    assertFalse(output.getHasStarted());
    assertFalse(output.getIsPaused());
    assertFalse(output.getIsLooping());
    assertFalse(output.getIsFinished());
  }

  /**
   * Test play functions.
   */
  @Test
  public void testPlay() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 100, c1, p1, s1);

    AnimateOutput output = model.generateOutput(true);
    output.next();
    assertTrue(output.getHasStarted());
    assertFalse(output.getIsPaused());
    assertFalse(output.getIsLooping());
    assertFalse(output.getIsFinished());
    output.switchPlay();
    assertTrue(output.getHasStarted());
    assertTrue(output.getIsPaused());
    assertFalse(output.getIsLooping());
    assertFalse(output.getIsFinished());
    for (int i = 0; i < 200; i++) {
      output.next();
    }
    assertTrue(output.getHasStarted());
    assertTrue(output.getIsPaused());
    assertFalse(output.getIsLooping());
    assertFalse(output.getIsFinished());

    output.switchPlay();
    for (int i = 0; i < 200; i++) {
      output.next();
    }
    assertTrue(output.getHasStarted());
    assertFalse(output.getIsPaused());
    assertFalse(output.getIsLooping());
    assertTrue(output.getIsFinished());

    output.switchLooping();
    for (int i = 0; i < 200; i++) {
      output.next();
    }
    assertTrue(output.getHasStarted());
    assertFalse(output.getIsPaused());
    assertTrue(output.getIsLooping());
    assertFalse(output.getIsFinished());
  }

  /**
   * Test generate frame.
   */
  @Test
  public void testGenerateFrame() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 99, c1, p1, s1);

    AnimateOutput output = model.generateOutput(true);
    AnimateFrame frame = output.generateFrame();
    assertTrue(frame.hasStarted());
    assertFalse(frame.isPaused());
    assertFalse(frame.isLooping());
    assertFalse(frame.isFinished());
    List<Attribute[]> list = frame.getList();
    assertEquals(2, list.size());
    for (Attribute[] arr : list) {
      for (int j = 0; j < 3; j++) {
        assertEquals(null, arr[j]);
      }
    }
    HashSet<Attribute> set = new HashSet<>();
    output.next();
    frame = output.generateFrame();
    list = frame.getList();
    assertTrue(frame.hasStarted());
    assertFalse(frame.isPaused());
    assertFalse(frame.isLooping());
    assertFalse(frame.isFinished());
    assertEquals(2, list.size());
    for (Attribute[] arr : list) {
      for (int j = 0; j < 3; j++) {
        if (arr[j] != null) {
          set.add(arr[j]);
        }
      }
    }
    assertEquals(3, set.size());
    assertTrue(set.contains(c0));
    assertTrue(set.contains(p0));
    assertTrue(set.contains(s0));

    for (int i = 0; i < 5; i++) {
      output.next();
    }
    frame = output.generateFrame();
    list = frame.getList();
    set.clear();
    for (Attribute[] arr : list) {
      for (int j = 0; j < 3; j++) {
        if (arr[j] != null) {
          set.add(arr[j]);
        }
      }
    }
    assertEquals(6, set.size());
    assertTrue(set.contains(c0));
    assertTrue(set.contains(p0));
    assertTrue(set.contains(s0));
    assertTrue(set.contains(c1));
    assertTrue(set.contains(p1));
    assertTrue(set.contains(s1));

    // Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50
    Position p2 = new Position2d(new LengthDouble(300.0), new LengthDouble(300.0));
    model.addAnimation("R", 10, p0, 50, p2);
    output = model.generateOutput(true);
    for (int i = 0; i < 200; i++) {
      output.next();
    }
    frame = output.generateFrame();
    list = frame.getList();
    set.clear();
    for (Attribute[] arr : list) {
      for (int j = 0; j < 3; j++) {
        if (arr[j] != null) {
          set.add(arr[j]);
        }
      }
    }
    assertEquals(3, set.size());
    assertTrue(set.contains(c0));
    assertTrue(set.contains(p2));
    assertTrue(set.contains(s0));
  }

  /**
   * Test output shapes.
   */
  @Test
  public void outputShapesTest() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 100, c1, p1, s1);

    AnimateOutput output = model.generateOutput(true);
    assertTrue(output.getHasStarted());
    assertFalse(output.getIsPaused());
    assertFalse(output.getIsLooping());
    assertFalse(output.getIsFinished());

    List<String> names = output.getNames();
    assertEquals(2, names.size());
    assertEquals("R", names.get(0));
    assertEquals("C", names.get(1));

    List<ShapeOutput> shapes = output.getShapes();
    assertEquals(2, shapes.size());
    assertEquals(Shapes.RECTANGLE,shapes.get(0).getShape());
    assertEquals(Shapes.OVAL,shapes.get(1).getShape());
    assertEquals(1, output.getInterval()[0]);
    assertEquals(100, output.getInterval()[1]);

    String e0 = "Shapes:\n" + "Name: R\n" + "Type: rectangle\n"
        + "Min corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n"
        + "Appears at t=1\nDisappears at t=100\n\n" + "Name: C\n" + "Type: oval\n"
        + "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6\n" + "Disappears at t=100";
    assertEquals(e0, output.toString());
    ShapeOutput rectangle = shapes.get(0);
    ShapeOutput oval = shapes.get(1);
    assertEquals(1, rectangle.getColorList().size());
    assertEquals(1, oval.getColorList().size());

    // Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50
    Position p2 = new Position2d(new LengthDouble(300.0), new LengthDouble(300.0));
    model.addAnimation("R", 10, p0, 50, p2);
    output = model.generateOutput(true);
    shapes = output.getShapes();
    rectangle = shapes.get(0);
    oval = shapes.get(1);
    assertEquals(3, rectangle.getPosList().size());
    assertEquals(1, rectangle.getColorList().size());

    StringBuilder sb = new StringBuilder();
    List<Interval<AnimateTime, Attribute>> aList = output.getShapes().get(0).getPosList();
    for (Interval<AnimateTime, Attribute> element : aList) {
      sb.append(element.getFirst().getKey().getVal() + "\n");
      sb.append(element.getFirst().getVal().toString() + "\n");
      sb.append(element.getSecond().getKey().getVal() + "\n");
      sb.append(element.getSecond().getVal().toString() + "\n");
    }
    String e3 = sb.toString().trim();
    assertEquals("1.0\n"
        + "(200.0,200.0)\n"
        + "10.0\n"
        + "(200.0,200.0)\n"
        + "10.0\n"
        + "(200.0,200.0)\n"
        + "50.0\n"
        + "(300.0,300.0)\n"
        + "50.0\n"
        + "(300.0,300.0)\n"
        + "100.0\n"
        + "(300.0,300.0)", e3);

    String e1 = "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50";
    assertEquals(e0 + "\n\n" + e1, model.toString());

    Color c2 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(0.0));
    model.addAnimation("C", 7, c1, 100, c2);
    output = model.generateOutput(true);
    shapes = output.getShapes();
    rectangle = shapes.get(0);
    oval = shapes.get(1);
    assertEquals(1, oval.getPosList().size());
    assertEquals(2, oval.getColorList().size());

    String e2 = "Shape C changes color from (0.0,0.0,1.0) to (0.0,0.0,0.0) from t=7 to t=100";
    assertEquals(e0 + "\n\n" + e2 + "\n" + e1, model.toString());
  }

  /**
   * Test get bounds from output.
   */
  @Test
  public void testGetBounds() {
    model.setBounds(1, 2, 3, 4);
    AnimateOutput output = this.model.generateOutput(false);
    int[] bounds = output.getBounds();
    assertEquals(4, bounds.length);
    for (int i = 0; i < 4; i++) {
      assertEquals(i + 1, bounds[i]);
    }
  }

  /**
   * Test get start and end.
   */
  @Test
  public void testGetStartEnd() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 100, c1, p1, s1);

    AnimateOutput output = this.model.generateOutput(false);
    int[] interval = output.getInterval();
    assertEquals(1, interval[0]);
    assertEquals(100, interval[1]);

    this.model = new AnimateModelImpl();
    model.addShape(Shapes.RECTANGLE, "R");
    model.addShape(Shapes.OVAL, "C");
    Position p2 = new Position2d(new LengthDouble(300.0), new LengthDouble(300.0));
    model.addAnimation("R", 10, p0, 50, p2);
    output = model.generateOutput(true);
    interval = output.getInterval();
    assertEquals(10, interval[0]);
    assertEquals(50, interval[1]);
  }
}