package org.vamdc.kida.xsams;

import static org.junit.Assert.*;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.junit.Test;
import org.vamdc.kida.dao.Biblio;
import org.vamdc.xsams.schema.AuthorType;

public class BiblioSourceTest {

	DataContext context = DataContext.createDataContext();
	
	@Test
	public void testBibSourceConstructor(){
		SelectQuery query = new SelectQuery(Biblio.class);
		query.addPrefetch("bibliography");
		query.addPrefetch("bibliography1");
		query.addPrefetch("bibliography2");
		
		for (Object source:context.performQuery(query)){
			assertTrue(source instanceof Biblio);
			BiblioSource sourceXML = new BiblioSource((Biblio) source);
			assertNotNull(sourceXML);
			assertNotNull(sourceXML.getSourceID());
			for (AuthorType aut:sourceXML.getAuthors().getAuthors()){
				if (aut.getName().contains("\n")){
					System.out.println(sourceXML.getSourceID());
					System.out.println(aut.getName());
					fail(aut.getName());
				}
			}
		}
	}
}
