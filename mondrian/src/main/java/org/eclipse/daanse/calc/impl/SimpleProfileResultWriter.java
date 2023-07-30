/*
 * This software is subject to the terms of the Eclipse Public License v1.0
 * Agreement, available at the following URL:
 * http://www.eclipse.org/legal/epl-v10.html.
 * You must accept the terms of that agreement to use this software.
 *
 * Copyright (c) 2002-2021 Hitachi Vantara..  All rights reserved.
 */

package org.eclipse.daanse.calc.impl;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.daanse.calc.api.CalcEvaluationProfile;
import org.eclipse.daanse.calc.api.CalcProfile;

public class SimpleProfileResultWriter {
	private static final int DEFAULT_INDENT = 4;
	private final PrintWriter printWriter;
	private final int indent;
	private int linePrefixLength;
	
	private String currentIndentPrefix="";

	public SimpleProfileResultWriter(PrintWriter printWriter) {
		this(printWriter, DEFAULT_INDENT);
	}

	public SimpleProfileResultWriter(PrintWriter printWriter, int indent) {
		this.printWriter = printWriter;
		this.indent = indent;
	}

	public void write(CalcProfile profile) {
		write(profile, false);
	}

	private void write(CalcProfile profile, boolean doIndent) {

		if (doIndent) {
			indent();
		}
		printWriter.print(currentIndentPrefix);
		printWriter.print(profile.name());

		printWriter.print("(");

		printWriter.print("name");
		printWriter.print("=");
		printWriter.print(profile.name());
		printWriter.print(", ");
		
		printWriter.print("class");
		printWriter.print("=");
		printWriter.print(profile.clazz());
		printWriter.print(", ");
		
		printWriter.print("type");
		printWriter.print("=");
		printWriter.print(profile.type());
		printWriter.print(", ");
		
		printWriter.print("resultStyle");
		printWriter.print("=");
		printWriter.print(profile.resultStyle());
		printWriter.print(", ");
	
		List<CalcEvaluationProfile> evaluationProfiles=	profile.evaluationProfiles();
		printWriter.print("callCount");
		printWriter.print("=");
		printWriter.print(evaluationProfiles.size());
		printWriter.print(", ");
		
		printWriter.print("callMillis");
		printWriter.print("=");
		printWriter.print(profile.duration().toMillis());



		profile.additionalValues().entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
			printWriter.print(", ");
			printWriter.print(entry.getKey());
			printWriter.print("=");
			printWriter.print(entry.getValue());
		});

		printWriter.print(")");

		printWriter.println();

		for (final CalcProfile childCalc : profile.childProfiles()) {
			write(childCalc, true);

		}
		if (doIndent) {
			outdent();
		}
	}

	private void indent() {
		linePrefixLength += indent;
		
		calcCurrentIndentPrefix();
	}



	private void outdent() {
		linePrefixLength -= indent;
		calcCurrentIndentPrefix();
	}
	private void calcCurrentIndentPrefix() {
		currentIndentPrefix=" ".repeat(linePrefixLength);	
	}

}
