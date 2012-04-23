package org.vamdc.kida.tap;

import java.sql.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.vamdc.kida.dao.*;
import org.vamdc.xsams.schema.SourceCategoryType;
import org.vamdc.xsams.sources.AuthorsType;
import org.vamdc.xsams.sources.SourceType;
import org.vamdc.xsams.util.IDs;

public class BiblioBuilder {

	public static SourceType writeBibliography(Biblio cvBiblio) {

		SourceType bibliography = new SourceType();
		if (cvBiblio.isABook())
			bibliography.setCategory(SourceCategoryType.BOOK);
		else if (cvBiblio.isAJournal())
			bibliography.setCategory(SourceCategoryType.JOURNAL);
		else if (cvBiblio.isAThesis())
			bibliography.setCategory(SourceCategoryType.THESIS);
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.setTime(new Date(cvBiblio.getYear()));
		XMLGregorianCalendar xmlCalendar = null;
		try {
			xmlCalendar = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(gCalendar);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bibliography.setYear(xmlCalendar);

		bibliography.setAuthors(new AuthorsType(cvBiblio.getAllAuthors()));
		bibliography.setTitle(cvBiblio.getTitle() );
		if (cvBiblio.isAJournal()) {
			writeBiblioJournal(cvBiblio, bibliography);

		}
		if (cvBiblio.isABook()) {
			writeBiblioBook(cvBiblio, bibliography);

		}
		if (cvBiblio.isAThesis()) {
			writeBiblioThesis(cvBiblio, bibliography);

		}
		bibliography.setSourceID(IDs.getSourceID(cvBiblio.getId()));
		return bibliography;

	}

	private static void writeBiblioThesis(Biblio cvBiblio, SourceType bibliography) {
		BiblioThesis bibThesis = cvBiblio.getBiblioThesis();
		bibliography.setCity(bibThesis.getSchool());

	}

	private static void writeBiblioBook(Biblio cvBiblio, SourceType bibliography) {
		BiblioBook bibBook = cvBiblio.getBiblioBook();
		
		String page = "";
		
		String pages = bibBook.getPages();
		String[] volumePage=null;
		if (pages!=null){
			volumePage = bibBook.getPages().split(",");
			if (volumePage.length>1)
				page = volumePage[1];
		}

		String[] pageBeginEnd = page.split("-");
		String pageDebut = "";
		String pageFin = "";
		try {
			pageDebut = pageBeginEnd[0];
			pageFin = pageBeginEnd[1];
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		if (volumePage!=null && volumePage.length>0)
			bibliography.setVolume(volumePage[0]);
		bibliography.setPageBegin(pageDebut);
		bibliography.setPageEnd(pageFin);
		bibliography.setDigitalObjectIdentifier(bibBook.getDoi());
		bibliography.setSourceName(bibBook.getBooktitle());

	}

	private static void writeBiblioJournal(Biblio cvBiblio, SourceType bibliography) {
		BiblioJournal bibJournal = cvBiblio.getBiblioJournal();
		String[] volumePage = bibJournal.getPage().split(",");
		String page = "";
		try {
			page = volumePage[1];
		} catch (ArrayIndexOutOfBoundsException e) {

		}

		String[] pageBeginEnd = page.split("-");
		String pageDebut = "";
		String pageFin = "";
		try {
			pageDebut = pageBeginEnd[0];
			pageFin = pageBeginEnd[1];
		} catch (ArrayIndexOutOfBoundsException e) {

		}

		bibliography.setVolume(volumePage[0]);
		bibliography.setPageBegin(pageDebut);
		bibliography.setPageEnd(pageFin);
		bibliography.setDigitalObjectIdentifier(bibJournal.getDoi());
		bibliography.setSourceName(bibJournal.getJournal());

	}
}
