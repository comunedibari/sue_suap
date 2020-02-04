[{
	"label" : "Id Pratica",
	"name" : "idPratica",
	"index" : "idPratica",
	"hidden" : false,
	"width" : 35,
	"align" : "center"
},
{
	"label" : "Identificativo Pratica",
	"name" : "identificativoPratica",
	"index" : "identificativoPratica",
	"hidden" : false,
	"width" : 110,
	"align" : "center"
},
{
	"label" : "Stato pratica",
	"name" : "statoPratica",
	"index" : "idStatoPratica",
	"width" : 55,
	"align" : "center"
},
{
	"label" : "Stato mail",
	"name" : "statoEmail",
	"index" : "statoEmail",
	"classes" : "statoEmail",
	"sortable" : false,
	"width" : 30,
	"formatter" : gestionePraticheStatoMailFormatter
},
{
	"label" : "Data ricezione",
	"name" : "dataRicezione",
	"index" : "dataRicezione",
	"datefmt" : "dd/mm/yyyy",
	"width" : 43,
	"align" : "center"
},
{
	"label" : "Pratica F.O.",
	"name" : "fascicolo",
	"index" : "fascicolo",
	"sortable" : true,
	"width" : 110,
	"align" : "left",
	"formatter" : gestionePraticheIdentificativoPraticaOrProtocolloFormatter
},
{
	"label" : "Oggetto",
	"name" : "oggettoPratica",
	"index" : "oggettoPratica"
},
{
	"label" : "Ente",
	"name" : "ente",
	"index" : "ente",
	"sortable" : true,
	"width" : 100
},
{
	"label" : "Comune",
	"name" : "comune",
	"index" : "comune",
	"sortable" : false,
	"width" : 80,
	"hidden" : true
},
{
	"label" : "Richiedenti/Tecnici",
	"name" : "richiedente",
	"index" : "richiedente",
	"sortable" : false,
	"width" : 150
},
{
	"label" : "In carico a",
	"name" : "inCarico",
	"index" : "inCarico",
	"sortable" : false,
	"width" : 80
},
{
	"label" : "",
	"name" : "azione",
	"index" : "idPratica",
	"classes" : "list_azioni",
	"sortable" : false,
	"width" : 70,
	"formatter" : gestionePraticheActionsFormatter
},
{
	"label" : "Integrazione",
	"name" : "integrazione",
	"index" : "integrazione",
	"sortable" : true,
	"width" : 50,
	"formatter" : gestionePraticheIntegrazione,
	"align" : "center"
}
]