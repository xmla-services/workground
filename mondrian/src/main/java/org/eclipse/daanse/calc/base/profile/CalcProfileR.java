package org.eclipse.daanse.calc.base.profile;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.daanse.calc.api.profile.CalcEvaluationProfile;
import org.eclipse.daanse.calc.api.profile.CalculationProfile;

import mondrian.calc.ResultStyle;
import mondrian.olap.type.Type;

public record CalcProfileR(String name, Class<?> clazz, Type type, ResultStyle resultStyle, Optional<Instant> start,
		Optional<Instant> end, Map<String, Object> additionalValues, List<CalcEvaluationProfile> evaluationProfiles,
		List<CalculationProfile> childProfiles) implements CalculationProfile {

	@Override
	public Duration duration() {
		if (start.isEmpty()) {
			return Duration.ofNanos(0);
		}
		if (end.isEmpty()) {
			return Duration.ofNanos(-1);
		}
		return Duration.between(start.get(), end.get());

	};

}
