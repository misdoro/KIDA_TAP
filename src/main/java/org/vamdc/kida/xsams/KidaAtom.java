package org.vamdc.kida.xsams;


import org.vamdc.kida.dao.Specie;
import org.vamdc.xsams.common.ChemicalElementType;
import org.vamdc.xsams.common.DataType;
import org.vamdc.xsams.species.atoms.AtomType;
import org.vamdc.xsams.species.atoms.AtomicIonType;
import org.vamdc.xsams.species.atoms.IsotopeParametersType;
import org.vamdc.xsams.species.atoms.IsotopeType;
import org.vamdc.xsams.util.IDs;
import org.vamdc.xsams.util.XsamsUnits;

public class KidaAtom extends AtomType{

	public KidaAtom(Specie atom){
		
		String symbol = atom.getElement().get(0).getSymbol();
		
		this.setChemicalElement(
				new ChemicalElementType(
						atom.getNuclearCharge(),symbol));

		IsotopeType isot = new IsotopeType();
		this.getIsotopes().add(isot);

		AtomicIonType iost = new AtomicIonType();
		isot.getIons().add(iost);
		iost.setIonCharge(atom.getCharge().intValue());
		
		iost.setInChI(atom.getInchi());
		iost.setInChIKey(atom.getInchiKey());
		iost.setSpeciesID(IDs.getSpecieID(atom.getId()));

		IsotopeParametersType ipt = new IsotopeParametersType();
		isot.setIsotopeParameters(ipt);
		ipt.setMassNumber((int)Math.round(atom.getMass()));
		ipt.setMass(new DataType(atom.getMass(), XsamsUnits.AMU));
		
	}
	
}
