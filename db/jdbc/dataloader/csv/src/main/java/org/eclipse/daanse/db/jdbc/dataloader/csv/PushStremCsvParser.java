package org.eclipse.daanse.db.jdbc.dataloader.csv;

import com.univocity.parsers.csv.CsvParserSettings;

public class PushStremCsvParser {
	public PushStremCsvParser() {
		CsvParserSettings settings = null;
		com.univocity.parsers.csv.CsvParser parser = new com.univocity.parsers.csv.CsvParser(settings);
	}

}
