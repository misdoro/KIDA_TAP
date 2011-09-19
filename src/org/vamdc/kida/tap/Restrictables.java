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


	public static Collection<Restrictable> Restricts= new ArrayList<Restrictable>(){{
		add(Restrictable.MoleculeIonCharge);
		add(Restrictable.MoleculeChemicalName);
		add(Restrictable.MoleculeInchi);
		add(Restrictable.MoleculeInchiKey);
	}};
	

	

	/**
	 * Species paths 
	 * */
	public final static Map<Restrictable,String> SpeciesPathSpec = new HashMap<Restrictable,String>(){{
		put(Restrictable.MoleculeIonCharge,"charge");
		put(Restrictable.MoleculeChemicalName,"commonName");
		put(Restrictable.MoleculeInchi,"inchi");
		put(Restrictable.MoleculeInchiKey,"inchiKey");
		
	}};
	
	/**
	 * Channel paths
	 */
	public final static Map<Restrictable,String> ChannelPathSpec = new HashMap<Restrictable,String>(){{
		put(Restrictable.MoleculeIonCharge,"specieRel.charge");
		put(Restrictable.MoleculeChemicalName,"specieRel.commonName");
		put(Restrictable.MoleculeInchi,"specieRel.inchi");
		put(Restrictable.MoleculeInchiKey,"specieRel.inchiKey");
	}};
	
	
	
}
