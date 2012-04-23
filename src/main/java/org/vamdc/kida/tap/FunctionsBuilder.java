package org.vamdc.kida.tap;

import org.vamdc.xsams.common.ArgumentType;
import org.vamdc.xsams.common.ExpressionType;
import org.vamdc.xsams.functions.ArgumentsType;
import org.vamdc.xsams.functions.FunctionParameterType;
import org.vamdc.xsams.functions.FunctionParametersType;
import org.vamdc.xsams.functions.FunctionType;
import org.vamdc.xsams.util.IDs;

public class FunctionsBuilder {

	private static FunctionType writeKooij() {
		FunctionType function = new FunctionType();
		function.setFunctionID(IDs.getFunctionID(1));
		function.setDescription("Kooij function");
		function.setName("Kooij");
		ExpressionType expression = new ExpressionType();
		expression.setComputerLanguage("Fortran");
		expression.setValue("alpha * (T/300)^beta * e^(-gamma / T)");
		function.setExpression(expression);
		ArgumentType argument = getValueArgument();
		function.setY(argument);
	
		ArgumentsType arguments = new ArgumentsType();
		arguments.getArguments().add(getTemperatureArgument());
		function.setArguments(arguments);
		
		FunctionParametersType params = new FunctionParametersType();
		FunctionParameterType alpha = new FunctionParameterType();
		alpha.setName("alpha");
		alpha.setUnits("cm3/s");
		alpha.setDescription("Alpha multiplier");
		
		params.getParameters().add(alpha);
		params.getParameters().add(getParameterBeta());
		params.getParameters().add(getParameterGamma());
	
		function.setParameters(params);
	
		return function;
	
	}

	private static ArgumentType getValueArgument() {
		ArgumentType argument = new ArgumentType();
		argument.setName("K");
		argument.setUnits("cm3/s");
		argument.setDescription("Rate coefficient vs temperature");
		return argument;
	}

	private static ArgumentType getTemperatureArgument() {
		ArgumentType argument = new ArgumentType();
		argument.setName("T");
		argument.setUnits("K");
		return argument;
	}

	private static FunctionType writeCosmicRay() {
		FunctionType function = new FunctionType();
		function.setFunctionID(IDs.getFunctionID(2));
		function.setDescription("Cosmic-ray ionization function");
		function.setName("Cosmic-ray ionization");
		ExpressionType expression = new ExpressionType();
		expression.setComputerLanguage("Fortran");
		expression.setValue("alpha * beta");
		function.setExpression(expression);
		ArgumentType argument = getValueArgument();
		function.setY(argument);
	
		FunctionParametersType params = new FunctionParametersType();
		params.getParameters().add(getParameterAlpha());
		params.getParameters().add(getParameterBeta());

		return function;
	
	}

	private static FunctionType writePhotoDissiociation() {
		FunctionType function = new FunctionType();
		function.setFunctionID(IDs.getFunctionID(3));
		function.setDescription("Photo-dissociation ionization function");
		function.setName("Photo-dissociation ionization");
		ExpressionType expression = new ExpressionType();
		expression.setComputerLanguage("Fortran");
		expression.setValue("alpha * e^(-gamma * T)");
		function.setExpression(expression);
		ArgumentType argument = getValueArgument();
	
		FunctionParametersType params = new FunctionParametersType();
		params.getParameters().add(getParameterAlpha());
		params.getParameters().add(getParameterBeta());
		params.getParameters().add(getParameterGamma());
	
		function.setParameters(params);
	
		return function;
	
	}

	private static FunctionParameterType getParameterGamma() {
		FunctionParameterType gamma = new FunctionParameterType();
		gamma.setName("gamma");
		gamma.setUnits("keV");
		gamma.setDescription("gamma exponent");
		return gamma;
	}

	private static FunctionParameterType getParameterBeta() {
		FunctionParameterType beta = new FunctionParameterType();
		beta.setName("beta");
		beta.setUnits("undef");
		beta.setDescription("beta power");
		return beta;
	}

	private static FunctionParameterType getParameterAlpha() {
		FunctionParameterType alpha = new FunctionParameterType();
		alpha.setName("alpha");
		alpha.setUnits("1/s");
		alpha.setDescription("Alpha multiplier");
		return alpha;
	}

	public static FunctionType writeFormula(String name) {
		if (name.equals("Kooij"))
			return writeKooij();
		if (name.equals("Cosmic-ray ionization"))
			return writeCosmicRay();
		if (name.equals("Photo-dissociation"))
			return writePhotoDissiociation();
	
		return null;
	
	}

	public FunctionsBuilder() {
		super();
	}

}