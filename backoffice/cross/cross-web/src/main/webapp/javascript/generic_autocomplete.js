function genericAutocomplete(self, keyEvent, url, minLen, dataJson, cleanFunction, successFunction, selectionFunction){
	if(keyEvent.keyCode == 8) {
		cleanFunction(self)
	} else {
    	self.autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: url,
                    dataType: "json",
                    data: dataJson(self, self.val()),
                    success: function (data) {
                        response($.map(data, successFunction));
                    }
                });
            },
            select: function (event, ui) {
            	return selectionFunction(self, event, ui);
            },
            minLength: minLen
        });
    }
}