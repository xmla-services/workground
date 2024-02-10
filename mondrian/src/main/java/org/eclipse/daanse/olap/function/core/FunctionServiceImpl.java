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
package org.eclipse.daanse.olap.function.core;

import static java.util.Collections.synchronizedList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.daanse.olap.api.function.FunctionMetaData;
import org.eclipse.daanse.olap.api.function.FunctionResolver;
import org.eclipse.daanse.olap.api.function.FunctionService;
import org.eclipse.daanse.olap.operation.api.OperationAtom;
import org.eclipse.daanse.olap.operation.api.PlainPropertyOperationAtom;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ServiceScope;

import mondrian.olap.fun.FunTableImpl.FunctionAtomCompareKey;
import mondrian.olap.fun.GlobalFunTable;

//@Designate(ocd = Config.class, factory = true)
@Component(service = FunctionService.class, scope = ServiceScope.SINGLETON)
public class FunctionServiceImpl implements FunctionService {
//    private static final Converter CONVERTER = Converters.standardConverter();
//	private Config config;

    private final List<FunctionResolver> resolvers = new ArrayList<>();
    private Map<FunctionAtomCompareKey, List<FunctionResolver>> mapNameToResolvers = new HashMap<>();

    private List<FunctionMetaData> representativeFunctionMetaDatas = new ArrayList<>();
    private List<String> reservedWords = new ArrayList<>();
    private List<String> propertyWords = new ArrayList<>();
    private GlobalFunTable oldfunctiontable;

    public FunctionServiceImpl() {
        oldfunctiontable = GlobalFunTable.instance();
        oldfunctiontable.init();
    }

    @Activate
    public FunctionServiceImpl(Map<String, Object> configuration) {
//		this.config = CONVERTER.convert(coniguration).to(Config.class);

        oldfunctiontable.getResolvers().forEach(this::addResolver);

    }

    @Deactivate
    public void deactivate() {
//		config = null;
    }

    @Override
    @Reference(service = FunctionResolver.class, cardinality = ReferenceCardinality.MULTIPLE)
    public void addResolver(FunctionResolver resolver) {

        resolvers.add(resolver);
        reInitialize();
    }

    @Override
    @Reference(service = FunctionResolver.class, cardinality = ReferenceCardinality.MULTIPLE)
    public void removeResolver(FunctionResolver resolver) {

        resolvers.add(resolver);
        reInitialize();
    }

    private void reInitialize() {

        final List<FunctionMetaData> newRepresentativeFunctionMetaDatas = synchronizedList(new ArrayList<>());
        final List<String> newPropertyWords = synchronizedList(new ArrayList<>(100));
        final List<String> newReservedWords = synchronizedList(new ArrayList<>(100));
        final Map<FunctionAtomCompareKey, List<FunctionResolver>> newMapNameToResolvers = new ConcurrentHashMap<>(100);

        resolvers.stream().parallel().forEach(resolver -> {

            newRepresentativeFunctionMetaDatas.addAll(resolver.getRepresentativeFunctionMetaDatas());

            OperationAtom functionAtom = resolver.getFunctionAtom();

            if (functionAtom instanceof PlainPropertyOperationAtom) {
                newPropertyWords.add(functionAtom.name().toUpperCase());
            }

            final List<String> reservedWordsInner = resolver.getReservedWords();
            for (String reservedWord : reservedWordsInner) {
                newReservedWords.add(reservedWord.toUpperCase());
            }

            FunctionAtomCompareKey key = new FunctionAtomCompareKey(resolver.getFunctionAtom());

            List<FunctionResolver> resolversToAdd = newMapNameToResolvers.computeIfAbsent(key, k -> new ArrayList<>());
            resolversToAdd.add(resolver);

        });

        representativeFunctionMetaDatas = newRepresentativeFunctionMetaDatas;
        propertyWords = newPropertyWords;
        reservedWords = newReservedWords;
        mapNameToResolvers = newMapNameToResolvers;
    }

    @Override
    public boolean isReservedWord(String word) {
        if (word == null) {
            return false;
        }
        return reservedWords.contains(word.toUpperCase());
    }

    @Override
    public boolean isProperty(String name) {
        if (name == null) {
            return false;
        }
        return propertyWords.contains(name.toUpperCase());
    }

    @Override
    public List<String> getReservedWords() {
        return reservedWords;
    }

    @Override
    public List<FunctionResolver> getResolvers() {

        return List.copyOf(resolvers);
    }

    @Override
    public List<FunctionResolver> getResolvers(OperationAtom operationAtom) {

        FunctionAtomCompareKey key = new FunctionAtomCompareKey(operationAtom);
        List<FunctionResolver> matchingResolvers = mapNameToResolvers.get(key);
        if (matchingResolvers == null) {
            matchingResolvers = Collections.emptyList();
        }
        return matchingResolvers;
    }

    @Override
    public List<FunctionMetaData> getFunctionMetaDatas() {
        return representativeFunctionMetaDatas;
    }

    @Override
    public void defineFunctions(FunctionTableCollector collector) {

    }
}
