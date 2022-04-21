package org.opencube.junit5;

import java.util.Locale;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import mondrian.resource.MondrianResource;

public class MondrianRuntimeExtension
		implements ExecutionCondition, BeforeAllCallback, ExtensionContext.Store.CloseableResource {

	@Override
	public void beforeAll(ExtensionContext context) {
		System.out.println("### MondrianRuntimeExtension:beforeAll");
		defineLocale();
	}

	private void defineLocale() {
		
		Locale.setDefault(Locale.US);
//		MondrianResource.setThreadLocale(Locale.US);
	}

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {

		try {
			DockerSupport.available();
			return ConditionEvaluationResult.enabled("Docker is available");
		} catch (Throwable ex) {
			return ConditionEvaluationResult.disabled("Docker is not available", ex.getMessage());
		}

	}

	@Override
	public void close() throws Throwable {

	}

}
