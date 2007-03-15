/*
 * Shows advanced form (and hides basic form) on search and search results
 * screens.  Advanced and basic search form elements need to have the correct
 * ids for this function to work properly.
 */
function showAdvancedForm() {
  // hide the basic form, header & button
  $( "#basicSearchForm" ).hide();
  $( "#basicSearchHeader" ).hide();
  $( "#basicSearchButton" ).hide();
  
  // show the advanced form & header
  $( "#advancedSearchForm" ).show();
  $( "#advancedSearchHeader" ).show();
  $( "#advancedSearchButton" ).show();
  
  // carry over any keywords entered if advanced keyword field is blank
  var advKeywordCriteria = document.getElementById( "advCriteria1" );
  if( advKeywordCriteria.value == "" ) {
    advKeywordCriteria.value = document.getElementById( "keywords" ).value;
  }
  
  resizeFrame();
}

/*
 * Shows basic form (and hides advanced form) on search and search results
 * screens.  Advanced and basic search form elements need to have the correct
 * ids for this function to work properly.
 */
function showBasicForm() {
  // hide the advanced form, header & button
  $( "#advancedSearchForm" ).hide();
  $( "#advancedSearchHeader" ).hide();
  $( "#advancedSearchButton" ).hide();
  
  // show the basic form, header & button
  $( "#basicSearchForm" ).show();
  $( "#basicSearchHeader" ).show();
  $( "#basicSearchButton" ).show();
  
  // carry over advanced fields entered
  var keywordBuffer = "";
  $( ".advField" ).each( function() {
    if( this.value && this.value != "" ) {
      keywordBuffer = keywordBuffer + this.value + " ";
    }
  } );
  
  if( keywordBuffer.length > 0 ) {
    var keywords = keywordBuffer.substr( 0, keywordBuffer.length-1 );
    document.getElementById( "keywords" ).value = keywords;
  }
  
  resizeFrame();
}

function clearAdvancedForm() {
  $( ".advField" ).each( function() {
    this.value = "";
  } );
}

/*
 * Shows/hides details associated with a given search result.
 *
 * Params:
 *   citationId  id of the citation with details to show/hide
 *   altShow     text used to set the alt of the show toggle icon
 *   altHide     text used to set the alt of the hide toggle icon
 */
function toggleDetails( citationId, altShow, altHide ) {
  $( "#details_" + citationId ).slideToggle( 300,
    function() {
      resizeFrame();
    }
  );
  
  // toggle expand/hide image
  var image = document.getElementById( "toggle_" + citationId );
  
  if( image.alt == altShow ) {
    image.src = "/library/image/sakai/collapse.gif?panel=Main";
    image.alt = altHide;
  } else {
    image.src = "/library/image/sakai/expand.gif?panel=Main";
    image.alt = altShow;
  }
}

/*
 * Shows the details of all search results on the screen.
 *
 * Params:
 *   altHide  text used to set the alt of the hide toggle icon
 */
function showAllDetails( altHide ) {
  // show descriptions
  $( ".citationDetails" ).show( function() {
      resizeFrame();
    }
  );

  // show proper toggle icon
  $( ".toggleIcon" ).each( function() {
    this.src = "/library/image/sakai/collapse.gif?panel=Main";
    this.alt = altHide;
  } );
}

/*
 * Hides the details of all search results on the screen.
 *
 * Params:
 *   altShow  text used to set the alt of the show toggle icon
 */
function hideAllDetails( altShow ) {
  // hide descriptions
  $( ".citationDetails" ).hide( function() {
      resizeFrame();
    }
  );
  
  // show proper toggle icon
  $( ".toggleIcon" ).each( function() {
    this.src = "/library/image/sakai/expand.gif?panel=Main";
    this.alt = altShow;
  } );
}

/*
 * Checks all checkboxes in the given form
 *
 * Params:
 *   formId  id of form element for which all checkboxes should be checked
 */
function selectAll( formId ) {
  $( "input:checkbox", "#" + formId ).attr( "checked", "checked" );
  highlightCheckedSelections();
}

/*
 * Unchecks all checkboxes in the given form
 *
 * Params:
 *   formId  id of form element for which all checkboxes should be unchecked
 */
function selectNone( formId ) {
  $( "input:checkbox", "#" + formId ).attr( "checked", "" );
  highlightCheckedSelections();
}

/*
 * Resizes the frame to avoid double scroll bars when making dynamic changes
 * to the page.  This method has not been tested with IE 5.5.
 */
function resizeFrame() {
  var frame = parent.document.getElementById( window.name );
      
  if( frame ) {
    var clientH = document.body.clientHeight + 10;
    $( frame ).height( clientH );
  } else {
    throw( "resizeFrame did not get the frame (using name=" + window.name + ")" );
  }
}

/*
 * Submits the form with the given id
 */
function submitform( id )
{
  var theForm = document.getElementById(id);
  if(theForm && theForm.onsubmit)
  {
    theForm.onsubmit();
  }
  if(theForm && theForm.submit)
  {
     theForm.submit();
  }
}

