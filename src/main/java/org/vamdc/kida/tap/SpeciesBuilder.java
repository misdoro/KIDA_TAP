package org.vamdc.kida.tap;

import java.util.List;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.dictionary.Requestable;
import org.vamdc.dictionary.VSSPrefix;
import org.vamdc.kida.dao.Specie;
import org.vamdc.kida.xsams.KidaAtom;
import org.vamdc.kida.xsams.KidaMolecule;
import org.vamdc.kida.xsams.KidaParticle;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.tapservice.query.QueryMapper;
import org.vamdc.tapservice.vss2.LogicNode;
import org.vamdc.tapservice.vss2.Prefix;
import org.vamdc.tapservice.vss2.Query;
import org.vamdc.xsams.util.SpeciesInterface;

public class SpeciesBuilder {

	public static void loadSpecies(RequestInterface request) {

		@SuppressWarnings("unchecked")
		List<Specie> species = (List<Specie>) request.getCayenneContext()
				.performQuery(
						getSpeciesQuery(request.getQuery())
						);

		for (Specie sp : species) 
			if (sp.isValid())
				request.getXsamsManager().addElement(getKidaSpecies(sp,request));
	}


	public static SpeciesInterface getKidaSpecies(Specie sp,RequestInterface request) {
		SpeciesInterface element = null;
		if (sp.isASpecialSpecies()){
			if (request.checkBranch(Requestable.Particles)) 
			element = new KidaParticle(sp);
		}else if (sp.isAnAtom()){
			if (request.checkBranch(Requestable.Atoms)) 
				element = new KidaAtom(sp);
		} else if (request.checkBranch(Requestable.Molecules)){
			element = new KidaMolecule(sp);
		}
		return element;
	}


	public static SelectQuery getSpeciesQuery(Query inputQuery) {
		
		Expression resultExpression=null,collExpr=null;

		resultExpression = QueryMapper.mapTree(inputQuery.getRestrictsTree(),Restrictables.SpeciesPathSpec);

		for (Prefix pref:inputQuery.getPrefixes())
		{
			VSSPrefix prefix = pref.getPrefix();
			int index = pref.getIndex();
			String strPrefix = prefix.name()+index;
			System.out.println(strPrefix);

			LogicNode collider = null;
			if ( prefix.name().equals("REACTANT"))
			{
				collider = inputQuery.getPrefixedTree(VSSPrefix.REACTANT, index);

			}
			if (  prefix.name().equals("PRODUCT"))
			{
				collider = inputQuery.getPrefixedTree(VSSPrefix.PRODUCT, index);
			}
			if ( collider == null ) // if prefix is not reactant or product ... we skip it
				continue;

			collExpr = QueryMapper.mapTree(collider, Restrictables.SpeciesPathSpec);
			if (collider!=null && collExpr!=null)//If we have some prefixed result, add it to the query with OR
				resultExpression = resultExpression.orExp(collExpr);

		}
		return new SelectQuery(Specie.class,resultExpression);
	}
}
