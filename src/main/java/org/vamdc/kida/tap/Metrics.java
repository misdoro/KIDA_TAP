package org.vamdc.kida.tap;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.dictionary.HeaderMetrics;
import org.vamdc.dictionary.Requestable;
import org.vamdc.kida.dao.Specie;
import org.vamdc.tapservice.query.QueryUtil;

import java.util.HashMap;
import java.util.Map;

public class Metrics {

	public static Map<HeaderMetrics, Object> estimate (RequestInterface request){
		Map<HeaderMetrics, Object> estimates = new HashMap<HeaderMetrics, Object>();

		//Estimate collisions
		SelectQuery query = ChannelBuilder.getCayenneQuery(request.getQuery());
		Long collisions = QueryUtil.countQuery((DataContext) request.getCayenneContext(), query);

		if (collisions>0 && request.checkBranch(Requestable.Collisions)){
			estimates.put(HeaderMetrics.VAMDC_COUNT_COLLISIONS, collisions.intValue());

			request.setLastModified(
					QueryUtil.lastTimestampQuery(
							(DataContext) request.getCayenneContext(), 
							query, 
							"t0.updated_at"));
		}


		//Estimate species
		Expression spExpr=SpeciesBuilder.getSpeciesExpression(request.getQuery());
		if (spExpr!=null || (request.getQueryString()!=null && "select species".equalsIgnoreCase(request.getQueryString().trim()))){

			SelectQuery spQuery = new SelectQuery(Specie.class,spExpr);
			Long nbSpecies = QueryUtil.countQuery((DataContext) request.getCayenneContext(), spQuery);

			if (nbSpecies>0){
				estimates.put(HeaderMetrics.VAMDC_COUNT_SPECIES, nbSpecies.intValue());
				request.setLastModified(
						QueryUtil.lastTimestampQuery(
								(DataContext) request.getCayenneContext(), 
								spQuery, 
								"t0.updated_at"));
			}
		}

		return estimates;

	}
}
