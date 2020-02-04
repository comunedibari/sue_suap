//<![CDATA[

	var savedStates=new Array();
	var savedStateCount=0;

if (!people) var people = {};

if (!people.console) people.console = {};

people.console.plh = {
		
    _delete: 
        function(actionData, paramSeparator, deleteGuardSuffix, deleteGuardVerifiedValue) {
                
            var parsedActionData = people.console.plh.parseActionData(actionData, paramSeparator);
            
            /*alert(parsedActionData[0] + '\n\n' + parsedActionData[1] + '\n\n' + parsedActionData[2]);*/
            
            /*alert(document.forms[0].elements[parsedActionData[1] + deleteGuardSuffix].value);*/
            
            document.forms[0].elements[parsedActionData[1] + deleteGuardSuffix].value = deleteGuardVerifiedValue;

            /*alert(document.forms[0].elements[parsedActionData[1] + deleteGuardSuffix].value);*/

            if (confirm('Eliminare l\'elemento selezionato?')) {
                return true;
            }
            else {
                return false;
            }
        },

    parseActionData: 
        function(actionData, paramSeparator) {

            var result = [];
            var queryStringStartIndex = 0;
            var plhIdStartIndex = 0;

            var plhId = '';
            var queryString = '';

            var buffer = '';
            for(index = 0; index < actionData.length; index = index + 1) {
                if (actionData[index] == '?') {
                    index = index + 1;
                    queryStringStartIndex = index;
                }
                if (queryStringStartIndex > 0 && plhIdStartIndex == 0 && actionData[index] != paramSeparator[0]) {
                    queryString += actionData[index];
                }
                if (queryStringStartIndex > 0 && actionData[index] == paramSeparator[0]) {
                    plhIdStartIndex = index + paramSeparator.length;
                    index = plhIdStartIndex;
                }
                if (plhIdStartIndex > 0) {
                    plhId += actionData[index];
                }
            }
            
            result[0] = queryString;
            result[1] = plhId;

            var buffer = [];
            var subString = '';
            var resultIndex = 0;
            for(index = 0; index < queryString.length; index = index + 1) {
                if (queryString[index] == '&') {
                    buffer[resultIndex] = subString;
                    resultIndex = resultIndex + 1;
                    subString = '';
                }
                else {
                    subString += queryString[index];
                }
            }
            if (subString.length > 0) {
                buffer[resultIndex] = subString;
            }
            
            result[2] = buffer;
            
            return result;
            
        },

        _addDynamicInput:
        	function (type, name, id, value) {
        		var element = document.createElement("input");
        		element.setAttribute("type", type);
        		element.setAttribute("name", name);
        		element.setAttribute("id", id);
        		element.setAttribute("value", value);
        		var dynamicSpan = document.getElementById("dynamicSpan");
        		dynamicSpan.appendChild(element);
        },

        _saveBackgroundStyle:
        	function (myElement) {
	          saved=new Object();
	          saved.element=myElement;
	          saved.className=myElement.className;
	          saved.backgroundColor=myElement.style["backgroundColor"];
	          return saved;   
        },        	
        	
        _restoreBackgroundStyle:
        	function (savedState) {
	          savedState.element.style["backgroundColor"]=savedState.backgroundColor;
	          if (savedState.className)
	          {
	            savedState.element.className=savedState.className;    
	          }
        },

        _findNode:
        	function (startingNode, tagName) {
	          // on Firefox, the TD node might not be the firstChild node of the TR node
	          myElement=startingNode;
	          var i=0;
	          while (myElement && (!myElement.tagName || (myElement.tagName && myElement.tagName!=tagName)))
	          {
	            myElement=startingNode.childNodes[i];
	            i++;
	          }  
	          if (myElement && myElement.tagName && myElement.tagName==tagName)
	          {
	            return myElement;
	          }
	          // on IE, the TD node might be the firstChild node of the TR node  
	          else if (startingNode.firstChild)
	            return people.console.plh._findNode(startingNode.firstChild, tagName);
	          return 0;
        },        

        _highlightTableRow:
        	function (myElement, highlightColor) {
	          var i=0;
	          // Restore color of the previously highlighted row
	          for (i; i<savedStateCount; i++)
	          {
	        	  people.console.plh._restoreBackgroundStyle(savedStates[i]);          
	          }
	          savedStateCount=0;
	
	          // To get the node to the row (ie: the <TR> element), 
	          // we need to traverse the parent nodes until we get a row element (TR)
	          // Netscape has a weird node (if the mouse is over a text object, then there's no tagName
	          while (myElement && ((myElement.tagName && myElement.tagName!="TR") || !myElement.tagName))
	          {
	            myElement=myElement.parentNode;
	          }
	
	          // If you don't want a particular row to be highlighted, set it's id to "header"
	          // If you don't want a particular row to be highlighted, set it's id to "header"
	          if (!myElement || (myElement && myElement.id && myElement.id=="header") || (myElement && myElement.id && myElement.id=="nohighlight") )
	            return;
	        		  
	          // Highlight every cell on the row
	          if (myElement)
	          {
	            var tableRow=myElement;
	            
	            // Save the backgroundColor style OR the style class of the row (if defined)
	            if (tableRow)
	            {
	        	  savedStates[savedStateCount]=people.console.plh._saveBackgroundStyle(tableRow);
	              savedStateCount++;
	            }
	
	            // myElement is a <TR>, then find the first TD
	            var tableCell=people.console.plh._findNode(myElement, "TD");    
	
	            var i=0;
	            // Loop through every sibling (a sibling of a cell should be a cell)
	            // We then highlight every siblings
	            while (tableCell)
	            {
	              // Make sure it's actually a cell (a TD)
	              if (tableCell.tagName=="TD")
	              {
	                // If no style has been assigned, assign it, otherwise Netscape will 
	                // behave weird.
	                if (!tableCell.style)
	                {
	                  tableCell.style={};
	                }
	                else
	                {
	                  savedStates[savedStateCount]=people.console.plh._saveBackgroundStyle(tableCell);        
	                  savedStateCount++;
	                }
	                // Assign the highlight color
	                tableCell.style["backgroundColor"]=highlightColor;
	
	                // Optional: alter cursor
	                tableCell.style.cursor='default';
	                i++;
	              }
	              // Go to the next cell in the row
	              tableCell=tableCell.nextSibling;
	            }
	          }
        },

        _trackTableHighlight:
        	function (mEvent, highlightColor) {
	          if (!mEvent)
	            mEvent=window.event;
	        		
	          // Internet Explorer
	          if (mEvent.srcElement)
	          {
	        	  people.console.plh._highlightTableRow( mEvent.srcElement, highlightColor);
	          }
	          // Netscape and Firefox
	          else if (mEvent.target)
	          {
	        	  people.console.plh._highlightTableRow( mEvent.target, highlightColor);		
	          }
        },        

        _highlightTableRowVersionA:
        	function A(myElement, highlightColor) {
	          var i=0;
	          // Restore color of the previously highlighted row
	          for (i; i<savedStateCount; i++)
	          {
	        	  people.console.plh._restoreBackgroundStyle(savedStates[i]);          
	          }
	          savedStateCount=0;
	
	          // If you don't want a particular row to be highlighted, set it's id to "header"
	          if (!myElement || (myElement && myElement.id && myElement.id=="header") || (myElement && myElement.id && myElement.id=="nohighlight") )
	            return;
	        		  
	          // Highlight every cell on the row
	          if (myElement)
	          {
	            var tableRow=myElement;
	            
	            // Save the backgroundColor style OR the style class of the row (if defined)
	            if (tableRow)
	            {
	        	  savedStates[savedStateCount]=people.console.plh._saveBackgroundStyle(tableRow);
	              savedStateCount++;
	            }
	
	            // myElement is a <TR>, then find the first TD
	            var tableCell=people.console.plh._findNode(myElement, "TD");    
	
	            var i=0;
	            // Loop through every sibling (a sibling of a cell should be a cell)
	            // We then highlight every siblings
	            while (tableCell)
	            {
	              // Make sure it's actually a cell (a TD)
	              if (tableCell.tagName=="TD")
	              {
	                // If no style has been assigned, assign it, otherwise Netscape will 
	                // behave weird.
	                if (!tableCell.style)
	                {
	                  tableCell.style={};
	                }
	                else
	                {
	                  savedStates[savedStateCount]=people.console.plh._saveBackgroundStyle(tableCell);        
	                  savedStateCount++;
	                }
	                // Assign the highlight color
	                tableCell.style["backgroundColor"]=highlightColor;
	
	                // Optional: alter cursor
	                tableCell.style.cursor='default';
	                i++;
	              }
	              // Go to the next cell in the row
	              tableCell=tableCell.nextSibling;
	            }
	          }
        }        
        
}


//]]>
