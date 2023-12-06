package org.jfuncmachine.jfuncmachine.sexprlang.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

public class Parser {
    public enum State {
        AwaitingItem,
        ReadingNumber,
        ReadingSymbol,
        ReadingString,
        AwaitingSeparator,
        SkippingLineComment,
        SkippingBlockComment,
        GotMinus
    }

    public static SexprItem parseFile(String filename)
        throws IOException {
        String fileString = Files.readString(new File(filename).toPath());
        return parseString(fileString, filename, false, new JavaSymbolMatcher());
    }

    public static SexprItem parseFile(String filename, boolean parseMultipleSexprs)
            throws IOException {
        String fileString = Files.readString(new File(filename).toPath());
        return parseString(fileString, filename, parseMultipleSexprs, new JavaSymbolMatcher());
    }

    public static SexprItem parseString(String str, String filename) throws IOException {
        return parseString(str, filename, false, new JavaSymbolMatcher());
    }

    public static SexprItem parseFile(String filename, SymbolMatcher symbolMatcher)
            throws IOException {
        String fileString = Files.readString(new File(filename).toPath());
        return parseString(fileString, filename, false, symbolMatcher);
    }

    public static SexprItem parseFile(String filename, boolean parseMultipleSexprs,
                                      SymbolMatcher symbolMatcher) throws IOException {
        String fileString = Files.readString(new File(filename).toPath());
        return parseString(fileString, filename, parseMultipleSexprs, symbolMatcher);
    }

    public static SexprItem parseString(String str, String filename, SymbolMatcher symbolMatcher)
            throws IOException {
        return parseString(str, filename, false, symbolMatcher);
    }

    public static SexprItem parseString(String str, String filename, boolean parseMultipleSexprs,
                                        SymbolMatcher symbolMatcher)
        throws IOException {
        int lineNumber = 1;
        char ch=' ';
        State state = State.AwaitingItem;
        StringBuilder builder = new StringBuilder();
        boolean gotDecimal = false;
        Stack<ArrayList<SexprItem>> arrayStack = new Stack<>();
        Stack<Integer> listStartStack = new Stack<>();
        arrayStack.push(new ArrayList<>());
        listStartStack.push(lineNumber);

        int colStart = 0;
        for (int pos=0; pos <= str.length(); pos++) {
            if (ch == '\n') {
                lineNumber++;
                colStart=pos+1;
                if (state == State.SkippingLineComment) {
                    state = State.AwaitingItem;
                }
            }
            if (pos < str.length()) {
                ch = str.charAt(pos);
            } else {
                ch = 0xffff;
            }
            int column = 1+pos-colStart;

            switch (state) {
                case State.AwaitingItem -> {
                    if (ch == '(') {
                        arrayStack.push(new ArrayList<>());
                        listStartStack.push(lineNumber);
                    } else if (ch == ')') {
                        ArrayList<SexprItem> top = arrayStack.pop();
                        arrayStack.peek().add(new SexprList(top, filename, listStartStack.pop()));
                    } else if (ch == 0xffff) {
                    } else if (Character.isWhitespace(ch)) {
                    } else if (ch == '-') {
                        state = State.GotMinus;
                    } else if (Character.isDigit(ch)) {
                        state = State.ReadingNumber;
                        gotDecimal = false;
                        builder = new StringBuilder();
                        builder.append(ch);
                    } else if (ch == '"') {
                        state = State.ReadingString;
                        builder = new StringBuilder();
                    } else if (ch == ';') {
                        state = State.SkippingLineComment;
                    } else if (ch == '{') {
                        state = State.SkippingBlockComment;
                    } else if (symbolMatcher.isSymbolFirstChar(ch)) {
                        state = State.ReadingSymbol;
                        builder = new StringBuilder();
                        builder.append(ch);
                    } else {
                        throw new IOException (
                                String.format("Unexpected char %c (%02x) at column %d on line %d in %s",
                                        ch, (int) ch, column, lineNumber, filename));
                    }
                }
                case State.ReadingNumber -> {
                    if (Character.isDigit(ch)) {
                        builder.append(ch);
                    } else if (ch == '.') {
                        if (gotDecimal) {
                            throw new IOException (
                                String.format("Extra decimal point at column %d on line %d in %s",
                                    column, lineNumber, filename));

                        }
                        builder.append(ch);
                        gotDecimal = true;
                    } else {
                        if (gotDecimal) {
                            arrayStack.peek().add(new SexprDouble(Double.parseDouble(builder.toString()), filename, lineNumber));
                        } else {
                            arrayStack.peek().add(new SexprInt(Integer.parseInt(builder.toString()), filename, lineNumber));
                        }
                        pos--;
                        if (ch == '\n') {
                            lineNumber--;
                        }
                        state = State.AwaitingSeparator;
                    }
                }
                case State.ReadingString -> {
                    if (ch == '"') {
                        arrayStack.peek().add(new SexprString(builder.toString(), filename, lineNumber));
                        state = State.AwaitingSeparator;
                    } else {
                        builder.append(ch);
                    }
                }
                case State.ReadingSymbol -> {
                    if (symbolMatcher.isSymbolChar(ch)) {
                        builder.append(ch);
                    } else {
                        arrayStack.peek().add(new SexprSymbol(builder.toString(), filename, lineNumber));
                        pos--;
                        if (ch == '\n') {
                            lineNumber--;
                        }
                        state = State.AwaitingSeparator;
                    }
                }
                case State.SkippingBlockComment -> {
                    if (ch == '}') {
                        state = State.AwaitingSeparator;
                    }
                }
                case State.AwaitingSeparator -> {
                    if (ch == ')') {
                        pos--;
                        state = State.AwaitingItem;
                    } else if (ch == ';') {
                        state = State.SkippingLineComment;
                    } else if (ch == '}') {
                        state = State.SkippingBlockComment;
                    } else if (Character.isWhitespace(ch)) {
                        state = State.AwaitingItem;
                    } else if (ch == 0xffff) {
                    } else {
                        throw new IOException (
                                String.format("Unexpected char %c (%02x) at column %d on line %d in %s",
                                        ch, (int) ch, column, lineNumber, filename));

                    }
                }
                case State.GotMinus -> {
                    builder = new StringBuilder();
                    builder.append('-');
                    if (Character.isDigit(ch)) {
                        state = State.ReadingNumber;
                        builder.append(ch);
                        gotDecimal = false;

                    } else if (!Character.isWhitespace(ch)) {
                        builder.append(ch);
                        state = State.ReadingSymbol;
                    } else {
                        arrayStack.peek().add(new SexprSymbol(builder.toString(), filename, lineNumber));
                        pos--;
                        if (ch == '\n') {
                            lineNumber--;
                        }
                        state = State.AwaitingSeparator;
                    }
                }
            }
        }
        if (arrayStack.size() > 1) {
            throw new IOException (
                    String.format("Outermost list does not terminate"));

        }

        ArrayList<SexprItem> top = arrayStack.pop();
        if ((top.size() != 1) && !parseMultipleSexprs) {
            throw new IOException("S-expression should contain only one item");
        }

        if (!parseMultipleSexprs) {
            return top.get(0);
        } else {
            return new SexprList(top, filename, 1);
        }
    }
}
