package org.eclipse.daanse.olap.rolap.dbmapper.provider.modifier;

import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingCube;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchema;
import org.eclipse.daanse.olap.rolap.dbmapper.model.record.CubeR;

public class ExampleModifyer extends RDbMappingSchemaModifier {

	public ExampleModifyer(MappingSchema mappingSchema) {
		super(mappingSchema);
	}

	@Override
	protected List<MappingCube> schemaCubes(MappingSchema mappingSchemaOriginal) {
		List<MappingCube> cubes = super.schemaCubes(mappingSchemaOriginal);

		cubes = cubes.stream().filter(c -> c.name() != "Cube123").toList();

		cubes.add(new CubeR(null, null, null, null, null, null, null, null, null, null, null, false, false, false, null,
				null));

		return cubes;
	}

	@Override
	protected String cubeDescription(MappingCube cube) {
		String description = super.cubeDescription(cube);
		if (description == null || description.isEmpty()) {
			return "Test";
		}
		return description;
	}

}
