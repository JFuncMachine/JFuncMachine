package org.jfuncmachine.reader;

import java.io.IOException;

public interface ReadHandler {
    public Object handleRead(char ch, ReaderStream stream)
        throws IOException;
}
