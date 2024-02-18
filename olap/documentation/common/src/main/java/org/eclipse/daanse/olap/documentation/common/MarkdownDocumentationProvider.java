package org.eclipse.daanse.olap.documentation.common;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.daanse.olap.api.Context;
import org.eclipse.daanse.olap.documentation.api.ConntextDocumentationProvider;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

@Component(service = ConntextDocumentationProvider.class, scope = ServiceScope.SINGLETON, immediate = true)
public class MarkdownDocumentationProvider extends AbstractContextDocumentationProvider {

	private static String ENTER = System.lineSeparator();

	@Override
	public void createDocumentation(Context context, Path path) throws Exception {

		File file = path.toFile();

		if (file.exists()) {
			Files.delete(path);
		}

		FileWriter writer = new FileWriter(file);
		writer.write("# Documentation");
		writer.write(ENTER);
		writer.write("CatalogName : " + context.getName());
		writer.flush();
		writer.close();

	}

	/*
	 * # General
	 * 
	 * Name: ${ContextName}
	 * 
	 * Description: ${ContextDescription}
	 * 
	 * # Olap Context Details:
	 * 
	 * ## Schemas
	 * 
	 * Overview Table on Schemas (with count of cubes and dimension)
	 * 
	 * ### Schema ${SchemaName}
	 * 
	 * Description: ${SchemaDescription}
	 * 
	 * Overview Table on Public Dimensions
	 * 
	 * Overview Table on Cubes
	 * 
	 * Overview Table on Roles
	 * 
	 * 
	 * #### Public Dimensions
	 * 
	 * Overview Table on Public Dimensions
	 * 
	 * ##### Public Dimension { DimName}
	 * 
	 * Description: ${CubeDescription}
	 * 
	 * ... Hierarchies
	 *
	 * #### Cubes
	 * 
	 * Overview Table on Cubes
	 * 
	 * #### Cubes ${CubeName} #### Cubes ${CubeName}
	 * 
	 * Description: ${CubeDescription}
	 * 
	 * .... Publi
	 * 
	 * #### Roles
	 * 
	 * # SQL Context Details:
	 * 
	 * 
	 * List of all Tables that are used in Olap with column and type and description
	 * in database.
	 * 
	 * 
	 * PRINT_FIRST_N_ROWS
	 * 
	 * 
	 * 
	 * # Checks:
	 * 
	 * errors in Mapping all errors we have in the verifyer
	 *
	 * 
	 * 
	 */

	private static String PRINT_FIRST_N_ROWS = """
				Fact:

					| DIM_KEY  |      VALUE    |
					|----------|:-------------:|
					|   1      |       42      |
					|   2      |       21      |
					|   3      |       84      |

					Level1:

					|   KEY    |     NAME      |
					|----------|:-------------:|
					|   1      |      A        |
					|   2      |      B        |

					Level2:

					|   KEY    |     NAME   |   L1_KEY   |
					|----------|:----------:|:----------:|
					|   1      |      AA    |     1      |
					|   2      |      AB    |     1      |
					|   3      |      BA    |     2      |
			""";

	/**
	 * Step 2
	 * 
	 * 
	 * 
	 * 
	 * # class diagram for lebel properties
	 * https://mermaid.js.org/syntax/classDiagram.html
	 * 
	 * #use ERD Diagrams for sql Table and the joins defines ion olapmapping
	 * https://mermaid.js.org/syntax/entityRelationshipDiagram.html
	 * 
	 * #use class Diagrams for olap Cubes -> Dimensions -> Hirarchies -> levels ->
	 * private Dim
	 * 
	 * each type a custom color https://mermaid.js.org/syntax/classDiagram.html
	 * 
	 * # Analyses Cubes
	 * 
	 * Y-AXIS : rows in fact table
	 * 
	 * X-Axis : number of hierarchies
	 * 
	 * https://mermaid.js.org/syntax/quadrantChart.html
	 * 
	 * 
	 */

}
