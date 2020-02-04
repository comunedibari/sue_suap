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
	"width" : 90,
	"align" : "center"
},
{
	"label" : "Nome File",
	"name" : "nomeFileCaricato",
	"index" : "nomeFileCaricato",
	"hidden" : false,
	"width" : 80,
	"align" : "center"
},
{
	"label" : "Descrizione errore",
	"name" : "descrizioneErrore",
	"index" : "descrizioneErrore",
	"hidden" : false,
	"width" : 180,
	"align" : "center"
},

{
	"label" : "Stato caricamento",
	"name" : "statoPratica",
	"index" : "idStatoPratica",
	"width" : 55,
	"align" : "center",
	"formatter" : gestionePraticheStatoMailFormatter
},

{
	"label" : "Data ricezione",
	"name" : "dataCaricamento",
	"index" : "dataCaricamento",
	"datefmt" : "dd/mm/yyyy",
	"width" : 43,
	"align" : "center"
},
{
	"label" : "",
	"name" : "azione",
	"index" : "idPratica",
	"classes" : "list_azioni",
	"sortable" : false,
	"width" : 70,
	"formatter" : gestionePraticheActionsFormatter
}
]