package org.vamdc.kida.xsams;

import java.util.Map;
import java.util.TreeMap;

import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.schema.MethodCategoryType;
import org.vamdc.xsams.schema.MethodType;
import org.vamdc.xsams.util.IDs;

public class Method extends MethodType{
	
	public enum Name{
		Estimation("Estimation","est",MethodCategoryType.EMPIRICAL),
		Calculations("Calculations","calc",MethodCategoryType.THEORY),
		Measurements("Measurements","mes",MethodCategoryType.EXPERIMENT),
		Evaluations("Reviews and Evaluations","eva",MethodCategoryType.EVALUATED),
		
		;
		
		private final String dbName;
		private final String identifier;
		private final MethodCategoryType xsamsType;
		Name(String databaseName, String identifier, MethodCategoryType xsamsType){
			this.dbName = databaseName;
			this.identifier = identifier;
			this.xsamsType = xsamsType;
		}
	}
	
	public static MethodType getMethod(XSAMSManager document, String dbName){
		if (dbName==null)
			return null;
		MethodType result = null;
		Name method = methods.get(dbName);
		
		if (method!=null){
			String methodId = getMethodId(method);
			result = document.getMethod(methodId);
			if (result==null)
				document.addMethod(result=new Method(method));
		}
		
		return result;
	}

	private static String getMethodId(Name method) {
		return IDs.getID('M', method.identifier);
	}
	
	
	private final static Map<String,Name> methods = new TreeMap<String,Name>(){
		private static final long serialVersionUID = -4462080373535785824L;

	{
		for (Name method:Name.values()){
			put(method.dbName,method);
		}
	}};
	
	private Method(Name type){
		super();
		this.setCategory(type.xsamsType);
		this.setDescription(type.dbName);
		this.setMethodID(getMethodId(type));
	}
	


}
