package org.jfuncmachine.reader;

import java.io.IOException;

public interface ReaderStream {
    public int peek() throws IOException;
    public int read() throws IOException;
    public Position getPosition();
}
