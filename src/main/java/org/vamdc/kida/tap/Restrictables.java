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

	//TODO: rewrite query mapping
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
		put(Restrictable.IonCharge,"species.charge");
		put(Restrictable.MoleculeChemicalName,"species.description");
		put(Restrictable.Inchi,"species.inchi");
		put(Restrictable.InchiKey,"species.inchiKey");
		put(Restrictable.MoleculeStoichiometricFormula,"species.formula");
		
		put(Restrictable.AtomSymbol,"species.formula");
		
		//put(Restrictable.CollisionIAEACode,"toTypeChannel");
	}};
	
	public static Map<Restrictable,String> getAliasedChannelMap(String prefix){
		Map<Restrictable,String> cps = new HashMap<Restrictable,String>();
		if (prefix!=null){
			cps.put(Restrictable.IonCharge, prefix+".specie.charge");
			cps.put(Restrictable.MoleculeChemicalName, prefix+".specie.description");
			cps.put(Restrictable.Inchi, prefix+".specie.inchi");
			cps.put(Restrictable.InchiKey, prefix+".specie.inchiKey");
			cps.put(Restrictable.MoleculeStoichiometricFormula, prefix+".specie.formula");

			cps.put(Restrictable.AtomSymbol, prefix+".specie.formula");
			
			cps.put(Restrictable.CollisionIAEACode, prefix+".typeChannel");
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
			cps.put(Restrictable.MoleculeStoichiometricFormula, prefix+".formula");
			cps.put(Restrictable.AtomSymbol, prefix+".formula");
		}
		return cps;
	}
	
	
}
