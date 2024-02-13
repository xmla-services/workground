/*
// This software is subject to the terms of the Eclipse Public License v1.0
// Agreement, available at the following URL:
// http://www.eclipse.org/legal/epl-v10.html.
// You must accept the terms of that agreement to use this software.
//
// Copyright (C) 2021 Sergei Semenkov
// All Rights Reserved.
*/

package mondrian.rolap;

import org.eclipse.daanse.olap.api.OlapAction;

public abstract class AbstractRolapAction implements OlapAction {
	private final String name;
	private final String caption;
	private final String description;

	public AbstractRolapAction(String name, String caption, String description) {
		this.name = name;
		this.caption = caption;
		this.description = description;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getCaption() {
		return this.caption;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
}
