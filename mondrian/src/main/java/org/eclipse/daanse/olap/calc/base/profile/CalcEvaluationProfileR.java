/*
* Copyright (c) 2023 Contributors to the Eclipse Foundation.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/

package org.eclipse.daanse.olap.calc.base.profile;

import java.time.Instant;
import java.util.Map;

import org.eclipse.daanse.olap.calc.api.profile.CalcEvaluationProfile;

public record CalcEvaluationProfileR(Instant start, Instant end, Object evaluationResult,
		Map<String, Object> additionalValues) implements CalcEvaluationProfile {

}