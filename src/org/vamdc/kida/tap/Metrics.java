package org.vamdc.kida.tap;

import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.kida.ChannelValue;
import org.vamdc.kida.Specie;
import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.tapservice.api.Dictionary.HeaderMetrics;
import org.vamdc.tapservice.query.CountQuery;

import java.util.HashMap;
import java.util.Map;

public class Metrics {

	public static Map<HeaderMetrics, Integer> estimate (RequestInterface request){
		Map<HeaderMetrics, Integer> estimates = new HashMap<HeaderMetrics, Integer>();
		
		//Estimate collisions
		Expression colExpression = ChannelBuilder.getExpression(request);
		SelectQuery query=new SelectQuery(ChannelValue.class,colExpression);
		Long collisions = CountQuery.count((DataContext) request.getCayenneContext(), query);
		
		if (collisions>0)
			estimates.put(HeaderMetrics.VAMDC_COUNT_COLLISIONS, collisions.intValue());
		
		
		//Estimate species
		Expression spExpression = SpeciesBuilder.getExpression(request);
		SelectQuery spQuery=new SelectQuery(Specie.class,spExpression);
		Long nbSpecies = CountQuery.count((DataContext) request.getCayenneContext(), spQuery);
		
		if (nbSpecies>0)
			estimates.put(HeaderMetrics.VAMDC_COUNT_SPECIES, nbSpecies.intValue());
		
		return estimates;
		
	}
}
