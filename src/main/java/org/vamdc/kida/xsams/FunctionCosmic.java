package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Formula;
import org.vamdc.xsams.common.ArgumentType;

public class FunctionCosmic extends KidaFunction{

	public FunctionCosmic(Formula formula) {
		super(formula);
		this.addParameter("alpha", "unitless", null);
		this.setY(new ArgumentType("k","1/s",null));
		this.addArgument("zeta", "1/s", "H2 cosmic ionization rate");
	}

}
