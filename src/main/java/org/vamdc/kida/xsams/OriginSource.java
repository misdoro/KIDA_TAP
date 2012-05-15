package org.vamdc.kida.xsams;

import java.util.Calendar;

import org.vamdc.kida.dao.ChannelValue;
import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.schema.SourceCategoryType;
import org.vamdc.xsams.schema.SourceType;
import org.vamdc.xsams.sources.AuthorsType;
import org.vamdc.xsams.util.IDs;

public class OriginSource extends SourceType{

	
	private OriginSource(ChannelValue channel){
		this.setSourceName(channel.getDatabase());
		this.setCategory(SourceCategoryType.DATABASE);
		this.setSourceID(getSourceID(channel.getDatabase()));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(channel.getCreatedAt());
		this.setYear(cal.get(Calendar.YEAR));
		this.setAuthors(new AuthorsType().addAuthor("unspecified"));
	}
	
	public static SourceType get(XSAMSManager document, ChannelValue channel){
		SourceType result = null;
		String origin = channel.getOrigin();
		String db = channel.getDatabase();
		
		if (origin!=null && origin.toLowerCase().contains("database") && getSourceID(db)!=null){
			result = document.getSource(getSourceID(db));
			
			if (result==null)
				document.addSource(result=new OriginSource(channel));
		}
		
		return result;
	}
	
	private static final String getSourceID(String cvDb){
		if (cvDb!=null && cvDb.length()>0)
			return IDs.getSourceID(cvDb.hashCode());
		return null;
	}
}
