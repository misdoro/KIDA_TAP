package org.vamdc.kida.tap;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.dictionary.HeaderMetrics;
import org.vamdc.tapservice.query.CountQuery;

import java.util.HashMap;
import java.util.Map;

public class Metrics {

	public static Map<HeaderMetrics, Integer> estimate (RequestInterface request){
		Map<HeaderMetrics, Integer> estimates = new HashMap<HeaderMetrics, Integer>();
		
		//Estimate collisions
		SelectQuery query = ChannelBuilder.getCayenneQuery(request.getQuery());
		Long collisions = CountQuery.count((DataContext) request.getCayenneContext(), query);
		
		if (collisions>0)
			estimates.put(HeaderMetrics.VAMDC_COUNT_COLLISIONS, collisions.intValue());
		
		
		//Estimate species
		SelectQuery spQuery = SpeciesBuilder.getSpeciesQuery(request.getQuery()); 
		Long nbSpecies = CountQuery.count((DataContext) request.getCayenneContext(), spQuery);
		
		if (nbSpecies>0)
			estimates.put(HeaderMetrics.VAMDC_COUNT_SPECIES, nbSpecies.intValue());
		
		return estimates;
		
	}
}
