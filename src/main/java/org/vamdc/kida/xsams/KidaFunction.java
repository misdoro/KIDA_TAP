package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Formula;
import org.vamdc.xsams.common.ArgumentType;
import org.vamdc.xsams.common.ExpressionType;
import org.vamdc.xsams.functions.ArgumentsType;
import org.vamdc.xsams.functions.FunctionParameterType;
import org.vamdc.xsams.functions.FunctionParametersType;
import org.vamdc.xsams.functions.FunctionType;
import org.vamdc.xsams.util.IDs;

public class KidaFunction extends FunctionType{

	public final static int FID_COSMIC=1;
	public final static int FID_PHOTO=2;
	public final static int FID_KOOIJ=3;
	public final static int FID_IONPOL1=4;
	public final static int FID_IONPOL2=5;

	public KidaFunction(Formula formula){

		this.setExpression(getMathExpression(formula));
		this.setName(formula.getName());
		this.setFunctionID(IDs.getFunctionID(formula.getId()));
		this.setComments("Function image available at http://kida.obs.u-bordeaux1.fr/images/formula/"+formula.getImage());

		addParameters(formula);

		this.setY(getValueArgument());
		this.setArguments(getTemperatureArgument());

	}

	private ExpressionType getMathExpression(Formula formula){
		ExpressionType result = new ExpressionType();
		result.setComputerLanguage("Fortran");
		result.setValue(formula.getMath());
		return result;

	}

	private void addParameters(Formula formula){
		switch(formula.getId().intValue()){
		case FID_COSMIC:
			addParameterAlpha();
			addParameterBeta();
			break;
		case FID_PHOTO:
			addParameterAlpha();
			addParameterGamma();
			break;
		case FID_KOOIJ:
		case FID_IONPOL1:
		case FID_IONPOL2:
			addParameterAlpha();
			addParameterBeta();
			addParameterGamma();
			break;
		default:
			break;
		}
	}

	private void addParameter(FunctionParameterType param){
		if (this.getParameters()==null)
			this.setParameters(new FunctionParametersType());
		this.getParameters().getParameters().add(param);
	}

	private void addParameterAlpha() {
		FunctionParameterType alpha = new FunctionParameterType();
		alpha.setName("alpha");
		alpha.setUnits("1/s");
		alpha.setDescription("Alpha multiplier");
		this.addParameter(alpha);
	}

	private void addParameterBeta() {
		FunctionParameterType beta = new FunctionParameterType();
		beta.setName("beta");
		beta.setUnits("undef");
		beta.setDescription("beta power");
		this.addParameter(beta);
	}

	private void addParameterGamma() {
		FunctionParameterType gamma = new FunctionParameterType();
		gamma.setName("gamma");
		gamma.setUnits("keV");
		//TODO: check units here
		gamma.setDescription("gamma exponent");
		this.addParameter(gamma);
	}

	private static ArgumentType getValueArgument() {
		ArgumentType argument = new ArgumentType();
		argument.setName("K");
		argument.setUnits("cm3/s");
		argument.setDescription("Rate coefficient vs temperature");
		return argument;
	}

	public static ArgumentsType getTemperatureArgument() {
		ArgumentsType arguments = new ArgumentsType();
		arguments.getArguments().add(new TemperatureArgument(null));
		return arguments;
	}

}
