/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.model.jaxb;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingRole;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingSchemaGrant;
import org.eclipse.daanse.olap.rolap.dbmapper.model.api.MappingUnion;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Role", propOrder = { "schemaGrants", "union" })
public class RoleImpl extends AbstractMainElement implements MappingRole {

	@XmlElement(name = "SchemaGrant", type = SchemaGrantImpl.class)
	protected List<MappingSchemaGrant> schemaGrants;
	@XmlElement(name = "Union", required = true, type = UnionImpl.class)
	protected MappingUnion union;

	@Override
	public List<MappingSchemaGrant> schemaGrants() {
		if (schemaGrants == null) {
			schemaGrants = new ArrayList<>();
		}
		return this.schemaGrants;
	}

	@Override
	public MappingUnion union() {
		return union;
	}

	public void setUnion(MappingUnion value) {
		this.union = value;
	}

	public void setSchemaGrants(List<MappingSchemaGrant> schemaGrants) {
		this.schemaGrants = schemaGrants;
	}
}
