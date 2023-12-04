package org.eclipse.daanse.db.jdbc.dataloader.csv;

import java.nio.charset.StandardCharsets;

import org.eclipse.daanse.util.io.watcher.api.PathListenerConfig;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public interface CsvDataLoadServiceConfig  extends PathListenerConfig{

    /**
     * @return Line Separator Detection Enabled
     */
    @AttributeDefinition(description = "lineSeparatorDetectionEnabled")
    default Boolean lineSeparatorDetectionEnabled() {
        return true;
    }

    /**
     * @return Null Value
     */
    @AttributeDefinition(description = "nullValue")
    default String nullValue() {
        return "NULL";
    }

    /**
     * @return Quote Escape
     */
    @AttributeDefinition(description = "quoteEscape")
    default char quoteEscape() {
        return '\\';
    }

    /**
     * @return Quote
     */
    @AttributeDefinition(description = "quote")
    default char quote() {
        return '"';
    }

    /**
     * @return Delimiter
     */
    @AttributeDefinition(description = "delimiter")
    default String delimiter() {
        return ",";
    }

    /**
     * @return CSV Folder Files Path
     */
    @AttributeDefinition(description = "csvFolderPath")
    default String csvFolderPath() {
        return "/";
    }

    /**
     * @return CSV File Suffix
     */
    @AttributeDefinition(description = "csvFileSuffix")
    default String csvFileSuffix() {
        return ".csv";
    }

    /**
     * @return CSV File Prefix
     */
    @AttributeDefinition(description = "csvFilePrefix")
    default String csvFilePrefix() {
        return "";
    }

    /**
     * @return Encoding default UTF-8
     */
    @AttributeDefinition(description = "encoding")
    default String encoding() {
        return StandardCharsets.UTF_8.name();
    }

    /**
     * @return Quote Detection Enabled
     */
    @AttributeDefinition(description = "quoteDetectionEnabled")
    default Boolean quoteDetectionEnabled() {
        return true;
    }

    /**
     * @return Clear Table Before Load Data
     */
    @AttributeDefinition(description = "clearTableBeforeLoad")
    default Boolean clearTableBeforeLoad() {
        return true;
    }

    /**
     * @return Batch Size. Use Batch operation if dialect support it
     */
    @AttributeDefinition(description = "batchSize")
    default int batchSize() {
        return 1000;
    }
}
