package org.eclipse.daanse.sql.dialect.resolver.basic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.sql.DataSource;

import org.eclipse.daanse.sql.dialect.api.Dialect;
import org.eclipse.daanse.sql.dialect.api.DialectResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implementation of {@link DialectResolver} that:<br>
 *
 * - does not cache <br>
 * - returns Dialect with best ranking - calculated using
 * {@link org.eclipse.daanse.sql.dialect.api.Dialect#compatibility(DataSource)}.
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

        return dialects.parallelStream()
                .map(calcCompatibility(dataSource))
                .filter(compatibleDialect())
                .sorted(comparedByRanking())
                .findFirst()
                .map(Entry::getKey)
                .orElse(null);
    }

    private Predicate<Entry<Dialect, Integer>> compatibleDialect() {
        return entry -> entry.getValue() >= 0;
    }

    private Comparator<Entry<Dialect, Integer>> comparedByRanking() {
        return (entry1, entry2) -> entry1.getValue() - entry2.getValue();
    }

    private Function<? super Dialect, ? extends Entry<Dialect, Integer>> calcCompatibility(DataSource dataSource) {
        return dialect -> Map.entry(dialect, dialect.compatibility(dataSource));
    }

}