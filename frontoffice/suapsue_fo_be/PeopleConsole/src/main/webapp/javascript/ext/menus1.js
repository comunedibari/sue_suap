/*

This file is part of Ext JS 4

Copyright (c) 2011 Sencha Inc

Contact:  http://www.sencha.com/contact

GNU General Public License Usage
This file may be used under the terms of the GNU General Public License version 3.0 as published by the Free Software Foundation and appearing in the file LICENSE included in the packaging of this file.  Please review the following information to ensure the GNU General Public License version 3.0 requirements will be met: http://www.gnu.org/copyleft/gpl.html.

If you are unsure which license is appropriate for your use, please contact the sales department at http://www.sencha.com/contact.

*/
Ext.require(['*']);

Ext.onReady(function(){
    Ext.QuickTips.init();

    var menu = Ext.create('Ext.menu.Menu', {
        id: 'mainMenu1',
        plain: true,
        style: {
            overflow: 'visible'     // For the Combo popup
        },
        items: [
            {
                text: 'I like Ext dddddddddddddd'
            },
            {
                text: 'I Ext'
            }
        ]
    });
    
    
    var tb = Ext.create('Ext.toolbar.Toolbar');
    tb.suspendLayout = true;
    tb.render('toolbar1');
    
    tb.add({
        text:'Button w/ Menu',
        
        menu: menu  // assign menu by instance
    });
	 
	menu.add(' ');

    tb.suspendLayout = false;
    tb.doLayout();
    

    /** Link di indirizzamento del menu **/
    var routes = { 
    		   			
    		"0": /* Pagina principale */ 
    			'/PeopleConsole/paginaPrincipale.mdo',
		    "11": /* Gestione Accounts Utenti */ 
		    	'/PeopleConsole/Amministrazione/Utenti/accounts.do',
			"12": /* Gestione Certificati visualizzazione XML */
				'/PeopleConsole/Amministrazione/Utenti/Certificati/certificatiAccessoXML.do',
			"13": /* Amministrazione - impostazioni */
				'/PeopleConsole/Amministrazione/Impostazioni/impostazioni.do',
			"14": /* Amministrazione - impostazioni */
				'/PeopleConsole/Amministrazione/Pratiche/cancellazione.do',	
			"21": /* Gestione Nodi */
				'/PeopleConsole/NodiFe/elenco.do',
			"22": /* Statistiche */
				'/PeopleConsole/NodiFe/auditStatistiche.do',
			"31": /* Gestione Servizi FE */
				'/PeopleConsole/ServiziFe/elenco.do',
			"32": /* Registrazione servizi dispiegati */
				'/PeopleConsole/ServiziFe/registrazioneServiziDispiegatiNodo.do', 
			"33": /* Copia da nodo a nodo */
				'/PeopleConsole/ServiziFe/copiaDaNodoANodo.do',
			"41": /* Gestione Servizi BE */
				'/PeopleConsole/ServiziBe/elenco.do',
			"42": /* Servizi BE */
				'/PeopleConsole/ServiziBe/modificaMassivaParametri.do',
			"51": /* Accreditamenti */
				'/PeopleConsole/Accreditamenti/accreditamentiManagement.do', 
			"52": /* Accreditamenti - Qualifiche */
				'/PeopleConsole/Accreditamenti/accreditamentiQualifiche.do',  
			"60": /* Logout */
				'TODO',
			"70": /* Gestione messaggi generali */
				'/PeopleConsole/MessaggiGenerali/elenco.do',
			"80": /* Osservatorio */	
				'/PeopleConsole/Monitoraggio/paginaPrincipale.do',
			"81": 
				'/PeopleConsole/Monitoraggio/Indicatori/indicatori.do'
    };
	
    function onSelectedMenuItem(caseMenuItem) {
    	location.href = routes[caseMenuItem];
    }

	
    
});



