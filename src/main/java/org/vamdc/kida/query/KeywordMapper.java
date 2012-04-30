package org.vamdc.kida.query;

import org.apache.cayenne.exp.Expression;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.vss2.RestrictExpression;

/**
 * Interface describing a keyword mapper between VSS2 
 * and internal db query with cayenne
 *
 */
public interface KeywordMapper {
	
	/**
	 * Translate RestrictExpression into cayenne Expression, intended for the query #queryIndex 
	 * @param input RestrictExpression to translate
	 * @param queryRootIndex index of an expression
	 * @return Apache Cayenne Expression that can be added to the translated logic tree
	 */
	public Expression translate(RestrictExpression input,int queryIndex);

	/**
	 * The same as Translate, but with possibility to alter path components for Cayenne path aliases
	 * @param input
	 * @param queryIndex
	 * @param alias
	 * @param aliasReplace
	 * @return
	 */
	public Expression translateAlias(RestrictExpression input, int queryIndex,
			String alias, String aliasReplace);

	
	/**
	 * Get a keyword that this mapper operates on
	 * @return Restrictable to be mapped
	 */
	public Restrictable getKeyword();
	
	/**
	 * Get maximum query index that can be applied to translate().
	 * This method is used for verification purposes, all mappers must give the same value
	 * @return maximum query index 
	 */
	public int getMaxQueryIndex();

	
}
