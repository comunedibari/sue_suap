[#ftl]
/**
*   Parametri gestiti
*       length : lunghezza massima stringa
*       delimiter: stringa di delimitazione
**/
function substring(cellvalue, options, rowObject){
    var length=options.parameters.length;
    var delimiter=options.parameters.delimiter;
    if(length!=null){
        return cellvalue.substring(0, length);
    }
    if(delimiter!=null){
        return cellvalue.substring(0, cellvalue.indexOf(delimiter));
    }    
    return cellvalue;
}