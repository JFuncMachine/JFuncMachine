package org.jfuncmachine.reader;

public class StringReaderStream implements ReaderStream {
    protected String inputString;
    protected int pos;
    protected int lineNumber;

    public StringReaderStream(String inputString) {
        this.inputString = inputString;
        this.pos = 0;
        lineNumber = 1;
    }

    public int peek() {
        if (pos >= inputString.length()) {
            return -1;
        }
        return inputString.charAt(pos);
    }

    public int read() {
        if (pos >= inputString.length()) {
            return -1;
        }
        int ch = inputString.charAt(pos);
        pos++;
        if (ch == '\n') {
            lineNumber++;
        }
        return inputString.charAt(pos++);
    }

    public Position getPosition() {
        return new Position(null, lineNumber);
    }
}
