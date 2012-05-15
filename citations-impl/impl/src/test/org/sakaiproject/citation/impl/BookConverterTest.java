package org.sakaiproject.citation.impl;

import java.util.List;

import org.jmock.Expectations;
import org.sakaiproject.citation.api.Citation;
import org.sakaiproject.citation.api.CitationService;
import org.sakaiproject.citation.api.Schema;
import org.sakaiproject.citation.api.Schema.Field;
import org.sakaiproject.citation.impl.openurl.BookConverter;
import org.sakaiproject.citation.impl.openurl.ContextObjectEntity;

import java.util.Collections;

public class BookConverterTest extends BaseCitationServiceSupport {

	private static final String DOI = "/doi/value";
	private static final String BOOK_SERIES = "A seris of books";
	private static final String ISBN = "1234567890";
	private static final String PUBLISHER = "A publisher of books.";
	private static final String DATE = "2008-08-08";
	private static final String AUTHOR = "An Author";
	private static final String TITLE = "A Book Title";

	public void testSimpleBookConverstion() throws Exception {
		CitationService citationService = createCitationService();
		BookConverter converter = new BookConverter();
		converter.setCitationService(citationService);
		
		Citation book = citationService.addCitation("book");
		book.setCitationProperty(Schema.TITLE, TITLE);
		book.setCitationProperty(Schema.CREATOR, AUTHOR);
		book.setCitationProperty(Schema.YEAR, DATE);
		book.setCitationProperty(Schema.PUBLISHER, PUBLISHER);
		book.setCitationProperty(Schema.ISN, ISBN);
		book.setCitationProperty(Schema.SOURCE_TITLE, BOOK_SERIES);
		book.setCitationProperty("doi", DOI);
		
		ContextObjectEntity bookContextObject = converter.convert(book);
		assertEquals(TITLE, bookContextObject.getValue("btitle"));
		assertEquals(AUTHOR, bookContextObject.getValue("au"));
		assertEquals(DATE, bookContextObject.getValue("date"));
		assertEquals(PUBLISHER, bookContextObject.getValue("pub"));
		assertEquals(ISBN, bookContextObject.getValue("isbn"));
		assertEquals(BOOK_SERIES, bookContextObject.getValue("series"));
		assertTrue(bookContextObject.getIds().contains("info:doi"+ DOI));
	}
	
	public void testSetMultipleProperties() throws Exception {
		CitationService citationService = createCitationService();
		Citation book = citationService.addCitation("book");

		// The Schema aren't loaded so we need to mock the schema.
		final Schema bookSchema = mock(Schema.class);
		final Field titleField = mock(Field.class);
		
		checking(new Expectations() {
			{
				// The single field in our fake schema.
				allowing(titleField).isMultivalued();
				will(returnValue(false));
				allowing(titleField).isRequired();
				will(returnValue(true));
				allowing(titleField).getDefaultValue();
				will(returnValue(null));
				
				// The Schema
				allowing(bookSchema).getFields();
				will(returnValue(Collections.singletonList(titleField)));
				allowing(bookSchema).getRequiredFields();
				will(returnValue(Collections.singletonList("title")));
				allowing(bookSchema).getField("title");
				will(returnValue(titleField));
				allowing(bookSchema).getField(with(any(String.class)));
				will(returnValue(null));
			}
		});
		
		
		book.setSchema(bookSchema);
		// Check that a property off schema can grow to be multiple values.
		book.setCitationProperty("otherId", "first");
		book.setCitationProperty("otherId", "second");
		assertTrue(book.getCitationProperty("otherId") instanceof List);
		List ids = (List)book.getCitationProperty("otherId");
		assertTrue(ids.contains("first"));
		assertTrue(ids.contains("second"));
		assertFalse(ids.contains("missing"));
		
		// Check that titles don't become multivalued.
		book.setCitationProperty("title", "My Book");
		assertEquals("My Book", book.getCitationProperty("title"));
		book.setCitationProperty("title", "Other Book");
		assertEquals("Other Book", book.getCitationProperty("title"));
	}
}