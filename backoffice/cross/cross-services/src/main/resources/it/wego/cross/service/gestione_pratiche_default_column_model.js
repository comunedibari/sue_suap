[
{
	"label" : "Azioni",
	"name" : "azione",
	"index" : "idPratica",
	"classes" : "list_azioni",
	"sortable" : false,
	"formatter" : gestionePraticheActionsFormatter
},
{
	"label" : "Id Pratica",
	"name" : "idPratica",
	"index" : "idPratica",
	"hidden" : true 
},
{
	"label" : "Identificativo Pratica",
	"name" : "identificativoPratica",
	"hidden" : true,
	"sortable" : false
},
{
	"label" : "<span><span>Id pratica</span><br/><span>Identificativo pratica</span><br/><span>Fascicolo</span></span>", 
	"name" : "complexId",
	"sortable" : true,
	"hidden" : false,
	"width" : 180,
	"autowidth": true,
	"title" : false,
	"formatter" : paperworkIdentificationFormatterHandler,
	"cellattr" : paperworkIdentificationCellattrHandler
},
{
	"label" : "Tipo",
	"name" : "type",
	"index" : "type",
	"width" : 60,
	"align" : "center",
	"sortable" : true
},
{
	"label" : "Numero",
	"name" : "number",
	"index" : "number",
	"width" : 60,
	"align" : "center",
	"sortable" : true
},
{
	"label" : "Anno",
	"name" : "year",
	"index" : "year",
	"width" : 40,
	"align" : "center",
	"sortable" : true
},
{
	"label" : "<span><span>Data ricezione</span><br/><span>Numero protocollo</span><br/><span>Data protocollo</span></span>",
	"name" : "receiving",
	"index" : "receiving",
	"hidden" : false,
	"sortable" : false,
	"width" : 150,
	"formatter" : paperworkReceivingFormatterHandler,
	"title" : false,
	"cellattr" : paperworkReceivingCellattrHandler
},
{
	"label" : "Tipologia intervento",
	"name" : "operations",
	"index" : "operations",
	"hidden" : false,
	"width" : 150,
	"formatter" : paperworkOperationsFormatterHandler,
	"title" : false,
	"cellattr" : paperworkOperationsCellattrHandler,
	"sortable" : false
},
{
	"label" : "Oggetto",
	"name" : "oggettoPratica",
	"index" : "oggettoPratica",
	"sortable" : true
},
{
	"label" : "Indirizzi",
	"name" : "addresses",
	"index" : "addresses",
	"hidden" : false,
	"sortable" : false,
	"width" : 150,
	"formatter" : paperworkAddressesFormatterHandler,
	"title" : false,
	"cellattr" : paperworkAddressesCellattrHandler
},
{
	"label" : "Dati catastali",
	"index" : "cadastralDataList",
	"sortable" : false,
	"hidden" : false,
	"width" : 150,
	"formatter" : paperworkCadastralDataFormatterHandler,
	"title" : false,
	"cellattr" : paperworkCadastralDataCellattrHandler
},
{
	"label" : "Soggetti coinvolti",
	"index" : "richiedente",
	"sortable" : false,
	"formatter" : paperworkApplicantsFormatterHandler,
	"title" : false,
	"cellattr" : paperworkApplicantsCellattrHandler
},
{
	"label" : "Stato",
	"name" : "statoPratica",
	"index" : "idStatoPratica",
	"width" : 80,
	"align" : "center",
	"sortable" : true
},
{
	"label" : "In carico a",
	"name" : "inCarico",
	"index" : "inCarico",
	"sortable" : true,
	"autowidth": true
},
{
	"label" : "Ultimo evento interno<br/>Tipo<br/>Data<br/>N. Protocollo<br/>Data Protocollo<br/>Operatore",
	"sortable" : false,
	"autowidth": true,
	"formatter" : paperworkInternalEventFormatterHandler,
	"title" : false,
	"cellattr" : paperworkInternalEventCellattrHandler
},
{
	"label" : "Ultimo evento esterno<br/>Tipo<br/>Data<br/>N. Protocollo<br/>Data Protocollo",
	"sortable" : false,
	"autowidth": true,
	"formatter" : paperworkExternalEventFormatterHandler,
	"title" : false,
	"cellattr" : paperworkExternalEventCellattrHandler
},
{
	"label" : "Giorni Trascorsi da ultimo evento esterno",
	"name" : "dataUltimoEventoEsterno",
	"index" : "dataUltimoEventoEsterno",
	"width" : 60,
	"align" : "center",
	"sortable" : true,
	"formatter" : paperworkDaysFormatterHandler,
	"title" : true
},
{
	"label" : "Ultima nota<br/>Data Nota<br/>Utente<br/>Contenuto",
	"sortable" : false,
	"width" : 100,
	"autowidth": true,
	"formatter" : paperworkNotesFormatterHandler,
	"title" : false,
	"cellattr" : paperworkNotesCellattrHandler
}
]