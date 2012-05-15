package org.vamdc.kida.xsams;

import static org.junit.Assert.*;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.junit.Test;
import org.vamdc.kida.dao.Channel;

public class CollisionTest {
	
	DataContext context = DataContext.createDataContext();
	
	@Test
	public void testCollisionConstructors(){
		SelectQuery q = new SelectQuery(Channel.class);
		q.addPrefetch("species");
		for (Object ch:context.performQuery(q)){
			Channel channel = (Channel) ch;
			assertNotNull(channel.getId());
			if (channel.isValid()){
				
			}
		}
	}
	
}
