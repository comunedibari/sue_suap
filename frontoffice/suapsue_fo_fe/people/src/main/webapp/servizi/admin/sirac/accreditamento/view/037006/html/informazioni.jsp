<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>


<div class="admin_sirac_accr_titolo">
    <bean:message key="label.Introduzione" />
</div>
<div class="text_block">
  <h2>Descrizione del Servizio</h2>
    <bean:message key="label.Introduzione.Welcome" /><br>
    <bean:message key="label.Introduzione.Intro" />
</div>

<table class="maintable" border="1" cellspacing="0" width="100%">
    <tr>
        <td>
            <span class="FixTextLabel">
                Da questa pagina, utilizzando il men&ugrave; sulla sinistra &egrave; possibile selezionare l&#8217;attivit&agrave; specifica di interesse. <br><br>
                Una volta terminata l&#8217;esecuzione degli step associati all&#8217;attivit&agrave; specifica selezionata cliccando su "Selezione Attivit&agrave;"
                sar&agrave; possibile accedere nuovamente al men&ugrave; di selezione delle attivit&agrave; di gestione degli accreditamenti.<br><br>
                
                Le attivit&agrave; attualmente definite sono:<br>
                <ul>
                	<li>Visualizzazione Profilo Locale.</li>
                	<li>Selezione di uno degli accreditamenti esistenti presso questo Comune.</li>
                	<li>Creazione di un nuovo accreditamento presso questo Comune.</li>
                	<li>Accreditamento di un nuovo operatore di una agenzia di intermediazione.<br>
                	    L'accesso a questa funzionalit&agrave; &egrave; consentito soltanto se l&#8217;accreditamento corrente   
                	    ha associato il tipo di qualifica "Intermediario" e la descrizione "Rappresentante CAF".<br>
                	</li>
                	<li>Attivazione del processo di creazione preliminare di delega.<br>
                	    Questa funzionalità prevede l'impostazione provvisoria di un accreditamento scelto tra quelli disponbiili, in cui il titolare è temporaneamente impostato uguale al richiedente.<br>
                	    In questo caso, dopo la selezione dell'accreditamento, il controllo viene trasferito al servizio di richiesta preliminare di delega, mediante il quale è possibile completare il procedimento.<br>
                	</li>
                </ul>
            </span>
        </td>
    </tr>
    <tr>
        <td>Selezionare una delle attivit&agrave; presenti nel men&ugrave; a sinistra&nbsp;</td>
    </tr>
	
</table>
