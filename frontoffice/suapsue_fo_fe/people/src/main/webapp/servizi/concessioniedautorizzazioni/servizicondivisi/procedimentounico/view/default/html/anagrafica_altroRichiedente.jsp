<%--
Copyright (c) 2011, Regione Emilia-Romagna, Italy

Licensed under the EUPL, Version 1.1 or - as soon they
will be approved by the European Commission - subsequent
versions of the EUPL (the "Licence");
You may not use this work except in compliance with the
Licence.

For convenience a plain text copy of the English version
of the Licence can be found in the file LICENCE.txt in
the top-level directory of this software distribution.

You may obtain a copy of the Licence in any of 22 European
Languages at:

http://www.osor.eu/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.model.ProcessData"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.SezioneCompilabileBean"%>
<%@page import="it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.AnagraficaBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="it.people.util.NavigatorHelper"%>
<script type="text/javascript" src="<%=request.getContextPath() + htmlPath%>/calendar/calendar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() + htmlPath%>/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() + htmlPath%>/calendar/lang/calendar-it.js"></script>
<link type="text/css" rel="stylesheet" media="all" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/calendar/aqua.css" />
<%
            ArrayList bottoniNascosti = (ArrayList) request.getAttribute("bottoniNascosti");
            if (bottoniNascosti != null) {
                bottoniNascosti.add(NavigatorHelper.BOTTONE_SALVA);
            }
