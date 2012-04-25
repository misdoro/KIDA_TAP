package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Biblio;
import org.vamdc.kida.dao.ChannelValue;
import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.common.DataSetType;
import org.vamdc.xsams.common.FitDataType;
import org.vamdc.xsams.schema.DataDescriptionType;
import org.vamdc.xsams.sources.SourceType;

public class FitDataSet extends DataSetType{
	
	public FitDataSet(ChannelValue fit,XSAMSManager document){
		
		this.setDataDescription(DataDescriptionType.RATE_COEFFICIENT);
		
		FitDataType fitData = new FitDataType();
		this.getFitDatas().add(fitData);
		
		Biblio cvBiblio = fit.getBibliography();
		if (cvBiblio != null) {
			SourceType bibliography = new BiblioSource(cvBiblio);
			document.addSource(bibliography);
			this.addSource(bibliography);
		}

		fitData.setProductionDate(fit.getCreatedAt());
		
		fitData.setFitParameters(new FitParameters(fit, document));
		
		
	}
	
}
