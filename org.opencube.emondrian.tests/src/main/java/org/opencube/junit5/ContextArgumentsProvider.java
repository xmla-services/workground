package org.opencube.junit5;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.opencube.junit5.context.LoadableContext;
import org.opencube.junit5.dataloader.DataLoader;
import org.opencube.junit5.dbprovider.DatabaseProvider;

import aQute.bnd.annotation.Cardinality;
import aQute.bnd.annotation.spi.ServiceConsumer;

@ServiceConsumer(cardinality = Cardinality.MULTIPLE, value = DatabaseProvider.class)
public class ContextArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<ContextSource> {
	private ContextSource contextSource;
	// TODO: cache all Loader-DatabaseProvider pair an reuse them
	private static List<Class<DataLoader>> loadedLoaders = new ArrayList<>();

	private static Map<Class<? extends DatabaseProvider>,Map<Class<? extends DataLoader>, DataSource>> store = new HashMap<>();

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {

		Stream<DatabaseProvider> providers;

		Class<? extends DatabaseProvider>[] dbHandlerClasses = contextSource.database();
		if (dbHandlerClasses == null || dbHandlerClasses.length == 0) {
			providers = ServiceLoader.load(DatabaseProvider.class, this.getClass().getClassLoader()).stream()
					.map(Provider::get);
		} else {
			providers = Stream.of(dbHandlerClasses).map(c -> {
				try {
					return c.getConstructor().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			});
		}
		// TODO: parallel
		Stream<? extends Arguments> args = providers.parallel().map(dbp -> {

			DataSource dataSource = null;
			Class<? extends DatabaseProvider> clazzProvider = dbp.getClass();

			
			if (!store.containsKey(clazzProvider)) {
				store.put(clazzProvider, new HashMap<>());
			}else {
				
			}

			List<Object> aaa = new ArrayList<>();
			
			Optional<AnnotatedElement> oElement = extensionContext.getElement();
			if (oElement.isPresent()) {
				if (oElement.get() instanceof Method) {
					Method method = (Method) oElement.get();
					for (Parameter param : method.getParameters()) {
						if (LoadableContext.class.isAssignableFrom(param.getType())) {
							Class<LoadableContext> loadableContextClass = (Class<LoadableContext>) param.getType();

							LoadableContext context;

							try {
								context = (LoadableContext) loadableContextClass.getConstructor().newInstance();

								Class<? extends DataLoader> dataLoaderClass = context.dataLoader();

								Map<Class<? extends DataLoader>, DataSource> loaders=store.get(clazzProvider);
								if (loaders.containsKey(dataLoaderClass)) {
									dataSource=loaders.get(dataLoaderClass);
								}else {
									dataSource = dbp.activate();
									DataLoader dataLoader = dataLoaderClass.getConstructor().newInstance();
									dataLoader.loadData(dataSource);
									loaders.put(dataLoaderClass, dataSource);
								}



							} catch (Exception e) {

								e.printStackTrace();
								throw new RuntimeException(e);
							}

							context.init(dataSource);

							aaa.add(context);
						}
					}
				}
			}
			return Arguments.of(aaa.toArray());
		});

		return args;

	}

	@Override
	public void accept(ContextSource annotation) {
		this.contextSource = annotation;

	}

}