/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ModelloUnicoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.ProcedimentoBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean;
import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SportelloBean;
import it.people.process.AbstractPplProcess;
import it.people.process.common.entity.Attachment;
import it.people.wrappers.IRequestWrapper;

public class BuilderHtml {

	public String generateHtmlCompilabile(ProcessData dataForm,SezioneCompilabileBean scb, IRequestWrapper request){
		String ret="";
		ret = HtmlRenderer.costruisciStringaHtml(scb,true, "SectionText", "TableRow0", "96%", "0", "6", null, "25", dataForm, request);
		return ret;
	}
	
	public String generateHtmlNonCompilabile(ProcessData dataForm,SezioneCompilabileBean scb, IRequestWrapper request){
		String ret="";
		ret = HtmlRenderer.costruisciStringaHtml(scb,false,"SectionText", null, null, null, null, null, null, dataForm, request);
		return ret;
	}
	public String generateHtmlNonCompilabileAnagrafica(ProcessData dataForm,SezioneCompilabileBean scb, IRequestWrapper request){
		String ret="";
		ret = HtmlRenderer.costruisciStringaHtml(scb,false,"SectionText", null, null, "0", null, "2", null, dataForm, request);
		return ret;
	}
	
    public String buildHtmlOggetto(ProcessData dataForm)
    {
        String oggettoPratica = "";
        if(dataForm.getOggettoIstanza() != null && dataForm.getOggettoIstanza().getHtml() != null && dataForm.getOggettoIstanza().getHtml().indexOf("</a></b></td></tr></table>") != -1)
        {
//            oggettoPratica = dataForm.getOggettoIstanza().getHtml();
//            int index = oggettoPratica.indexOf("</a></b></td></tr></table>");
//            oggettoPratica = oggettoPratica.substring(index + "</a></b></td></tr></table>".length());
//            index = oggettoPratica.indexOf(">");
//            oggettoPratica = oggettoPratica.substring(index + 1);
            oggettoPratica = "<tr><td>" + buildTextOggetto(dataForm) + "</td></tr></table><br /><br />";
            oggettoPratica = (new StringBuilder("<table width=\"100%\" style=\"border-width: 1px; border-style: solid;\">")).
            		append(oggettoPratica).toString();
        } else
        {
            oggettoPratica = "<table width=\"100%\" style=\"border-width: 1px; border-style: solid;\"><tr><td>Procedimento Unico</td></tr></table><br /><br />";
        }
        return oggettoPratica;
    }

    public String buildOggettoFromTitoloHref(ProcessData dataForm)
    {
        String oggettoPratica = "";
        if(dataForm.getOggettoIstanza() != null && dataForm.getOggettoIstanza().getHtml() != null && dataForm.getOggettoIstanza().getHtml().indexOf("</a></b></td></tr></table>") != -1)
            oggettoPratica = (new StringBuilder("<table width=\"100%\" style=\"border-width: 1px; border-style: solid;\"><tr><td  colspan=1>")).append(dataForm.getOggettoIstanza().getTitolo()).append("</td></tr></table><table><tr><td></td></tr></table><br/>").toString();
        else
            oggettoPratica = "Procedimento Unico";
        return oggettoPratica;
    }

    public String buildTextOggetto(ProcessData dataForm)
    {
        String oggettoPratica = "";
        if(dataForm.getOggettoIstanza() != null && dataForm.getOggettoIstanza().getCampi() != null 
        		&& dataForm.getOggettoIstanza().getCampi().size() > 0) {
            oggettoPratica = ((HrefCampiBean)dataForm.getOggettoIstanza().getCampi().get(0)).getValoreUtente();
        } else {
            oggettoPratica = "Procedimento Unico";
        }
        return oggettoPratica;
    }
    
