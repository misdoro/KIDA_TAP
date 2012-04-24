package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.ValidityRange;
import org.vamdc.xsams.common.ArgumentType;

public class TemperatureArgument extends ArgumentType{
	public TemperatureArgument(ValidityRange range){
		this.setUnits("K");
		this.setName("T");
		if (range!=null){
			this.setUpperLimit(range.getTmax().doubleValue());
			this.setLowerLimit(range.getTmin().doubleValue());
		}
	}
}
