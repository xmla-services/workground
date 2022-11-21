package org.eclipse.daanse.core.api.olap;

import java.util.List;

/**
 * Description of an implicit conversion that occurred while resolving an
 * operator call.
 */
public interface Conversion {
    /**
     * Returns the cost of the conversion. If there are several matching
     * overloads, the one with the lowest overall cost will be preferred.
     *
     * @return Cost of conversion
     */
    int getCost();

    /**
     * Checks the viability of implicit conversions. Converting from a
     * dimension to a hierarchy is valid if is only one hierarchy.
     */
    void checkValid();

    /**
     * Applies this conversion to its argument, modifying the argument list
     * in place.
     *
     * @param validator Validator
     * @param args Argument list
     */
    void apply(Validator validator, List<Exp> args);
}
