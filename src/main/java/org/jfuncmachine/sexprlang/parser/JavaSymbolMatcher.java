package org.jfuncmachine.sexprlang.parser;

/** A symbol matcher that matches Java identifiers */
public class JavaSymbolMatcher implements SymbolMatcher {
    @Override
    public boolean isSymbolFirstChar(char ch) {
        return Character.isJavaIdentifierStart(ch);
    }

    @Override
    public boolean isSymbolChar(char ch) {
        return Character.isJavaIdentifierPart(ch);
    }

    /** Create a Java symbol matcher */
    public JavaSymbolMatcher() {

    }
}
