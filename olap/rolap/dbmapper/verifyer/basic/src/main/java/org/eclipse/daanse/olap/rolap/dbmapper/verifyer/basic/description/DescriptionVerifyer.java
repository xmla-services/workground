/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   SmartCity Jena, Stefan Bischof - initial
 *
 */
package org.eclipse.daanse.olap.rolap.dbmapper.verifyer.basic.description;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.eclipse.daanse.olap.rolap.dbmapper.api.Schema;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.VerificationResult;
import org.eclipse.daanse.olap.rolap.dbmapper.verifyer.api.Verifyer;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Designate(ocd = DescriptionVerifierConfig.class, factory = true)
@Component(service = Verifyer.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class DescriptionVerifyer implements Verifyer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DescriptionVerifyer.class);
    public static final Converter CONVERTER = Converters.standardConverter();

    private DescriptionVerifierConfig config;

    @Activate
    public void activate(Map<String, Object> configMap) {
        this.config = CONVERTER.convert(configMap)
                .to(DescriptionVerifierConfig.class);
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    DescriptionWalker descriptionWalker;

    @Override
    public List<VerificationResult> verify(Schema schema, DataSource dataSource) {

        return new DescriptionWalker(config).checkSchema(schema);
    }

}