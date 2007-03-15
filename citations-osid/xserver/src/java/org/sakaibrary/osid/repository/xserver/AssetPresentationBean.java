package org.sakaibrary.osid.repository.xserver;

import org.sakaibrary.osid.repository.xserver.CreatorPartStructure;
import org.sakaibrary.osid.repository.xserver.DatePartStructure;
import org.sakaibrary.osid.repository.xserver.PagesPartStructure;
import org.sakaibrary.osid.repository.xserver.SourceTitlePartStructure;
import org.sakaibrary.osid.repository.xserver.URLPartStructure;

/**
 * This class wraps an org.osid.repository.Asset to make it more suitable
 * for presentation
 * 
 * @author gbhatnag
 */
public class AssetPresentationBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private static final org.apache.commons.logging.Log LOG =
		org.apache.commons.logging.LogFactory.getLog(
				"org.sakaibrary.osid.repository.xserver.AssetPresentationBean" );

	private org.osid.repository.Asset asset;
	private String assetId;
	private String displayName;  // title
	private String description;  // abstract
	private Integer content;
	private java.util.ArrayList parts;

	/**
	 * Constructor takes an org.osid.repository.Asset and extracts all
	 * data for easier presentation.
	 */
	public AssetPresentationBean( org.osid.repository.Asset asset ) {
		parts = new java.util.ArrayList();
		
		try {
			assetId = asset.getId().getIdString();
			displayName = asset.getDisplayName();
			description = asset.getDescription();
			content = ( Integer )asset.getContent();
			
			org.osid.repository.RecordIterator rit = asset.getRecords();
			while( rit.hasNextRecord() ) {
				org.osid.repository.Record record = rit.nextRecord();
				
				org.osid.repository.PartIterator pit = record.getParts();
				while( pit.hasNextPart() ) {
					parts.add( pit.nextPart() );
				}
			}
		} catch( Throwable t ) {
			LOG.warn( "AssetPresentationBean() failed to loop through Asset, " +
					"Record, Parts: " + t.getMessage(), t );
		}

		this.asset = asset;
	}

	public String getAssetId() {
		return assetId;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}
	
	public Integer getContent() {
		return content;
	}
	
	public String getAuthor() throws org.osid.repository.RepositoryException,
	org.osid.shared.SharedException {
		org.osid.shared.ObjectIterator objIterator =
			asset.getPartValuesByPartStructure( CreatorPartStructure.getInstance().getId() );
		
		return ( objIterator.hasNextObject() ) ?
				((String)objIterator.nextObject() + ".") : null;
	}
	
	public String getSourceTitle() throws org.osid.repository.RepositoryException,
	org.osid.shared.SharedException {
		org.osid.shared.ObjectIterator objIterator =
			asset.getPartValuesByPartStructure( SourceTitlePartStructure.getInstance().getId() );
		
		return ( objIterator.hasNextObject() ) ?
				(String)objIterator.nextObject() : null;
	}
	
	public String getDate() throws org.osid.repository.RepositoryException,
	org.osid.shared.SharedException {
		org.osid.shared.ObjectIterator objIterator =
			asset.getPartValuesByPartStructure( DatePartStructure.getInstance().getId() );
		
		return ( objIterator.hasNextObject() ) ?
				(String)objIterator.nextObject() : null;
	}
	
	public String getCoverage() throws org.osid.repository.RepositoryException,
	org.osid.shared.SharedException {
		org.osid.shared.ObjectIterator objIterator =
			asset.getPartValuesByPartStructure( PagesPartStructure.getInstance().getId() );
		
		return ( objIterator.hasNextObject() ) ?
				(String)objIterator.nextObject() : null;
	}
	
	public String getUrl() throws org.osid.repository.RepositoryException,
	org.osid.shared.SharedException {
		org.osid.shared.ObjectIterator objIterator =
			asset.getPartValuesByPartStructure( URLPartStructure.getInstance().getId() );
		
		return ( objIterator.hasNextObject() ) ?
				(String)objIterator.nextObject() : null;
	}

	public java.util.ArrayList getParts() {
		return parts;
	}
}