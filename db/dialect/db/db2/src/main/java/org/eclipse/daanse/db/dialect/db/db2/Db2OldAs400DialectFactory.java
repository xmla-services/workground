package org.eclipse.daanse.db.dialect.db.db2;

import java.sql.Connection;
import java.util.function.Function;

import org.eclipse.daanse.db.dialect.api.DialectFactory;
import org.eclipse.daanse.db.dialect.db.common.AbstractDialectFactory;
import org.osgi.service.component.annotations.Component;

import aQute.bnd.annotation.spi.ServiceProvider;

@ServiceProvider(value = DialectFactory.class, attribute = { "database.dialect.type:String='DB2_OLD_AS400'",
    "database.product:String='DB2_OLD_AS400'" })
@Component(service = DialectFactory.class)
public class Db2OldAs400DialectFactory extends AbstractDialectFactory<Db2OldAs400Dialect> {
    private static final String SUPPORTED_PRODUCT_NAME = "DB2_OLD_AS400";

    @Override
    public boolean isSupportedProduct(String productName, String productVersion, Connection connection) {
        return SUPPORTED_PRODUCT_NAME.equalsIgnoreCase(productVersion);
    }

    @Override
    public Function<Connection, Db2OldAs400Dialect> getConstructorFunction() {
        return Db2OldAs400Dialect::new;
    }
}
