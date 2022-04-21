package org.opencube.junit5.context;

import org.opencube.junit5.dataloader.DataLoader;

public interface LoadableContext extends Context{

	Class<? extends DataLoader> dataLoader();
	
}
