package org.eclipse.daanse.olap.xmla.bridge;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

 class ContextsSupplyerImplTest {

	
		
		@Test
		void get_Empty() throws Exception {
			ContextsSupplyerImpl csi= new ContextsSupplyerImpl(List.of());
			assertThat(csi.get()).isNotNull().isEmpty();
		}
		
		@Test
		void get_Null() throws Exception {
			ContextsSupplyerImpl csi= new ContextsSupplyerImpl(null);
			assertThat(csi.get()).isNotNull().isEmpty();
		}
		
		
		
		@Test
		void get_1() throws Exception {
			ContextsSupplyerImpl csi= new ContextsSupplyerImpl(null);
			
//			Predicate<Context> p=null;
//			csi.get(p);
		}
}
