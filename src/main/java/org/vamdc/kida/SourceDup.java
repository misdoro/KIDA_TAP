package org.vamdc.kida;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.Query;
import org.apache.cayenne.query.SelectQuery;
import org.vamdc.kida.dao.Biblio;


public enum SourceDup {
	INSTANCE;
	//
	private static Map<Integer,Biblio> idToBib=new TreeMap<Integer,Biblio>();
	
	static{
		fillDuplicates();
	}
	
	public Biblio getBiblio(Integer id){
		return idToBib.get(id);
	}
	
	private static void fillDuplicates(){
		ObjectContext context ;
			context=DataContext.createDataContext();
		SelectQuery query = new SelectQuery(Biblio.class);
		Collection<Biblio> bibs = context.performQuery(query);
		Map<Biblio,Biblio> uniqueBib = new HashMap<Biblio,Biblio>();
		for (Biblio bib:bibs){
			Biblio firstBib=null;
			if ((firstBib=uniqueBib.get(bib))==null){
				firstBib=bib;
				uniqueBib.put(bib, bib);
				System.out.println("adding"+firstBib.getId()+firstBib.getTitle());
				
				
			}else{
				System.out.println("reusing"+firstBib.getId()+firstBib.getTitle());
			}
			idToBib.put(bib.getId(), firstBib);
		}
		
	}
	
}
