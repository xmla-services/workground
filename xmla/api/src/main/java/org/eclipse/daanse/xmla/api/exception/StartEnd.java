package org.eclipse.daanse.xmla.api.exception;

/**
 * The End element contains a Line element (integer) and a Column element (integer) that
 * indicates the ending point of the Warning or Error.
 */
public interface StartEnd {

    int line();

    int column();
}
