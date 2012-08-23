package org.vamdc.kida.query;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.kida.constants.XSAMSProcessCodes;
import org.vamdc.tapservice.querymapper.KeywordMapper;
import org.vamdc.tapservice.querymapper.KeywordMapperImpl;
import org.vamdc.tapservice.vss2.RestrictExpression;

public class KidaReactionMapper extends KeywordMapperImpl implements KeywordMapper {

	public KidaReactionMapper(Restrictable key) {
		super(key);
	}

	@Override
	protected Expression buildExpression(RestrictExpression restrictor,String pathSpec){
		Object value = restrictor.getValue();	//For most cases we will need this value
		//Check all known operators, try to handle them
		if (value instanceof String){
			switch (restrictor.getOperator()){
			case EQUAL_TO:
				return ExpressionFactory.inExp(pathSpec, XSAMSProcessCodes.getProcIDs((String)value));
			case NOT_EQUAL_TO:
				return ExpressionFactory.notInExp(pathSpec, XSAMSProcessCodes.getProcIDs((String)value));
			case LESS_THAN:
				return ExpressionFactory.expFalse();
			case GREATER_THAN:
				return ExpressionFactory.expFalse();
			case LESS_THAN_EQUAL_TO:
				return ExpressionFactory.expFalse();
			case GREATER_THAN_EQUAL_TO:
				return ExpressionFactory.expFalse();
			case BETWEEN:
				return ExpressionFactory.expFalse();
			case IN:
				Collection<Integer> codes = new ArrayList<Integer>();
				for (Object oneValue:restrictor.getValues()){
					if (oneValue instanceof String)
						codes.addAll(XSAMSProcessCodes.getProcIDs((String)value));
				}
				
				return ExpressionFactory.inExp(pathSpec, codes);
			case LIKE:
				return ExpressionFactory.expFalse();
			default:
				return ExpressionFactory.expFalse();
			}
		}
		return ExpressionFactory.expFalse();
		
	}



}
