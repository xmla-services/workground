package mondrian.olap.type;

import org.eclipse.daanse.olap.api.model.Cube;
import org.eclipse.daanse.olap.api.model.Dimension;
import org.eclipse.daanse.olap.api.model.Hierarchy;
import org.eclipse.daanse.olap.api.model.Level;

public class TypeUsesUtil {

	
	static boolean usesHierarchy(Object object,Hierarchy hierarchy, boolean definitely) {
		
		if(object instanceof Cube) {
			return false;
		}
		return false;
		
	}
	
	public boolean usesDimension(Object object,Dimension dimension, boolean definitely) {
		
		if(object instanceof Cube) {
			return false;
		}
        return false;
    }
	

	public Dimension getDimension(Object object) {
		if(object instanceof Cube) {
			return null;
		}
        return null;
    }

	public Hierarchy getHierarchy(Object object) {
		if(object instanceof Cube) {
			return null;
		}
        return null;
    }

	public Level getLevel(Object object) {
		if(object instanceof Cube) {
			return null;
		}
        return null;
    }

}
