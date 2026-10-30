<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
    String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<div id="ricerca_albero">

  <div class="ctrlHolder" >
              
			 <HTML>
<!-- 			        <HEAD> -->
<!-- 			            <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE"> -->
<!-- 			            <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"> -->
<!-- 			            <META HTTP-EQUIV="EXPIRES" CONTENT="0"> -->
			           
<!-- 			        </HEAD> -->
			
			      <script type="text/javascript">
			       
			     
			
			            //Chiude tutti i menù
			            function collapseALL()
			                {
			                    //NASCONDE IL MENU
			                    //inserisce in una collezione di oggetti tutti i TAG UL del documento
			                    var uls = document.getElementsByTagName("UL");
			                   
			                    //per ogni TAG UL ripete il seguente controllo:
			                    for (i=0;i<uls.length;i++)
			                        {   
			                            //Se il nome della classe e "Sottomenu" allora imposta "display" a "none".
			                            if(uls[i].className=="Sottomenu")uls[i].style.display = "none";
			                        }
			                    //CAMBIA LE ICONE DELLA CARTELLA
			                    //inserisce in una collezione di oggetti tutti i TAG LI del documento
			                    var lis = document.getElementsByTagName("LI");
			                   
			                    //per ogni TAG LI ripete il seguente controllo:
			                    for (i=0;i<lis.length;i++)
			                        {   
			                            //Se il nome della classe e "Menu" allora imposta "listStyleImage" a "closedfolder.png".
			                            if(lis[i].className=="Menu")lis[i].style.listStyleImage = "url(../themes/default/images/icons/closedfolder.png)" ;
			                        }
			                }
			           
			            //apre tutti i menù
			            function expandALL()
			                {
			                    //MOSTRA I MENU
			                    //inserisce in una collezione di oggetti tutti i TAG UL del documento
			                    var uls = document.getElementsByTagName("UL");
			                   
			                    //per ogni TAG UL ripete il seguente controllo:
			                    for (i=0;i<uls.length;i++)
			                        {   
			                            //Se il nome della classe e "Sottomenu" allora imposta "display" a "block".
			                            if(uls[i].className=="Sottomenu")uls[i].listStyleImage = "url(../themes/default/images/icons/openfolder.png)" ;      
			                        }
			                   
			                    //CAMBIA LE ICONE DELLA CARTELLA
			                    //inserisce in una collezione di oggetti tutti i TAG LI del documento
			                    var lis = document.getElementsByTagName("LI");
			                   
			                    //per ogni TAG LI ripete il seguente controllo:
			                    for (i=0;i<lis.length;i++)
			                        {   
			                            //Se il nome della classe e "Menu" allora imposta "listStyleImage" a "openfolder.png".
			                            if(lis[i].className=="Menu")lis[i].listStyleImage = "url(../themes/default/images/icons/openfolder.png)" ;
			                        }
			                   
			                }
			           
			            //Mostra/Nasconde i menù
			            function MostraMenu(IDmenu)
			                {
			                    //Carico l'oggetto UL che devo modificare
			                    var menu = document.getElementById(IDmenu);
			                   
			                    //Controllo lo stato dell'opzione display
			                    var display = menu.style.display;
			                   
			                    //se lo stato è a "block" la imposta a "none" e vice versa
			                    menu.style.display = (display == "block") ? "none" : "block";
			           
			                    //modifica l'icona a closedfolder.png se display è block, altrimenti la imposta a openfolder.png
			                    menu.parentNode.style.listStyleImage = (display == "block") ? "url(../themes/default/images/icons/closedfolder.png)" : "url(../themes/default/images/icons/openfolder.png)";
			                }
			   
			        </script>
			  <form id="form" name="form" method="post" action="ricerca_albero">
			        <BODY onload="javascript:collapseALL();">
			            <TABLE>
			                <TR>
			                    <TD>
			                        <FORM>
