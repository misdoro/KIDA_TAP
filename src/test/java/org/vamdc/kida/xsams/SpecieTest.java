package org.vamdc.kida.xsams;

import static org.junit.Assert.*;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.junit.Test;
import org.vamdc.kida.dao.Specie;

public class SpecieTest {
	
	DataContext context = DataContext.createDataContext();
	
	@Test
	public void testSpecieConstructors(){
		SelectQuery query = new SelectQuery(Specie.class);
		query.addPrefetch("specieHasElements");
		
		for (Object element:context.performQuery(query)){
			Specie sp = (Specie) element;
			assertNotNull(sp.getId());
			
			if (sp.isASpecialSpecies()) {
				KidaParticle particle = new KidaParticle(sp);
				assertNotNull(particle.getSpeciesID());
			} else if (sp.isAnAtom()) {
				KidaAtom atom = new KidaAtom(sp);
				assertNotNull(atom.getSpeciesID());
			} else {
				KidaMolecule molecule = new KidaMolecule(sp);
				assertNotNull(molecule.getSpeciesID());
			}
		}
	}
	
}
