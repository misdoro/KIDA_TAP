package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Biblio;
import org.vamdc.kida.dao.ChannelValue;
import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.common.DataSetType;
import org.vamdc.xsams.common.FitDataType;
import org.vamdc.xsams.common.FitParametersType;
import org.vamdc.xsams.common.NamedDataType;
import org.vamdc.xsams.common.ValueType;
import org.vamdc.xsams.schema.DataDescriptionType;
import org.vamdc.xsams.schema.FunctionType;
import org.vamdc.xsams.sources.SourceType;
import org.vamdc.xsams.util.IDs;

public class FitDataSet extends DataSetType{
	
	public FitDataSet(ChannelValue fit,XSAMSManager document){
		
		this.setDataDescription(DataDescriptionType.RATE_COEFFICIENT);
		
		FitDataType fitData = new FitDataType();

		// Bibliography
		Biblio cvBiblio = fit.getBibliography();
		if (cvBiblio != null) {
			SourceType bibliography = new BiblioSource(cvBiblio);
			document.addSource(bibliography);
			this.addSource(bibliography);
		}

		// channel value
		fitData.setProductionDate(fit.getCreatedAt());
		
		fitData.setFitParameters(writeFitParameters(fit, document));
		this.getFitDatas().add(fitData);
	}
	
	private static FitParametersType writeFitParameters(
			ChannelValue channelValue, XSAMSManager document) {
		FitParametersType result = new FitParametersType();

		String funcId = IDs.getFunctionID(channelValue.getFormula().getId());
		FunctionType func = document.getFunction(funcId);
		if (func==null){
			func = new KidaFunction(channelValue.getFormula());
			document.addFunction(func);
		}
		result.setFunctionRef(func);

		result.getFitArguments().add(new TemperatureArgument(channelValue.getValidityRange()));

		NamedDataType alpha = new NamedDataType();
		alpha.setValue(new ValueType(channelValue.getValue("alpha"), 
				channelValue.getChannel().getUnitAlpha()));
		alpha.setName("alpha");
		result.getFitParameters().add(alpha);
		NamedDataType beta = new NamedDataType();
		beta.setValue(new ValueType(channelValue.getValue("beta"), "undef"));
		beta.setName("beta");
		result.getFitParameters().add(beta);
		NamedDataType gamma = new NamedDataType();
		gamma.setValue(new ValueType(channelValue.getValue("gamma"), "keV"));
		gamma.setName("gamma");
		result.getFitParameters().add(gamma);

		return result;

	}

}
