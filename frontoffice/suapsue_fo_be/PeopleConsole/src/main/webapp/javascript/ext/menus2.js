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
        id: 'mainMenu',
        style: {
            overflow: 'visible'     
        }
    });

    var tb = Ext.create('Ext.toolbar.Toolbar');
    tb.suspendLayout = true;
    tb.render('toolbar');
    
	tb.add({
        text: 'Pagina principale',
        handler: function(){onSelectedMenuItem(0);}
    });
	
	//tb.add({xtype: 'tbseparator'});

    tb.add({
            text: 'Amministrazione',
            id: 'Amministrazione',
            //width: 150,
            menu: {
            	 xtype: 'menu',
                 plain: true,
                 items: {
                    xtype: 'buttongroup',
                    columns: 1,
                    defaults: {
                        scale: 'medium',
						width: 120
                    },
                    items: [{
                        text: 'Utenti',
                        handler: function(){onSelectedMenuItem(11);}
                    },{
                        text: 'Certificati',
                        handler: function(){onSelectedMenuItem(12);}
                    	/*, width: 130, scale: 'small' */                     
                    },{
                        text: 'Impostazioni',
                        handler: function(){onSelectedMenuItem(13);}
                    },{
                        text: 'Pratiche',
                        handler: function(){onSelectedMenuItem(14);}                  
                    }]
                }
            }
        }
     );
	
    // controllo Console Admin
	var hasRoleConsoleAdmin = document.getElementById('hasRoleConsoleAdmin_menu'); 
	if(!hasRoleConsoleAdmin) {
		tb.items.get('Amministrazione').hide();
	} 
    
	// tb.add({xtype: 'tbseparator'});
	
    
	// -------------------  Menu Gestione  ------------------- //
	var menuGestione = Ext.create('Ext.menu.Menu', {
        id: 'mainMenuGestione',
        plain: true,
        style: 'background-image: none;',
        items: [
            {
                text: 'Nodi FE',
                menu: { 
                	plain: true,
                    items: [{
	                   text: 'Gestione Nodi',
	                   handler: function(){onSelectedMenuItem(21);}                        
	                },{
	                   text: 'Statistiche',
	                   handler: function(){onSelectedMenuItem(22);}
                    }]
                }
           }, {
               text: 'Servizi di FE',
               menu: {  
            	   plain: true,
                   items: [{
                       text: 'Gestione Servizi di FE',
                       handler: function(){onSelectedMenuItem(31);}                        
                   },{
                       text: 'Registrazione servizi dispiegati',
                       handler: function(){onSelectedMenuItem(32);}
                   },{
                       text: 'Copia da nodo a nodo',
                       handler: function(){onSelectedMenuItem(33);}
                   },{
                       text: 'Modifica massiva parametri',
                       handler: function(){onSelectedMenuItem(34);}
                   },{
                       text: 'Eliminazione massiva',
                       handler: function(){onSelectedMenuItem(35);}
                   }]
               }
          }, {
        	  text: 'Servizi di BE',
        	  menu: {
        		  plain: true,
        		  items: [{
                      text: 'Gestione Servizi di BE',
                      handler: function(){onSelectedMenuItem(41);}                        
                  },{
                      text: 'Modifica massiva parametri',
                      handler: function(){onSelectedMenuItem(42);}
                  },{
                      text: 'Eliminazione massiva',
                      handler: function(){onSelectedMenuItem(43);}
                  }]
        	  }
          },{
              text: 'Messaggi',
              handler: function(){onSelectedMenuItem(70);}
          }
        ]
    });
    tb.add({
    	text:'Gestione',
    	menu: menuGestione  
		}
    );
	// ----------------  Fine Menu Gestione  ----------------- //

    
    // ----------------  Versione precedente  ---------------- //
    /*
    tb.add({
        text: 'Gestione messaggi',
        handler: function(){
        	onSelectedMenuItem(70); 
		}
    });
    
	tb.add({
        text: 'Gestione Nodi',
        menu: {
            xtype: 'menu',
            plain: true,
            items: {
                xtype: 'buttongroup',
                //title: 'Gestione Nodi options',
                columns: 1,
                defaults: {
                    xtype: 'button',
                    scale: 'medium',
                    iconAlign: 'left',
					width: 110
                },
                items: [{
                    text: 'Gestione Nodi',
                    
                    handler: function(){
                    	onSelectedMenuItem(21); 
					}                        
                },{
                    text: 'Statistiche',
                    handler: function(){
                    	onSelectedMenuItem(22); 
					}
                }]
            }
        }
    });
	
	tb.add({
            text: 'Servizi di FE'
            menu: {
                xtype: 'menu',
                plain: true,
                items: {
                    xtype: 'buttongroup',
                    title: 'Servizi di FE options',
                    columns: 1,
                    defaults: {
                        xtype: 'button',
                        scale: 'medium',
                        iconAlign: 'left',
						width: 190
                    },
                    items: [{
                        text: 'Gestione Servizi di FE',
                        handler: function(){
                        	onSelectedMenuItem(31); 
						}                        
                    },{
                        text: 'Registrazione servizi dispiegati',
                        handler: function(){
                        	onSelectedMenuItem(32); 
						}
                    },{
                        text: 'Copia da nodo a nodo',
                        handler: function(){
                        	onSelectedMenuItem(33); 
						}
                    },{
                        text: 'Modifica massiva parametri',
                        handler: function(){
                        	onSelectedMenuItem(34); 
						}
                    },{
                        text: 'Eliminazione massiva',
                        handler: function(){
                        	onSelectedMenuItem(35); 
						}
                    }]
                }
            }
        }
     );
	
	tb.add({
        text: 'Servizi di BE',
        // tooltip: {text:'Accesso alla gestione dei servizi di Back-End', title:'Servizi di BE'}, 
        menu: {
            xtype: 'menu',
            plain: true,
            items: {
                xtype: 'buttongroup',
                title: 'Servizi di BE options',
                columns: 1,
                defaults: {
                    xtype: 'button',
                    scale: 'medium',
                    iconAlign: 'left',
					width: 190
                },
                items: [{
                    text: 'Gestione Servizi di BE',
                    handler: function(){
                    	onSelectedMenuItem(41); 
					}                        
                }
                
                ,{
                    text: 'Modifica massiva dei parametri',
                    handler: function(){
                    	onSelectedMenuItem(42); 
					}
                }]
            }
        }
    });
     */
    
    // ----------------  fine versione precedente  ---------------- //
    
    tb.add({
        text: 'Accreditamenti',
        id: 'Accreditamenti',
        menu: {
        	 xtype: 'menu',
             plain: true,
             items: {
                xtype: 'buttongroup',
                columns: 1,
                defaults: {
                    scale: 'medium',
					width: 120
                },
                items: [{
                    text: 'Accreditamenti',
                    handler: function(){onSelectedMenuItem(51);}
                },{
                    text: 'Qualifiche',
                    handler: function(){onSelectedMenuItem(52);}
                },{
                    text: 'Visibilit&agrave Qualifiche',
                    handler: function(){onSelectedMenuItem(53);}
                }]
            }
        }
    });
	
	//tb.add({xtype: 'tbseparator'});

    
    tb.add({
    	text: 'Osservatorio',
        id: 'Osservatorio',
        menu: {
        	 xtype: 'menu',
             plain: true,
             items: {
                xtype: 'buttongroup',
                columns: 1,
                defaults: {
                    scale: 'medium',
					width: 170
                },
                items: [{
                    text: 'Performance delle soluzioni',
                    handler: function(){onSelectedMenuItem(81);}
                }]
             }
        }
    });
    
    
	tb.add({
        text: 'Logout',
        id: 'Logout',
        handler: function(){onSelectedMenuItem(60);}
    });

    // controllo pulsante Logout
	var includeLogout = document.getElementById('includeLogout_menu'); 
	if(!includeLogout) {
		tb.items.get('Logout').hide();
	} 
	
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
			"14": /* Amministrazione - cancellazione pratiche */
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
			"34": /* Modifica massiva parametri */
				'/PeopleConsole/ServiziFe/ModificaMassiva/modifica.do?tipo=modifica',
			"35": /* Eliminazione massiva */
				'/PeopleConsole/ServiziFe/ModificaMassiva/modifica.do?tipo=elimina',
			"41": /* Gestione Servizi BE */
				'/PeopleConsole/ServiziBe/elenco.do',
			"42": /* Servizi BE */
				'/PeopleConsole/ServiziBe/ModificaMassiva/modifica.do?tipo=modifica',
			"43": /* Servizi BE */
				'/PeopleConsole/ServiziBe/ModificaMassiva/modifica.do?tipo=elimina',
			"51": /* Accreditamenti */
				'/PeopleConsole/Accreditamenti/accreditamentiManagement.do', 
			"52": /* Accreditamenti - Qualifiche */
				'/PeopleConsole/Accreditamenti/accreditamentiQualifiche.do', 
			"53": /* Accreditamenti - Visibilità Qualifiche */
				'/PeopleConsole/Accreditamenti/accreditamentiVisibilitaQualifiche.do',
			"60": /* Logout */
				'/PeopleConsole/logout.do',
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



