package org.vamdc.kida.query;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.vss2.RestrictExpression;

public class KidaAtomSymbolMapper extends SimpleKeywordMapper{

	public KidaAtomSymbolMapper(Restrictable key) {
		super(key);
	}
	
	@Override
	protected Expression buildExpression(RestrictExpression restrictor,String pathSpec){
		Expression result = super.buildExpression(restrictor, pathSpec);
		
		//TODO:check the path if updating model
		if (result!=null){
			String occurrencePath = pathSpec.replace("element.symbol", "specieHasElement.ocurrence");
			result.andExp(ExpressionFactory.matchExp(occurrencePath, 1));
		}
		return result;
	}
	

}
