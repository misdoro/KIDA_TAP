package org.vamdc.kida.query;

import java.util.Iterator;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.querymapper.KeywordMapper;
import org.vamdc.tapservice.querymapper.SimpleKeywordMapper;
import org.vamdc.tapservice.vss2.RestrictExpression;

/**
 * Kida mapper that adds temperature lookup support 
 * @author doronin
 *
 */
public class KidaTemperatureMapper extends SimpleKeywordMapper implements KeywordMapper{

	public KidaTemperatureMapper(Restrictable key) {
		super(key);
	}
	
	/**
	 * Specifically handle all possible operators, since we need to look up two fields, tmax and tmin, depending on
	 * the operator.
	 */
	@Override
	protected Expression buildExpression(RestrictExpression restrictor,String pathSpec){
		verifyParameters(restrictor,pathSpec);
	
		Expression result = treatRestrictorPart(restrictor,pathSpec);
		if (result!=null)
			result=result.andExp(ExpressionFactory.noMatchExp(pathSpec+".tmax", new Integer(9999)));
		return result;
		
	}
	
	private Expression treatRestrictorPart(RestrictExpression restrictor,String pathSpec){
		Object value = restrictor.getValue();	//For most cases we will need this value
		//Check all known operators, try to handle them
		switch (restrictor.getOperator()){
		case EQUAL_TO:
			return getTempEquals(pathSpec, value);
		case NOT_EQUAL_TO:
			return ExpressionFactory.noMatchExp(pathSpec, value);
		case LESS_THAN:
			return ExpressionFactory.lessExp(pathSpec+".tmax", value);
		case GREATER_THAN:
			return ExpressionFactory.greaterExp(pathSpec+".tmin", value);
		case LESS_THAN_EQUAL_TO:
			return ExpressionFactory.lessOrEqualExp(pathSpec+".tmax", value);
		case GREATER_THAN_EQUAL_TO:
			return ExpressionFactory.greaterOrEqualExp(pathSpec+".tmin", value);
		case BETWEEN:
			if (restrictor.getValues().size()==2){
				Iterator<Object> iter = restrictor.getValues().iterator();
				return ExpressionFactory.lessOrEqualExp(pathSpec+".tmax", iter.next()).andExp(
						ExpressionFactory.greaterOrEqualExp(pathSpec+".tmin", iter.next()));
			}
		case IN:
			Expression result = ExpressionFactory.expTrue();
			for (Object oneValue:restrictor.getValues()){
				result=result.andExp(getTempEquals(pathSpec,oneValue));
			}
			return result;
		case LIKE:
			return getTempEquals(pathSpec,value);
		default:
			return ExpressionFactory.expFalse();
		}
	}

	
	
	protected Expression getTempEquals(String pathSpec, Object value) {
		return ExpressionFactory.lessOrEqualExp(pathSpec+".tmin", value)
				.andExp(ExpressionFactory.greaterOrEqualExp(pathSpec+".tmax", value)
						);
	}

}
