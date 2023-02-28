package org.eclipse.daanse.xmla.model.record.engine300;

import org.eclipse.daanse.xmla.api.engine300.CalculationPropertiesVisualizationProperties;

import java.math.BigInteger;

public record CalculationPropertiesVisualizationPropertiesR(
    BigInteger folderPosition,
    String contextualNameRule,
    String alignment,
    Boolean isFolderDefault,
    Boolean isRightToLeft,
    String sortDirection,
    String units,
    BigInteger width,
    Boolean isDefaultMeasure,
    BigInteger defaultDetailsPosition,
    BigInteger sortPropertiesPosition,
    Boolean isSimpleMeasure) implements CalculationPropertiesVisualizationProperties {

}
