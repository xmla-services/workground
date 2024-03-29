/*
* This software is subject to the terms of the Eclipse Public License v1.0
* Agreement, available at the following URL:
* http://www.eclipse.org/legal/epl-v10.html.
* You must accept the terms of that agreement to use this software.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package mondrian.i18n;

import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mondrian.olap.MondrianProperties;
import mondrian.olap.Util;
import mondrian.spi.DynamicSchemaProcessor;
import mondrian.spi.impl.FilterDynamicSchemaProcessor;

/**
 * Schema processor which helps localize data and metadata.
 *
 * @author arosselet
 * @since August 26, 2005
 */
public class LocalizingDynamicSchemaProcessor
    extends FilterDynamicSchemaProcessor
    implements DynamicSchemaProcessor
{
    private static final Logger LOGGER =
        LoggerFactory.getLogger(LocalizingDynamicSchemaProcessor.class);

    /** Creates a new instance of LocalizingDynamicSchemaProcessor */
    public LocalizingDynamicSchemaProcessor() {
    }

    private ResourceBundle bundle;

    /**
     * Regular expression for variables.
     */
    private static final Pattern pattern = Pattern.compile("(%\\{.*?\\})");

    /**
     * Populates the bundle with the given resource.
     *
     * <p>The name of the property file is typically the name of a class, as
     * per {@link ResourceBundle#getBundle(String)}. However, for backwards
     * compatibility, the name can contain slashes (which are converted to
     * dots) and end with ".properties" (which is removed). Therefore
     * "com/acme/MyResource.properties" is equivalent to
     * "com.acme.MyResource".
     *
     * @see MondrianProperties#LocalePropFile
     *
     * @param propFile The name of the property file
     */
    void populate(String propFile) {
        if (propFile.endsWith(".properties")) {
            propFile =
                propFile.substring(
                    0,
                    propFile.length() - ".properties".length());
        }
        try {
            bundle = ResourceBundle.getBundle(
                propFile,
                Util.parseLocale(locale),
                getClass().getClassLoader());
        } catch (Exception e) {
            LOGGER.warn(
                new StringBuilder("Mondrian: Warning: no suitable locale file found for locale '")
                    .append(locale)
                    .append("'").toString(),
                e);
        }
    }

    private void loadProperties() {
        String propFile = MondrianProperties.instance().LocalePropFile.get();
        if (propFile != null) {
            populate(propFile);
        }
    }

    private void applyLocale( Util.PropertyList connectInfo ) {
        setLocale( connectInfo.get("Locale") == null
            ? Locale.getDefault().toString()
            : connectInfo.get("Locale"));
    }

    private String applyReplacement( String content ) {
        if (bundle != null) {
            content = doRegExReplacements(content);
        }
        LOGGER.debug(content);
        return content;
    }

    @Override
	public String filter( String schemaUrl, Util.PropertyList connectInfo, InputStream stream) throws Exception {
        applyLocale(connectInfo);
        loadProperties();
        return applyReplacement(super.filter(schemaUrl, connectInfo, stream));
    }

    @Override
	public String filter( String catalog, Util.PropertyList connectInfo ) {
        applyLocale(connectInfo);
        loadProperties();
        return applyReplacement(catalog);
    }

    private String doRegExReplacements( String schema ) {
        // As of JDK 1.5, cannot use StringBuilder - appendReplacement requires
        // the antediluvian StringBuffer.
        StringBuffer intlSchema = new StringBuffer();
        Matcher match = pattern.matcher(schema);
        String key;
        while (match.find()) {
            key = extractKey(match.group());
            int start = match.start();
            int end = match.end();

            try {
                String intlProperty = bundle.getString(key);
                if (intlProperty != null) {
                    match.appendReplacement(intlSchema, intlProperty);
                }
            } catch (MissingResourceException e) {
                LOGGER.error(new StringBuilder("Missing resource for key [").append(key).append("]").toString(), e);
            } catch (NullPointerException e) {
                LOGGER.error(
                    new StringBuilder("missing resource key at substring(").append(start).append(",").append(end)
                    .append( ")").toString(),
                    e);
            }
        }
        match.appendTail(intlSchema);
        return intlSchema.toString();
    }

    private String extractKey(String group) {
        // removes leading '%{' and tailing '%' from the matched string
        // to obtain the required key
        return group.substring(2, group.length() - 1);
    }

    /**
     * Property locale.
     */
    private String locale;

    /**
     * Returns the property locale.
     *
     * @return Value of property locale.
     */
    public String getLocale() {
        return this.locale;
    }

    /**
     * Sets the property locale.
     *
     * @param locale New value of property locale.
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }
}
