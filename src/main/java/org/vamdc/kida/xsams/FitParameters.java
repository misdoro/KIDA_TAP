package org.vamdc.kida.xsams;

import java.util.Collection;

import org.vamdc.kida.dao.ChannelValue;
import org.vamdc.kida.dao.ValidityRange;
import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.schema.ArgumentType;
import org.vamdc.xsams.schema.FunctionParameterType;
import org.vamdc.xsams.common.FitParametersType;
import org.vamdc.xsams.common.NamedDataType;
import org.vamdc.xsams.functions.FunctionType;

public class FitParameters extends FitParametersType {

	public FitParameters(ChannelValue channelValue, XSAMSManager document){
		super();
		
		FunctionType function = KidaFunction.getKidaFunction(channelValue.getFormula(), document);
		
		this.setFunctionRef(function);
		
		this.getFitArguments().add(
				getTemperatureRange(function.getArgument(), channelValue.getValidityRange()));
		
		loadFitParameters(channelValue, function.getParameters().getParameters());
	}

	public void loadFitParameters(ChannelValue channelValue, Collection<FunctionParameterType> parameters) {
		for (FunctionParameterType fp:parameters){
			String name = fp.getName();
			this.getFitParameters().add(
					new NamedDataType(
							name,
							channelValue.getValue(name),
							fp.getUnits()));
		}
	}
	
	private static ArgumentType getTemperatureRange(ArgumentType argument,ValidityRange valid){
		ArgumentType result = argument;
		if (argument!=null && argument.getName().equals("T")){
			result = (ArgumentType) argument.clone();
			result.setLowerLimit(valid.getTmin().doubleValue());
			result.setUpperLimit(valid.getTmax().doubleValue());
			return result;
		}
		return null;
	}
	
}
