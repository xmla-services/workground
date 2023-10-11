package org.eclipse.daanse.db.jdbc.ecoregen;

import java.util.Map;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;

public class EcoreCreateUtil {
	public static void addAnnotation(EModelElement eModelElement) {
		addAnnotation(eModelElement, "dummy", Map.of("documentation", "", "documentation.en", ""));
	}

	public static void addAnnotation(EModelElement eModelElement, String source, Map<String, String> details) {
		final EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		// always add to container first
		eModelElement.getEAnnotations().add(annotation);
		annotation.setSource(source);
		EMap<String, String> annotationDetails = annotation.getDetails();

		details.forEach((k, v) -> {
			annotationDetails.put(k, v);
		});

	}

	public static void addAttribute(EClass customerRow, String name, EClassifier type, boolean isId, int lowerBound,
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

	public static void addReference(EClass customerRow, String name, EClassifier type, int lowerBound, int upperBound) {
		final EReference reference = EcoreFactory.eINSTANCE.createEReference();
		// always add to container first
		customerRow.getEStructuralFeatures().add(reference);
		reference.setName(name);
		reference.setEType(type);
		reference.setLowerBound(lowerBound);
		reference.setUpperBound(upperBound);
	}

	public static EPackage createPackage(final String name, final String prefix, final String uri) {
		final EPackage epackage = EcoreFactory.eINSTANCE.createEPackage();
		epackage.setName(name);
		epackage.setNsPrefix(prefix);
		epackage.setNsURI(uri);
		return epackage;
	}

	public static EClass createEClass(final String name) {
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(name);
		return eClass;
	}

	public static void addSuperType(EClass customerRow, EPackage ddlPackage, String name) {
		final EClass eSuperClass = (EClass) ddlPackage.getEClassifier(name);
		customerRow.getESuperTypes().add(eSuperClass);
	}
}
