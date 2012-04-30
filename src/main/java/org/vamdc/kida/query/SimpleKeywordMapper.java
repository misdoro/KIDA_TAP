package org.vamdc.kida.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.vss2.RestrictExpression;

/**
 * Simple, one-to-one keyword mapper. 
 * Can be extended to add more restrictions.
 *
 */
public class SimpleKeywordMapper implements KeywordMapper{

	private List<String> paths = new ArrayList<String>();
	private Restrictable keyword;

	public SimpleKeywordMapper(Restrictable key){
		this.keyword = key;
	}

	@Override
	public Expression translate(RestrictExpression input, int queryIndex) {
		return translateAlias(input,queryIndex,null,null);
	}
	
	@Override
	public Expression translateAlias(RestrictExpression input, int queryIndex, String alias, String aliasReplace)
	{
		if (queryIndex>paths.size() || queryIndex<0)
			throw new IllegalArgumentException("query index out of range");
		if (input==null)
			throw new IllegalArgumentException("input expression is null");

		String path = paths.get(queryIndex);
		if (alias!=null && aliasReplace !=null)
			path = path.replace(alias, aliasReplace);

		return buildExpression(input,path);
	}

	@Override
	public Restrictable getKeyword() {
		return keyword;
	}

	@Override
	public int getMaxQueryIndex() {
		return paths.size();
	}

	/**
	 * Add new mapping path
	 * @param path database relations path
	 */
	public SimpleKeywordMapper addNewPath(String path){
		paths.add(path);
		return this;
	}

	/**
	 * Build Cayenne Expression from VSS RestrictExpression
	 * @param restrictor VSS RestrictExpression
	 * @param pathSpec path specification in Cayenne model relations tree
	 * @return Cayenne Expression corresponding to the RestrictExpression and it's path
	 */
	private static Expression buildExpression(RestrictExpression restrictor,String pathSpec){
		//If we didn't specify restrictor, or path is undefined, throw an illegal argument exception
		if (restrictor==null || restrictor.getOperator()==null || pathSpec==null)
			throw new IllegalArgumentException("Path or operator is not defined for mapping "+restrictor+","+pathSpec);

		Object value = restrictor.getValue();	//For most cases we will need this value
		//Check all known operators, try to handle them
		switch (restrictor.getOperator()){
		case EQUAL_TO:
			return ExpressionFactory.matchExp(pathSpec, value);
		case NOT_EQUAL_TO:
			return ExpressionFactory.noMatchExp(pathSpec, value);
		case LESS_THAN:
			return ExpressionFactory.lessExp(pathSpec, value);
		case GREATER_THAN:
			return ExpressionFactory.greaterExp(pathSpec, value);
		case LESS_THAN_EQUAL_TO:
			return ExpressionFactory.lessOrEqualExp(pathSpec, value);
		case GREATER_THAN_EQUAL_TO:
			return ExpressionFactory.greaterOrEqualExp(pathSpec, value);
		case BETWEEN:
			if (restrictor.getValues().size()==2){
				Iterator<Object> iter = restrictor.getValues().iterator();
				return ExpressionFactory.betweenExp(pathSpec, iter.next(), iter.next());
			}
		case IN:
			return ExpressionFactory.inExp(pathSpec, restrictor.getValues());
		case LIKE:
			return ExpressionFactory.likeExp(pathSpec, value);
		default:
			return ExpressionFactory.expFalse();
		}
	}

}
