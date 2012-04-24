package org.vamdc.kida.tap;

import java.util.List;
import java.util.Vector;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.dictionary.VSSPrefix;
import org.vamdc.kida.dao.Channel;
import org.vamdc.kida.dao.TypeChannel;
import org.vamdc.kida.xsams.Collision;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.tapservice.query.QueryMapper;
import org.vamdc.tapservice.vss2.Prefix;
import org.vamdc.tapservice.vss2.Query;

public class ChannelBuilder {

	public static SelectQuery getCayenneQuery(Query query) {
		Expression channelExp = null;
		Expression prefExp = null;
		// Loop over all defined prefixes
		Vector<String> aliases = new Vector<String>();
		for (Prefix pref : query.getPrefixes()) {
			VSSPrefix prefix = pref.getPrefix();
			int index = pref.getIndex();

			// Add alias to vector
			String strPrefix = prefix.name() + index;
			aliases.add(strPrefix);

			if (prefix == VSSPrefix.REACTANT) {// Handle REACTANT
				Expression chsex = ExpressionFactory.matchExp(strPrefix
						+ ".type", Channel.REACTANT);
				prefExp = QueryMapper.mapTree(
						query.getPrefixedTree(prefix, index),
						Restrictables.getAliasedChannelMap(strPrefix));// Build
				// tree
				// using
				// aliases
				if (prefExp != null)
					prefExp = prefExp.andExp(chsex);

			} else if (prefix == VSSPrefix.PRODUCT) {// Handle PRODUCT
				Expression chsex = ExpressionFactory.matchExp(strPrefix
						+ ".type", Channel.PRODUCT);
				prefExp = QueryMapper.mapTree(
						query.getPrefixedTree(prefix, index),
						Restrictables.getAliasedChannelMap(strPrefix));// Build
				// tree
				// using
				// aliases
				if (prefExp != null)
					prefExp = prefExp.andExp(chsex);
			} else {
				prefExp = null;
			}
			if (channelExp == null) {// Channel exp is yet empty, just assign
				// prefExp to it.
				channelExp = prefExp;
				prefExp = null;
			} else if (prefExp != null) {
				channelExp = channelExp.andExp(prefExp);//
			}
		}

		// add all keywords that don't require or don't have a prefix.
		prefExp = QueryMapper.mapTree(query.getPrefixedTree(null, 0),
				Restrictables.getAliasedChannelMap("unprefixed"));
		aliases.add("unprefixed");

		if (channelExp == null) {// Channel exp is yet empty, just assign
			// prefExp to it.
			channelExp = prefExp;
			prefExp = null;
		} else if (prefExp != null) {
			channelExp = channelExp.andExp(prefExp);
		}

		System.out.println("Expression:" + channelExp);
		SelectQuery q = new SelectQuery(Channel.class, channelExp);

		if (aliases.size() > 0)
			q.aliasPathSplits("channelHasSpecies",
					aliases.toArray(new String[0]));

		return q;

	}

	public static void buildChannels(RequestInterface request) {

		SelectQuery atquery = getCayenneQuery(request.getQuery());
		
		@SuppressWarnings("unchecked")
		List<Channel> atms = (List<Channel>) request.getCayenneContext()
				.performQuery(atquery);

		for (Channel chan : atms) {
			if (chan.getAddedStatus() == 0)
				continue;
			TypeChannel tc = chan.getTypeChannel();
			if (tc == null || tc.getAbbrev().equals("3-body"))
				continue;

			request.getXsamsManager().addProcess(new Collision(chan,request.getXsamsManager()));
		}
	}	

}
