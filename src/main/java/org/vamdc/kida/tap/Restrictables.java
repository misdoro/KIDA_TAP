package org.vamdc.kida.tap;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.kida.query.KidaParticleMapper;
import org.vamdc.kida.query.QueryMapper;
import org.vamdc.kida.query.QueryMapperImpl;
import org.vamdc.kida.query.SimpleKeywordMapper;

/*
 * Here we put all supported restrictables
 * VOSICapabilities gets them from here also!
 * */
public class Restrictables {

	public final static int QUERY_SPECIES=0;
	public final static int QUERY_CHANNEL=1;
	
	public final static QueryMapper queryMapper= new QueryMapperImpl(){{
		
		this.addMapper(
				new SimpleKeywordMapper(Restrictable.IonCharge)
					.addNewPath("charge")
					.addNewPath("alias.specie.charge"));
		
		this.addMapper(
				new SimpleKeywordMapper(Restrictable.MoleculeChemicalName)
					.addNewPath("description")
					.addNewPath("alias.specie.description"));
		
		this.addMapper(
				new SimpleKeywordMapper(Restrictable.Inchi)
					.addNewPath("inchi")
					.addNewPath("alias.specie.inchi"));
		
		this.addMapper(
				new SimpleKeywordMapper(Restrictable.InchiKey)
					.addNewPath("inchiKey")
					.addNewPath("alias.specie.inchiKey"));
		
		this.addMapper(
				new SimpleKeywordMapper(Restrictable.MoleculeStoichiometricFormula)
					.addNewPath("formula")
					.addNewPath("alias.specie.formula"));
		
		this.addMapper(
				new SimpleKeywordMapper(Restrictable.AtomSymbol)
					.addNewPath("formula")
					.addNewPath("alias.specie.formula"));
		
		this.addMapper(
				new KidaParticleMapper(Restrictable.ParticleName)
					.addNewPath("formula")
					.addNewPath("alias.specie.formula"));
		
		
		
	}};
	
	
}