	public String generateRiepilogoNonFirmatoSmall(IRequestWrapper request,AbstractPplProcess process,SportelloBean sportello,ArrayList listaAnagrafiche){
		ProcessData dataForm = (ProcessData) process.getData();
		
		String html ="<html><body>"
			.concat("<div id=\"Content\">") 
			.concat("	<table width=\"100%\" style=\"font-size: 90%;\">")
			.concat("		<tr><td align=\"right\"><b>"+sportello.getDescrizioneSportello()+"</b></td></tr>")
			.concat("		<tr><td align=\"right\">"+sportello.getIndirizzo()+"</td></tr>")
			.concat("		<tr><td align=\"right\">"+sportello.getCap()+" "+sportello.getCitta()+"</td></tr>")
			.concat("	</table>")
			.concat("	<br />")
			.concat("	<table width=\"100%\" style=\"font-size: 90%;\">")
			.concat("		<tr><td width=\"20%\"  style=\"border-width:1px; padding:3px; border-style: solid;\" ><b>Codice domanda</b></td>")
			.concat("			<td width=\"80%\" style=\"border-width:1px; padding:3px; border-style: solid;\"><b>"+dataForm.getIdentificatorePeople().getIdentificatoreProcedimento()+((dataForm.getListaSportelli().size()>1)?("/"+String.valueOf(sportello.getIdx())):"")+"</b></td>")
			.concat("		</tr>")
			.concat("	</table>")
			.concat("	<br />")
			.concat("	<b>OGGETTO PRATICA</b>")
			.concat("	"+buildHtmlOggetto(dataForm))
			.concat("	<table><tr><td></td></tr></table><br/>")
			.concat("	<b>PROCEDIMENTI ATTIVATI</b>")
			.concat("	<table width=\"100%\" style=\"border-width: 1px; border-style: solid;\" cellspacing=\"5\" cellpadding=\"5\">");
			for (Iterator iterator = sportello.getCodProcedimenti().iterator(); iterator.hasNext();) {
				String keyProc = (String) iterator.next();
				ProcedimentoBean procedimento = (ProcedimentoBean) dataForm.getListaProcedimenti().get(keyProc);
				html += "<tr><td>"+procedimento.getNome()+" ("+procedimento.getEnte()+")</td></tr>";
			}
			html=html.concat("	</table>")
			.concat("	<br /><b>ANAGRAFICA DICHIARANTE</b>")
			.concat("	<table width=\"100%\" style=\"border-width: 1px; border-style: solid;\" cellspacing=\"5\" cellpadding=\"5\">");
			for (Iterator iterator = listaAnagrafiche.iterator(); iterator.hasNext();) {
				String anag = (String) iterator.next();
				html += "<tr><td >"+anag+"</td></tr>";
			}
			html=html.concat("	</table>")
			.concat("	<br /><b>FILE ALLEGATI</b>")
			.concat("	<table width=\"100%\" style=\"border-width: 1px; border-style: solid;\" cellspacing=\"5\" cellpadding=\"5\">");
			if (dataForm.getAllegati()==null || dataForm.getAllegati().size()==0){
				html +="		<tr><td >...nessun allegato inserito</td></tr>";
			} else {
//				String nuovaGestioneFile = request.getUnwrappedRequest().getSession().getServletContext().getInitParameter("remoteAttachFile");
//            	if (nuovaGestioneFile!=null && nuovaGestioneFile.equalsIgnoreCase("true")){ //nuova gestione dei file
				if (!process.isEmbedAttachmentInXml()) {
            		for (Iterator iterator = dataForm.getAllegati().iterator(); iterator.hasNext();) { 
            			Attachment attach = (Attachment) iterator.next();
            			String path = attach.getPath();
            			String nomeFile = "";
            			int idx = path.lastIndexOf(File.separator);
            			if (idx!=-1){
            				String nome = path.substring(idx+1, path.length());
            				if (nome.indexOf("_")!=-1){
            					nomeFile = nome.substring((nome.indexOf("_")+1),nome.length());
            					html +="		<tr><td >"+nomeFile+"</td></tr>";
            				}
            			}
            		}
            	} else { // vecchia gestione dei file
            		for (Iterator iterator = dataForm.getAllegati().iterator(); iterator.hasNext();) { 
            			Attachment attach = (Attachment) iterator.next();
					    String nome = attach.getName();
					    if (nome.indexOf("_")!=-1) {
					    	nome = nome.substring(nome.indexOf("_")+1,nome.length());
					    }
					    html +="		<tr><td >"+nome+"</td></tr>";
            		}
            	}
			}
			html = html.concat("	</table>")
			.concat("	<br />Dichiaro di aver preso visione della pratica in formato PDF")
			.concat("	<br />")
			.concat("	</div>")
			.concat("</body></html>");
		return html;
	}
}
