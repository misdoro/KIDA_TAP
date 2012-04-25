package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Formula;
import org.vamdc.xsams.XSAMSManager;
import org.vamdc.xsams.common.ExpressionType;
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

	}

	private ExpressionType getMathExpression(Formula formula){
		ExpressionType result = new ExpressionType();
		result.setComputerLanguage("Fortran");
		result.setValue(formula.getMath());
		return result;
	}
	
	public static FunctionType newKidaFunction(Formula formula){
		if (formula!=null){
			switch(formula.getId()){
			case FID_COSMIC:
				return new FunctionCosmic(formula);
			case FID_PHOTO:
				return new FunctionPhoto(formula);
			case FID_KOOIJ:
				return new FunctionKooij(formula);
			case FID_IONPOL1:
			case FID_IONPOL2:
				return new FunctionIonpol(formula);
			}
		}
		return null;	
	}
	
	public static FunctionType getKidaFunction(Formula formula,
			XSAMSManager document) {
		String funcId = IDs.getFunctionID(formula.getId());
		FunctionType func = (FunctionType) document.getFunction(funcId);
		if (func==null){
			func = KidaFunction.newKidaFunction(formula);
			document.addFunction(func);
		}
		return func;
	}
}
