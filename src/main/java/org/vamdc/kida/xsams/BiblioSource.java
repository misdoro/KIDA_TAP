package org.vamdc.kida.xsams;

import org.vamdc.kida.dao.Biblio;
import org.vamdc.kida.dao.BiblioBook;
import org.vamdc.kida.dao.BiblioJournal;
import org.vamdc.kida.dao.BiblioThesis;
import org.vamdc.xsams.schema.SourceCategoryType;
import org.vamdc.xsams.sources.AuthorsType;
import org.vamdc.xsams.sources.EditorsType;
import org.vamdc.xsams.sources.SourceType;
import org.vamdc.xsams.util.IDs;

public class BiblioSource extends SourceType{

	public BiblioSource(Biblio cvBiblio){

		if (cvBiblio.isABook())
			setCategory(SourceCategoryType.BOOK);
		else if (cvBiblio.isAJournal())
			setCategory(SourceCategoryType.JOURNAL);
		else if (cvBiblio.isAThesis())
			setCategory(SourceCategoryType.THESIS);

		setYear(cvBiblio.getYear());

		setAuthors(new AuthorsType(cvBiblio.getAllAuthors()));

		setTitle(cvBiblio.getTitle());

		if (getCategory()!=null){
			switch(getCategory()){
			case BOOK:
				writeBiblioBook(cvBiblio);
				break;
			case JOURNAL:
				writeBiblioJournal(cvBiblio);
				break;
			case THESIS:
				writeBiblioThesis(cvBiblio);
				break;
			default:
				break;
			}
		}
		this.setSourceID(IDs.getSourceID(cvBiblio.getId()));
	}

	private void writeBiblioThesis(Biblio cvBiblio) {
		BiblioThesis bibThesis = cvBiblio.getBiblioThesis();
		this.setCity(bibThesis.getSchool());
	}

	private void writeBiblioBook(Biblio cvBiblio) {
		BiblioBook bibBook = cvBiblio.getBiblioBook();
		setPages(bibBook.getPages());
		this.setDigitalObjectIdentifier(bibBook.getDoi());
		this.setSourceName(bibBook.getBooktitle());
		this.setPublisher(bibBook.getPublisher());

		EditorsType editors = new EditorsType();
		editors.getNames().add(bibBook.getEditor());
		this.setEditors(editors);
	}

	private void writeBiblioJournal(Biblio cvBiblio) {

		BiblioJournal bibJournal = cvBiblio.getBiblioJournal();
		setPages(bibJournal.getPage());
		this.setDigitalObjectIdentifier(bibJournal.getDoi());
		this.setSourceName(bibJournal.getJournal());

	}

	private void setPages(String pages) {
		if (pages==null)
			return;
		String page = "";
		String[] volumePage=null;

		volumePage = pages.split(",");
		if (volumePage.length>1)
			page = volumePage[1];


		String[] pageBeginEnd = page.split("-");
		String pageDebut = "";
		String pageFin = "";
		try {
			pageDebut = pageBeginEnd[0];
			pageFin = pageBeginEnd[1];
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		if (volumePage!=null && volumePage.length>0)
			this.setVolume(volumePage[0]);
		this.setPageBegin(pageDebut);
		this.setPageEnd(pageFin);
	}



}
