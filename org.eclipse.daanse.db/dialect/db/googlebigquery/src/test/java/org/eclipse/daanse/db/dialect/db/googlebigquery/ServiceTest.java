package org.eclipse.daanse.db.dialect.db.googlebigquery;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.db.googlebigquery.GoogleBigQueryDialect;
import org.junit.jupiter.api.Test;
import org.osgi.test.common.annotation.InjectService;


public class ServiceTest {
    @Test
    void serviceExists(@InjectService List<Dialect> dialects) throws Exception {

        assertThat(dialects).isNotNull().isNotEmpty().anyMatch(GoogleBigQueryDialect.class::isInstance);
    }
}
