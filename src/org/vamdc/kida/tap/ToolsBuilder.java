package org.vamdc.kida.tap;

import org.vamdc.xsams.common.ArgumentType;
import org.vamdc.xsams.common.ExpressionType;
import org.vamdc.xsams.functions.FunctionParameterType;
import org.vamdc.xsams.functions.FunctionParametersType;
import org.vamdc.xsams.functions.FunctionType;
import org.vamdc.xsams.schema.IAEACodeType;
import org.vamdc.xsams.util.IDs;

public class ToolsBuilder {

	public static  IAEACodeType writeIAEACode(Integer id) {
		if ( id == 1 )
			return IAEACodeType.EIN;
		
		return null;
	}

	public static    FunctionType writeFormula(String name) {
		if ( name.equals("Kooij") )
		{
			FunctionType function = new FunctionType();
			function.setFunctionID(IDs.getFunctionID(1));
			function.setDescription("Kooij function");
			function.setName("Kooij");
			ExpressionType expression = new ExpressionType();
			expression.setComputerLanguage("Fortran");
			expression.setValue("alpha * (T/300)^beta * e^(-gamma / T)");
			function.setExpression(expression);
			ArgumentType argument = new ArgumentType();
			argument.setName("K");
			argument.setUnits("cm3/s");
			argument.setDescription("Rate coefficient vs temperature");
			function.setY(argument);
			
			FunctionParametersType params = new FunctionParametersType();
			FunctionParameterType alpha = new FunctionParameterType();
			alpha.setName("alpha");
			alpha.setUnits("cm3/s");
			alpha.setDescription("Alpha multiplier");
			
			FunctionParameterType beta = new FunctionParameterType();
			beta.setName("beta");
			beta.setUnits("undef");
			beta.setDescription("beta power");
			
			FunctionParameterType gamma = new FunctionParameterType();
			gamma.setName("gamma");
			gamma.setUnits("keV");
			gamma.setDescription("gamma exponent");

			params.getParameters().add(alpha );
			params.getParameters().add(beta );
			params.getParameters().add(gamma );
			function.setParameters(params);
			
			return function;
		}
		if ( name.equals("Cosmic-ray ionization")  )
		{
			FunctionType function = new FunctionType();
			function.setFunctionID(IDs.getFunctionID(2));
			function.setDescription("Cosmic-ray ionization function");
			function.setName("Cosmic-ray ionization");
			ExpressionType expression = new ExpressionType();
			expression.setComputerLanguage("Fortran");
			expression.setValue("alpha * beta");
			function.setExpression(expression);
			ArgumentType argument = new ArgumentType();
			argument.setName("K");
			argument.setUnits("cm3/s");
			argument.setDescription("Rate coefficient vs temperature");
			function.setY(argument);
			
			FunctionParametersType params = new FunctionParametersType();
			FunctionParameterType alpha = new FunctionParameterType();
			alpha.setName("alpha");
			alpha.setUnits("1/s");
			
			alpha.setDescription("Alpha multiplier");
			
			FunctionParameterType beta = new FunctionParameterType();
			beta.setName("beta");
			beta.setUnits("undef");
			beta.setDescription("beta power");
			
			FunctionParameterType gamma = new FunctionParameterType();
			gamma.setName("gamma");
			gamma.setUnits("keV");
			gamma.setDescription("gamma exponent");

			params.getParameters().add(alpha );
			params.getParameters().add(beta );
			params.getParameters().add(gamma );
			function.setParameters(params);
			
			return function;
		}
		if ( name.equals("Photo-dissociation")  )
		{
			FunctionType function = new FunctionType();
			function.setFunctionID(IDs.getFunctionID(3));
			function.setDescription("Photo-dissociation ionization function");
			function.setName("Photo-dissociation ionization");
			ExpressionType expression = new ExpressionType();
			expression.setComputerLanguage("Fortran");
			expression.setValue("alpha * e^(-gamma * T)");
			function.setExpression(expression);
			ArgumentType argument = new ArgumentType();
			argument.setName("K");
			argument.setUnits("cm3/s");
			argument.setDescription("Rate coefficient vs temperature");
			function.setY(argument);
			
			FunctionParametersType params = new FunctionParametersType();
			FunctionParameterType alpha = new FunctionParameterType();
			alpha.setName("alpha");
			alpha.setUnits("1/s");
			alpha.setDescription("Alpha multiplier");
			
			FunctionParameterType beta = new FunctionParameterType();
			beta.setName("beta");
			beta.setUnits("undef");
			beta.setDescription("beta power");
			
			FunctionParameterType gamma = new FunctionParameterType();
			gamma.setName("gamma");
			gamma.setUnits("keV");
			gamma.setDescription("gamma exponent");

			params.getParameters().add(alpha );
			params.getParameters().add(beta );
			params.getParameters().add(gamma );
			function.setParameters(params);
			
			return function;
		}
		return null;
			
		
	}
}
