package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Formula;
import org.vamdc.xsams.common.ArgumentType;

public class FunctionPhoto extends KidaFunction {

	public FunctionPhoto(Formula formula) {
		super(formula);
		this.addParameter("alpha", "1/s", null);
		this.addParameter("gamma", "unitless", null);
		this.addArgument("Av", "unitless", "visual extinction");
		this.setY(new ArgumentType("k","1/s","Photo-dissociation rate"));
	}

}
