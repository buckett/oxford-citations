package edu.indiana.lib.osid.base.repository.http;

/*******************************************************************************
 * Copyright (c) 2003, 2004, 2005, 2007, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *******************************************************************************/

public class DOIPartStructure implements org.osid.repository.PartStructure {

		private static org.apache.commons.logging.Log	_log = edu.indiana.lib.twinpeaks.util.LogUtils.getLog(DOIPartStructure.class);
		
  private org.osid.shared.Id DOI_PART_STRUCTURE_ID = null;
  private org.osid.shared.Type type = new Type( "sakaibrary", "partStructure",
      "doi", "Digital Object Identifier" );
  private String displayName = "DOI";
  private String description = "Digital Object Identifier";
  private boolean mandatory = false;
  private boolean populatedByRepository = false;
  private boolean repeatable = true;

  private static DOIPartStructure doiPartStructure =
    new DOIPartStructure();

  protected static DOIPartStructure getInstance()
  {
    return doiPartStructure;
  }

	/**
	 * Public method to fetch the PartStructure ID
	 */
	public static org.osid.shared.Id getPartStructureId()
	{
		org.osid.shared.Id id = null;

		try
		{
			id = getInstance().getId();
		}
		catch (org.osid.repository.RepositoryException ignore) { }

		return id;
	}

  public String getDisplayName()
    throws org.osid.repository.RepositoryException
  {
    return this.displayName;
  }

  public String getDescription()
    throws org.osid.repository.RepositoryException
  {
    return this.description;
  }

  public boolean isMandatory()
    throws org.osid.repository.RepositoryException
  {
    return this.mandatory;
  }

  public boolean isPopulatedByRepository()
    throws org.osid.repository.RepositoryException
  {
    return this.populatedByRepository;
  }

  public boolean isRepeatable()
    throws org.osid.repository.RepositoryException
  {
    return this.repeatable;
  }

  protected DOIPartStructure()
  {
    try
    {
      this.DOI_PART_STRUCTURE_ID =
      	Managers.getIdManager().getId("8na2340898wna345f5467ks72hk0001ff");
    }
    catch (Throwable t)
    {
    }
  }

  public void updateDisplayName(String displayName)
    throws org.osid.repository.RepositoryException
  {
    throw new org.osid.repository.RepositoryException(
        org.osid.OsidException.UNIMPLEMENTED);
  }

  public org.osid.shared.Id getId()
    throws org.osid.repository.RepositoryException
  {
    return this.DOI_PART_STRUCTURE_ID;
  }

  public org.osid.shared.Type getType()
    throws org.osid.repository.RepositoryException
  {
    return this.type;
  }

  public org.osid.repository.RecordStructure getRecordStructure()
    throws org.osid.repository.RepositoryException
  {
    return RecordStructure.getInstance();
  }

  public boolean validatePart(org.osid.repository.Part part)
    throws org.osid.repository.RepositoryException
  {
    return true;
  }

  public org.osid.repository.PartStructureIterator getPartStructures()
    throws org.osid.repository.RepositoryException
  {
    return new PartStructureIterator(new java.util.Vector());
  }
}