<!-- 			                        <input type="button" onClick="javascript:expandALL();" value="Apri Tutti"> -->
			                            <input type="button" onClick="javascript:collapseALL();" value="Chiudi Tutti">
			                        </FORM>
			                    </TD>
			                </TR>
			                <TR>
			                    <TD>
			                        <UL>
			                            <LI class="Menu"><a href="javascript:MostraMenu('Voce-1')">COMMERCIO IN SEDE FISSA</a>
			                                <UL class="Sottomenu" id="Voce-1">
			                                    <LI>
													<a href="#">ESERCIZI DI VICINATO</a>
			                                    	<UL>
			                                           	<LI><input  type="checkbox"><a href="#">Apertura - Nuovo esercizio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Apertura - Concentrazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Apertura per subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Trasferimento di sede</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Ampliamento o riduzione superficie di vendita</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Variazione del settore merceologico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Gestione di reparto</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
			                                        </UL>
												</LI>
			                                  
			                                    <LI><a href="#">MEDIE STRUTTURE DI VENDITA</a>
			                                    	<UL>
			                                            <LI><input  type="checkbox"><a href="#">Apertura - Nuovo esercizio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Apertura - Concentrazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Apertura per subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Riduzione superficie di vendita o settore merceologico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Trasferimento di sede</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Ampliamento superficie di vendita o settore mercelogico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Gestione di reparto</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
			                                        </UL>
			                                    
			                                    
			                                    </LI>
			                                    <LI><a href="#">GRANDI STRUTTURE DI VENDITA</a>
				                                    <UL>
				                                     	<LI><input  type="checkbox"><a href="#">Apertura - Nuovo esercizio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Apertura - Concentrazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Apertura per subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Riduzione superficie di vendita o settore merceologico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Trasferimento di sede</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Ampliamento superficie di vendita o settore mercelogico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Gestione di reparto</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
				                                    </UL>
			                                    
			                                    </li>
			                                    <LI><a href="#">SPACCI INTERNI</a>
			                                    	<ul>
			                                    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Apertura per subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Trasferimento di sede</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Ampliamento superficie di vendita o settore mercelogico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazione del settore merceologico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
			                                    	</ul>
			                                    </LI>
												<LI><a href="#">PER MEZZO DI APPARECCHI AUTOMATICI</a>
													<ul>
														<LI><input  type="checkbox"><a href="#">Avvio su area pubblica</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Avvio su area privata</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Trasferimento</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attivita in una sede già indicata</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
													</ul>
												</LI>
												<LI><a href="#">VENDITA PER CORRISPONDENZA, TELEVISIONE O ALTRI SISTEMI DI COMUNICAZIONE</a>
													<ul>
														<LI><input  type="checkbox"><a href="#">Vendita per corrispondenza</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Vendita per televisione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Vendita tramite altri sistemi di comunicazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Trasferimento di sede</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Variazione del settore merceologico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
													</ul>
												</LI>
												<LI><a href="#">COMMERCIO ELETTRONICO</a>
													<ul>
														<LI><input  type="checkbox"><a href="#">Avvio</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Trasferimento di sede</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazione del settore merceologico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazione del sito web</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
													</ul>
												</LI>
												<LI><a href="#">VENDITA PRESSO IL DOMICILIO DEL CONSUMATORE</a>
													<ul>
														<LI><input  type="checkbox"><a href="#">Avvio attività</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazione del settore merceologico</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
													</ul>
												</LI>
												<LI><a href="#">FARMACIE</a>
													<ul>
														<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Trasferimento</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Gestione provvisoria</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
													</ul>
												</LI>
												<LI><a href="#">TABACCHI</a>
													<ul>
														<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Trasferimento</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Gestione provvisoria</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione attività</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
													</ul>
												</LI>
			                                </UL>
			                            </LI>
			                            <LI class="Menu"><a href="javascript:MostraMenu('Voce-2')">Rivendite quotidiani e periodici</a>
			                                <UL class="Sottomenu" id="Voce-2">
			                                	
											    <LI><input  type="checkbox"><a href="#">Punti esclusivi</a></LI>
			                                    <LI><input  type="checkbox"><a href="#">Punto non esclusivi</a></LI>
			                                    <LI><input  type="checkbox"><a href="#">Storico</a></li>
			                                </UL>
			                            </LI>
										<LI class="Menu"><a href="javascript:MostraMenu('Voce-3')">Distributori di carburante</a>
			                                <UL class="Sottomenu" id="Voce-3">
			                                    <LI><input  type="checkbox"><a href="#">Autorizzazione servizio notturno</a></LI>
			                                    <LI><input  type="checkbox"><a href="#">Autorizzazione servizio ininterrotto</a></LI>
			                                    <LI><input  type="checkbox"><a href="#">Autorizzazione deroga del turno</a></LI>
			                                    <LI><input  type="checkbox"><a href="#">Richiesta cambio di turno</a></LI>
												<LI><input  type="checkbox"><a href="#">Adempimenti vari</a></li>
			                                </UL>
			                            </LI>
										<LI class="Menu"><a href="javascript:MostraMenu('Voce-4')">Pubblici esercizi</a>
			                                <UL class="Sottomenu" id="Voce-4">
			                                    <LI><input  type="checkbox"><a href="#">Apertura</a></LI>
												<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
												<LI><input  type="checkbox"><a href="#">Trasferimento</a></LI>
												<LI><input  type="checkbox"><a href="#">Variazioni</a>
													<ul>
														<LI><input  type="checkbox"><a href="#">Adempimento o riduzione di superficie</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Cambio amministrazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Modifica ragione sociale</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Nomina o sostituzione legale rappresentante</a></LI>
													</ul>
												</LI>
												<LI><input  type="checkbox"><a href="#">Cessazione per subingresso</a></LI>
												<LI><input  type="checkbox"><a href="#">Cessazione definitiva</a></LI>
			                                    <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
												<LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
												<LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
			                                </UL>
			                            </LI>
										<LI class="Menu"><a href="javascript:MostraMenu('Voce-5')">Strutture ricettive</a>
			                                <UL class="Sottomenu" id="Voce-5">
											    <LI><a href="#">Alberghiere</a>
											    	<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Trasferimento di sede</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Adempimento o riduzione di superficie e/o della capacità ricettiva </a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Modifica della ragione sociale</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Cambio amministrazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni - Nomina o sostituzione del direttore</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
											    </LI>
												<LI><a href="#">Bed & Breakfast</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura - Conduzione familiare</a></LI>
											    		<LI><input  type="checkbox"><a href="#">Apertura - Conduzione imprenditoriale</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                          	<LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Comunicazione tipologia ex art, 14 c. 2 L.R.27/2013</a></LI>
											    	</ul>
												</LI>
												<LI><a href="#">Agriturismo</a></LI>
												<LI><a href="#">Affittacamere</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
												</LI>
												<LI><a href="#">Case ferie</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
												</LI>
												<LI><a href="#">Residenze turistiche</a>
													
												</LI>
												<LI><a href="#">Case o appartamenti vacanze</a>
			                                    	<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
			                                    </LI>
			                                    <LI><a href="#">Ostello gioventù</a>
			                                    	<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
			                                    </LI>
												<LI><a href="#">Villaggi turistici</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
												</LI>
												<LI><a href="#">Campeggi</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
												</LI>
												<LI><a href="#">Case di riposo o case protette</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Reintestazione</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Revoca o cessazione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione da parte dell'utente</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Sospensione d'ufficio</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
												</LI>
			                                </UL>
			                            </LI>
										<LI class="Menu"><a href="javascript:MostraMenu('Voce-6')">Artigianato</a>
			                                <UL class="Sottomenu" id="Voce-6">
											<LI><input  type="checkbox"><a href="#"></a></LI>
												<LI><a href="#">Acconciatori</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Trasferimento</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
												</LI>
												<LI><a href="#">Estetisti</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Trasferimento</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Variazioni</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Adempimenti vari</a></LI>
											    	</ul>
												</LI>
												<LI><a href="#">Tintolavanderie</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                           	<LI><input  type="checkbox"><a href="#">Subingresso</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
											    	</ul>
												</LI>
												<LI><a href="#">Tatuatori</a>
													<ul>
											    		<LI><input  type="checkbox"><a href="#">Apertura</a></LI>
			                                            <LI><input  type="checkbox"><a href="#">Cessazione</a></LI>
											    	</ul>
												</LI>
			                                </UL>
			                            </LI>
			                        </UL>
			                    <TD>
			                </TR>
			            </TABLE>
			        </BODY>
			    </form>
        </div>
     
   
</div>