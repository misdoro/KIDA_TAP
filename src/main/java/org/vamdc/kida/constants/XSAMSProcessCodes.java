package org.vamdc.kida.constants;

import java.util.Collection;

import org.apache.commons.collections.map.MultiValueMap;
import org.vamdc.xsams.schema.CodeType;

public class XSAMSProcessCodes {

	private static MultiValueMap idToCodes = new MultiValueMap();
	private static MultiValueMap codeToIds = new MultiValueMap();
	
	static{
		addCode(Process.COSMIC_RAY,CodeType.IONI);
		addCode(Process.COSMIC_RAY,CodeType.DISS);
		
		addCode(Process.COSMIC_RAY_PHOTON,CodeType.IONI);
		addCode(Process.COSMIC_RAY_PHOTON,CodeType.DISS);
		addCode(Process.COSMIC_RAY_PHOTON,CodeType.ELDT);
		addCode(Process.COSMIC_RAY_PHOTON,CodeType.PHAB);
		
		addCode(Process.PHOTO,CodeType.IONI);
		addCode(Process.PHOTO,CodeType.DISS);
		addCode(Process.PHOTO,CodeType.ELDT);
		addCode(Process.PHOTO,CodeType.PHAB);
		
		addCode(Process.BIMO,CodeType.CHEM);
		addCode(Process.BIMO,CodeType.EXCH);
		
		addCode(Process.CHARGE_EX,CodeType.TRAN);
		
		addCode(Process.RAD_ASSO,CodeType.PHEM);
		addCode(Process.RAD_ASSO,CodeType.ASSO);
		
		addCode(Process.ASS_DETACH,CodeType.IONI);
		
		addCode(Process.ELEC_RECO,CodeType.PHEM);
		addCode(Process.ELEC_RECO,CodeType.EXCH);
		addCode(Process.ELEC_RECO,CodeType.ELAT);
		addCode(Process.ELEC_RECO,CodeType.DISS);
		
	}
	
	private static void addCode(Integer kidaCode, CodeType XsamsCode){
		idToCodes.put(kidaCode, XsamsCode);
		codeToIds.put(XsamsCode, kidaCode);
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<CodeType> getCodes(Integer id){
		return idToCodes.getCollection(id);
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Integer> getProcIDs(String code){
		return codeToIds.getCollection(code);
	}
	
}
