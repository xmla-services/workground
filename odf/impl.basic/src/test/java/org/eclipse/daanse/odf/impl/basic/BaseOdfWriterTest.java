package org.eclipse.daanse.odf.impl.basic;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.daanse.odf.api.OdfWriter;
import org.eclipse.daanse.odf.api.SpreadSheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BaseOdfWriterTest {

    @TempDir
    Path tempPath;

    Path file;

    @BeforeEach
    void beforeEach() throws IOException {
        file = Files.createTempFile(tempPath, "test", "ods");

    }

    @Test
    void testName() throws Exception {
        SpreadSheet spreadSheet = mock(SpreadSheet.class);
        OdfWriter writer = new BaseOdfWriter();
        writer.write(file, spreadSheet);
    }
}
