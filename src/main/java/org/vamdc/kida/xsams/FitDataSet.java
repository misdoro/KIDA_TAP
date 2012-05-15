package org.vamdc.kida.xsams;

import org.vamdc.kida.constants.Evaluation;
import org.vamdc.kida.dao.ChannelValue;
import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.common.DataSetType;
import org.vamdc.xsams.common.FitDataType;
import org.vamdc.xsams.schema.DataDescriptionType;
import org.vamdc.xsams.util.StringUtil;

public class FitDataSet extends DataSetType{
	
	public FitDataSet(ChannelValue fit,XSAMSManager document){
		
		this.setDataDescription(DataDescriptionType.RATE_COEFFICIENT);
		
		FitDataType fitData = new FitDataType();
		this.getFitDatas().add(fitData);
		
		fitData.setProductionDate(fit.getCreatedAt());
		
		fitData.setFitParameters(new FitParameters(fit, document));
		
		fitData.setFitAccuracy(fit.getAccuracy());
		
		fitData.setComments(Evaluation.getEvaluation(fit));

		this.addSource(BiblioSource.get(document, fit.getBibliography()));
		
		this.addSource(OriginSource.get(document, fit));
		
		this.setMethodRef(Method.getMethod(document, fit.getMethod()));
		this.setComments(StringUtil.checkNull(fit.getDescription()));
		
		
	}
	
}
