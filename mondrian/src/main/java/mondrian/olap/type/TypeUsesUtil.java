package mondrian.olap.type;

import org.eclipse.daanse.olap.api.element.Cube;
import org.eclipse.daanse.olap.api.element.Dimension;
import org.eclipse.daanse.olap.api.element.Hierarchy;
import org.eclipse.daanse.olap.api.element.Level;

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
