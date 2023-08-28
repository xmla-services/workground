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
package org.eclipse.daanse.function;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.converter.Converter;
import org.osgi.util.converter.Converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Designate(ocd = Config.class, factory = true)
@Component(service = FunctionServiceImpl.class, scope = ServiceScope.SINGLETON)
public class FunctionServiceImpl implements FunctionService {
    private static final Converter CONVERTER = Converters.standardConverter();
    private Config config;

    /**
     * Maps the upper-case name of a function plus its
     * {@link Syntax} to an array of
     * {@link FunctionResolver} objects for that name.
     */
    private Map<Pair<String, Syntax>, List<FunctionResolver>> mapNameToResolvers;
    private Set<String> reservedWordSet = new HashSet<>();
    private List<String> reservedWordList = new ArrayList<>();
    private Set<String> propertyWords = new HashSet<>();
    private List<FunInfo> funInfoList = new ArrayList<>();
    private final List<FunctionResolver> resolverList = new ArrayList<>();

    @Activate
    public FunctionServiceImpl(Map<String, Object> coniguration) {
        this.config = CONVERTER.convert(coniguration)
                .to(Config.class);
    }

    @Deactivate
    public void deactivate() {
        config = null;
    }

    @Override
    @Reference(service = FunctionResolver.class, cardinality = ReferenceCardinality.MULTIPLE )
    public void addResolver(FunctionResolver resolver) {
        funInfoList.add(FunInfo.make(resolver));
        if (resolver.getSyntax() == Syntax.Property) {
            propertyWords.add(resolver.getName().toUpperCase());
        }
        resolverList.add(resolver);
        final String[] reservedWordsInner = resolver.getReservedWords();
        Collections.addAll(reservedWordList, reservedWordsInner);
    }

    @Override
    @Reference(service = FunctionResolver.class, cardinality = ReferenceCardinality.MULTIPLE )
    public void removeResolver(FunctionResolver resolver) {
        FunInfo funInfo = FunInfo.make(resolver);
        funInfoList.removeIf(f -> f.compareTo(funInfo) == 0);
        resolverList.removeIf(r -> r.compareTo(resolver) == 0);
        if (resolver.getSyntax() == Syntax.Property) {
            final String pw = resolver.getName().toUpperCase();
            // check if other resolvers have property words
            if (resolverList.stream().noneMatch(r -> r.getSyntax() == Syntax.Property
                && pw.equals(r.getName().toUpperCase()))) {
                propertyWords.remove(pw);
            }
        }
        final String[] reservedWordsInner = resolver.getReservedWords();
        for (String reservedWord : reservedWordsInner) {
            if (resolverList.stream().noneMatch(r ->
                Stream.of(r.getReservedWords()).anyMatch(w -> w.equals(reservedWord)))) {
                reservedWordList.remove(reservedWord);
            }
        }
    }

    @Override
    public boolean isReserved(String s) {
        return reservedWordSet.contains(s.toUpperCase());
    }

    @Override
    public boolean isProperty(String s) {
        return reservedWordSet.contains(s.toUpperCase());
    }

    @Override
    public List<String> getReservedWords() {
        return reservedWordList;
    }

    @Override
    public List<FunctionResolver> getResolvers() {
        final List<FunctionResolver> list = new ArrayList<>();
        for (List<FunctionResolver> resolvers : mapNameToResolvers.values()) {
            list.addAll(resolvers);
        }
        return list;
    }

    @Override
    public List<FunctionResolver> getResolvers(String name, Syntax syntax) {
        final List<FunctionResolver> list = new ArrayList<>();
        for (List<FunctionResolver> resolvers : mapNameToResolvers.values()) {
            list.addAll(resolvers);
        }
        return list;
    }

    @Override
    public List<FunInfo> getFunInfoList() {
        return funInfoList;
    }
}
