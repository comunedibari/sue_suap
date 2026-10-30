package it.wego.cross.beans.pratica;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//public List ParserJsonAddPratica(String jsonStr) throws Exception { 
public class ParserJsonAddPratica { 
	public static JSONObject getChildJSONObjectByName(JSONObject jsonObject, String childName) {
		JSONObject extnJsonObj = (JSONObject) jsonObject.get(childName);

		if (extnJsonObj == null)
			extnJsonObj = new JSONObject();
		return extnJsonObj;
	}

	public static JSONArray getChildJSONObjectArrayByName(JSONObject jsonObject, String childName) {
		JSONArray jsonObjArray = (JSONArray) jsonObject.get(childName);

		if (jsonObjArray == null)
			jsonObjArray = new JSONArray();
		return jsonObjArray;
	}
	public void parser(String jsonStr) { 
	
	
	
/*	
List<String> keyList = new ArrayList<String>();
JSONObject authorJsonObj = (JSONObject) new JSONParser().parse(jsonStr); ;
    JSONObject authorJsonObj =getChildJSONObjectByName(authorJsonObj, "author");
    String  owner = (String)authorJsonObj.get("owner");
    String  public = (String)authorJsonObj.get("public");
    String  name = (String)authorJsonObj.get("name");
      JSONObject typeMetadataJsonObj =getChildJSONObjectByName(authorJsonObj, "typeMetadata");
      String  bUSTA = (String)typeMetadataJsonObj.get("BUSTA");
       JSONArray dATI CATASTALIJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "DATI CATASTALI");
        for(int i=0; i<dATI CATASTALIJsonArr.size(); i++ ) { 
        JSONObject dATI CATASTALIJsonObj = (JSONObject)dATI CATASTALIJsonArr.get(i); 
           } 
      String  fASCICOLO = (String)typeMetadataJsonObj.get("FASCICOLO");
       JSONArray rEFERENTIJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "REFERENTI");
        for(int j=0; j<rEFERENTIJsonArr.size(); j++ ) { 
        JSONObject rEFERENTIJsonObj = (JSONObject)rEFERENTIJsonArr.get(j); 
        String  cODICE FISCALE = (String)rEFERENTIJsonObj.get("CODICE FISCALE");
        String  rUOLO = (String)rEFERENTIJsonObj.get("RUOLO");
        String  pARTITA IVA = (String)rEFERENTIJsonObj.get("PARTITA IVA");
        String  nOME = (String)rEFERENTIJsonObj.get("NOME");
        String  cOGNOME_DENOMINAZIONE = (String)rEFERENTIJsonObj.get("COGNOME_DENOMINAZIONE");
          String  cODICE FISCALEOfREFE = (String)rEFERENTIJsonObj.get("CODICE FISCALE");
          String  rUOLOOfREFE = (String)rEFERENTIJsonObj.get("RUOLO");
          String  pARTITA IVAOfREFE = (String)rEFERENTIJsonObj.get("PARTITA IVA");
          String  nOMEOfREFE = (String)rEFERENTIJsonObj.get("NOME");
          String  cOGNOME_DENOMINAZIONEOfREFE = (String)rEFERENTIJsonObj.get("COGNOME_DENOMINAZIONE");
            String  cODICE FISCALEOfREFE = (String)rEFERENTIJsonObj.get("CODICE FISCALE");
            String  rUOLOOfREFE = (String)rEFERENTIJsonObj.get("RUOLO");
            String  pARTITA IVAOfREFE = (String)rEFERENTIJsonObj.get("PARTITA IVA");
            String  nOMEOfREFE = (String)rEFERENTIJsonObj.get("NOME");
            String  cOGNOME_DENOMINAZIONEOfREFE = (String)rEFERENTIJsonObj.get("COGNOME_DENOMINAZIONE");
              String  cODICE FISCALEOfREFE = (String)rEFERENTIJsonObj.get("CODICE FISCALE");
              String  rUOLOOfREFE = (String)rEFERENTIJsonObj.get("RUOLO");
              String  pARTITA IVAOfREFE = (String)rEFERENTIJsonObj.get("PARTITA IVA");
              String  nOMEOfREFE = (String)rEFERENTIJsonObj.get("NOME");
              String  cOGNOME_DENOMINAZIONEOfREFE = (String)rEFERENTIJsonObj.get("COGNOME_DENOMINAZIONE");
                String  cODICE FISCALEOfREFE = (String)rEFERENTIJsonObj.get("CODICE FISCALE");
                String  rUOLOOfREFE = (String)rEFERENTIJsonObj.get("RUOLO");
                String  pARTITA IVAOfREFE = (String)rEFERENTIJsonObj.get("PARTITA IVA");
                String  nOMEOfREFE = (String)rEFERENTIJsonObj.get("NOME");
                String  cOGNOME_DENOMINAZIONEOfREFE = (String)rEFERENTIJsonObj.get("COGNOME_DENOMINAZIONE");
                     } 
                 JSONArray tIPIZZAZIONEJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "TIPIZZAZIONE");
                  for(int k=0; k<tIPIZZAZIONEJsonArr.size(); k++ ) { 
                  JSONObject tIPIZZAZIONEJsonObj = (JSONObject)tIPIZZAZIONEJsonArr.get(k); 
                     } 
                String  tORNATA = (String)typeMetadataJsonObj.get("TORNATA");
                 JSONArray dATA_1JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "DATA_1");
                  for(int l=0; l<dATA_1JsonArr.size(); l++ ) { 
                  JSONObject dATA_1JsonObj = (JSONObject)dATA_1JsonArr.get(l); 
                     } 
                 JSONArray dATA_2JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "DATA_2");
                  for(int m=0; m<dATA_2JsonArr.size(); m++ ) { 
                  JSONObject dATA_2JsonObj = (JSONObject)dATA_2JsonArr.get(m); 
                     } 
                 JSONArray dATA_3JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "DATA_3");
                  for(int n=0; n<dATA_3JsonArr.size(); n++ ) { 
                  JSONObject dATA_3JsonObj = (JSONObject)dATA_3JsonArr.get(n); 
                     } 
                String  sETTORE = (String)typeMetadataJsonObj.get("SETTORE");
                 JSONArray aBUSIVISMOJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "ABUSIVISMO");
                  for(int o=0; o<aBUSIVISMOJsonArr.size(); o++ ) { 
                  JSONObject aBUSIVISMOJsonObj = (JSONObject)aBUSIVISMOJsonArr.get(o); 
                     } 
                 JSONArray dATA_4JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "DATA_4");
                  for(int p=0; p<dATA_4JsonArr.size(); p++ ) { 
                  JSONObject dATA_4JsonObj = (JSONObject)dATA_4JsonArr.get(p); 
                     } 
                 JSONArray eDIFICIOJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "EDIFICIO");
                  for(int q=0; q<eDIFICIOJsonArr.size(); q++ ) { 
                  JSONObject eDIFICIOJsonObj = (JSONObject)eDIFICIOJsonArr.get(q); 
                     } 
                String  iD = (String)typeMetadataJsonObj.get("ID");
                 JSONArray pROTOCOLLO IN ARRIVOJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "PROTOCOLLO IN ARRIVO");
                  for(int r=0; r<pROTOCOLLO IN ARRIVOJsonArr.size(); r++ ) { 
                  JSONObject pROTOCOLLO IN ARRIVOJsonObj = (JSONObject)pROTOCOLLO IN ARRIVOJsonArr.get(r); 
                     } 
                 JSONArray dATA_5JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "DATA_5");
                  for(int s=0; s<dATA_5JsonArr.size(); s++ ) { 
                  JSONObject dATA_5JsonObj = (JSONObject)dATA_5JsonArr.get(s); 
                     } 
                 JSONArray oGGETTOJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "OGGETTO");
                  for(int t=0; t<oGGETTOJsonArr.size(); t++ ) { 
                  JSONObject oGGETTOJsonObj = (JSONObject)oGGETTOJsonArr.get(t); 
                  String  dESCRIZIONE INTERVENTO = (String)oGGETTOJsonObj.get("DESCRIZIONE INTERVENTO");
                  String  tIPOLOGIA DI INTERVENTO = (String)oGGETTOJsonObj.get("TIPOLOGIA DI INTERVENTO");
                  String  dATA DI PRESENTAZIONE = (String)oGGETTOJsonObj.get("DATA DI PRESENTAZIONE");
                       } 
                   JSONArray cAMPO_5JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "CAMPO_5");
                    for(int u=0; u<cAMPO_5JsonArr.size(); u++ ) { 
                    JSONObject cAMPO_5JsonObj = (JSONObject)cAMPO_5JsonArr.get(u); 
                       } 
                   JSONArray cAMPO_4JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "CAMPO_4");
                    for(int v=0; v<cAMPO_4JsonArr.size(); v++ ) { 
                    JSONObject cAMPO_4JsonObj = (JSONObject)cAMPO_4JsonArr.get(v); 
                       } 
                   JSONArray cAMPO_3JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "CAMPO_3");
                    for(int w=0; w<cAMPO_3JsonArr.size(); w++ ) { 
                    JSONObject cAMPO_3JsonObj = (JSONObject)cAMPO_3JsonArr.get(w); 
                       } 
                   JSONArray cAMPO_2JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "CAMPO_2");
                    for(int x=0; x<cAMPO_2JsonArr.size(); x++ ) { 
                    JSONObject cAMPO_2JsonObj = (JSONObject)cAMPO_2JsonArr.get(x); 
                       } 
                   JSONArray cAMPO_1JsonArr = getChildJSONObjectArrayByName(booksJsonObj, "CAMPO_1");
                    for(int y=0; y<cAMPO_1JsonArr.size(); y++ ) { 
                    JSONObject cAMPO_1JsonObj = (JSONObject)cAMPO_1JsonArr.get(y); 
                       } 
                  String  aNNO = (String)typeMetadataJsonObj.get("ANNO");
                   JSONArray pROVVEDIMENTO FINALEJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "PROVVEDIMENTO FINALE");
                    for(int z=0; z<pROVVEDIMENTO FINALEJsonArr.size(); z++ ) { 
                    JSONObject pROVVEDIMENTO FINALEJsonObj = (JSONObject)pROVVEDIMENTO FINALEJsonArr.get(z); 
                       } 
                  String  tIPO PRATICA = (String)typeMetadataJsonObj.get("TIPO PRATICA");
                   JSONArray sTRUMENTI ATTUATIVIJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "STRUMENTI ATTUATIVI");
                    for(int ii=0; ii<sTRUMENTI ATTUATIVIJsonArr.size(); ii++ ) { 
                    JSONObject sTRUMENTI ATTUATIVIJsonObj = (JSONObject)sTRUMENTI ATTUATIVIJsonArr.get(ii); 
                       } 
                  String  nUMERO = (String)typeMetadataJsonObj.get("NUMERO");
                   JSONArray aZIONE UTENTEJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "AZIONE UTENTE");
                    for(int ij=0; ij<aZIONE UTENTEJsonArr.size(); ij++ ) { 
                    JSONObject aZIONE UTENTEJsonObj = (JSONObject)aZIONE UTENTEJsonArr.get(ij); 
                    String  dATA AZIONE = (String)aZIONE UTENTEJsonObj.get("DATA AZIONE");
                    String  uTENZA = (String)aZIONE UTENTEJsonObj.get("UTENZA");
                    String  aZIONE = (String)aZIONE UTENTEJsonObj.get("AZIONE");
                         } 
                     JSONArray uBICAZIONEJsonArr = getChildJSONObjectArrayByName(booksJsonObj, "UBICAZIONE");
                      for(int ik=0; ik<uBICAZIONEJsonArr.size(); ik++ ) { 
                      JSONObject uBICAZIONEJsonObj = (JSONObject)uBICAZIONEJsonArr.get(ik); 
                      String  pIANO = (String)uBICAZIONEJsonObj.get("PIANO");
                      String  lONGITUDINE = (String)uBICAZIONEJsonObj.get("LONGITUDINE");
                      String  nUMERO CIVICO = (String)uBICAZIONEJsonObj.get("NUMERO CIVICO");
                      String  sCALA = (String)uBICAZIONEJsonObj.get("SCALA");
                      String  iNDIRIZZO DELLA PRATICA = (String)uBICAZIONEJsonObj.get("INDIRIZZO DELLA PRATICA");
                      String  lATITUDINE = (String)uBICAZIONEJsonObj.get("LATITUDINE");
                      String  iNTERNO = (String)uBICAZIONEJsonObj.get("INTERNO");
                      String  fRAZIONE_LOCALITÃ = (String)uBICAZIONEJsonObj.get("FRAZIONE_LOCALITÃ");
                           } 
                      String  typeId = (String)authorJsonObj.get("typeId");
                      String  id = (String)authorJsonObj.get("id");
                      String  version = (String)authorJsonObj.get("version");
*/
	
	}
}