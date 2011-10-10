package org.vamdc.kida.tap;

import org.vamdc.xsams.common.ArgumentType;
import org.vamdc.xsams.common.ExpressionType;
import org.vamdc.xsams.functions.FunctionParameterType;
import org.vamdc.xsams.functions.FunctionParametersType;
import org.vamdc.xsams.functions.FunctionType;
import org.vamdc.xsams.process.collisions.CollisionalProcessClassType;
import org.vamdc.xsams.schema.IAEACodeType;
import org.vamdc.xsams.util.IDs;

public class ToolsBuilder {

	public static void writeProcessCodes(CollisionalProcessClassType process,
			Integer id) {
		if (id == 1) // CR
		{
			process.getCodes().add("ioni");
			process.getCodes().add("diss");
			return;
		}
		if (id == 2) // CRP
		{
			process.getCodes().add("ioni");
			process.getCodes().add("diss");
			process.getCodes().add("eldt");
			process.getCodes().add("phab");

			return;
		}
		if (id == 3) // Phot
		{
			process.getCodes().add("ioni");
			process.getCodes().add("diss");
			process.getCodes().add("eldt");
			process.getCodes().add("phab");
			return;
		}
		if (id == 4) // Bimo
		{
			process.getCodes().add("chem");
			process.getCodes().add("exch");
			return;
		}
		if (id == 5) // CE
		{
			process.getCodes().add("tran");
			return;
		}
		if (id == 6) // RA
		{
			process.getCodes().add("phem");
			process.getCodes().add("asso");
			return;
		}
		if (id == 7) // AD
		{
			process.getCodes().add("ion");
			return;
		}
		if (id == 8) // ER
		{
			process.getCodes().add("phem");
			process.getCodes().add("exch");
			process.getCodes().add("elat");
			process.getCodes().add("diss");
			return;
		}
	}

	public static void writeIAEACodes(CollisionalProcessClassType process,
			Integer id) {
		/*if (id == 1) // CR
		{
			process.getIAEACodes().add(IAEACodeType.EIN);
			process.getIAEACodes().add(IAEACodeType.EDI);
			process.getIAEACodes().add(IAEACodeType.EDS);
			return;
		}
		if (id == 2) // CRP
		{
			process.getIAEACodes().add(IAEACodeType.PDS);
			return;
		}
		if (id == 3) // Phot
		{
			process.getIAEACodes().add(IAEACodeType.PDS);
			return;
		}
		if (id == 4) // Bimo
		{
			process.getIAEACodes().add(IAEACodeType.ASS);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		if (id == 5) // CE
		{
			process.getIAEACodes().add(IAEACodeType.HCX);
			process.getIAEACodes().add(IAEACodeType.HMN);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		if (id == 6) // RA
		{
			process.getIAEACodes().add(IAEACodeType.PDT);
			process.getIAEACodes().add(IAEACodeType.ASS);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		if (id == 7) // AD
		{
			process.getIAEACodes().add(IAEACodeType.ASS);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		if (id == 8) // ER
		{
			process.getIAEACodes().add(IAEACodeType.ENI);
			process.getIAEACodes().add(IAEACodeType.ERC);
			process.getIAEACodes().add(IAEACodeType.ERR);
			process.getIAEACodes().add(IAEACodeType.ERD);
			process.getIAEACodes().add(IAEACodeType.MCR);
			return;
		}
		*/

	}

	private static FunctionType writeKooij() {
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

		params.getParameters().add(alpha);
		params.getParameters().add(beta);
		params.getParameters().add(gamma);
		function.setParameters(params);

		return function;

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

		params.getParameters().add(alpha);
		params.getParameters().add(beta);
		params.getParameters().add(gamma);
		function.setParameters(params);

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

		params.getParameters().add(alpha);
		params.getParameters().add(beta);
		params.getParameters().add(gamma);
		function.setParameters(params);

		return function;

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
}
