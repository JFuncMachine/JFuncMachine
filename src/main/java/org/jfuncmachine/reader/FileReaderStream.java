package org.jfuncmachine.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderStream {
    protected BufferedReader inStream;
    protected int peekCh;
    protected boolean hasPeek;
    protected int lineNumber;
    protected String filename;

    public FileReaderStream(String filename)
        throws IOException {
        inStream = new BufferedReader(new FileReader(filename));
        lineNumber = 1;
        hasPeek = false;
        File file = new File(filename);
        this.filename = file.getName();
    }

    public FileReaderStream(File file)
        throws IOException {
        inStream = new BufferedReader(new FileReader(file));
        lineNumber = 1;
        hasPeek = false;
        this.filename = file.getName();
    }

    public int peek() throws IOException {
        if (hasPeek) {
            return peekCh;
        }
        peekCh = inStream.read();
        hasPeek = true;
        return peekCh;
    }

    public int read() throws IOException {
        if (hasPeek) {
            hasPeek = false;
            return peekCh;
        }
        int ch = inStream.read();
        if (ch == '\n') {
            lineNumber++;
        }
        return ch;
    }

    public Position getPosition() {
        return new Position(filename, lineNumber);
    }
}
