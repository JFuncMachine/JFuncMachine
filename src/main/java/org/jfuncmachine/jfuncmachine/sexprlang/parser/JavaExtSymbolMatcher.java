package org.jfuncmachine.jfuncmachine.sexprlang.parser;

public class JavaExtSymbolMatcher implements SymbolMatcher {
    @Override
    public boolean isSymbolFirstChar(char ch) {
        return Character.isJavaIdentifierStart(ch);
    }

    @Override
    public boolean isSymbolChar(char ch) {
        return ch == '.' || Character.isJavaIdentifierPart(ch);
    }
}
