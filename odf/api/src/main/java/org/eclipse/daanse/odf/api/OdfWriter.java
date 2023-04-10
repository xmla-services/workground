package org.eclipse.daanse.odf.api;

import java.io.IOException;
import java.nio.file.Path;

public interface OdfWriter {

    void write(Path targetFilePath, SpreadSheet spreadSheet) throws  IOException;


}
