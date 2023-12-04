package org.jfuncmachine.jfuncmachine.sexprlang.parser;

public class JavaSymbolMatcher implements SymbolMatcher {
    @Override
    public boolean isSymbolFirstChar(char ch) {
        return Character.isJavaIdentifierStart(ch);
    }

    @Override
    public boolean isSymbolChar(char ch) {
        return Character.isJavaIdentifierPart(ch);
    }
}
