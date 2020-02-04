//aggiorna i servizi associati all'area scelta
function updateArea(){
	
	var el = document.createElement("input");
	 el.type = "hidden";
	 el.id = "sceltaArea";
	 el.name = "sceltaArea";
	 el.value = "sceltaArea";

	 var form = document.forms[0];
	 form.appendChild(el);
	
}
