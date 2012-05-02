package org.vamdc.kida.constants;

import org.vamdc.xsams.process.collisions.CollisionalProcessClassType;
import org.vamdc.xsams.schema.IAEACodeType;

public class ProcessCodes{

	public static void writeProcessCodes(CollisionalProcessClassType process,
			Integer id) {
		if (id == 1) // CR
		{
			process.getCodes().add("ioni");
			process.getCodes().add("diss");
			return;
		}
		if (id == 2) // CRP
		{
			process.getCodes().add("ioni");
			process.getCodes().add("diss");
			process.getCodes().add("eldt");
			process.getCodes().add("phab");

			return;
		}
		if (id == 3) // Phot
		{
			process.getCodes().add("ioni");
			process.getCodes().add("diss");
			process.getCodes().add("eldt");
			process.getCodes().add("phab");
			return;
		}
		if (id == 4) // Bimo
		{
			process.getCodes().add("chem");
			process.getCodes().add("exch");
			return;
		}
		if (id == 5) // CE
		{
			process.getCodes().add("tran");
			return;
		}
		if (id == 6) // RA
		{
			process.getCodes().add("phem");
			process.getCodes().add("asso");
			return;
		}
		if (id == 7) // AD
		{
			process.getCodes().add("ioni");
			return;
		}
		if (id == 8) // ER
		{
			process.getCodes().add("phem");
			process.getCodes().add("exch");
			process.getCodes().add("elat");
			process.getCodes().add("diss");
			return;
		}
	}

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
