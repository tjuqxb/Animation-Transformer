package animator.view;

import java.io.IOException;

import model.AnimateOutput;

/**
 * This class is the implementation of TextView.
 */
public class TextViewImpl implements TextView {
  private Appendable output;
  private AnimateOutput modelOutput;
  private Integer tick;

  /**
   * The constructor of the text view.
   *
   * @param output the output character stream
   */
  public TextViewImpl(Appendable output) {
    this.output = output;
    this.tick = null;
  }

  @Override
  public void display() throws IllegalStateException {
    try {
      if (this.tick == null) {
        this.output.append(this.modelOutput.toString());
      } else {
        this.output.append(this.modelOutput.toString(tick));
      }
    } catch (IOException e) {
      throw new IllegalStateException("IO has some problem.");
    }
  }

  @Override
  public void update(Object o) throws IllegalArgumentException {
    if (!(o instanceof AnimateOutput)) {
      throw new IllegalArgumentException("Require AnimteOutput type.");
    }
    this.modelOutput = (AnimateOutput) o;
  }

  @Override
  public void updateFreq(int f) throws IllegalArgumentException {
    if (f <= 0) {
      throw new IllegalArgumentException("Speed should be greater than 0.");
    }
    this.tick = f;
  }
}