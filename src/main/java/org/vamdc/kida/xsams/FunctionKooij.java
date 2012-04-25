package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Formula;
import org.vamdc.xsams.common.ArgumentType;

public class FunctionKooij extends KidaFunction {

	public FunctionKooij(Formula formula) {
		super(formula);
		this.addParameter("alpha", "cm3/s", null);
		this.addParameter("beta","unitless",null);
		this.addParameter("gamma", "unitless", null);
		this.addArgument("T","K", "Temperature");
		this.setY(new ArgumentType("k","cm3/s","Reaction rate"));
	}

}
