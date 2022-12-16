import java.io.IOException;
import java.nio.CharBuffer;

/**
 * Mocking failing readable.
 */
public class FailingReadable implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException("Fail!");
  }
}