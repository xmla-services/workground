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

import static com.mysql.cj.conf.PropertyDefinitions.PROPERTY_CATEGORIES;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.metatype.AttributeDefinition;

import com.mysql.cj.conf.PropertyDefinition;

final class MySqlAttributeDefinition implements AttributeDefinition, Comparable<MySqlAttributeDefinition> {

	static final Comparator<PropertyDefinition<?>> COMPARATOR;
	static {
		Map<String, Integer> categoryOrderMap = new HashMap<>();
		for (int i = 0; i < PROPERTY_CATEGORIES.length; i++) {
			categoryOrderMap.put(PROPERTY_CATEGORIES[i], i);
		}
		Comparator<PropertyDefinition<?>> inCategoryOrder = Comparator.comparingInt(PropertyDefinition::getOrder);
		Comparator<PropertyDefinition<?>> categoryOrder = Comparator.comparing(PropertyDefinition::getCategory,
				(s1, s2) -> {
					if (s1.equals(s1)) {
						return 0;
					}
					Integer o1 = categoryOrderMap.getOrDefault(s1, -1);
					Integer o2 = categoryOrderMap.getOrDefault(s2, -1);
					return o1.compareTo(o2);
				});
		COMPARATOR = categoryOrder.thenComparing(inCategoryOrder);
	}

	private PropertyDefinition<?> definition;

	public MySqlAttributeDefinition(PropertyDefinition<?> value) {
		this.definition = value;
	}

	@Override
	public String validate(String value) {
		try {
			definition.parseObject(value, null);
		} catch (RuntimeException e) {
			return e.getMessage();
		}
		return "";
	}

	@Override
	public int getType() {
		switch (definition.getPropertyKey()) {
		case PASSWORD:
			return PASSWORD;
		case PORT:
			return INTEGER;
		default: {
			return STRING;
		}
		}
	}

	@Override
	public String[] getOptionValues() {
		return definition.getAllowableValues();
	}

	@Override
	public String[] getOptionLabels() {
		String[] values = getOptionValues();
		if (values == null) {
			return null;
		}
		return Arrays.stream(values).map(s -> String.format("%%s", s)).toArray(String[]::new);
	}

	@Override
	public String getName() {
		return String.format("%%s", definition.getPropertyKey().getKeyName());
	}

	@Override
	public String getID() {
		return definition.getPropertyKey().getKeyName();
	}

	@Override
	public String getDescription() {
		return String.format("[%s] %s", definition.getCategory(), definition.getDescription());
	}

	@Override
	public String[] getDefaultValue() {
		Object defaultValue = definition.getDefaultValue();
		if (defaultValue == null) {
			return new String[0];
		}
		return new String[] { defaultValue.toString() };
	}

	@Override
	public int getCardinality() {
		return 1;
	}

	@Override
	public int compareTo(MySqlAttributeDefinition o) {
		return COMPARATOR.compare(definition, o.definition);
	}
}