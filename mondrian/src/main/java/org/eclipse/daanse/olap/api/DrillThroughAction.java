package org.eclipse.daanse.olap.api;

import java.util.List;

import org.eclipse.daanse.olap.api.element.OlapElement;

public interface DrillThroughAction extends OlapAction{

	boolean getIsDefault();

	List<OlapElement> getOlapElements();

}