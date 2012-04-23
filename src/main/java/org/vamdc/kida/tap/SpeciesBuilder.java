package org.vamdc.kida.tap;

import java.util.List;
import java.util.Vector;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.dictionary.VSSPrefix;
import org.vamdc.kida.dao.*;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.tapservice.query.QueryMapper;
import org.vamdc.tapservice.vss2.LogicNode;
import org.vamdc.tapservice.vss2.Prefix;
import org.vamdc.tapservice.vss2.Query;
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
		//SelectQuery atquery = new SelectQuery(Specie.class, specieExpr);
		SelectQuery atquery = getCayenneQuery(request.getQuery());
		System.out.println(atquery);
		List<Specie> atms = (List<Specie>) request.getCayenneContext()
				.performQuery(atquery);
		
		System.out.println(atms);

		for (Specie sp : atms) {
			if (sp.isASpecialSpecies()) {
				request.getXsamsManager().addElement(
						SpeciesBuilder.writeParticle(sp, request));
			} else if (sp.isAnAtom()) {
				request.getXsamsManager().addElement(
						SpeciesBuilder.writeAtom(sp, request));
			} else {
				request.getXsamsManager().addElement(
						SpeciesBuilder.writeSpecies(sp));
			}
			tabSpeciesId.addElement(new Integer(sp.getId()));

		}

	}
	
	
	private static SelectQuery getCayenneQuery(Query query) {
		Expression myExpression=null,collExpr=null;
		
		myExpression = QueryMapper.mapTree(query.getRestrictsTree(),Restrictables.SpeciesPathSpec);
		
		for (Prefix pref:query.getPrefixes())
		{
			VSSPrefix prefix = pref.getPrefix();
			int index = pref.getIndex();
			String strPrefix = prefix.name()+index;
			System.out.println(strPrefix);
			
			LogicNode collider = null;
			if ( prefix.name().equals("REACTANT"))
			{
				collider = query.getPrefixedTree(VSSPrefix.REACTANT, index);
				
			}
			if (  prefix.name().equals("PRODUCT"))
			{
				collider = query.getPrefixedTree(VSSPrefix.PRODUCT, index);
			}
			if ( collider == null ) // if prefix is not reactant or product ... we skip it
				continue;
			
			collExpr = QueryMapper.mapTree(collider, Restrictables.SpeciesPathSpec);
			if (collider!=null && collExpr!=null)//If yes, add it also
				myExpression = myExpression.orExp(collExpr);
			
			
			
		}
		SelectQuery q = new SelectQuery(Specie.class, myExpression);
		return q;
			
	}


	public static ParticleType writeParticle(Specie sp,RequestInterface myrequest )
	{
		ParticleType myParticle = new ParticleType();
		if ( sp.getCommonName().equals("Photon"))
		{
			myParticle.setName(ParticleNameType.PHOTON);
		}
		else if ( sp.getCommonName().equals("e-"))
		{
			myParticle.setName(ParticleNameType.ELECTRON);
		}
		else if ( sp.getCommonName().equals("CR")){
			myParticle.setName(ParticleNameType.PHOTON);
			myParticle.setComments("Cosmic rays");
		}
		else if (sp.getCommonName().equals("CRP"))
			myParticle.setName(ParticleNameType.COSMIC);
		
		myParticle.setSpeciesID(IDs.getSpecieID(sp.getId()));
		 
		return myParticle;

		
	}
	
	public static AtomType writeAtom(Specie sp, RequestInterface myrequest) {
		AtomType myatom = new AtomType();


		ChemicalElementType element = new ChemicalElementType(sp.getNuclearCharge(),sp.getFormula());
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
	
	public static Expression getExpression(RequestInterface myrequest) {
		//Query for energy tables:
		Expression myExpression=null,collExpr=null,tgtExpr=null;
		//Check if we have target tree defined
		LogicNode target = myrequest.getQuery().getPrefixedTree(VSSPrefix.REACTANT, 0);
		tgtExpr = QueryMapper.mapTree(target, Restrictables.SpeciesPathSpec);
		
		if (target!=null && tgtExpr!=null){
			myExpression = tgtExpr;
			
			//Check if we have collider tree defined		
			LogicNode collider = myrequest.getQuery().getPrefixedTree(VSSPrefix.REACTANT, 1);
			collExpr = QueryMapper.mapTree(collider, Restrictables.SpeciesPathSpec);
			if (collider!=null && collExpr!=null)//If yes, add it also
				myExpression = myExpression.orExp(collExpr);
		}else{
			//No prefixes are defined, so sad, let's try unprefixed VSS1 mode
			myExpression = QueryMapper.mapTree(myrequest.getRestrictsTree(),Restrictables.SpeciesPathSpec);
		}
		Expression etExpression = myExpression;
		return etExpression;
	}
}
