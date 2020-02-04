[#ftl]

[#assign standardRowActions = {
    "search":{
        "color":"orange",
        "class":"fa-search",
        "legend":"Cerca"
    },
    "add":{
        "color":"purple",
        "class":"fa-plus-circle",
        "legend":"Crea nuovo"
    },
    "refresh":{
        "color":"green",
        "class":"fa-refresh",
        "legend":"Ricarica i dati",
        "action":{
            "type":"trigger",
            "trigger":"reloadGrid"
        }
    },
    "view":{
        "color":"blue",
        "class":"fa-search-plus",
        "legend":"Vedi dettaglio"
    },
    "edit":{
        "color":"green",
        "class":"fa-pencil",
        "legend":"Modifica record"
    },
    "delete":{
        "color":"red",
        "class":"fa-trash-o",
        "legend":"Elimina record",
        "action":{
            "confirmTitle":"Confermi la cancellazione?",
            "confirmText":"Confermi di voler procedere con la cancellazione?",
            "buttonOkClassIcon":"fa-trash-o",
            "buttonOkClassColor":"btn-danger"
        }
    }
}]