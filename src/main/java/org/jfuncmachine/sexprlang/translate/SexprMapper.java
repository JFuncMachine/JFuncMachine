package org.jfuncmachine.sexprlang.translate;

import org.jfuncmachine.sexprlang.parser.*;

/** An interface for mapping S-expressions to objects */
public interface SexprMapper {
    /** Map an S-expression symbol to an object
     *
     * @param symbol The symbol to map
     * @return The value the symbol maps to
     * @throws MappingException If there is an error performing the mapping
     */
    Object mapSymbol(SexprSymbol symbol) throws MappingException;
    /** Map an S-expression symbol to a class
     *
     * @param symbol The symbol to map
     * @return The class the symbol maps to
     * @throws MappingException If there is an error performing the mapping
     */
    Class mapSymbolToClass(SexprSymbol symbol) throws MappingException;
    /** Map an S-expression double value to an object
     *
     * @param d The double value to map
     * @return The object the value maps to
     * @throws MappingException If there is an error performing the mapping
     */
    Object mapDouble(SexprDouble d) throws MappingException;
    /** Map an S-expression int to an object
     *
     * @param i The S-expression int that should be mapped
     * @return The object the int maps to
     * @throws MappingException If there is an error performing the mapping
     */
    Object mapInt(SexprInt i) throws MappingException;
    /** Map an S-expression string to an object
     *
     * @param s The string to map
     * @return The object the string maps to
     * @throws MappingException If there is an error performing the mapping
     */
    Object mapString(SexprString s) throws MappingException;
    /** Map an S-expression list to an object
     *
     * @param l The list to map
     * @param targetClass The class that the object is expected to map to
     * @return The object that the list has been mapped into
     * @throws MappingException If there is an error performing the mapping
     */
    Object mapList(SexprList l, Class targetClass) throws MappingException;
}
