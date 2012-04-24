package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Specie;
import org.vamdc.xsams.common.DataType;
import org.vamdc.xsams.species.molecules.MolecularChemicalSpeciesType;
import org.vamdc.xsams.species.molecules.MolecularPropertiesType;
import org.vamdc.xsams.species.molecules.MoleculeType;
import org.vamdc.xsams.species.molecules.ReferencedTextType;
import org.vamdc.xsams.util.IDs;
import org.vamdc.xsams.util.XsamsUnits;

public class KidaMolecule extends MoleculeType{

	public KidaMolecule(Specie molecule){
		MolecularChemicalSpeciesType mcst = new MolecularChemicalSpeciesType();
		this.setMolecularChemicalSpecies(mcst);
		mcst.setInChI(molecule.getInchi());

		mcst.setOrdinaryStructuralFormula(new ReferencedTextType(molecule.getCommonNameLatex())); 
		mcst.setStoichiometricFormula(molecule.getFormulaSorted());
		if ( molecule.getDescription() != null && molecule.getDescription().length() > 0)
		{
			mcst.setChemicalName(new ReferencedTextType(molecule.getDescription()));  
		}

		mcst.setURLFigure("http://kida.obs.u-bordeaux1.fr/images/species/"
				+ molecule.getId() + ".jpg");

		if (molecule.getCas() != null && molecule.getCas().length() != 0) {
			mcst.setCASRegistryNumber(new ReferencedTextType(molecule.getCas()));
		}
		mcst.setInChIKey(molecule.getInchiKey());

		MolecularPropertiesType mpt = new MolecularPropertiesType();
		mcst.setStableMolecularProperties(mpt);
		mpt.setMolecularWeight(new DataType(molecule.getMass(), XsamsUnits.AMU)) ;
		

		

		this.setSpeciesID(IDs.getSpecieID(molecule.getId()));
	}
	
}
