package org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingAnnotation;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "AbstractMainElement", propOrder = { "annotations", "name", "description", "caption" })
@XmlSeeAlso({ ActionImpl.class, CalculatedMemberImpl.class, CalculatedMemberPropertyImpl.class, CubeImpl.class,
		DimensionUsageImpl.class, DrillThroughActionImpl.class, HierarchyImpl.class, LevelImpl.class, MeasureImpl.class,
		NamedSetImpl.class, PrivateDimensionImpl.class, PropertyImpl.class, RoleImpl.class, SchemaImpl.class,
		SharedDimensionImpl.class, VirtualCubeDimensionImpl.class, VirtualCubeImpl.class,
		VirtualCubeMeasureImpl.class, })
public abstract class AbstractMainElement {
	@XmlElement(name = "Annotation", type = AnnotationImpl.class)
	@XmlElementWrapper(name = "Annotations")
	protected List<MappingAnnotation> annotations;
	@XmlAttribute(name = "name", required = true)
	protected String name;
	@XmlAttribute(name = "description")
	protected String description;
	@XmlAttribute(name = "caption")
	protected String caption;

	public AbstractMainElement() {
		super();
	}


    public List<MappingAnnotation> annotations() {
        if (annotations == null) {
            annotations = new ArrayList<>();
        }
        return this.annotations;
    }

	public String caption() {
		return caption;
	}

	public String description() {
		return description;
	}

	public String name() {
		return name;
	}

	public void setAnnotations(List<MappingAnnotation> value) {
		this.annotations = value;
	}

	public void setCaption(String value) {
		this.caption = value;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public void setName(String value) {
		this.name = value;
	}

}