package org.vamdc.kida.tap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.vamdc.dictionary.Restrictable;

/*
 * Here we put all supported restrictables
 * VOSICapabilities gets them from here also!
 * */
@SuppressWarnings("serial")
public class Restrictables {

	public static Collection<Restrictable> Restricts = new ArrayList<Restrictable>() {
		{
			add(Restrictable.IonCharge);
			add(Restrictable.MoleculeChemicalName);
			add(Restrictable.Inchi);
			add(Restrictable.InchiKey);
			add(Restrictable.MoleculeStoichiometricFormula);
			
			add(Restrictable.AtomSymbol);
			
			add(Restrictable.ParticleName);
			
			//add(Restrictable.CollisionIAEACode);
		}
	};

	/**
	 * Species paths
	 * */
	public final static Map<Restrictable, String> SpeciesPathSpec = new HashMap<Restrictable, String>() {
		{
			put(Restrictable.IonCharge, "charge");
			//put(Restrictable.MoleculeChemicalName, "commonName");
			put(Restrictable.MoleculeChemicalName, "description");
			put(Restrictable.Inchi, "inchi");
			put(Restrictable.InchiKey, "inchiKey");
			put(Restrictable.MoleculeStoichiometricFormula, "formula"); // warning : C2H2 different of H2C2 OR CCHH
			
			put(Restrictable.AtomSymbol, "formula");

		}
	};

	/**
	 * Channel paths
	 */
	public final static Map<Restrictable,String> ChannelPathSpec = new HashMap<Restrictable,String>(){{
		put(Restrictable.IonCharge,"specieRel.charge");
		//put(Restrictable.MoleculeChemicalName,"specieRel.commonName");
		put(Restrictable.MoleculeChemicalName,"specieRel.description");
		put(Restrictable.Inchi,"specieRel.inchi");
		put(Restrictable.InchiKey,"specieRel.inchiKey");
		put(Restrictable.MoleculeStoichiometricFormula,"formula");
		
		put(Restrictable.AtomSymbol,"specieRel.formula");
		
		//put(Restrictable.CollisionIAEACode,"toTypeChannel");
	}};
	
	public static Map<Restrictable,String> getAliasedChannelMap(String prefix){
		Map<Restrictable,String> cps = new HashMap<Restrictable,String>();
		if (prefix!=null){
			cps.put(Restrictable.IonCharge, prefix+".specieRel.charge");
			//cps.put(Restrictable.MoleculeChemicalName, prefix+".specieRel.commonName");
			cps.put(Restrictable.MoleculeChemicalName, prefix+".specieRel.description");
			cps.put(Restrictable.Inchi, prefix+".specieRel.inchi");
			cps.put(Restrictable.InchiKey, prefix+".specieRel.inchiKey");
			cps.put(Restrictable.MoleculeStoichiometricFormula, prefix+".specieRel.formula");

			cps.put(Restrictable.AtomSymbol, prefix+".specieRel.formula");
			
			cps.put(Restrictable.CollisionIAEACode, prefix+".toTypeChannel");
		}
		return cps;
	}
	
	public static Map<Restrictable,String> getAliasedSpeciesMap(String prefix){
		Map<Restrictable,String> cps = new HashMap<Restrictable,String>();
		if (prefix!=null){
			cps.put(Restrictable.IonCharge, prefix+".charge");
			cps.put(Restrictable.MoleculeChemicalName, prefix+".description");
			cps.put(Restrictable.Inchi, prefix+".inchi");
			cps.put(Restrictable.InchiKey, prefix+".inchiKey");
			cps.put(Restrictable.MoleculeStoichiometricFormula, prefix+".specieRel.formula");
			cps.put(Restrictable.AtomSymbol, prefix+".formula");
		}
		return cps;
	}
	
	
}
