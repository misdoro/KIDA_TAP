package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Formula;
import org.vamdc.xsams.common.ArgumentType;

public class FunctionIonpol extends KidaFunction {

	public FunctionIonpol(Formula formula) {
		super(formula);
		this.addParameter("alpha", "unitless", "branching ratio");
		this.addParameter("beta","cm3/s",null);
		this.addParameter("gamma", "unitless", null);
		this.addArgument("T","K", "Temperature");
		this.setY(new ArgumentType("k","cm3/s","Reaction rate"));
	}
}
