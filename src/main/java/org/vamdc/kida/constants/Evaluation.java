package org.vamdc.kida.constants;

import java.util.Map;
import java.util.TreeMap;

import org.vamdc.kida.dao.ChannelValue;

public enum Evaluation {
	
	NotRecommended(0,"Not recommended to use"),
	Unknown(1,"No evaluation available"),
	ValidOverRange(2,"Fit valid over temperature range"),
	Recommended(3,"Fit recommended over temperature range")
	
	;
	
	private final static Map<Integer,String> descriptions=new TreeMap<Integer,String>();
	static{
		for (Evaluation eval:Evaluation.values()){
			descriptions.put(eval.id,eval.descr);
		}
	}
	
	private Integer id;
	private String descr;
	
	
	Evaluation(Integer kidaId, String description){
		this.id=kidaId;
		this.descr = description;
	}
	
	public static String getEvaluation(ChannelValue fit){
		return descriptions.get(fit.getExpertize());
	}

}
