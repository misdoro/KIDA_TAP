package org.vamdc.kida.query;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.tapservice.querymapper.SimpleKeywordMapper;
import org.vamdc.xsams.schema.ParticleNameType;

public class KidaParticleMapper extends SimpleKeywordMapper{

	public KidaParticleMapper(Restrictable key) {
		super(key);
	}
	
	@Override
	protected Object valueTranslator(Object value){
		if (value!=null && value instanceof String){
			if (ParticleNameType.COSMIC.name().equalsIgnoreCase((String) value))
				return "CR";
			if (ParticleNameType.ELECTRON.name().equalsIgnoreCase((String) value))
				return "e-";
			if (ParticleNameType.PHOTON.name().equalsIgnoreCase((String)value))
				return "CRP";
		}
		
		return value;
		
	}
	

}
