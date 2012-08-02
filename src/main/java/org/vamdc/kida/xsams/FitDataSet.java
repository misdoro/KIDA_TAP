package org.vamdc.kida.xsams;

import org.vamdc.kida.constants.Evaluation;
import org.vamdc.kida.dao.ChannelValue;
import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.common.DataSetType;
import org.vamdc.xsams.common.FitDataType;
import org.vamdc.dictionary.DataDescriptionType;
import org.vamdc.xsams.util.StringUtil;

public class FitDataSet extends DataSetType{
	
	public FitDataSet(ChannelValue fit,XSAMSManager document){
		
		this.setDataDescription(DataDescriptionType.rateCoefficient.name());
		
		FitDataType fitData = new FitDataType();
		this.getFitDatas().add(fitData);
		
		fitData.setProductionDate(fit.getCreatedAt());
		
		fitData.setFitParameters(new FitParameters(fit, document));
		
		fitData.setFitAccuracy(fit.getAccuracy());
		
		fitData.setComments(Evaluation.getEvaluation(fit));

		fitData.addSource(BiblioSource.get(document, fit.getBibliography()));
		
		fitData.addSource(OriginSource.get(document, fit));
		
		fitData.setMethodRef(Method.getMethod(document, fit.getMethod()));
		fitData.setComments(StringUtil.checkNull(fit.getDescription()));
		
		
	}
	
}
