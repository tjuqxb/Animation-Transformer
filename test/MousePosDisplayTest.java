import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import animator.controller.MousePosListener;
import animator.view.PlaybackView;

/**
 * A JUnit case for testing MouseDisplayer.
 */
public class MousePosDisplayTest {

  private PlaybackView view;
  private MousePosListener listener;

  /**
   * Set up variables before each test.
   */
  @Before
  public void setUp() {
    view = new FakePlaybackView();
    listener = new MousePosListener(view);
  }

  /**
   * Test MouseMoved() method.
   */
  @Test
  public void testMouseMoved() {
    listener.mouseMoved(new MouseEvent(new JPanel(), 0, 0, 0, 0, 0, 0, false, 0));
    assertEquals("x: 0  y: 0", ((FakePlaybackView)view).getCoordinates());

    listener.mouseMoved(new MouseEvent(new JPanel(), 0, 0, 0, 7, 8, 0, false, 0));
    assertEquals("x: 7  y: 8", ((FakePlaybackView)view).getCoordinates());

    listener.mouseMoved(new MouseEvent(new JPanel(), 0, 0, 0, -7, -8, 0, false, 0));
    assertEquals("x: -7  y: -8", ((FakePlaybackView)view).getCoordinates());
  }

  /**
   * Test null value input.
   */
  @Test
  public void testNullValue() {
    try {
      listener.mouseMoved(null);
      fail("Should throw exceptions");
    } catch (Exception e) {
      assertEquals(NullPointerException.class, e.getClass());
    }
  }

  /**
   * Test null source.
   */
  @Test
  public void testNullSource() {
    try {
      listener.mouseMoved(new MouseEvent(null, 0, 0, 0, 7, 8, 0, false, 0));
      fail("Should throw exceptions.");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

}
