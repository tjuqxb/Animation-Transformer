import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import animator.view.TextViewImpl;
import animator.view.View;
import model.AnimateModel;
import model.AnimateModelImpl;
import model.AnimateOutput;
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
 * JUnit test case for TextView.
 */
public class TextViewTest {
  private View textView;
  private AnimateModel model;
  
  /**
   * Set up test variable.
   */
  @Before
  public void setup() {
    this.model = new AnimateModelImpl();
  }
  
  /**
   * Test empty model.
   */
  @Test
  public void testEmptyModel() {
    AnimateOutput output = model.generateOutput(true);
    
    StringBuilder sb = new StringBuilder();
    this.textView = new TextViewImpl(sb);
    this.textView.update(output);
    this.textView.display();
    assertEquals("Shapes:", sb.toString());
  }
  
  /**
   * Test display without tick setting.
   */
  @Test 
  public void testDisplay() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 100, c1, p1, s1);
    
    StringBuilder sb = new StringBuilder();
    AnimateOutput output = model.generateOutput(true);
    this.textView = new TextViewImpl(sb);
    this.textView.update(output);
    this.textView.display();
    
    String e0 = "Shapes:\n" + "Name: R\n" + "Type: rectangle\n"
        + "Min corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n"
        + "Appears at t=1\nDisappears at t=100\n\n" + "Name: C\n" + "Type: oval\n"
        + "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=6\n" + "Disappears at t=100";
    assertEquals(e0, sb.toString());
    
    Position p2 = new Position2d(new LengthDouble(300.0), new LengthDouble(300.0));
    model.addAnimation("R", 10, p0, 50, p2);
    output = model.generateOutput(true);
    this.textView.update(output);
    sb.setLength(0);
    this.textView.display();
    
    String e1 = "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50";
    assertEquals(e0 + "\n\n" + e1, sb.toString());
    
    Color c2 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(0.0));
    model.addAnimation("C", 7, c1, 100, c2);
    output = model.generateOutput(true);
    this.textView.update(output);
    sb.setLength(0);
    this.textView.display();
    
    String e2 = "Shape C changes color from (0.0,0.0,1.0) to (0.0,0.0,0.0) from t=7 to t=100";
    assertEquals(e0 + "\n\n" + e2 + "\n" + e1, sb.toString());
  }
  
  /**
   * Test display with tick settings.
   */
  @Test
  public void testDisplayWithTickSetting() {
    Color c0 = new ColorRgb(new LengthDouble(1.0), new LengthDouble(0.0), new LengthDouble(0.0));
    Position p0 = new Position2d(new LengthDouble(200.0), new LengthDouble(200.0));
    Size s0 = new SizeRectangle(new LengthDouble(50.0), new LengthDouble(100.0));
    model.addShape(Shapes.RECTANGLE, "R", 1, 100, c0, p0, s0);

    Color c1 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(1.0));
    Position p1 = new Position2d(new LengthDouble(500.0), new LengthDouble(100.0));
    Size s1 = new SizeOval(new LengthDouble(60.0), new LengthDouble(30.0));
    model.addShape(Shapes.OVAL, "C", 6, 100, c1, p1, s1);
    
    StringBuilder sb = new StringBuilder();
    AnimateOutput output = model.generateOutput(true);
    this.textView = new TextViewImpl(sb);
    this.textView.updateFreq(50);
    this.textView.update(output);
    this.textView.display();
    String e0 = "Shapes:\n" + "Name: R\n" + "Type: rectangle\n"
        + "Min corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n"
        + "Appears at t=20ms\nDisappears at t=2000ms\n\n" + "Name: C\n" + "Type: oval\n"
        + "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n"
        + "Appears at t=120ms\n" + "Disappears at t=2000ms";
    assertEquals(e0, sb.toString());
    
    Position p2 = new Position2d(new LengthDouble(300.0), new LengthDouble(300.0));
    model.addAnimation("R", 10, p0, 50, p2);
    output = model.generateOutput(true);
    this.textView.update(output);
    sb.setLength(0);
    this.textView.display();
    
    String e1 = "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=200ms to t=1000ms";
    assertEquals(e0 + "\n\n" + e1, sb.toString());
    
    Color c2 = new ColorRgb(new LengthDouble(0.0), new LengthDouble(0.0), new LengthDouble(0.0));
    model.addAnimation("C", 7, c1, 100, c2);
    output = model.generateOutput(true);
    this.textView.update(output);
    sb.setLength(0);
    this.textView.display();
    
    String e2 = "Shape C changes color from (0.0,0.0,1.0) to (0.0,0.0,0.0) "
        + "from t=140ms to t=2000ms";
    assertEquals(e0 + "\n\n" + e2 + "\n" + e1, sb.toString());
  }
  
  /**
   * Test invalid appendable.
   */
  @Test
  public void testInvalidAppendable() {
    AnimateOutput output = model.generateOutput(true);
    this.textView = new TextViewImpl(new FailingAppendable());
    this.textView.update(output);
    try {
      this.textView.display();
    } catch (Exception e) {
      assertEquals(IllegalStateException.class, e.getClass());
    }
  }
  
  /**
   * Test invalid tick per second.
   */
  @Test
  public void testInvalidFrequency() {
    AnimateOutput output = model.generateOutput(true);
    StringBuilder sb = new StringBuilder();
    this.textView = new TextViewImpl(sb);
    this.textView.update(output);
    try {
      this.textView.updateFreq(0);
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
    try {
      this.textView.updateFreq(-1);
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }
  
  /**
   * Test invalid update object type.
   */
  @Test
  public void testInvlaidUpdateObject() {
    StringBuilder sb = new StringBuilder();
    this.textView = new TextViewImpl(sb);
    try {
      this.textView.update(1);
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
    
  }
}
