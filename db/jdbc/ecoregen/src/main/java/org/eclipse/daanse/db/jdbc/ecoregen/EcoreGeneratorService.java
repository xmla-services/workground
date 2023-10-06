package org.eclipse.daanse.db.jdbc.ecoregen;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

//@Designate(ocd = Config.class, factory = true)
@Component(immediate = true)
public class EcoreGeneratorService {

	private static Converter CONVERTER = Converters.standardConverter();

//	@Reference
	DataSource dataSource;

	@Activate
	public void activate(Map<String, Object> map) {
		Config config = CONVERTER.convert(map).to(Config.class);
		activate(config);
	}

	public void activate(Config config) {
		try {
			createModel("de.jena", "de.jena", "http://jena.de", null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Resource createModel(String packageName, String nsPrefix, String nsUri, String schema,
			List<String> tableFilters) throws IOException {

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI("test.ecore");
		System.out.println(uri);
		Resource resource = resourceSet.createResource(uri);

		final EPackage basePackage = createPackage(packageName, nsPrefix, nsUri);
		// add our new package to resource contents
		resource.getContents().add(basePackage);
		addAnnotation(basePackage);

		// next, create the row class
		EClass customerRow = createEClass("CustomerRow");
		// add to package before we do anything else
		basePackage.getEClassifiers().add(customerRow);
		
		addAnnotation(customerRow);
		// add our super-class
//		addSuperType(customerRow, otherPackage, "AbstractRow");
		// add our features
		addAttribute(customerRow, "id", EcorePackage.Literals.ESTRING, true, 1, 1);
		addAttribute(customerRow, "firstName", EcorePackage.Literals.ESTRING, false, 0, 1);
		addAttribute(customerRow, "lastName", EcorePackage.Literals.ESTRING, false, 0, 1);

		// next, create the table class
		EClass customers = createEClass("Customers");
		// add to package before we do anything else
		basePackage.getEClassifiers().add(customers);
		// add our super-class
//		addSuperType(customers, otherPackage, "AbstractTable");
		// add our features
		addReference(customers, "rows", customerRow, 0, -1);

		// and at last, we save to standard out. Remove the first argument to save to
		// file specified in pathToOutputFile
		resource.save(System.out, Collections.emptyMap());

		return resource;

	}

	private void addAnnotation(EModelElement eModelElement) {
		final EAnnotation annotation= EcoreFactory.eINSTANCE.createEAnnotation();
		// always add to container first
		eModelElement.getEAnnotations().add(annotation);
		annotation.setSource("foo");
		

		annotation.getDetails().put("doc","text");


	}
	
	private void addAttribute(EClass customerRow, String name, EClassifier type, boolean isId, int lowerBound,
			int upperBound) {
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		// always add to container first
		customerRow.getEStructuralFeatures().add(attribute);
		attribute.setName(name);
		attribute.setEType(type);
		attribute.setID(isId);
		attribute.setLowerBound(lowerBound);
		attribute.setUpperBound(upperBound);
	}

	private void addReference(EClass customerRow, String name, EClassifier type, int lowerBound, int upperBound) {
		final EReference reference = EcoreFactory.eINSTANCE.createEReference();
		// always add to container first
		customerRow.getEStructuralFeatures().add(reference);
		reference.setName(name);
		reference.setEType(type);
		reference.setLowerBound(lowerBound);
		reference.setUpperBound(upperBound);
	}

	private EPackage createPackage(final String name, final String prefix, final String uri) {
		final EPackage epackage = EcoreFactory.eINSTANCE.createEPackage();
		epackage.setName(name);
		epackage.setNsPrefix(prefix);
		epackage.setNsURI(uri);
		return epackage;
	}

	private EClass createEClass(final String name) {
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(name);
		return eClass;
	}

	private void addSuperType(EClass customerRow, EPackage ddlPackage, String name) {
		final EClass eSuperClass = (EClass) ddlPackage.getEClassifier(name);
		customerRow.getESuperTypes().add(eSuperClass);
	}

}
