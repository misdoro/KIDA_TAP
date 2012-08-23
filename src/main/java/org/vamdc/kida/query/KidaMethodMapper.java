package org.vamdc.kida.query;

import java.util.HashMap;
import java.util.Map;

import org.vamdc.dictionary.Restrictable;
import org.vamdc.kida.xsams.Method;
import org.vamdc.tapservice.querymapper.KeywordMapperImpl;

/**
 * Keyword mapper for MethodCategory. 
 * Warning, only "=" and "in()" queries are guaranteed to be supported.
 */
public class KidaMethodMapper extends KeywordMapperImpl{
	public KidaMethodMapper(Restrictable key) {
		super(key);
	}
	
	private static Map<String,String> methods = new HashMap<String,String>(){
		private static final long serialVersionUID = -8264083107847391165L;

	{
		for (Method.Name keyword:Method.Name.values()){
			//Note lower case
			put(keyword.getMethodCategory().name().toLowerCase(),keyword.getDbName());
		}
	}};
	
	@Override
	protected Object valueTranslator(Object value){
		if (value!=null && value instanceof String){
			String lcValue = ((String)value).toLowerCase();
			String kidaMethod = methods.get(lcValue);
			if (kidaMethod!=null && kidaMethod.length()>0)
				return kidaMethod;
		};
		
		return value;
	}
		

}
