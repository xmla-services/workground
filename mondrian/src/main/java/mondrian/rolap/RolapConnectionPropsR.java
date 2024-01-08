package mondrian.rolap;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public record RolapConnectionPropsR(List<String> roles, boolean useSchemaPool, Locale locale, long pinSchemaTimeout,
		TimeUnit pinSchemaTimeoutUnit, Optional<String> aggregateScanSchema, Optional<String> aggregateScanCatalog)
		implements RolapConnectionProps {

	public RolapConnectionPropsR() {
		this(List.of(), true, Locale.getDefault(), -1, TimeUnit.SECONDS, Optional.empty(), Optional.empty());
	}

}