%>
<script type="text/javascript">
    function setValue(concat)
    {
        if(concat != "")
        {
            if(concat.indexOf('@')>0)
            {
                var temp1 = concat.split('@');
                for (i=0; i<temp1.length; i++)
                {
                    if(temp1[i].indexOf('|')>=0)
                    {
                        var temp2 = temp1[i].split('|');
                        ccc = document.getElementById('ANAG_'+temp2[1]);
                        if(document.getElementById('ANAG_'+temp2[1]) != null)
                        {
                            campo = document.getElementById('ANAG_'+temp2[1])
                            campo.value = temp2[0];
                        }
                    }
                }
            }
        }
    }
	
    function formatNumber(field, interi, decimali)
    {
        campo = document.getElementById(field);
		
        if(campo.value.indexOf(',')>0){
            var temp = campo.value.split(',');
            if(temp[0].length > interi){
                alert('Sono ammesse al massimo '+interi+' cifre intere');
                campo.value="";
                campo.focus();
                return false;
            }
            else if(temp[1].length > decimali){
                alert('Sono ammesse al massimo '+decimali+' cifre decimali');
                campo.value="";
                campo.focus();
                return false;
            }
        }
        else{
            if(campo.value.length > interi){
                alert('Sono ammessi valori interi di non più di '+interi+' cifre');
                campo.value="";
                campo.focus();
                return false;
            }
        }
    }
	
    function onlyNumeric(e)
    {
        var ascii;
        if (e && e.charCode) {
            ascii = e.charCode;
        } else if (event && event.keyCode) {
            ascii = event.keyCode;
        } else {
            return true;
        }
        if ((ascii < 48 || ascii > 57) && ascii != 44) {
            return false;
        }
        return true;
    }
	
    /*
        Modifica INIT 23/02/2007 (versione 1.2.2):
        Aggiunto javascript per controllare che i campi immessi in un campo testo siano solo numerici e char
     */
    function onlyLettersAndNumeric(e)
    {
        /*var ascii;
                if (e && e.charCode) {
                ascii = e.charCode;
                } else if (event && event.keyCode) {
                ascii = event.keyCode;
                } else {
                return true;
                } */
        /*
                Modifica DSI 27/02/2007 (versione 1.2.3):
                Modificato javascript per controllare che i campi immessi in un campo testo siano solo numerici e char
         */
        /*if ((ascii>=48 && ascii <= 57)) return true;
                if ((ascii>=65 && ascii <= 90)) return true;
                if (ascii==32) return true;
                if (ascii==39) return true;
                if ((ascii>=97 && ascii <= 122)) return true;
                return false;*/
	  	
        return true;
    }
	
    function pulisci(field)
    {
        campo = document.getElementById(field);
        campo.value = '';
    }
	

	
    // Controllo P.IVA e cod.Fiscale
    function ControllaCF(cf)
    {
        var validi, i, s, set1, set2, setpari, setdisp;
        if( cf == '' )  return '';
        cf = cf.toUpperCase();
        if( cf.length != 16 )
            return "La lunghezza del codice fiscale non è\n"
            +"corretta: il codice fiscale dovrebbe essere lungo\n"
            +"esattamente 16 caratteri.\n";
        validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for( i = 0; i < 16; i++ ){
            if( validi.indexOf( cf.charAt(i) ) == -1 )
                return "Il codice fiscale contiene un carattere non valido `" +
                cf.charAt(i) +
                "'.\nI caratteri validi sono le lettere e le cifre.\n";
        }
        set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
        setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
        s = 0;
        for( i = 1; i <= 13; i += 2 )
            s += setpari.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
        for( i = 0; i <= 14; i += 2 )
            s += setdisp.indexOf( set2.charAt( set1.indexOf( cf.charAt(i) )));
        if( s%26 != cf.charCodeAt(15)-'A'.charCodeAt(0) )
            return "Il codice fiscale non è corretto:\n"+
            "il codice di controllo non corrisponde.\n";
        return "";
    }
	
	
    function ControllaPIVA(pi)
    {
        if( pi == '' )  return '';
        if( pi.length != 11 )
            return "La lunghezza della partita IVA non ?\n" +
            "corretta: la partita IVA dovrebbe essere lunga\n" +
            "esattamente 11 caratteri.\n";
        validi = "0123456789";
        for( i = 0; i < 11; i++ ){
            if( validi.indexOf( pi.charAt(i) ) == -1 )
                return "La partita IVA contiene un carattere non valido `" +
                pi.charAt(i) + "'.\nI caratteri validi sono le cifre.\n";
        }
        s = 0;
        for( i = 0; i <= 9; i += 2 )
            s += pi.charCodeAt(i) - '0'.charCodeAt(0);
        for( i = 1; i <= 9; i += 2 ){
            c = 2*( pi.charCodeAt(i) - '0'.charCodeAt(0) );
            if( c > 9 )  c = c - 9;
            s += c;
        }
        if( ( 10 - s%10 )%10 != pi.charCodeAt(10) - '0'.charCodeAt(0) )
            return "La partita IVA non è valida:\n" +
            "il codice di controllo non corrisponde.\n";
        return '';
    }
    // PC - nuovo controllo
    // function verificaPIVA(id)
    function verificaPIVA(id,msg)
    // PC - nuovo controllo
    {
        value = id.value;
        if( value.length > 0)  {
            if( value.length == 11 ) {
                err = ControllaPIVA(value);
            } else {
                err = "Errore: Partita Iva non valida";
            }
            if( err > '' ) {
                // PC - nuovo controllo
                if (msg.length > 0) {
                    err=msg;
                }
                // alert("Errore: Partita Iva non valida");
                alert(err);
                // PC - nuovo controllo
                id.value='';
                id.focus();
            } else {
                // alert("Ok: Partita Iva valida");
            }
        }
    }
    // PC - nuovo controllo
    function verificaREGEXP(id,pattern,msg)
    {
        value = id.value;
        if( value.length > 0)  {
            if ( pattern.length > 0 ){
                var p= new RegExp(pattern);
                if (p.test(value)== false) {
                    if (msg.length > 0) {
                        err=msg;
                    } else {
                        err="Campo errato";
                    }
                    if (err != ''){
                        alert(err);
                        id.value='';
                        id.focus();
                    }
                }
            }
        }
    }
    // PC - nuovo controllo
    // PC - nuovo controllo
    // function verificaCodFisc(id)
    function verificaCodFisc(id,msg)
    // PC - nuovo controllo
    {
        value = id.value;
        if( value.length > 0)  {
            if( value.length == 16 ) {
                err = ControllaCF(value);
            } else {
                err = "Il codice deve essere di 16 caratteri";
            }
            if (err == ''){
                //alert("Ok: Codice Fiscale valido");
            } else {
                // PC - nuovo controllo
                if (msg.length > 0) {
                    err=msg;
                }
                // PC - nuovo controllo
                alert(err);
                id.value='';
                id.focus();
            }
        }
    }
	
    // PC - nuovo controllo
    // function verificaPIVACodFisc(campo)
    function verificaPIVACodFisc(campo,msg)
    // PC - nuovo controllo
    {
        value = campo.value;
        if( value.length > 0)  {
            if( value.length == 11 ) {
                err = ControllaPIVA(value);
            } else if( value.length == 16 ) {
                err = ControllaCF(value);
            } else  {
                err = "Codice Fiscale o Partita Iva non valido";
            }
            if( err > '' ) {
                // PC - nuovo controllo
                if (msg.length > 0) {
                    err=msg;
                }
                // alert("Errore: Codice Fiscale o Partita Iva non valido");
                alert(err);
                // PC - nuovo controllo
                campo.value="";
                campo.focus();
            }
        }
        return true;
    }

    // Modifica Init 19/03/2007 (versione 2.0):
    // Aggiunte le seguenti 2 funzioni per il controllo delle date inserite direttamente dall'utente
    // PC - nuovo controllo
    // function errore(TextBox)
    function errore(TextBox,msg)
    // PC - nuovo controllo
    {
        // PC - nuovo controllo
        if (msg.length > 0) {
            err=msg;
        } else {
            err="La data non è valida";
        }
        // PC - nuovo controllo
        alert(err);
        TextBox.value="";
        TextBox.focus();
    }

     // PC - nuovo controllo
    // function isValidDate(TextBox,MostraERR)
    function isValidDate(TextBox,MostraERR,msg)
    // PC - nuovo controllo
    {
        dateStr = TextBox.value;
        var datePat = /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{2,4})$/;
	
        if (dateStr == "")
        {
            return true;
        }
		
        if (dateStr.match("/") == null)
        {
            if ((dateStr.length=8)|| (dateStr.length=6)){
                var dateStrTmp = dateStr.substring(0,2) + "/" + dateStr.substring(2,4) + "/" + dateStr.substring(4,dateStr.length);
                dateStr = dateStrTmp;
                TextBox.value = dateStr;
            }else{
                // PC - nuovo controllo
                // errore(TextBox);
                errore(TextBox,msg);
                // PC - nuovo controllo
            }
        }else{
            aData=dateStr.split("/");
            day = aData[0];
            month = aData[1];
            year = aData[2];
            if (day.length==1){
                day="0" + day;
            }
            if (month.length==1){
                month="0" + month;
            }
            var dateStrTmp = day + "/" + month + "/" + year;
            dateStr = dateStrTmp;
            TextBox.value = dateStr;
        }
		
        var matchArray = dateStr.match(datePat);
	
        if (matchArray == null) {
            if (MostraERR)
            {
                // PC - nuovo controllo
                // errore(TextBox);
                errore(TextBox,msg);
                // PC - nuovo controllo
            }
            return false;
        }
        month = matchArray[3];
        day = matchArray[1];
        year = matchArray[4];
        // controllo lunghezza anno
        if (year.length==3){
            if (MostraERR) {
                // PC - nuovo controllo
                // errore(TextBox);
                errore(TextBox,msg);
                // PC - nuovo controllo
            }
            return false;
        }
        // Aggiusta l'anno
        switch (year.length)
        {
            case 2:
                if (year > "50")
                {
                    year = "19" + year.substring(0,2) ;
                }
                else
                {
                    year = "20" + year.substring(0,2) ;
                }
                break;
        }
        dateStr = dateStr.substring(0,6) + year;
        //Riscrive la data nel text box
        TextBox.value = dateStr;
		
        if (month < 1 || month > 12) {
            if (MostraERR)
            {
                // PC - nuovo controllo
                // errore(TextBox);
                errore(TextBox,msg);
                // PC - nuovo controllo
            }
            return false;
        }
	
        if (day < 1 || day > 31) {
            if (MostraERR)
            {
                // PC - nuovo controllo
                // errore(TextBox);
                errore(TextBox,msg);
                // PC - nuovo controllo
            }
            return false;
        }
	
        if ((month==4 || month==6 || month==9 || month==11) && day==31) {
            if (MostraERR)
            {
                // PC - nuovo controllo
                // errore(TextBox);
                errore(TextBox,msg);
                // PC - nuovo controllo
            }
            return false
        }
	
        if (month == 2) {
            var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
            if (day>29 || (day==29 && !isleap)) {
                if (MostraERR)
                {
                // PC - nuovo controllo
                // errore(TextBox);
                errore(TextBox,msg);
                // PC - nuovo controllo
                }
                return false;
            }
        }
        return true;
    }
	

    function cambiaStatoRadioButton(value,vettoreCampiTesto,vettoreRadioButtonAssociati){
        j=0;
        while(j < vettoreCampiTesto.length) {
            if (value==vettoreRadioButtonAssociati[j]){
                var elements = document.getElementsByName(vettoreCampiTesto[j]);
                index = 0;
                while (index<elements.length) {
                    elements[index].checked=false;
                    elements[index].disabled=false;
                    index++;
                }
            } else {
                var elements = document.getElementsByName(vettoreCampiTesto[j]);
                index = 0;
                while (index<elements.length) {
                    if (elements[index].getAttribute('type') =='checkbox'){
                        elements[index].checked=false;
                        elements[index].disabled=true;
                        var imageattributes=elements[index].attributes;
                        k=0;
                        while(k<imageattributes.length) {
                            if (imageattributes[k].name=='onclick'){
                                s = imageattributes[k].value;
                                var str2 = s.substr(0, s.length-7);
                                eval(str2);
                            }
                            k++;
                        }
                    } else if (elements[index].getAttribute('type') =='radio') {
                        elements[index].checked=false;
                        elements[index].disabled=true;
                        var imageattributes=elements[index].attributes;
                        k=0;
                        while(k<imageattributes.length) {
                            if (imageattributes[k].name=='onclick'){
                                s = imageattributes[k].value;
                                var str2 = s.substr(0, s.length-7);
                                str2 = str2.replace("cambiaStatoRadioButton","tmp_radio");
                                eval(str2);
                            }
                            k++;
                        }
                    } else {
                        elements[index].value='';
                        elements[index].disabled=true;
                    }
                    index++;
                }
            }
            j++;
        }
    }
	
    function cambiaStatoCheckBox(obj,vettoreCampiTesto){
        i=0;
        if (obj.checked){
            while(i<vettoreCampiTesto.length) {
                var elemNodo = document.getElementById(vettoreCampiTesto[i]);
                if ((elemNodo.type)=='text' || (elemNodo.type) =='textarea' )  {
                    document.getElementById(vettoreCampiTesto[i]).disabled=false;
                    indice = 1;
                    while(document.getElementById(vettoreCampiTesto[i]+"#"+indice) != null) {
                        document.getElementById(vettoreCampiTesto[i]+"#"+indice).disabled=false;
                        indice++;
                    }
				
                }
                if ((elemNodo.type) =='checkbox')   { document.getElementById(vettoreCampiTesto[i]).disabled=false;      }
                if ((elemNodo.type) =='select-one')   { document.getElementById(vettoreCampiTesto[i]).disabled=false;      }
                if ((elemNodo.type) =='radio')  {
                    var elements = document.getElementsByName(elemNodo.getAttribute('name'));
                    j=0;
                    while (j<elements.length) {
                        elements[j].disabled=false;
                        j++;
                    }
                    indice=1;
                    while(document.getElementById(vettoreCampiTesto[i]+"#"+indice) != null) {
                        var elements = document.getElementsByName(document.getElementById(vettoreCampiTesto[i]+"#"+indice).getAttribute('name'));
                        j=0;
                        while (j<elements.length) {
                            elements[j].disabled=false;
                            j++;
                        }
                        indice++;
                    }
                }
                i++;
            }
        }
        if (!obj.checked){
            while(i<vettoreCampiTesto.length) {
                var elemNodo = document.getElementById(vettoreCampiTesto[i]);
                if ((elemNodo.type) =='text'  ||  (elemNodo.type)=='textarea'  )   {
                    elemNodo.value="";
                    elemNodo.disabled=true;
                    indice = 1;
                    while(document.getElementById(vettoreCampiTesto[i]+"#"+indice) != null) {
                        document.getElementById(vettoreCampiTesto[i]+"#"+indice).value="";
                        document.getElementById(vettoreCampiTesto[i]+"#"+indice).disabled=true;
                        indice++;
                    }
                }
                if ((elemNodo.type) =='radio')  {
                    var elements = document.getElementsByName(elemNodo.getAttribute('name'));
                    j=0;
                    while (j<elements.length) {
                        elements[j].checked=false;
                        elements[j].disabled=true;
                        j++;
                    }
					
					
                    j=0;
                    while (j<elements.length){
                        var imageattributes=elements[j].attributes;
                        k=0;
                        while(k<imageattributes.length) {
                            if (imageattributes[k].name=='onclick'){
                                s = imageattributes[k].value;
                                var str2 = s.substr(0, s.length-7);
                                eval(str2);
                            }
                            k++;
                        }
                        j++;
                    }
					
					
                    indice=1;
                    while(document.getElementById(vettoreCampiTesto[i]+"#"+indice) != null) {
                        var elements = document.getElementsByName(document.getElementById(vettoreCampiTesto[i]+"#"+indice).getAttribute('name'));
                        j=0;
                        while (j<elements.length) {
                            elements[j].checked=false;
                            elements[j].disabled=true;
                            j++;
                        }
                        indice++;
                    }
					
                }
                if ((elemNodo.type) =='checkbox') {
                    elemNodo.value="";
                    elemNodo.checked=false;
                    elemNodo.disabled=true;
                }
                if ((elemNodo.type) =='select-one') {
                    // elemNodo.value="";
                    elemNodo.disabled=true;
                }
                i++;
            }
		
        }
	
	
    }
	
	
	
    function cambiaStatoCheckBoxOLDD(obj,vettoreCampiTesto){
        /*
                z=0;
                if (obj.checked){
                        while(z<vettoreCampiTesto.length) {
                                document.getElementById(vettoreCampiTesto[z]).disabled=false;
                                z++;
                        }
			
                }
                if (!obj.checked){
                        while(z<vettoreCampiTesto.length) {
                                document.getElementById(vettoreCampiTesto[z]).value="";
                                document.getElementById(vettoreCampiTesto[z]).disabled=true;
                                z++;
                        }
                }
         */
	
        i=0;
        if (obj.checked){
            while(i<vettoreCampiTesto.length) {
                var elemNodo = document.getElementById(vettoreCampiTesto[i]);
                if (elemNodo.getAttribute('type') =='text')   { document.getElementById(vettoreCampiTesto[i]).disabled=false;     }
                if (elemNodo.getAttribute('type') =='checkbox')   { document.getElementById(vettoreCampiTesto[i]).disabled=false;      }
                if (elemNodo.getAttribute('type') =='select')   { document.getElementById(vettoreCampiTesto[i]).disabled=false;     }
                if (elemNodo.getAttribute('type') =='radio')  {
                    var elements = document.getElementsByName(elemNodo.getAttribute('name'));
                    j=0;
                    while (j<elements.length) {
                        //alert(elements[j].value);
                        elements[j].disabled=false;
                        j++;
                    }
                }
                i++;
            }
        }
        if (!obj.checked){
            while(i<vettoreCampiTesto.length) {
                var elemNodo = document.getElementById(vettoreCampiTesto[i]);
                if (elemNodo.getAttribute('type') =='text')   {
                    elemNodo.value="";
                    elemNodo.disabled=true;
                }
                if (elemNodo.getAttribute('type') =='radio')  {
                    var elements = document.getElementsByName(elemNodo.getAttribute('name'));
                    j=0;
                    while (j<elements.length) {
                        elements[j].checked=false;
                        elements[j].disabled=true;
                        j++;
                    }
                }
                if (elemNodo.getAttribute('type') =='checkbox') {
                    elemNodo.value="";
                    elemNodo.disabled=true;
                }
                if (elemNodo.getAttribute('type') =='select') {
                    elemNodo.value="";
                    elemNodo.disabled=true;
                }
                i++;
            }
        }
	
    }
	
	
    function tmp_radio(value,vettoreCampiTesto,vettoreRadioButtonAssociati){
        mm=0;
        while(mm < vettoreCampiTesto.length) {
            if (value==vettoreRadioButtonAssociati[mm]){
                var elements = document.getElementsByName(vettoreCampiTesto[mm]);
                index2 = 0;
                while (index2<elements.length) {
                    elements[index2].checked=false;
                    elements[index2].disabled=false;
                    index2++;
                }
            } else {
                if (document.getElementById(vettoreCampiTesto[mm])!=null) {
                    var elements = document.getElementsByName(vettoreCampiTesto[mm]);
                    index2 = 0;
                    while (index2<elements.length) {
                        if (elements[index2].getAttribute('type') =='checkbox'){
                            elements[index2].checked=false;
                            elements[index2].disabled=true;
                            var imageattributes=elements[index2].attributes;
                            k=0;
                            while(k<imageattributes.length) {
                                if (imageattributes[k].name=='onclick'){
                                    s = imageattributes[k].value;
                                    var str2 = s.substr(0, s.length-7);
								
                                    eval(str2);
                                }
                                k++;
                            }
                        } else if (elements[index2].getAttribute('type') =='radio') {
                            elements[index2].checked=false;
                            elements[index2].disabled=true;
                        } else {
                            elements[index2].value='';
                            elements[index2].disabled=true;
                        }
                        elements[index2].disabled=true;
                        index2++;
                    }
                }
            }
            mm++;
        }
        return;
    }
	
