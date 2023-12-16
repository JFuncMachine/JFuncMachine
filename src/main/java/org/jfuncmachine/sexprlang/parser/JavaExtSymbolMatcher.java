package org.jfuncmachine.sexprlang.parser;

/** A symbol matcher that matches Java identifiers including those with '.' in them */
public class JavaExtSymbolMatcher implements SymbolMatcher {
    @Override
    public boolean isSymbolFirstChar(char ch) {
        return Character.isJavaIdentifierStart(ch);
    }

    @Override
    public boolean isSymbolChar(char ch) {
        return ch == '.' || Character.isJavaIdentifierPart(ch);
    }

    /** Create an extended Java symbol matcher */
    public JavaExtSymbolMatcher() {

    }
}
