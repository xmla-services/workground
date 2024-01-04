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
package org.eclipse.daanse.olap.rolap.aggmatch.jaxb;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;
import mondrian.rolap.aggmatcher.Recognizer;

import java.util.ArrayList;
import java.util.List;

@XmlType(name = "RegexMapper")
@XmlSeeAlso({LevelMap.class, MeasureMap.class, IgnoreMap.class})
public abstract class RegexMapper extends Base {

    /**
     * The unique identifier for this Matcher.
     */
    @XmlElement(name = "id")
    String id;
    /**
     * This is an array of Regex. A match occurs if any one of
     * the Regex matches; it is the equivalent of or-ing the
     * regular expressions together. A candidate string is processed
     * sequentially by each Regex in their document order until
     * one matches. In none match, well, none match.
     */
    @XmlElementRef(name = "regex", type = Regex.class, required = false)
    List<Regex> regexs;

    protected String getTag() {
        return id;
    }

    public void validate(
        final AggRules rules,
        final mondrian.recorder.MessageRecorder msgRecorder
    ) {
        msgRecorder.pushContextName(getName());
        try {

            List<String> templateNames = getTemplateNames();

            for (Regex regex : regexs) {
                regex.validate(rules, templateNames, msgRecorder);
            }

        } finally {
            msgRecorder.popContextName();
        }
    }

    /**
     * This must be defined in derived classes. It returns an array of
     * symbolic names that are the symbolic names allowed to appear
     * in the regular expression templates.
     *
     * @return array of symbol names
     */
    protected abstract List<String> getTemplateNames();

    protected Recognizer.Matcher getMatcher(final String[] names) {

        final List<java.util.regex.Pattern> patterns =
            new ArrayList<>();

        for (Regex regex : regexs) {
            patterns.add(regex.getPattern(names));
        }

        return new Recognizer.Matcher() {
            public boolean matches(String name) {
                for (java.util.regex.Pattern pattern : patterns) {
                    if ((pattern != null) &&
                        pattern.matcher(name).matches()) {

                        if (AggRules.getLogger().isDebugEnabled()) {
                            debug(name, pattern);
                        }

                        return true;
                    }
                }
                return false;
            }

            private void debug(String name, java.util.regex.Pattern p) {
                StringBuilder bf = new StringBuilder(64);
                bf.append("DefaultDef.RegexMapper");
                bf.append(".Matcher.matches:");
                bf.append(" name \"");
                bf.append(name);
                bf.append("\" matches regex \"");
                bf.append(p.pattern());
                bf.append("\"");
                if ((p.flags() &
                    java.util.regex.Pattern.CASE_INSENSITIVE) != 0) {
                    bf.append(" case_insensitive");
                }

                String msg = bf.toString();
                AggRules.getLogger().debug(msg);
            }
        };
    }

    @Override
    protected String getName() {
        return "RegexMapper";
    }
}
