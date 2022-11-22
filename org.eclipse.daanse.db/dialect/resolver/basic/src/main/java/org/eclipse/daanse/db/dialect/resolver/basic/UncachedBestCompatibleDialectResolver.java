package org.eclipse.daanse.db.dialect.resolver.basic;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.sql.DataSource;

import org.eclipse.daanse.db.dialect.api.Dialect;
import org.eclipse.daanse.db.dialect.api.DialectResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implementation of {@link DialectResolver} that:<br>
 *
 * - does not cache <br>
 * - returns Dialect with best ranking - calculated using
 * {@link org.eclipse.daanse.db.dialect.api.Dialect#isCompatible(DataSource)}.
 *
 * @author stbischof
 *
 */
@Component(service = DialectResolver.class)
public class UncachedBestCompatibleDialectResolver implements DialectResolver {

    List<Dialect> dialects = new ArrayList<>();

    @Reference(service = Dialect.class)
    public void bindDialect(Dialect dialect) {
        dialects.add(dialect);
    }

    public void unbindDialect(Dialect dialect) {
        dialects.remove(dialect);
    }

    public void updatedDialect(Dialect dialect) {
        unbindDialect(dialect);
        bindDialect(dialect);
    }

    @Override
    public Dialect resolve(DataSource dataSource) {

        return dialects.parallelStream().map(calcCompatibility(dataSource)).filter(compatibleDialect()).findFirst()
                .map(Entry::getKey).orElse(null);
    }

    private Function<? super Dialect, ? extends Entry<Dialect, Boolean>> calcCompatibility(DataSource dataSource) {
        return dialect ->  new AbstractMap.SimpleEntry(dialect, dialect.isCompatible(dataSource));
    }

    private Predicate<Entry<Dialect, Boolean>> compatibleDialect() {
        return entry -> entry.getValue();
    }

}