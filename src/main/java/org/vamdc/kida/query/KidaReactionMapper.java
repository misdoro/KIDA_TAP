package org.vamdc.kida.query;

import org.apache.cayenne.exp.Expression;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.vss2.RestrictExpression;

public class KidaReactionMapper extends SimpleKeywordMapper implements KeywordMapper {

	public KidaReactionMapper(Restrictable key) {
		super(key);
	}

	@Override
	public Expression translate(RestrictExpression input, int queryIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression translateAlias(RestrictExpression input, int queryIndex,
			String alias, String aliasReplace) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Restrictable getKeyword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMaxQueryIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

}