/*
 * Adds/removes a citation to/from a citation collection.
 *
 * This function makes an AJAX call to the server to add/remove the citation
 * without having to refresh the page.
 *
 * Params:
 *   baseUrl         base URL for this tool
 *   citationButton  button used to control add/remove of a citation
 *   collectionId    id of the citation collection to edit
 *   spinnerId       id of the html element used to show/hide a spinner while
 *                   the AJAX process completes
 *   addLabel        value used for the button to indicate add
 *   removeLabel     value used for the button to indicate remove
 */
function toggleCitation( baseUrl, citationButton, collectionId, spinnerId, addLabel, removeLabel )
{
  if(citationButton && citationButton.value)
  {
    // switch to spinner
    $( "#" + citationButton.id ).hide();
    $( "#" + spinnerId ).show();

    if( addLabel == citationButton.value )
    { 
      // do AJAX load
      $( "#messageDiv" ).load( baseUrl + "&sakai_action=doMessageFrame&collectionId=" + collectionId + "&citationId=" + citationButton.id + "&operation=add",
        function() {
          // update the button's id using the value from the AJAX response
          document.getElementById( citationButton.id ).id = document.getElementById( "addedCitationId" ).value;
          
          // hide the spinner
          $( "#" + spinnerId ).hide();
          $( "#" + citationButton.id ).show();
          
          // change label to remove
          citationButton.value = removeLabel;
          
          // change highlighting
          highlightButtonSelections( removeLabel );
        } );
    }
    else
    {
      // do AJAX load
      $( "#messageDiv" ).load( baseUrl + "&sakai_action=doMessageFrame&collectionId=" + collectionId + "&citationId=" + citationButton.id + "&operation=remove",
        function() {
          // hide the spinner
          $( "#" + spinnerId ).hide();
          $( "#" + citationButton.id ).show();
          
          // change label to add
          citationButton.value = addLabel;
          
          // change highlighting
          highlightButtonSelections( removeLabel );
        } );
    }
  }
}

/*
 * Highlights rows selected in a table using a checkbox element
 */
function highlightCheckedSelections() {
  $( "tr[input]" ).removeClass( "highLightAdded" );
  $( "tr[input[@checked]]" ).addClass( "highLightAdded" );
}

function countDatabases( checkbox, maxDbNum ) {
  var numSelected = $( "input[@type=checkbox][@checked]", "#databaseArea" ).size();

  if( numSelected > maxDbNum ) {
    alert( "Sorry - No more than " + maxDbNum + " databases can be searched at one time." );
    checkbox.checked = false;
  }
}

function showDbDescriptions( altHide ) {
  // show descriptions
  $( ".dbDescription" ).show( function() {
      resizeFrame();
    }
  );

  // show proper toggle icon
  $( ".dbToggleIcon" ).each( function() {
    this.src = "/library/image/sakai/collapse.gif?panel=Main";
    this.alt = altHide;
  } );
}

function hideDbDescriptions( altShow ) {
  // hide descriptions
  $( ".dbDescription" ).hide( function() {
      resizeFrame();
    }
  );
  
  // show proper toggle icon
  $( ".dbToggleIcon" ).each( function() {
    this.src = "/library/image/sakai/expand.gif?panel=Main";
    this.alt = altShow;
  } );
}

function toggleDbDescription( database_id, altShow, altHide ) {
  // toggle description
  $( "#description_" + database_id ).slideToggle( 300,
    function() {
      resizeFrame();
    }
  );
  
  // toggle expand/hide image
  var image = document.getElementById( "toggle_" + database_id );
  
  if( image.alt == altShow ) {
    image.src = "/library/image/sakai/collapse.gif?panel=Main";
    image.alt = altHide;
  } else {
    image.src = "/library/image/sakai/expand.gif?panel=Main";
    image.alt = altShow;
  }
}

function showSpinner( spinnerSelector ) {
  // show the spinner, replacing anything marked to be replaced
  $( spinnerSelector + "_replace" ).hide();
  $( spinnerSelector ).show();
  
  // disable buttons
  $( "input[@type=button]:visible" ).attr( "disabled", "disabled" );
}

function details( record ) {
  $( "#details_" + record.id ).slideToggle( 300,
    function() {
      resizeFrame();
    }
  );
}

function doCitationAction( action, baseUrl, title, collectionId ) {
  // do action
  if( action == "exportSelected" ) {
    exportSelectedCitations( baseUrl, title, collectionId );
  }
  
  // reset select boxes
  $( ".citationActionSelect" ).each( function() {
      this.selectedIndex = 0;
    }
  );
}

function updateSelectableActions() {
  if( $( "input[@type=checkbox][@checked]" ).size() > 0 ) {
    $( ".selectAction" ).attr( "disabled", "" );
  } else {
    $( ".selectAction" ).attr( "disabled", "disabled" );
  }
}

var exportWindow;
var t;
function exportSelectedCitations(baseUrl, title, collectionId)
{
  var exportUrl = baseUrl + "?collectionId=" + collectionId;
  
  // get each selected checkbox and append it to be exported
  $( "input[@type=checkbox][@checked]" ).each( function() {
      exportUrl += "&citationId=" + this.value;
    }
  );
    
  alert( "exportUrl: " + exportUrl );  
  exportWindow = top.window.open(exportUrl, title, "height=600,width=600,scrollbars=no,menubar=no,toolbar=no,location=no,status=no");

  t = window.setTimeout('closeExportWindow()', 15000);
}

function closeExportWindow()
{
	if(exportWindow)
	{
		exportWindow.close();
	}
}