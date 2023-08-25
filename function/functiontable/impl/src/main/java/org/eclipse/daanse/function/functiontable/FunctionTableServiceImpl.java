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
*   SmartCity Jena - initial
*   Stefan Bischof (bipolis.org) - initial
*/
package org.eclipse.daanse.function.functiontable;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

import java.util.List;
import java.util.Map;

@Designate(ocd = Config.class, factory = true)
@Component(service = FunctionTableServiceImpl.class, scope = ServiceScope.SINGLETON)
public class FunctionTableServiceImpl implements FunctionTableService{
    private static final Converter CONVERTER = Converters.standardConverter();
    private Config config;

    @Activate
    public FunctionTableServiceImpl(Map<String, Object> coniguration) {
        this.config = CONVERTER.convert(coniguration)
                .to(Config.class);
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    @Override
    public void addResolver() {
    }

    @Override
    public void removeResolver() {
    }

    @Override
    public boolean isReserved(String s) {
        return false;
    }

    @Override
    public boolean isProperty(String s) {
        return false;
    }

    @Override
    public List<String> getReservedWords() {
        return null;
    }

    @Override
    public List<Resolver> getResolvers() {
        return null;
    }

    @Override
    public List<Resolver> getResolvers(String name, Syntax syntax) {
        return null;
    }

    @Override
    public List<FunInfo> getFunInfoList() {
        return null;
    }
}
