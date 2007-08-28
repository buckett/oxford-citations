/**********************************************************************************
 * $URL$
 * $Id$
 ***********************************************************************************
 *
 * Copyright (c) 2006 The Sakai Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *      http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.citation.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.sakaiproject.entity.api.Entity;
import org.sakaiproject.exception.IdUnusedException;


/**
 * 
 *
 */
public interface Citation 	// extends Entity
{
	/**
     * @param label
     * @param url
     * @return A unique identifier for the URL and its label.
     */
    public void addCustomUrl(String label, String url);
    
    /**
     * @param name
     * @param value
     */
    public void addPropertyValue(String name, Object value);
    
    /**
     * Write this citation in RIS format to an output stream
     * @param buffer
     * @throws IOException 
     */
    public void exportRis(StringBuffer buffer) throws IOException;
    
    /**
	 * Access a mapping of name-value pairs for various bibliographic information about the resource.
	 * Ideally, the names will be expressed as URIs for standard tags representing nuggets of bibliographic metadata.
	 * Values will be strings in formats specified by the standards defining the property names.  For example if the 
	 * name is a URI referencing a standard format for a "publication_date" and the standard indicates that the value
	 * should be expressed as in xs:date format, the value should be expressed as a string representation of xs:date.
	 * @return The mapping of name-value pairs.  The mapping may be empty, but it should never be null.
	 */
	public Map getCitationProperties();

	/**
	 * Access a representation of the value(s) of a named property.  If the property is multivalued, 
	 * the object returned will be a (possibly empty) java.util.List.  Otherwise it will be an Object 
	 * of a type appropriate for the named property (usually a String or a Date). The value(s)
	 * must be of a type for which toString() is defined and returns a reasonable representation 
	 * for use in a textual display of the citation.
	 * @param name The name of the property for which a value is to be returned.
	 * @return A representation of the value(s) of the named property.  May be an empty String ("") 
	 * if the property is not defined. 
	 */
	public Object getCitationProperty(String name);
	
	/**
	 * @return
	 */
	public String getCreator();
	
	/**
     * @param id
     * @return
     */
    public String getCustomUrl(String id) throws IdUnusedException;
    
	/**
     * @return
     */
    public List getCustomUrlIds();
	
	/**
     * @param id
     * @return
	 * @throws IdUnusedException 
     */
    public String getCustomUrlLabel(String id) throws IdUnusedException;
    
    public String getYear();
	
	/**
	 * Access the brief "title" of the resource, which can be used to display the item in a list of items.
	 * @return The display name.  
	 */
	public String getDisplayName();

	/**
     * @return
     */
    public String getFirstAuthor();
	
	/**
     * @return
     */
    public String getId();
	
	/**
	 * @return
	 */
	public String getOpenurl();

	/**
	 * @return
	 */
	public String getOpenurlParameters();
	
	public String getSaveUrl(String collectionId);

	/**
	 * Access the schema for the Citation
	 * @return th
	 */
	public Schema getSchema();
    
    /**
	 * @return
	 */
	public String getSource();
	
	/**
     * @return
     */
    public boolean hasCustomUrls();

	/**
	 * @return
	 */
	public boolean hasPropertyValue(String fieldId);

	/**
     * Read this citation from an input stream in RIS format
     * @param istream
	 * @throws IOException 
     */
    public void importFromRis(InputStream ris) throws IOException;
    
    /**
	 * @return
	 */
	public boolean importFromRisList(List risList);

	/**
	 * @return
	 */
	public boolean isAdded();
    
    /**
     * @param fieldId
     * @return
     */
    public boolean isMultivalued(String fieldId);

	/**
	 * Access a list of names of citation properties defined for this resource.
	 * @return The list of property names.  The list may be empty, but it should never be null.
	 */
	public List listCitationProperties();
    
    /**
     * @param added
     */
    public void setAdded(boolean added);
    
    /**
	 * @param name
	 * @param value
	 */
	public void setCitationProperty(String name, Object value);
    
    /**
	 * @param name
	 */
	public void setDisplayName(String name);

    /**
     * @param schema
     */
    public void setSchema(Schema schema);

	/**
	 * Replaces the current value(s) of a citation property.  If the field identified by the parameter "name" 
	 * is multivalued, the values in the list parameter "values" replace the current values.  If the field is 
	 * single valued, the first value in the list "values" replaces the current value. In either case, if the
	 * values parameter is null or empty, all current values are removed and no new values are added.  
	 * 
	 * @param name
	 * @param values
	 */
	public void updateCitationProperty(String name, List values);

	/**
     * @param urlid
     * @param label
     * @param url
     */
    public void updateCustomUrl(String urlid, String label, String url);

} // interface Citation

