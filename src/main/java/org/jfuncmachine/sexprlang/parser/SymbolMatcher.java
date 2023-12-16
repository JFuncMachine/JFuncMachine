package org.jfuncmachine.sexprlang.parser;

/** A symbol matcher that tells the S-expression parser whether
 * a character is part of a symbol
 */
public interface SymbolMatcher {
    /** Tests whether a character is allowed to begin a symbol
     *
     * @param ch The character to test
     * @return True if the character is allowed at the beginning of a symbol
     */
    boolean isSymbolFirstChar(char ch);

    /** Tests whether a character is allowed to be in a symbol
     *
     * @param ch The character to test
     * @return True if the character is allowed anywhere after the first character of a symbol
     */
    boolean isSymbolChar(char ch);
}
