package org.eclipse.daanse.olap.rolap.dbmapper.provider.sample.foodmart.xml;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true)
public class Activator {

    @Reference
    ConfigurationAdmin ca;
    private Configuration c;

    @Activate
    public void activate(BundleContext bc) throws IOException {
        c = ca.createFactoryConfiguration(
                "org.eclipse.daanse.olap.rolap.dbmapper.provider.xml.XmlDbMappingSchemaProvider", "?");

        URL url = bc.getBundle()
                .getEntry("/FoodMart.xml");

        Hashtable<String, Object> ht = new Hashtable<>();
        ht.put("url", url.toString());
        c.update(ht);
    }

    @Deactivate
    public void deactivate() throws IOException {
        c.delete();

    }

}
