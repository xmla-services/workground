package org.eclipse.daanse.xmla.api.discover.mdschema.functions;

public interface ParameterInfo {
    /**
     * @return The name of the parameter.
     */
    String name();

    /**
     * @return The description of the parameter.
     */
    String description();

    /**
     * @return A Boolean that, when true, indicates that the parameter is
     *     optional.
     */
    Boolean optional();

    /**
     * @return A Boolean that, when true, indicates that multiple values can be
     *     specified for this parameter.
     */
    Boolean repeatable();

    /**
     * @return The index of the repeat group of this parameter.
     */
    Integer repeatGroup();

}
