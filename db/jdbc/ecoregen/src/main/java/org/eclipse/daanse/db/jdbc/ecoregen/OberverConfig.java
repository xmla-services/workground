package org.eclipse.daanse.db.jdbc.ecoregen;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import biz.aQute.scheduler.api.CronJob;

@ObjectClassDefinition(name = "DatabaseSchemaObserver", description = "")
public interface OberverConfig {
	public static String HOURLY = "0 * * * * *";

	@AttributeDefinition(name = "databaseSchema", description = "name of the schema in the database", required = true)
	default String database_schema() {
		return null;
	}

	@AttributeDefinition(name = "ecoreUrl", description = "url of the ecore file that could be used to compare the database with")
	default String ecore_url() {
		return null;
	}

	@AttributeDefinition(name = CronJob.CRON, description = "The cron expression, specifies when the Observation-Job should run", defaultValue = HOURLY)
	default String cron() {
		return HOURLY;
	}

}
