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
				request.getXsamsManager().addElement(new KidaParticle(sp));
			} else if (sp.isAnAtom()) {
				request.getXsamsManager().addElement(new KidaAtom(sp));
			} else {
				request.getXsamsManager().addElement(new KidaMolecule(sp));
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
