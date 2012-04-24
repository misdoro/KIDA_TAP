package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Specie;
import org.vamdc.xsams.schema.ParticleNameType;
import org.vamdc.xsams.species.particles.ParticleType;
import org.vamdc.xsams.util.IDs;

public class KidaParticle extends ParticleType{
	
	public KidaParticle(Specie particle){

		this.setSpeciesID(IDs.getSpecieID(particle.getId()));
		
		String particleName = particle.getCommonName();
		if (particleName==null)
			return;
		
		if ( particleName.equals("Photon"))
			this.setName(ParticleNameType.PHOTON);
		
		else if ( particleName.equals("e-"))
			this.setName(ParticleNameType.ELECTRON);
		
		else if ( particleName.equals("CR")){
			this.setName(ParticleNameType.COSMIC);
			
		}else if (particleName.equals("CRP")){
			this.setName(ParticleNameType.PHOTON);
			this.setComments("Cosmic ray photon");
		}
		
		
	}
	
}
