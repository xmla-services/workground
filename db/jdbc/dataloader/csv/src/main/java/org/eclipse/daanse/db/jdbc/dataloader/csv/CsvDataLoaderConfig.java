package org.eclipse.daanse.db.jdbc.dataloader.csv;

import org.eclipse.daanse.util.io.watcher.api.PathListenerConfig;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

@ObjectClassDefinition()
@PathListenerConfig
public @interface CsvDataLoaderConfig {

	/**
	 * @return Line Separator Detection Enabled
	 */
	@AttributeDefinition(description = "lineSeparatorDetectionEnabled")
	boolean lineSeparatorDetectionEnabled() default true;

	/**
	 * @return Null Value
	 */
	@AttributeDefinition(description = "nullValue")
	String nullValue() default "NULL";

	/**
	 * @return Quote Escape
	 */
	@AttributeDefinition(description = "quoteEscape")
	char quoteEscape() default '\\';

	/**
	 * @return Quote
	 */
	@AttributeDefinition(description = "quote")
	char quote() default '"';

	/**
	 * @return Delimiter
	 */
	@AttributeDefinition(description = "delimiter")
	String delimiter() default ",";

	/**
	 * @return Encoding default UTF-8
	 */
	@AttributeDefinition(description = "encoding", options = { @Option(value = "UTF_8"), @Option(value = "US_ASCII"),
			@Option(value = "ISO_8859_1"), @Option(value = "UTF_16BE"), @Option(value = "UTF_16LE"),
			@Option(value = "UTF_16") })
	String encoding() default "UTF_8";

	/**
	 * @return Quote Detection Enabled
	 */
	@AttributeDefinition(description = "quoteDetectionEnabled")
	boolean quoteDetectionEnabled() default true;

	/**
	 * @return Clear Table Before Load Data
	 */
	@AttributeDefinition(description = "clearTableBeforeLoad")
	boolean clearTableBeforeLoad() default true;

	/**
	 * @return Batch Size. Use Batch operation if dialect support it
	 */
	@AttributeDefinition(description = "batchSize", defaultValue = "1000")
	int batchSize() default 1000;

}
