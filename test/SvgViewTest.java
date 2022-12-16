import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import animator.view.SvgViewImpl;
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
 * Junit test case for svg view.
 */
public class SvgViewTest {
  private View svgView;
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
    this.svgView = new SvgViewImpl(sb);
    this.svgView.update(output);
    this.svgView.display();
    assertEquals("<?xml version=\"1.0\" standalone=\"no\"?>\n"
        + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \n"
        + "  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" + "<svg viewBox=\"0 0 0 0\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n" + "\n" + "</svg>",
        sb.toString());
  }

  /**
   * Test display when no animation is add.
   */
  @Test
  public void TestDisplayNoAnimation() {
    model.addShape(Shapes.RECTANGLE, "R");
    model.addShape(Shapes.OVAL, "C");
    StringBuilder sb = new StringBuilder();
    AnimateOutput output = model.generateOutput(true);
    this.svgView = new SvgViewImpl(sb);
    this.svgView.update(output);
    this.svgView.display();
    String e0 = "<?xml version=\"1.0\" standalone=\"no\"?>\n"
        + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \n"
        + "  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" + "<svg viewBox=\"0 0 0 0\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n" + "\n\n" + "</svg>";
    assertEquals(e0, sb.toString());
  }

  /**
   * Test display.
   */
  @Test
  public void testDisplay() {
    model.setBounds(0, 0, 1000, 1000);
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
    this.svgView = new SvgViewImpl(sb);
    this.svgView.update(output);
    this.svgView.display();
    String e0 = "<?xml version=\"1.0\" standalone=\"no\"?>\n"
        + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \n"
        + "  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
        + "<svg viewBox=\"0 0 1000 1000\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n"
        + "  <rect  x=\"200\" y=\"200\" width=\"50\" height=\"100\"\n"
        + "        fill=\"rgb(1,0,0)\"  visibility=\"hidden\">\n"
        + "    <animate attributeName=\"visibility\" attributeType=\"XML\"\n"
        + "             begin=\"0ms\" dur=\"1000ms\" "
        + "fill=\"freeze\" from=\"hidden\" to=\"visible\" />\n"
        + "    <animate attributeName=\"fill\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"99000ms\" "
        + "fill=\"freeze\" from=\"rgb(1,0,0)\" to=\"rgb(1,0,0)\" />\n"
        + "    <animate attributeName=\"x\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"99000ms\" "
        + "fill=\"freeze\" from=\"200\" to=\"200\" />\n"
        + "    <animate attributeName=\"y\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"99000ms\" "
        + "fill=\"freeze\" from=\"200\" to=\"200\" />\n"
        + "    <animate attributeName=\"width\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"99000ms\" fill=\"freeze\" from=\"50\" to=\"50\" />\n"
        + "    <animate attributeName=\"height\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"99000ms\" "
        + "fill=\"freeze\" from=\"100\" to=\"100\" />\n"
        + "  </rect>\n" + "  <ellipse  cx=\"500\" cy=\"100\" rx=\"60\" ry=\"30\"\n"
        + "        fill=\"rgb(0,0,1)\"  visibility=\"hidden\">\n"
        + "    <animate attributeName=\"visibility\" attributeType=\"XML\"\n"
        + "             begin=\"0ms\" dur=\"6000ms\" "
        + "fill=\"freeze\" from=\"hidden\" to=\"visible\" />\n"
        + "    <animate attributeName=\"fill\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" "
        + "fill=\"freeze\" from=\"rgb(0,0,1)\" to=\"rgb(0,0,1)\" />\n"
        + "    <animate attributeName=\"cx\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" "
        + "fill=\"freeze\" from=\"500\" to=\"500\" />\n"
        + "    <animate attributeName=\"cy\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" "
        + "fill=\"freeze\" from=\"100\" to=\"100\" />\n"
        + "    <animate attributeName=\"rx\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" fill=\"freeze\" from=\"60\" to=\"60\" />\n"
        + "    <animate attributeName=\"ry\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" fill=\"freeze\" from=\"30\" to=\"30\" />\n"
        + "  </ellipse>\n" + "</svg>";
    assertEquals(e0, sb.toString());

    // Shape R moves from (200.0,200.0) to (1000.0,1000.0) from t=10 to t=50
    Position p2 = new Position2d(new LengthDouble(1000.0), new LengthDouble(1000.0));
    model.addAnimation("R", 10, p0, 20, p2);
    output = model.generateOutput(true);
    this.svgView.update(output);
    sb.setLength(0);
    this.svgView.display();
    String e1 = "<?xml version=\"1.0\" standalone=\"no\"?>\n"
        + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \n"
        + "  \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
        + "<svg viewBox=\"0 0 1000 1000\"\n"
        + "     xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">\n"
        + "  <rect  x=\"200\" y=\"200\" width=\"50\" height=\"100\"\n"
        + "        fill=\"rgb(1,0,0)\"  visibility=\"hidden\">\n"
        + "    <animate attributeName=\"visibility\" attributeType=\"XML\"\n"
        + "             begin=\"0ms\" dur=\"1000ms\" "
        + "fill=\"freeze\" from=\"hidden\" to=\"visible\" />\n"
        + "    <animate attributeName=\"fill\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"99000ms\" "
        + "fill=\"freeze\" from=\"rgb(1,0,0)\" to=\"rgb(1,0,0)\" />\n"
        + "    <animate attributeName=\"x\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"9000ms\" "
        + "fill=\"freeze\" from=\"200\" to=\"200\" />\n"
        + "    <animate attributeName=\"y\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"9000ms\" "
        + "fill=\"freeze\" from=\"200\" to=\"200\" />\n"
        + "    <animate attributeName=\"x\" attributeType=\"XML\"\n"
        + "             begin=\"10000ms\" dur=\"10000ms\" "
        + "fill=\"freeze\" from=\"200\" to=\"1000\" />\n"
        + "    <animate attributeName=\"y\" attributeType=\"XML\"\n"
        + "             begin=\"10000ms\" dur=\"10000ms\" "
        + "fill=\"freeze\" from=\"200\" to=\"1000\" />\n"
        + "    <animate attributeName=\"x\" attributeType=\"XML\"\n"
        + "             begin=\"20000ms\" dur=\"80000ms\" "
        + "fill=\"freeze\" from=\"1000\" to=\"1000\" />\n"
        + "    <animate attributeName=\"y\" attributeType=\"XML\"\n"
        + "             begin=\"20000ms\" dur=\"80000ms\" "
        + "fill=\"freeze\" from=\"1000\" to=\"1000\" />\n"
        + "    <animate attributeName=\"width\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"99000ms\" fill=\"freeze\" from=\"50\" to=\"50\" />\n"
        + "    <animate attributeName=\"height\" attributeType=\"XML\"\n"
        + "             begin=\"1000ms\" dur=\"99000ms\" "
        + "fill=\"freeze\" from=\"100\" to=\"100\" />\n"
        + "  </rect>\n" + "  <ellipse  cx=\"500\" cy=\"100\" rx=\"60\" ry=\"30\"\n"
        + "        fill=\"rgb(0,0,1)\"  visibility=\"hidden\">\n"
        + "    <animate attributeName=\"visibility\" attributeType=\"XML\"\n"
        + "             begin=\"0ms\" dur=\"6000ms\" "
        + "fill=\"freeze\" from=\"hidden\" to=\"visible\" />\n"
        + "    <animate attributeName=\"fill\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" "
        + "fill=\"freeze\" from=\"rgb(0,0,1)\" to=\"rgb(0,0,1)\" />\n"
        + "    <animate attributeName=\"cx\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" "
        + "fill=\"freeze\" from=\"500\" to=\"500\" />\n"
        + "    <animate attributeName=\"cy\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" "
        + "fill=\"freeze\" from=\"100\" to=\"100\" />\n"
        + "    <animate attributeName=\"rx\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" fill=\"freeze\" from=\"60\" to=\"60\" />\n"
        + "    <animate attributeName=\"ry\" attributeType=\"XML\"\n"
        + "             begin=\"6000ms\" dur=\"94000ms\" fill=\"freeze\" from=\"30\" to=\"30\" />\n"
        + "  </ellipse>\n" + "</svg>";
    assertEquals(e1, sb.toString());

    output = model.generateOutput(true);
    this.svgView.updateFreq(1000);
    this.svgView.update(output);
    sb.setLength(0);
    this.svgView.display();
    String e2 = e1.replace("000ms", "ms");
    assertEquals(e2, sb.toString());
  }

  /**
   * Test invalid appendable.
   */
  @Test
  public void testInvalidAppendable() {
    AnimateOutput output = model.generateOutput(true);
    this.svgView = new SvgViewImpl(new FailingAppendable());
    this.svgView.update(output);
    try {
      this.svgView.display();
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
    this.svgView = new SvgViewImpl(sb);
    this.svgView.update(output);
    try {
      this.svgView.updateFreq(0);
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
    try {
      this.svgView.updateFreq(-1);
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
    this.svgView = new SvgViewImpl(sb);
    try {
      this.svgView.update(1);
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }
}
