package animator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import animator.controller.ControllerImpl;
import animator.util.AnimationBuilder;
import animator.util.AnimationBuilderImpl;
import animator.util.AnimationReader;
import animator.view.GuiViewImpl;
import animator.view.PlaybackViewImpl;
import animator.view.SvgViewImpl;
import animator.view.TextViewImpl;
import animator.view.View;
import model.AnimateModel;

/**
 * The main program class.
 */
public final class EasyAnimator {
  private final static String IN = "-in";
  private final static String VIEW = "-view";
  private final static String SPEED = "-speed";
  private final static String OUT = "-out";
  private final static String TEXT_VIEW = "text";
  private final static String SVG_VIEW = "svg";
  private final static String VISUAL_VIEW = "visual";
  private final static String PLAYBACK_VIEW = "playback";

  /**
   * The entry point for animator.
   * Mandatory arguments:
   *  -in       input file
   *  -view     specified view (text, svg, visual, playback)
   * Other arguments:
   *  -speed    ticks per second(integer)
   *  -out      output file
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    JFrame errorDisplay = new JFrame();
    if (args.length < 4 || args.length % 2 == 1) {
      JOptionPane.showMessageDialog(errorDisplay, "Wrong number of arguments.");
      return;
    }
    String input = null;
    ViewOpitions viewOpt = null;
    Integer tickPerSecond = null;
    String output = null;
    boolean invalid = false;
    for (int i = 0; i < args.length; i += 2) {
      if (args[i].equalsIgnoreCase(IN)) {
        input = args[i + 1];
      } else if (args[i].equalsIgnoreCase(VIEW)) {
        if (args[i + 1].equalsIgnoreCase(VISUAL_VIEW)) {
          viewOpt = ViewOpitions.VISUAL_VIEW;
        } else if (args[i + 1].equalsIgnoreCase(TEXT_VIEW)) {
          viewOpt = ViewOpitions.TEXT_VIEW;
        } else if (args[i + 1].equalsIgnoreCase(SVG_VIEW)) {
          viewOpt = ViewOpitions.SVG_VIEW;
        } else if (args[i + 1].equalsIgnoreCase(PLAYBACK_VIEW)) {
          viewOpt = ViewOpitions.PLAY_BACK;
        } else {
          invalid = true;
          JOptionPane.showMessageDialog(errorDisplay, "Invalid view name.");
        }
      } else if (args[i].equalsIgnoreCase(SPEED)) {
        try {
          tickPerSecond = Integer.parseInt(args[i + 1]);
          if (tickPerSecond <= 0) {
            invalid = true;
            JOptionPane.showMessageDialog(errorDisplay, "Speed must be greater than 0.");
          }
        } catch (Exception e) {
          invalid = true;
          JOptionPane.showMessageDialog(errorDisplay, "Wrong number format for speed.");
        }
      } else if (args[i].equalsIgnoreCase(OUT)) {
        output = args[i + 1];
      } else {
        JOptionPane.showMessageDialog(errorDisplay, "Wrong argument type name.");
        invalid = true;
      }
    }
    if (invalid) {
      return;
    }
    if (input == null || viewOpt == null) {
      JOptionPane.showMessageDialog(errorDisplay, "Must declare input and view option.");
      return;
    }
    Readable readable = null;
    Appendable appendable = System.out;
    try {
      readable = new InputStreamReader(new FileInputStream(new File(input)));
      if (output != null && (viewOpt == ViewOpitions.SVG_VIEW 
          || viewOpt == ViewOpitions.TEXT_VIEW)) {
        appendable = new BufferedWriter(new FileWriter(output));
      }
      View view = null;
      if (viewOpt == ViewOpitions.SVG_VIEW) {
        view = new SvgViewImpl(appendable);
      } else if (viewOpt == ViewOpitions.TEXT_VIEW) {
        view = new TextViewImpl(appendable);
      } else if (viewOpt == ViewOpitions.VISUAL_VIEW) {
        view = new GuiViewImpl();
      } else if (viewOpt == ViewOpitions.PLAY_BACK) {
        view = new PlaybackViewImpl();
      }
      AnimationBuilder<AnimateModel> ab = new AnimationBuilderImpl();
      AnimateModel animateModel = ab.build();
      AnimationReader.parseFile(readable, ab);
      new ControllerImpl(view).tranformAnimation(animateModel, tickPerSecond);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(errorDisplay, e.getMessage());
    } finally {
      try {
        if (readable != null) {
          ((InputStreamReader) readable).close();
        }
        if (appendable instanceof BufferedWriter) {
          ((BufferedWriter) appendable).close();
        }
      } catch (IOException e) {
        JOptionPane.showMessageDialog(errorDisplay, e.getMessage());
      }
    }
  }
}