package org.vamdc.kida.tap;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.kida.query.KidaAtomSymbolMapper;
import org.vamdc.kida.query.KidaMethodMapper;
import org.vamdc.kida.query.KidaParticleMapper;
import org.vamdc.kida.query.KidaReactionMapper;
import org.vamdc.kida.query.KidaTemperatureMapper;
import org.vamdc.tapservice.querymapper.QueryMapper;
import org.vamdc.tapservice.querymapper.QueryMapperImpl;
import org.vamdc.tapservice.querymapper.KeywordMapperImpl;

/*
 * Here we put all supported restrictables
 * VOSICapabilities gets them from here also!
 * */
public class Restrictables {

	public final static int QUERY_SPECIES=0;
	public final static int QUERY_CHANNEL=1;

	/**
	 * Query index 0 must have path relevant from Species table,
	 * query index 1 gives restriction on species for channel table, replacing "alias" with "channel_has_specie"
	 */
	public final static QueryMapper queryMapper= new QueryMapperImpl(){{

		this.addMapper(
				new KeywordMapperImpl(Restrictable.IonCharge)
				.addNewPath("charge")
				.addNewPath("alias.specie.charge")
				);

		this.addMapper(
				new KeywordMapperImpl(Restrictable.MoleculeChemicalName)
				.addNewPath("description")
				.addNewPath("alias.specie.description")
				);

		this.addMapper(
				new KeywordMapperImpl(Restrictable.Inchi)
				.addNewPath("inchi")
				.addNewPath("alias.specie.inchi")
				);

		this.addMapper(
				new KeywordMapperImpl(Restrictable.InchiKey)
				.addNewPath("inchiKey")
				.addNewPath("alias.specie.inchiKey")
				);
		
		this.addMapper(new KeywordMapperImpl(Restrictable.VAMDCSpeciesID)
				.addNewPath("inchiKey")
				.addNewPath("alias.specie.inchiKey"));

		this.addMapper(
				new KeywordMapperImpl(Restrictable.MoleculeStoichiometricFormula)
				.addNewPath("formula")
				.addNewPath("alias.specie.formula")
				);

		this.addMapper(
				new KidaAtomSymbolMapper(Restrictable.AtomSymbol)
				.addNewPath("formula")
				.addNewPath("alias.specie.formula")
				);

		this.addMapper(
				new KidaParticleMapper(Restrictable.ParticleName)
				.addNewPath("formula")
				.addNewPath("alias.specie.formula")
				);

		this.addMapper(
				new KidaTemperatureMapper(Restrictable.EnvironmentTemperature)
				.addNewPath("")
				.addNewPath("channelValues.validityRange")
				);
		
		this.addMapper(
				new KidaReactionMapper(Restrictable.CollisionCode)
				.addNewPath("")
				.addNewPath("typeChannel.id")
				
				);
		this.addMapper(
				new KidaMethodMapper(Restrictable.MethodCategory)
				.addNewPath("")
				.addNewPath("channelValues.method"));

	}};


}
