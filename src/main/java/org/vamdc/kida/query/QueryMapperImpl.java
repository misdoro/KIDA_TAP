package org.vamdc.kida.query;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.vss2.LogicNode;
import org.vamdc.tapservice.vss2.LogicNode.Operator;
import org.vamdc.tapservice.vss2.RestrictExpression;

public class QueryMapperImpl implements QueryMapper{

	private Map<Restrictable, KeywordMapper> mappers = new TreeMap<Restrictable,KeywordMapper>();
	
	@Override
	public void addMapper(KeywordMapper mapper) {
		mappers.put(mapper.getKeyword(), mapper);
	}

	@Override
	public Collection<Restrictable> getRestrictables() {
		return mappers.keySet();
	}

	@Override
	public Expression mapTree(LogicNode root, int queryIndex) {
		return mapSubTree(root,queryIndex);
	}

	@Override
	public boolean isReady() {
		int length=0;
		for (KeywordMapper mapper:mappers.values()){
			if (length>0 && length!=mapper.getMaxQueryIndex())
				return false;
			else if (length==0)
				length = mapper.getMaxQueryIndex();
		}
		return true;
	}

	
	private Expression mapSubTree(LogicNode root, int queryIndex){
		if (root==null)
			throw new IllegalArgumentException("Incoming tree should not be null");
		
		if (root instanceof RestrictExpression){
			return mapExpression((RestrictExpression) root, queryIndex);
		}else{
			Expression result=null,newExpr=null;
			switch (root.getOperator()){
			case NOT:
				return mapSubTree((LogicNode) root.getValue(),queryIndex).notExp();
			default:
				for (Object branch:root.getValues()){
					newExpr = mapSubTree((LogicNode)branch,queryIndex);
					if (result==null)
						result = newExpr;
					else if (root.getOperator()==Operator.OR)
						result = result.orExp(newExpr);
					else if (root.getOperator()==Operator.AND)
						result = result.andExp(newExpr);
					else
						throw new IllegalArgumentException("Unknown expression operator "+root.getOperator());
				}
			};
			return result;
		}
	}

	public Expression mapExpression(RestrictExpression node, int queryIndex) {
		KeywordMapper mapper = mappers.get(node.getColumn());
		if (mapper!=null)
			return mapper.translate(node, queryIndex);
		else 
			throw new IllegalArgumentException("Query contains a keyword that is not supported");
			//TODO: add configuration option to return true here
	}
	
}
