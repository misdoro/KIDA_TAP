package org.vamdc.kida.constants;

import org.vamdc.xsams.process.collisions.CollisionalProcessClassType;
import org.vamdc.xsams.schema.IAEACodeType;

public class Process{

	public final static int COSMIC_RAY = 1;
	public final static int COSMIC_RAY_PHOTON = 2;
	public final static int PHOTO = 3;
	public final static int BIMO = 4;
	public final static int CHARGE_EX = 5;
	public final static int RAD_ASSO = 6;
	public final static int ASS_DETACH = 7;
	public final static int ELEC_RECO = 8;
	public final static int THREE_BODY = 9;
	
	

	public static void writeIAEACodes(CollisionalProcessClassType process,
			Integer id) {
		/*if (id == 1) // CR
		{
			process.getIAEACodes().add(IAEACodeType.EIN);
			process.getIAEACodes().add(IAEACodeType.EDI);
			process.getIAEACodes().add(IAEACodeType.EDS);
			return;
		}
		if (id == 2) // CRP
		{
			process.getIAEACodes().add(IAEACodeType.PDS);
			return;
		}
		if (id == 3) // Phot
		{
			process.getIAEACodes().add(IAEACodeType.PDS);
			return;
		}
		if (id == 4) // Bimo
		{
			process.getIAEACodes().add(IAEACodeType.ASS);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		if (id == 5) // CE
		{
			process.getIAEACodes().add(IAEACodeType.HCX);
			process.getIAEACodes().add(IAEACodeType.HMN);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		if (id == 6) // RA
		{
			process.getIAEACodes().add(IAEACodeType.PDT);
			process.getIAEACodes().add(IAEACodeType.ASS);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		if (id == 7) // AD
		{
			process.getIAEACodes().add(IAEACodeType.ASS);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		if (id == 8) // ER
		{
			process.getIAEACodes().add(IAEACodeType.ENI);
			process.getIAEACodes().add(IAEACodeType.ERC);
			process.getIAEACodes().add(IAEACodeType.ERR);
			process.getIAEACodes().add(IAEACodeType.ERD);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		*/

	}
}
