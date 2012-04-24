package org.vamdc.kida.tap;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import org.vamdc.dictionary.Requestable;
import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.api.Dictionary.HeaderMetrics;
import org.vamdc.tapservice.api.RequestInterface;


public class OutputBuilder implements org.vamdc.tapservice.api.DatabasePlug {

	public void buildXSAMS(RequestInterface request) {

		Vector<Integer> tabSpeciesId = new Vector<Integer>();
		Vector<String> tabFormulaName = new Vector<String>();
		if (request.checkBranch(Requestable.Species)
				|| request.checkBranch(Requestable.Molecules)
				|| request.checkBranch(Requestable.Atoms)
				|| request.checkBranch(Requestable.Particles))
		// if there is a Species
		// in query or a *
		{
			SpeciesBuilder.buildSpecies(request, tabSpeciesId);
		}
		if (request.checkBranch(Requestable.Collisions)) // if there is a
		// Collision in
		// query or a *
		{
			ChannelBuilder.buildChannels(request, tabSpeciesId, tabFormulaName);

		}

	}

	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<Restrictable> getRestrictables() {
		return Restrictables.Restricts;
	}

	public boolean isAvailable() {
		return true;
	}

	public Map<HeaderMetrics, Integer> getMetrics(RequestInterface request) {
		if (request.isValid())
			return Metrics.estimate(request);
		return null;
	}

}
