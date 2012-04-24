package org.vamdc.kida.tap;

import java.util.List;
import java.util.Vector;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.dictionary.VSSPrefix;
import org.vamdc.kida.dao.*;
import org.vamdc.kida.xsams.KidaAtom;
import org.vamdc.kida.xsams.KidaMolecule;
import org.vamdc.kida.xsams.KidaParticle;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.tapservice.query.QueryMapper;
import org.vamdc.tapservice.vss2.LogicNode;
import org.vamdc.tapservice.vss2.Prefix;
import org.vamdc.tapservice.vss2.Query;

public class SpeciesBuilder {

	public static void buildSpecies(RequestInterface request,
			Vector<Integer> tabSpeciesId) {

		SelectQuery atquery = getSpeciesQuery(request.getQuery());

		@SuppressWarnings("unchecked")
		List<Specie> atms = (List<Specie>) request.getCayenneContext()
				.performQuery(atquery);

		for (Specie sp : atms) {
			if (sp.isASpecialSpecies()) {
				request.getXsamsManager().addElement(new KidaParticle(sp));
			} else if (sp.isAnAtom()) {
				request.getXsamsManager().addElement(new KidaAtom(sp));
			} else {
				request.getXsamsManager().addElement(new KidaMolecule(sp));
			}
			tabSpeciesId.addElement(new Integer(sp.getId()));
		}

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
