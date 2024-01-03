package org.jfuncmachine.compiler.utils.mapper.mappermodel;

import org.jfuncmachine.sexprlang.parser.SymbolMatcher;

import java.util.regex.Pattern;

public class TestSymbolMatcher implements SymbolMatcher {
    Pattern firstCharPattern;
    Pattern charPattern;

    public TestSymbolMatcher() {
        firstCharPattern = Pattern.compile("[-A-Za-z0-9.+=<>!*/]");
        charPattern = Pattern.compile("[-A-Za-z0-9.+=<>!*/]");
    }

    @Override
    public boolean isSymbolFirstChar(char ch) {
        return firstCharPattern.matcher(""+ch).matches();
    }

    @Override
    public boolean isSymbolChar(char ch) {
        return charPattern.matcher(""+ch).matches();
    }
}