</script>


<link rel="stylesheet" type="text/css" href="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/view/default/html/style.css" />
<html:xhtml/>
<logic:equal value="true" name="pplProcess" property="data.internalError">
    <jsp:include page="defaultError.jsp" flush="true" />
</logic:equal>
<%
            String index = (String) request.getAttribute("INDEX");
%>
<logic:notEqual value="true" name="pplProcess" property="data.internalError">
    <%
                ProcessData data = (ProcessData) pplProcess.getData();
                if (data.getErroreSuHref().size() > 0) {
    %>
    <table style="border:2px dotted red; padding: 3px; width:96%;">
        <tr>
            <td>
                <img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico//img/iconWarning.gif" alt="attenzione" />
                <b><span class="errHeaderFont">Si sono verificati i seguenti errori:</span>
                    <ul class="errHeader">
                        <%
                                    Iterator it = data.getErroreSuHref().iterator();
                                    while (it.hasNext()) {
                        %>
                        <li class="errItemFont"> <%=((String) it.next())%></li>
                        <%
                                    }

                        %>
                    </ul>
                </b>
            </td>
        </tr>
    </table>
    <br/><br/>
    <%
                }
                data.setErroreSuHref(new ArrayList());
    %>

    <logic:messagesPresent>
        <table style="border:2px dotted red; padding: 3px; width:96%;">
            <tr>
                <td><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/iconWarning.gif" alt="attenzione" />
                    <b><ppl:errors /></b>
                </td>
            </tr>
        </table>
        <br/>
    </logic:messagesPresent>

    <jsp:include page="webclock.jsp" flush="true" />
