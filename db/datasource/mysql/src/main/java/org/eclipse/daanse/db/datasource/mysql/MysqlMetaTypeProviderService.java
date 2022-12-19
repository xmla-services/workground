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
*   Christoph Läubrich - initial implementation
*/
package org.eclipse.daanse.db.datasource.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.MetaTypeProvider;
import org.osgi.service.metatype.ObjectClassDefinition;

import com.mysql.cj.conf.PropertyDefinition;
import static com.mysql.cj.conf.PropertyDefinitions.*;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.jdbc.JdbcPropertySet;

public abstract class MysqlMetaTypeProviderService implements MetaTypeProvider, ManagedServiceFactory {

	// categories that should not be exposed can be defined here
	private static final Set<String> IGNORED_CATEGORIES = Set.of(CATEGORY_XDEVAPI);

	// property key that are required can be defined here
	private static final Set<PropertyKey> REQUIRED_PROPERTY_KEY = Set.of();
	
	private final class MySqlObjectClassDefinition implements ObjectClassDefinition {

		@Override
		public String getName() {
			return MysqlMetaTypeProviderService.this.getName();
		}

		@Override
		public InputStream getIcon(int size) throws IOException {
			return null;
		}

		@Override
		public String getID() {
			return MysqlMetaTypeProviderService.this.getClass().getName();
		}

		@Override
		public String getDescription() {
			return null;
		}

		@Override
		public AttributeDefinition[] getAttributeDefinitions(int filter) {
			boolean all = filter == ALL;
			boolean required = all || filter == REQUIRED;
			boolean optional = all || filter == OPTIONAL;
			List<MySqlAttributeDefinition> selected = new ArrayList<>();
			for (Entry<PropertyKey, PropertyDefinition<?>> entry : PROPERTY_KEY_TO_PROPERTY_DEFINITION.entrySet()) {
				if (IGNORED_CATEGORIES.contains(entry.getValue().getCategory())) {
					continue;
				}
				if (all || (required && REQUIRED_PROPERTY_KEY.contains(entry.getKey()))
						|| (optional && !REQUIRED_PROPERTY_KEY.contains(entry.getKey()))) {
					selected.add(new MySqlAttributeDefinition(entry.getValue()));
				}
			}
			
			return selected.toArray(AttributeDefinition[]::new);
		}
	}

	@Override
	public ObjectClassDefinition getObjectClassDefinition(String id, String locale) {
		return new MySqlObjectClassDefinition();
	}

	@Override
	public String[] getLocales() {
		return null;
	}

}
