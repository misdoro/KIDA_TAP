package org.vamdc.kida.query;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.querymapper.KeywordMapperImpl;
import org.vamdc.tapservice.vss2.LogicNode.Operator;
import org.vamdc.tapservice.vss2.RestrictExpression;

public class KidaAtomSymbolMapper extends KeywordMapperImpl{

	public KidaAtomSymbolMapper(Restrictable key) {
		super(key);
	}
	
	@Override
	protected Expression buildExpression(RestrictExpression restrictor,String pathSpec){
		
		Expression result =null;
		
		//Treat equals by adding positive and negative ions, look up formula field
		if (restrictor.getOperator()==Operator.EQUAL_TO){
			String atom = (String) restrictor.getValue();
			result=ExpressionFactory.inExp(pathSpec, atom,atom+"+",atom+"-");
		}else{
			result= super.buildExpression(restrictor, pathSpec);
		}
		
		return result;
	}
	

}