<%--    <logic:empty name="INDIETRO" scope="request">
        <% String commandProperty = "compilaRichiedente.jsp&svuota=TRUE&href=ANAG_" + index;%>
        <b><bean:message key="mu.label.anagrafica.altroDichiarante"/></b>&nbsp;&nbsp;<ppl:commandLoopback commandProperty="<%=commandProperty%>" styleClass="button_AeC_operazioni" >Svuota campi precaricati</ppl:commandLoopback>
    </logic:empty> --%>
    <table  style="border:1px solid #EAEAEA; padding: 5px; width:96%;">
        <%

                    AnagraficaBean anagrafica = (AnagraficaBean) data.getAltriRichiedenti().get(Integer.parseInt(index));
                    if (anagrafica.isFineCompilazione()) {
        %>
        <tr>
            <td>
                <table style="border:2px dotted green; padding: 3px;" width="100%">
                    <tr>
                        <td width="5%"><img src="/people/servizi/concessioniedautorizzazioni/servizicondivisi/procedimentounico/img/accept_big.jpg" alt="ok" /></td>
                        <td width="95%" valign="middle" style="font-size:15px; color:green;">
                            <br/>Compilazione completata (tutti i campi obbligatori sono stati compilati). Cliccare sul pulsante "TORNA AL MODELLO UNICO"
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <%}%>

        <%
                    if (!anagrafica.getHtmlHistory().isEmpty()) {
                        Iterator it__ = anagrafica.getHtmlHistory().iterator();
                        while (it__.hasNext()) {
        %>
        <tr>
            <td bgcolor="#DDFFFF" >
                <%=(String) it__.next()%>
            </td>
        </tr>
        <%
                        }
                    }
        %>


        <% if (!anagrafica.isFineCompilazione()) {%>

        <tr>
            <td style="border:2px dotted black; padding: 3px;">
                <%=anagrafica.getHtmlStepAttuale()%>
            </td>
        </tr>
        <% }%>



        <tr>
            <td>
             <%
                            if (request.getAttribute("attivaRicerca") != null && !((java.util.LinkedList) request.getAttribute("attivaRicerca")).isEmpty()) {
                                java.util.LinkedList list = (java.util.LinkedList) request.getAttribute("attivaRicerca");
                                java.util.Vector vec = (java.util.Vector) list.get(0);
                                list.remove(0);
                %>
                <table style="border:1px solid #EAEAEA; padding: 5px; width:96%;">
                    <tr>
                        <%
                            for (int i = 0; i < vec.size(); i++) {
                        %>
                        <td>
                            <b><%=vec.get(i)%></b>
                        </td>
                        <%
                            }
                        %>
                        <td>
                            &nbsp;
                        </td>
                    <tr>
                        <%
                            if (!list.isEmpty()) {
                                java.util.Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    vec = (java.util.Vector) it.next();
                                    String concat = "";
                        %>
                    <tr>
                        <%
                                            for (int i = 0; i < vec.size(); i++) {
                                                String dato = (String) vec.get(i);
                                                concat += dato.indexOf("|") != -1 ? dato + "@" : "";
                        %>
                        <td>
                            <%=dato.substring(0, dato.indexOf("|") != -1 ? dato.indexOf("|") : dato.length())%>
                        </td>
                        <%
                                            }
                        %>
                        <td>
                            <input type="button" onkeypress="JavaScript:setValue('<%=concat%>');" onclick="JavaScript:setValue('<%=concat%>');" property="seleziona" value="Seleziona" />
                        </td>
                    <tr>
                        <%
                                }
                            }
                        %>
                </table>
                <%}%>
               </td>
        </tr>
        <tr>
            <td>
                <logic:notEmpty name="INDIETRO" scope="request">
                    <% String backStep = "indietroAnagraficaAltroRichiedente&href=ANAG_" + index;%>
                    <ppl:commandLoopback commandProperty="<%=backStep%>" styleClass="button_AeC_operazioni" >
                        &#60;&#60;&nbsp;<bean:message key="keyloopback.sceltaAnagraficaPrecedente"/>&nbsp;
                    </ppl:commandLoopback>
                </logic:notEmpty>
                <logic:notEmpty name="AVANTI" scope="request">
                    <% String nextStep = "avantiAnagraficaAltroRichiedente&href=ANAG_" + index;%>
                    <ppl:commandLoopback commandProperty="<%=nextStep%>" validate="true" styleClass="button_AeC_operazioni" >
                        <bean:message key="keyloopback.sceltaAnagraficaSuccessiva"/>&nbsp;&#62;&#62;
                    </ppl:commandLoopback>
                </logic:notEmpty>
            </td>
        </tr>

    </table>
    <br/>
    <table align="center">
        <tr>
            <td><!--input type="button" class="btn"
			alt="Accessibilità: la chiave di accesso da tastiera è B"
			accesskey="B"
			onkeypress="javascript:executeSubmit('loopBack.do?propertyName=modelloUnico.jsp');"
			onclick="javascript:executeSubmit('loopBack.do?propertyName=modelloUnico.jsp');"
			value="TORNA AL MODELLO UNICO" /-->
                <% String property = "modelloUnico.jsp&href=ANAG_" + index;%>
                <ppl:linkLoopback accesskey="B" property="<%=property%>" styleClass="btn" ><b>&nbsp;TORNA AL MODELLO UNICO&nbsp;</b></ppl:linkLoopback>
            </td>
        </tr>
    </table>
    <input type="hidden" value="ANAG_<%= index%>" name="href"/>
</logic:notEqual>
