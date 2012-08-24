package org.vamdc.kida.tap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.dictionary.VSSPrefix;
import org.vamdc.kida.dao.Channel;
import org.vamdc.kida.xsams.Collision;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.tapservice.vss2.LogicNode;
import org.vamdc.tapservice.vss2.Prefix;
import org.vamdc.tapservice.vss2.Query;

public class ChannelBuilder {

	public static SelectQuery getCayenneQuery(Query query) {
		Expression result = null;
		// Loop over all defined prefixes
		Collection<String> aliases = new ArrayList<String>();
		for (Prefix pref : query.getPrefixes()) {
			VSSPrefix prefix = pref.getPrefix();
			int index = pref.getIndex();

			String strPrefix = prefix.name() + index;
			
			Expression chanSelector = null;
			switch(prefix){
			case REACTANT:
			case TARGET:
			case COLLIDER:
				chanSelector = ExpressionFactory.matchExp(strPrefix
						+ ".type", Channel.REACTANT);
				break;
			case PRODUCT:
				chanSelector = ExpressionFactory.matchExp(strPrefix
						+ ".type", Channel.PRODUCT);
				break;
			default:
				break;
			}
			
			result = addPrefixedTree(query.getPrefixedTree(prefix, index),
					result, aliases, strPrefix, chanSelector);
		}

		// add all keywords that don't require or don't have a prefix.
		result = addPrefixedTree(query.getPrefixedTree(null, 0),
				result,aliases,"unprefixed",null);
		
		SelectQuery q = new SelectQuery(Channel.class, result);
		
		if (aliases.size() > 0)
			q.aliasPathSplits("channelHasSpecies",
					aliases.toArray(new String[0]));

		return q;

	}

	private static Expression addPrefixedTree(LogicNode subtree, Expression result,
			Collection<String> aliases,
			String strPrefix, Expression chanSelector) {
		if (subtree==null)
			return result;
		// Build tree using aliases
		Expression prefExp = Restrictables.queryMapper.mapAliasedTree(
			subtree,
			Restrictables.QUERY_CHANNEL,
			"alias", 
			strPrefix);
		
		if (prefExp != null){
			aliases.add(strPrefix);
			if (chanSelector!=null)
				prefExp = prefExp.andExp(chanSelector);
			
			if (result == null) // Channel exp is yet empty, just assign prefExp to it.
				result = prefExp;
			else
				result = result.andExp(prefExp);
		}
		return result;
	}

	public static void buildChannels(RequestInterface request) {

		SelectQuery query = getCayenneQuery(request.getQuery());
		
		@SuppressWarnings("unchecked")
		List<Channel> channels = (List<Channel>) request.getCayenneContext()
				.performQuery(query);

		for (Channel chan : channels) {
			if (chan.isValid()){
				
				Boolean status = request.getXsamsManager().addProcess(new Collision(chan,request));
				if (!status)
					break;//Do not add more processes if limit is exceeded
				request.setLastModified(chan.getUpdatedAt());
			}
		}
	}	

}
