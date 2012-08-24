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

		Expression result=null;

		//Map unprefixed keywords
		result =addPrefixedTree (null,
				inputQuery.getPrefixedTree(null, 0));

		for (Prefix pref:inputQuery.getPrefixes())
		{
			VSSPrefix prefix = pref.getPrefix();

			switch(prefix){
			case REACTANT:
			case PRODUCT:
			case TARGET:
			case COLLIDER:
				result = addPrefixedTree(result,
						inputQuery.getPrefixedTree(pref.getPrefix(),pref.getIndex()));
				break;
			default:
				break;
			}
		}
		return new SelectQuery(Specie.class,result);
	}


	public static Expression addPrefixedTree(
			Expression resultExpression,
			LogicNode subtree) {
		if (subtree!=null){
			Expression collExpr = Restrictables.queryMapper.mapTree(subtree, Restrictables.QUERY_SPECIES);
			if (collExpr!=null)
				if (resultExpression == null)
					resultExpression = collExpr;
				else 
					resultExpression = resultExpression.orExp(collExpr);
		}
		return resultExpression;
	}
}
