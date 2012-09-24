package org.vamdc.kida.xsams;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.junit.Test;
import org.vamdc.kida.SourceDup;
import org.vamdc.kida.dao.Biblio;
import org.vamdc.xsams.schema.AuthorType;

public class BiblioSourceTest {

	DataContext context = DataContext.createDataContext();
	
	@Test
	public void testBibSourceConstructor(){
		
		for (Object source:getBiblio(getBiblioQuery())){
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
	
	@Test
	public void testBibSourceDup(){
		boolean remap=false;
		for (Object source:getBiblio(getBiblioQuery())){
			Biblio src = (Biblio) source;
			if (!SourceDup.INSTANCE.getBiblio(src.getId()).getId().equals(src.getId())){
				System.out.println("remap"+src.getId()+src.getTitle()+" to "+SourceDup.INSTANCE.getBiblio(src.getId()).getId());
				remap=true;
			}
		}
		if (!remap)
			fail("No items were remapped");
	}

	private List getBiblio(SelectQuery query) {
		return context.performQuery(query);
	}

	private SelectQuery getBiblioQuery() {
		SelectQuery query = new SelectQuery(Biblio.class);
		query.addPrefetch("bibliography");
		query.addPrefetch("bibliography1");
		query.addPrefetch("bibliography2");
		return query;
	}
}
