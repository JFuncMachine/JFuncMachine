package org.jfuncmachine.sexprlang.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Stack;

/** A simple S-expression parser */
public class Parser {
    /** The states of the parser */
    public enum State {
        /** Indicates the parser is waiting for the next item */
        AwaitingItem,
        /** Indicates the parser is reading a number */
        ReadingNumber,
        /** Indicates the parser is reading a symbol */
        ReadingSymbol,
        /** Indicates the parser is reading a String */
        ReadingString,
        /** Indicates the parser is waiting for a separator */
        AwaitingSeparator,
        /** Indicates the parser is skipping a line comment */
        SkippingLineComment,
        /** Indicates the parser is skipping a block comment */
        SkippingBlockComment,
        /** Indicates the parser is has seen a minus to start an item, but doesn't know yet
         * if it is part of a number or part of a symbol*/
        GotMinus
    }

    /** Parses a file and returns the first S-expression
     *
     * @param filename The file to parse
     * @return The first S-expression in the file
     * @throws IOException If there is an error reading or parsing the file
     */
    public static SexprItem parseFile(String filename)
        throws IOException {
        String fileString = Files.readString(new File(filename).toPath());
        return parseString(fileString, filename, false, new JavaSymbolMatcher());
    }

    /** Parses a file and returns either the first or all S-expressions in it
     *
     * @param filename The file to parse
     * @param parseMultipleSexprs True if the parser should return all S-expressions instead of just the first
     * @return Either the first or all the S-expressions in the file
     * @throws IOException If there is an error reading or parsing the file
     */
    public static SexprItem parseFile(String filename, boolean parseMultipleSexprs)
            throws IOException {
        String fileString = Files.readString(new File(filename).toPath());
        return parseString(fileString, filename, parseMultipleSexprs, new JavaSymbolMatcher());
    }

    /** Parses an S-expression from a String
     *
     * @param str The string to parse
     * @param filename The filename to use as the filename in the generated S-expressions
     * @return The first S-expression in the string
     * @throws IOException If there is an error parsing the string
     */
    public static SexprItem parseString(String str, String filename) throws IOException {
        return parseString(str, filename, false, new JavaSymbolMatcher());
    }

    /** Parses an S-expression from a file using the specified matcher to identify symbols
     *
     * @param filename The file to parse
     * @param symbolMatcher The matcher that determines if a character is part of a symbol
     * @return The first S-expression matched
     * @throws IOException If there is an error reading or parsing the file
     */
    public static SexprItem parseFile(String filename, SymbolMatcher symbolMatcher)
            throws IOException {
        String fileString = Files.readString(new File(filename).toPath());
        return parseString(fileString, filename, false, symbolMatcher);
    }

    /** Parses S-expressions from a file using the specified matcher to identify symbols
     *
     * @param filename The file to parse
     * @param parseMultipleSexprs If true, all S-expressions in the file are parsed, not just the first
     * @param symbolMatcher The matcher that determines if a character is part of a symbol
     * @return The first S-expression matched
     * @throws IOException If there is an error reading or parsing the file
     */
    public static SexprItem parseFile(String filename, boolean parseMultipleSexprs,
                                      SymbolMatcher symbolMatcher) throws IOException {
        String fileString = Files.readString(new File(filename).toPath());
        return parseString(fileString, filename, parseMultipleSexprs, symbolMatcher);
    }

    /** Parses an S-expression from a string using the specified matcher to identify symbols
     *
     * @param str The string to parse
     * @param filename The filename to use as the filename in the generated S-expression items
     * @param symbolMatcher The matcher that determines if a character is part of a symbol
     * @return The first S-expression matched
     * @throws IOException If there is an error parsing the string
     */
    public static SexprItem parseString(String str, String filename, SymbolMatcher symbolMatcher)
            throws IOException {
        return parseString(str, filename, false, symbolMatcher);
    }

    /** Parses an S-expression from a string using the specified matcher to identify symbols
     *
     * @param str The string to parse
     * @param filename The filename to use as the filename in the generated S-expression items
     * @param parseMultipleSexprs If true, all S-expressions in the file are parsed, not just the first
     * @param symbolMatcher The matcher that determines if a character is part of a symbol
     * @return The first S-expression matched
     * @throws IOException If there is an error parsing the string
     */
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

    private Parser() {}
}
