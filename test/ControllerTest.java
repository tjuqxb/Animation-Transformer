import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.event.ActionEvent;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.junit.Before;
import org.junit.Test;

import animator.controller.Controller;
import animator.controller.ControllerImpl;
import animator.controller.MousePosListener;
import animator.util.AnimationBuilder;
import animator.util.AnimationBuilderImpl;
import animator.util.AnimationReader;
import animator.view.TextViewImpl;
import animator.view.View;
import model.AnimateFrame;
import model.AnimateModel;
import model.AnimateModelImpl;
import model.Attribute;
import model.ColorRgb;
import model.Position2d;
import model.SizeRectangle;

/**
 * JUnit test case for controller.
 */
public class ControllerTest {
  private View fakeView;

  /**
   * Set up variables.
   */
  @Before
  public void setUp() {
    this.fakeView = new FakePlaybackView();
  }

  /**
   * Test invalid input for transform Animation.
   */
  @Test
  public void testInvalidFreq() {
    Controller controller = new ControllerImpl(fakeView);
    try {
      controller.tranformAnimation(new AnimateModelImpl(), -1);
      fail("Should throw exception");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
    try {
      controller.tranformAnimation(null, -1);
      fail("Should throw exception");
    } catch (Exception e) {
      assertEquals(IllegalArgumentException.class, e.getClass());
    }
  }

  /**
   * Test get mouse adapter.
   */
  @Test
  public void testMouseAdapter() {
    Controller controller = new ControllerImpl(fakeView);
    assertEquals(MousePosListener.class, controller.getMouseAdapter().getClass());
    controller = new ControllerImpl(new TextViewImpl(System.out));
    assertEquals(null, controller.getMouseAdapter());
  }

  /**
   * Consume a string and transform it to attribute[]. This helper function only
   * consumes a valid string input row. For example, "motion disk1 1 190 180 20 30
   * 0 49 90 1 190 180 20 30 0 49 90"
   * 
   * @param str input one line string
   * @return Attribute[]
   */
  private Attribute[] readAttr(String str) {
    // t x y w h r g b
    List<Integer> list = new ArrayList<>();
    String[] arr0 = str.split("\\s+");
    for (String ele : arr0) {
      try {
        int num = Integer.parseInt(ele);
        list.add(num);
      } catch (Exception e) {
        // TODO: handle exception
      }
    }
    Integer[] arr = new Integer[16];
    list.toArray(arr);
    Attribute[] ret = new Attribute[6];
    ret[0] = new Position2d(arr[1], arr[2]);
    ret[1] = new SizeRectangle(arr[3], arr[4]);
    ret[2] = new ColorRgb(arr[5], arr[6], arr[7]);
    ret[3] = new Position2d(arr[9], arr[10]);
    ret[4] = new SizeRectangle(arr[11], arr[12]);
    ret[5] = new ColorRgb(arr[13], arr[14], arr[15]);
    return ret;
  }

  /**
   * Test simple animation and action listener in controller.
   */
  @Test
  public void actionListener() {
    String input = "canvas 145 50 410 220\n" + "shape disk1 rectangle\n" + "shape disk2 rectangle\n"
        + "shape disk3 rectangle\n"
        + "motion disk1 1 190 180 20 30 0 49 90  1 190 180 20 30 0 49 90\n"
        + "motion disk1 1 190 180 20 30 0 49 90  25 190 180 20 30 0 49 90\n"
        + "motion disk2 1 167 210 65 30 6 247 41  1 167 210 65 30 6 247 41\n"
        + "motion disk2 1 167 210 65 30 6 247 41  57 167 210 65 30 6 247 41\n"
        + "motion disk3 1 145 240 110 30 11 45 175  1 145 240 110 30 11 45 175\n"
        + "motion disk3 1 145 240 110 30 11 45 175  121 145 240 110 30 11 45 175";
    AnimationBuilder<AnimateModel> ab = new AnimationBuilderImpl();
    AnimateModel animateModel = ab.build();
    AnimationReader.parseFile(new StringReader(input), ab);
    Controller controller = new ControllerImpl(fakeView);
    controller.tranformAnimation(animateModel, 20);
    int f = ((FakePlaybackView) fakeView).getF();
    Controller c = ((FakePlaybackView) fakeView).getController();
    Object frame = ((FakePlaybackView) fakeView).getFrame();
    assertEquals(c, controller);
    assertEquals(20, f);
    assertEquals(AnimateFrame.class, frame.getClass());
    String coors = ((FakePlaybackView) fakeView).getCoordinates();
    assertEquals(null, coors);
    AnimateFrame ff = (AnimateFrame) frame;
    assertEquals(3, ff.getList().size());
    assertEquals(145, ff.getBounds()[0]);
    assertEquals(50, ff.getBounds()[1]);
    assertEquals(410, ff.getBounds()[2]);
    assertEquals(220, ff.getBounds()[3]);
    List<Attribute[]> list = ff.getList();

    Set<Attribute> set = new HashSet<>();
    for (int i = 0; i < list.size(); i++) {
      Attribute[] arr = list.get(i);
      for (int j = 0; j < arr.length; j++) {
        set.add(arr[j]);
      }
    }
    assertEquals(1, set.size());
    for (Attribute attr : set) {
      assertEquals(null, attr);
    }

    JFrame trigger = new JFrame();
    Timer timeTrigger = new Timer(1, null);
    trigger.setName("Start");
    ActionEvent controlEvent = new ActionEvent(trigger, 0, "");
    ((ControllerImpl) controller).actionPerformed(controlEvent);
    ActionEvent timeEvent = new ActionEvent(timeTrigger, 0, "");
    ((ControllerImpl) controller).actionPerformed(timeEvent);

    // begin frame
    set.clear();
    ff = (AnimateFrame) ((FakePlaybackView) fakeView).getFrame();
    list = ff.getList();
    for (int i = 0; i < list.size(); i++) {
      Attribute[] arr = list.get(i);
      for (int j = 0; j < arr.length; j++) {
        set.add(arr[j]);
      }
    }
    assertEquals(9, set.size());
    String[] lines = input.split("\n");
    Attribute[] a0 = readAttr(lines[4]);
    for (Attribute att : a0) {
      assertTrue(set.contains(att));
    }
    Attribute[] a3 = readAttr(lines[6]);
    for (Attribute att : a3) {
      assertTrue(set.contains(att));
    }
    Attribute[] a4 = readAttr(lines[8]);
    for (Attribute att : a4) {
      assertTrue(set.contains(att));
    }

    // end frame
    for (int i = 0; i < 125; i++) {
      ((ControllerImpl) controller).actionPerformed(timeEvent);
    }
    set.clear();
    ff = (AnimateFrame) ((FakePlaybackView) fakeView).getFrame();
    list = ff.getList();
    for (int i = 0; i < list.size(); i++) {
      Attribute[] arr = list.get(i);
      for (int j = 0; j < arr.length; j++) {
        set.add(arr[j]);
      }
    }
    assertEquals(4, set.size());
    assertTrue(set.contains(null));
    Attribute[] a1 = readAttr(lines[9]);
    for (int i = 3; i < 6; i++) {
      assertTrue(set.contains(a1[i]));
    }

    // begin frame
    trigger.setName("Looping");
    ((ControllerImpl) controller).actionPerformed(controlEvent);
    ((ControllerImpl) controller).actionPerformed(timeEvent);
    ((ControllerImpl) controller).actionPerformed(timeEvent);
    set.clear();
    ff = (AnimateFrame) ((FakePlaybackView) fakeView).getFrame();
    list = ff.getList();
    for (int i = 0; i < list.size(); i++) {
      Attribute[] arr = list.get(i);
      for (int j = 0; j < arr.length; j++) {
        set.add(arr[j]);
      }
    }
    assertEquals(9, set.size());
    for (Attribute att : a0) {
      assertTrue(set.contains(att));
    }
    for (Attribute att : a3) {
      assertTrue(set.contains(att));
    }
    for (Attribute att : a4) {
      assertTrue(set.contains(att));
    }

    // middle frame
    for (int i = 0; i < 30; i++) {
      ((ControllerImpl) controller).actionPerformed(timeEvent);
    }
    set.clear();
    ff = (AnimateFrame) ((FakePlaybackView) fakeView).getFrame();
    list = ff.getList();
    for (int i = 0; i < list.size(); i++) {
      Attribute[] arr = list.get(i);
      for (int j = 0; j < arr.length; j++) {
        set.add(arr[j]);
      }
    }
    assertEquals(7, set.size());
    assertTrue(set.contains(null));
    for (Attribute att : a3) {
      assertTrue(set.contains(att));
    }
    for (Attribute att : a4) {
      assertTrue(set.contains(att));
    }

    // Pause middle frame
    trigger.setName("Start");
    ((ControllerImpl) controller).actionPerformed(controlEvent);
    for (int i = 0; i < 60; i++) {
      ((ControllerImpl) controller).actionPerformed(timeEvent);
    }
    set.clear();
    ff = (AnimateFrame) ((FakePlaybackView) fakeView).getFrame();
    list = ff.getList();
    for (int i = 0; i < list.size(); i++) {
      Attribute[] arr = list.get(i);
      for (int j = 0; j < arr.length; j++) {
        set.add(arr[j]);
      }
    }
    assertEquals(7, set.size());
    assertTrue(set.contains(null));
    for (Attribute att : a3) {
      assertTrue(set.contains(att));
    }
    for (Attribute att : a4) {
      assertTrue(set.contains(att));
    }

    // resume -> end frame
    trigger.setName("Start");
    ((ControllerImpl) controller).actionPerformed(controlEvent);
    for (int i = 0; i < 60; i++) {
      ((ControllerImpl) controller).actionPerformed(timeEvent);
    }
    set.clear();
    ff = (AnimateFrame) ((FakePlaybackView) fakeView).getFrame();
    list = ff.getList();
    for (int i = 0; i < list.size(); i++) {
      Attribute[] arr = list.get(i);
      for (int j = 0; j < arr.length; j++) {
        set.add(arr[j]);
      }
    }
    assertEquals(4, set.size());
    assertTrue(set.contains(null));
    for (int i = 3; i < 6; i++) {
      assertTrue(set.contains(a1[i]));
    }

    // stop looping, restart -> start frame
    trigger.setName("Looping");
    ((ControllerImpl) controller).actionPerformed(controlEvent);
    trigger.setName("Restart");
    ((ControllerImpl) controller).actionPerformed(controlEvent);
    ((ControllerImpl) controller).actionPerformed(timeEvent);
    set.clear();
    ff = (AnimateFrame) ((FakePlaybackView) fakeView).getFrame();
    list = ff.getList();
    for (int i = 0; i < list.size(); i++) {
      Attribute[] arr = list.get(i);
      for (int j = 0; j < arr.length; j++) {
        set.add(arr[j]);
      }
    }
    assertEquals(9, set.size());
    for (Attribute att : a0) {
      assertTrue(set.contains(att));
    }
    for (Attribute att : a3) {
      assertTrue(set.contains(att));
    }
    for (Attribute att : a4) {
      assertTrue(set.contains(att));
    }
  }
}