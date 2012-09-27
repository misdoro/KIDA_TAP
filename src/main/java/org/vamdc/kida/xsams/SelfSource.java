package org.vamdc.kida.xsams;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.vamdc.tapservice.api.RequestInterface;
import org.vamdc.xsams.schema.SourceCategoryType;
import org.vamdc.xsams.sources.AuthorsType;
import org.vamdc.xsams.sources.SourceType;
import org.vamdc.xsams.util.IDs;

public class SelfSource extends SourceType{
	
	public SelfSource(RequestInterface request){
		super();
		
		setSourceID(IDs.getSourceID(0));
		setCategory(SourceCategoryType.DATABASE);
		
		setSourceName("A KInetic Database for Astrochemistry (KIDA)");
		
		setAuthors(new AuthorsType("Wakelam, V. and Herbst, E. and Loison, J.-C. and Smith, I.W.M. and " +
				"Chandrasekaran, V. and Pavone, B. and Adams, N.G. and " +
				"Bacchus-Montabonel, M.-C. and Bergeat, A. and B\'eroff, K. and " +
				"Bierbaum, V.M. and Chabot, M. and Dalgarno, A. and van Dishoeck, E.F. and " +
				"Faure, A. and Geppert, W.D. and Gerlich, D. and Galli, D. and " +
				"Hebrard, E. and Hersant, F. and Hickson, K.M. and " +
				"Honvault, P. and Klippenstein, S.J. and Le Picard, S. and " +
				"Nyman, G. and Pernot, P. and Schlemmer, S. and Selsis, F. and " +
				"Sims, I.R. and Talbi, D. and Tennyson, J. and Troe, J. and " +
				"Wester, R. and Wiesenfeld, L."
				, " and "));
		
		setDigitalObjectIdentifier("10.1088/0067-0049/199/1/21");
		
		setComments("QUERY "+request.getQueryString());
		
		Calendar now = new GregorianCalendar();
		setYear(now.get(Calendar.YEAR));
		setProductionDate(now.getTime());
	}

}
