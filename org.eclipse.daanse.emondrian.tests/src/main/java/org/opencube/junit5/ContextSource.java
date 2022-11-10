package org.opencube.junit5;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.opencube.junit5.dataloader.DataLoader;
import org.opencube.junit5.dbprovider.DatabaseProvider;
import org.opencube.junit5.propupdator.PropertyUpdater;

@Target({
	ElementType.ANNOTATION_TYPE, ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ArgumentsSource(ContextArgumentsProvider.class)
@ExtendWith(MondrianRuntimeExtension.class)
public @interface ContextSource {

    Class<? extends DatabaseProvider>[] database()default {};

	Class<? extends DataLoader> dataloader() ;
	
    Class<? extends PropertyUpdater>[] propertyUpdater()default {};

    
}