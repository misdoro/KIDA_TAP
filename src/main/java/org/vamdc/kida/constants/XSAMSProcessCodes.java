package org.vamdc.kida.constants;

import java.util.Collection;

import org.apache.commons.collections.map.MultiValueMap;

public class XSAMSProcessCodes {

	private static MultiValueMap idToCodes = new MultiValueMap();
	private static MultiValueMap codeToIds = new MultiValueMap();
	
	static{
		addCode(Process.COSMIC_RAY,"ioni");
		addCode(Process.COSMIC_RAY,"diss");
		
		addCode(Process.COSMIC_RAY_PHOTON,"ioni");
		addCode(Process.COSMIC_RAY_PHOTON,"diss");
		addCode(Process.COSMIC_RAY_PHOTON,"eldt");
		addCode(Process.COSMIC_RAY_PHOTON,"phab");
		
		addCode(Process.PHOTO,"ioni");
		addCode(Process.PHOTO,"diss");
		addCode(Process.PHOTO,"eldt");
		addCode(Process.PHOTO,"phab");
		
		addCode(Process.BIMO,"chem");
		addCode(Process.BIMO,"exch");
		
		addCode(Process.CHARGE_EX,"tran");
		
		addCode(Process.RAD_ASSO,"phem");
		addCode(Process.RAD_ASSO,"asso");
		
		addCode(Process.ASS_DETACH,"ioni");
		
		addCode(Process.ELEC_RECO,"phem");
		addCode(Process.ELEC_RECO,"exch");
		addCode(Process.ELEC_RECO,"elat");
		addCode(Process.ELEC_RECO,"diss");
		
	}
	
	private static void addCode(Integer kidaCode, String XsamsCode){
		idToCodes.put(kidaCode, XsamsCode);
		codeToIds.put(XsamsCode, kidaCode);
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<String> getCodes(Integer id){
		return idToCodes.getCollection(id);
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<Integer> getProcIDs(String code){
		return codeToIds.getCollection(code);
	}
	
}
