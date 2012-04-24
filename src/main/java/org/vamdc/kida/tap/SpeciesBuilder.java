package org.vamdc.kida.tap;

import java.util.List;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;
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

	public static void buildSpecies(RequestInterface request) {

		SelectQuery atquery = getSpeciesQuery(request.getQuery());

		@SuppressWarnings("unchecked")
		List<Specie> atms = (List<Specie>) request.getCayenneContext()
				.performQuery(atquery);

		for (Specie sp : atms) {
			SpeciesInterface element = getKidaSpecies(sp);
			request.getXsamsManager().addElement(element);
		}

	}


	public static SpeciesInterface getKidaSpecies(Specie sp) {
		SpeciesInterface element = null;
		if (sp.isASpecialSpecies()) {
			element = new KidaParticle(sp);
		} else if (sp.isAnAtom()) {
			element = new KidaAtom(sp);
		} else {
			element = new KidaMolecule(sp);
		}
		return element;
	}


	public static SelectQuery getSpeciesQuery(Query query) {
		
		return  new SelectQuery(Specie.class, getExpression(query));

	}

	private static Expression getExpression(Query query) {
		Expression result=null,collExpr=null;

		result = QueryMapper.mapTree(query.getRestrictsTree(),Restrictables.SpeciesPathSpec);

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
			if (collider!=null && collExpr!=null)//If we have some prefixed result, add it to the query with OR
				result = result.orExp(collExpr);

		}
		return result;
	}
}
