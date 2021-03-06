package org.vamdc.kida.tap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.vamdc.dictionary.Requestable;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.dictionary.HeaderMetrics;
import org.vamdc.kida.xsams.SelfSource;
import org.vamdc.tapservice.api.RequestInterface;


public class OutputBuilder implements org.vamdc.tapservice.api.DatabasePlugin {


	
	public void buildXSAMS(RequestInterface request) {

		request.getXsamsManager().addSource(new SelfSource(request));
		
		if (request.checkBranch(Requestable.Species)
				|| request.checkBranch(Requestable.Molecules)
				|| request.checkBranch(Requestable.Atoms)
				|| request.checkBranch(Requestable.Particles))
		// if there is a Species
		// in query or a *
		{
			SpeciesBuilder.loadSpecies(request);
		}
		if (request.checkBranch(Requestable.Collisions)) // if there is a
		// Collision in
		// query or a *
		{
			ChannelBuilder.buildChannels(request);

		}

	}

	public String getErrorMessage() {
		return null;
	}

	public Collection<Restrictable> getRestrictables() {
		return Restrictables.queryMapper.getRestrictables();
	}

	public boolean isAvailable() {
		return Restrictables.queryMapper.isReady();
	}

	public Map<HeaderMetrics, Object> getMetrics(RequestInterface request) {
		if (request.isValid())
			return Metrics.estimate(request);
		return Collections.emptyMap();
	}

}
