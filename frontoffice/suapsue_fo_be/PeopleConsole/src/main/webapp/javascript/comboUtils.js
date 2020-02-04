/**
 * document.forms[0].yourFieldName.value = "yourValue";
 */

/**
 * Submit with a param in the Query String.
 * If the param already exists, will be overwriten. 
 */
function submitWithParam(param, value) {

	var actionString = new String(document.forms[0].action);
	
	var oldQueryString = new String(location.search);
	
	var newQueryString = updateQueryStringParameter(oldQueryString, param, value);
	
	
	if (location.search.length !== 0) {
		document.forms[0].action = actionString.replace(oldQueryString, newQueryString);
	}
	else {
		document.forms[0].action = actionString.concat(newQueryString);
	}

	document.forms[0].submit();
}	

function updateQueryStringParameter(queryString, param, value) {
    var re = new RegExp("([?|&])" + param + "=.*?(&|$)", "i"), separator = queryString.indexOf('?') !== -1 ? "&" : "?";

    if (queryString.match(re)) return queryString.replace(re, '$1' + param + "=" + value + '$2');
    else return queryString + separator + param + "=" + value;
    
}