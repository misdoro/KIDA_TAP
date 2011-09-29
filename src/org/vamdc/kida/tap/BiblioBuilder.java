package org.vamdc.kida.tap;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.vamdc.kida.Biblio;
import org.vamdc.kida.BiblioBook;
import org.vamdc.kida.BiblioJournal;
import org.vamdc.kida.BiblioThesis;
import org.vamdc.xsams.schema.SourceCategoryType;
import org.vamdc.xsams.sources.AuthorType;
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

		AuthorsType authors = new AuthorsType();
		Vector<String> biblioAuthors = cvBiblio.getAllAuthorsCollection();
		for (int i = 0; i < biblioAuthors.size(); i++) {
			AuthorType author = new AuthorType();
			author.setName(biblioAuthors.elementAt(i));
			authors.getAuthors().add(author);
		}
		bibliography.setAuthors(authors);
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
		
		String[] volumePage = bibBook.getPages().split(",");
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
