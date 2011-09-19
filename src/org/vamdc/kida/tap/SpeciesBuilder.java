package org.vamdc.kida.tap;

import java.util.List;
import java.util.Vector;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.kida.Specie;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.tapservice.query.QueryMapper;
import org.vamdc.xsams.common.ChemicalElementType;
import org.vamdc.xsams.common.DataType;
import org.vamdc.xsams.schema.ParticleNameType;
import org.vamdc.xsams.species.atoms.AtomType;
import org.vamdc.xsams.species.atoms.AtomicIonType;
import org.vamdc.xsams.species.atoms.IsotopeParametersType;
import org.vamdc.xsams.species.atoms.IsotopeType;
import org.vamdc.xsams.species.molecules.MolecularChemicalSpeciesType;
import org.vamdc.xsams.species.molecules.MolecularPropertiesType;
import org.vamdc.xsams.species.molecules.MolecularStateType;
import org.vamdc.xsams.species.molecules.MoleculeType;
import org.vamdc.xsams.species.molecules.ReferencedTextType;
import org.vamdc.xsams.species.particles.ParticleType;
import org.vamdc.xsams.util.IDs;
import org.vamdc.xsams.util.XsamsUnits;

public class SpeciesBuilder {

	public static void buildSpecies(RequestInterface request,
			Vector<Integer> tabSpeciesId) {
		Expression specieExpr = QueryMapper.mapTree(request.getRestrictsTree(),
				Restrictables.SpeciesPathSpec);
		SelectQuery atquery = new SelectQuery(Specie.class, specieExpr);
		List<Specie> atms = (List<Specie>) request.getCayenneContext()
				.performQuery(atquery);

		for (Specie sp : atms) {
			if (sp.isASpecialSpecies()) {
				request.getXsamsroot().addElement(
						SpeciesBuilder.writeParticle(sp, request));
			} else if (sp.isAnAtom()) {
				request.getXsamsroot().addElement(
						SpeciesBuilder.writeAtom(sp, request));
			} else {
				request.getXsamsroot().addElement(
						SpeciesBuilder.writeSpecies(sp));
			}
			tabSpeciesId.addElement(new Integer(sp.getId()));

		}

	}
	
	
	public static ParticleType writeParticle(Specie sp,RequestInterface myrequest )
	{
		ParticleType myParticle = new ParticleType();
		if ( sp.getCommonName().equals("Photon") || sp.getCommonName().equals("PHOTON")  || sp.getCommonName().equals("photon") )
		{
			myParticle.setName(ParticleNameType.PHOTON);
		}
		else if ( sp.getCommonName().equals("e") || sp.getCommonName().equals("e-")  || sp.getCommonName().equals("E") )
		{
			myParticle.setName(ParticleNameType.ELECTRON);
		}
		
		myParticle.setStateID(IDs.getStateID(0, sp.getId()));
		 
		return myParticle;

		
	}
	
	public static AtomType writeAtom(Specie sp, RequestInterface myrequest) {
		AtomType myatom = new AtomType();

		ChemicalElementType element = new ChemicalElementType();
		element.setElementSymbol(sp.getFormula());
		if ( sp.getNuclearCharge() != null )
			element.setNuclearCharge(sp.getNuclearCharge());
		myatom.setChemicalElement(element);

		IsotopeType isot = new IsotopeType();
		myatom.getIsotopes().add(isot);

		AtomicIonType iost = new AtomicIonType();
		isot.getIons().add(iost);
		iost.setIonCharge(sp.getCharge().intValue());
		iost.setInChIKey(sp.getInchiKey());
		iost.setSpeciesID(IDs.getSpecieID(sp.getId()));

		

		IsotopeParametersType ipt = new IsotopeParametersType();
		isot.setIsotopeParameters(ipt);
		ipt.setMassNumber((int) Math.round(sp.getMass()));
		ipt.setMass(new DataType(sp.getMass(), XsamsUnits.AMU));
		

		return myatom;

	}
	
	/**
	 * @param sp
	 *            a species object
	 */
	public static MoleculeType writeSpecies(Specie sp) {
		MoleculeType mt = new MoleculeType();
		MolecularChemicalSpeciesType mcst = new MolecularChemicalSpeciesType();
		mcst.setInChI(sp.getInchi());

		mcst.setOrdinaryStructuralFormula(new ReferencedTextType(sp.getCommonNameLatex())); 
		mcst.setStoichiometricFormula(sp.getFormulaSorted());
		if ( sp.getDescription() != null && sp.getDescription().length() > 0)
		{
			mcst.setChemicalName(new ReferencedTextType(sp.getDescription()));  
		}

		mcst.setURLFigure("http://kida.obs.u-bordeaux1.fr/images/species/"
				+ sp.getId() + ".jpg");

		if (sp.getCas() != null && sp.getCas().length() != 0) {
			mcst.setCASRegistryNumber(new ReferencedTextType(sp.getCas()));
		}
		mcst.setInChIKey(sp.getInchiKey());

		MolecularPropertiesType mpt = new MolecularPropertiesType();
		mpt.setMolecularWeight(new DataType(sp.getMass(), XsamsUnits.AMU)) ;
		mcst.setStableMolecularProperties(mpt);

		mt.setMolecularChemicalSpecies(mcst);

		MolecularStateType ms = new MolecularStateType();
		//ms.setStateID(IDs.getStateID(sp.getId(), 0)); // species id, 0
		// ms.setDescription("description");
		//mt.getMolecularStates().add(ms);

		mt.setSpeciesID(IDs.getSpecieID(sp.getId()));

		return mt;
	}
}
