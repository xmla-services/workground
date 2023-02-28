package org.eclipse.daanse.odf.impl.basic;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.daanse.odf.api.OdfWriter;
import org.eclipse.daanse.odf.api.SpreadSheet;
import org.eclipse.daanse.odf.xml.manifest.Manifest;

public class BaseOdfWriter implements OdfWriter {

    private final String MIMETYPE = "application/vnd.oasis.opendocument.spreadsheet";
    String ZIP_ENTRY_NAME_MANIFEST = "META-INF/manifest.xml";
    String ZIP_ENTRY_NAME_MIMETYPE = "mimetype";

    String ZIP_ENTRY_NAME_CONTENT = "content.xml";
    String ZIP_ENTRY_NAME_STYLES = "styles.xml";

    @Override
    public void write(Path targetFilePath, SpreadSheet spreadSheet) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(targetFilePath.toFile()));) {
            zos.write(null);

            addManifest(zos, spreadSheet);
            addMimeType(zos, spreadSheet);
            addSpreadsheet(zos, spreadSheet);
            addSettingsStyle(zos, spreadSheet);

        }

    }

    private void addSettingsStyle(ZipOutputStream zos, SpreadSheet spreadSheet) throws IOException {
        Supplier<byte[]> byteSupplier = () -> new byte[] {};
        
        addEntry(zos, ZIP_ENTRY_NAME_STYLES, byteSupplier);
    }

    private void addSpreadsheet(ZipOutputStream zos, SpreadSheet spreadSheet) throws IOException {
        Supplier<byte[]> byteSupplier = () -> new byte[] {};
        addEntry(zos, ZIP_ENTRY_NAME_CONTENT, byteSupplier);
    }

    private void addMimeType(ZipOutputStream zos, SpreadSheet spreadSheet) throws IOException {
        Supplier<byte[]> byteSupplier = MIMETYPE::getBytes;
        addEntry(zos, ZIP_ENTRY_NAME_MIMETYPE, byteSupplier);
    }

    private void addManifest(ZipOutputStream zos, SpreadSheet spreadSheet) throws IOException {
        Supplier<byte[]> byteSupplier = manifest();
        addEntry(zos, ZIP_ENTRY_NAME_MANIFEST, byteSupplier);
    }

    private Supplier<byte[]> manifest() {
        Manifest m=new Manifest();
        
        return null;
    }

    private static void addEntry(ZipOutputStream zos, String fileName, Supplier<byte[]> byteSupplier)
            throws IOException {
        ZipEntry e = new ZipEntry(fileName);
        zos.putNextEntry(e);
        zos.write(byteSupplier.get());
        zos.closeEntry();
    }

}
