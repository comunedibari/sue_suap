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

http://joinup.ec.europa.eu/software/page/eupl

Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.

This product includes software developed by Yale University

See the Licence for the specific language governing
permissions and limitations under the Licence.
--%>
<%@ page contentType="text/html"%>
<%@ taglib uri="http://sourceforge.net/projects/jsf-comp/clientvalidators" prefix="cv"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<html>
	<head>
		<title>Dettagli</title>
	</head>
	<body>
		<f:view>
			<h:form id="form1">
				<br />
				<h2>
					<f:subview id="titleEdit" rendered="#{!tableManager.insert}">
						<t:outputText value="Scheda di #{tableManager.current.nome} #{tableManager.current.cognome}" />
					</f:subview>
					<f:subview id="titleInsert" rendered="#{tableManager.insert}">
						<t:outputText value="Inserisci nuovo utente" />
					</f:subview>
				</h2>
				<t:outputText value="#{tableManager.tableAction.error}"
					style="color: red;" />
				<br />
				<h3>
					<t:outputText value="Anagrafica" />
				</h3>
				<div class="Box" style="width: 75%;">
					<t:panelGrid columns="4" cellspacing="5">
						<t:outputText value="Cognome: " />
						<t:inputText value="#{tableManager.current.cognome}"
							style="width:200px;" id="cognome" />
						<t:outputText value="Nome: " />
						<t:inputText value="#{tableManager.current.nome}"
							style="width:200px;" id="nome"  />
						<t:outputText value="Data di nascita: (GG/MM/AAAA) " />
						<t:inputText value="#{tableManager.current.dataNascita}"
							maxlength="10" id="dataNascita" 
							style="width:100px;">
						</t:inputText>
						<t:outputText value="Luogo di nascita: " />
						<t:inputText value="#{tableManager.current.luogoNascita}"
							style="width:200px;" id="luogoNascita" />
						<t:outputText value="Provincia di nascita: " />
						<t:inputText value="#{tableManager.current.provinciaNascita}"
							maxlength="2" style="width:30px;" />
						<t:outputText value="Stato di nascita: " />
						<t:inputText value="#{tableManager.current.statoNascita}"
							style="width:200px;" />
					</t:panelGrid>
					<t:panelGrid columns="1" cellspacing="5">
						<cv:requiredFieldValidator componentToValidate="cognome"
							highlight="true" display="dynamic"
							errorMessage="Il Cognome è obbligatorio" style="error" />
						<cv:requiredFieldValidator componentToValidate="nome"
							highlight="true" display="dynamic"
							errorMessage="Il Nome è obbligatorio" style="error" />
						<cv:requiredFieldValidator componentToValidate="dataNascita"
							highlight="true" display="dynamic"
							errorMessage="La data di nascita è obbligatoria" style="error" />
						<cv:requiredFieldValidator componentToValidate="luogoNascita"
							highlight="true" display="dynamic"
							errorMessage="Il luogo di nascita è obbligatorio" style="error" />
					</t:panelGrid>
				</div>
				<h3>
					<t:outputText value="Generalità" />
				</h3>
				<div class="Box" style="width: 75%;">
					<t:panelGrid columns="4" cellspacing="5">
						<t:outputText value="Codice Fiscale: " />
						<t:inputText id="codiceFiscale" 
							value="#{tableManager.current.codiceFiscale}" maxlength="16"
							style="width:160px;" readonly="#{!tableManager.insert}" disabled="#{!tableManager.insert}"/>
						<t:outputText value="Carta d'indentità: " />
						<t:inputText value="#{tableManager.current.cartaIdentita}"
							maxlength="9" style="width:100px;" />
						<t:outputText value="Sesso: " />
						<t:selectOneRadio id="sesso" value="#{tableManager.current.sesso}">
							<f:selectItem itemLabel="M" itemValue="M" />
							<f:selectItem itemLabel="F" itemValue="F" />
						</t:selectOneRadio>
						<t:outputText value="Titolo: " />
						<t:inputText value="#{tableManager.current.titolo}"
							style="width:200px;" />
						<t:outputText value="Lavoro: " />
						<t:inputText value="#{tableManager.current.lavoro}"
							style="width:200px;" />
					</t:panelGrid>
					<t:panelGrid columns="1" cellspacing="5">
						<cv:requiredFieldValidator componentToValidate="codiceFiscale"
							highlight="true" display="dynamic"
							errorMessage="Il Codice Fiscale è obbligatorio" style="error" />
					</t:panelGrid>
				</div>
				<h3>
					<t:outputText value="Domicilio" />
				</h3>
				<div class="Box" style="width: 75%;">
					<t:panelGrid columns="4" cellspacing="5">
						<t:outputText value="Città di domicilio: " />
						<t:inputText value="#{tableManager.current.cittaDomicilio}"
							style="width:200px;" />
						<t:outputText value="Provincia di domicilio: " />
						<t:inputText value="#{tableManager.current.provinciaDomicilio}"
							maxlength="2" style="width:30px;" />
						<t:outputText value="CAP di domicilio: " />
						<t:inputText value="#{tableManager.current.capDomicilio}"
							maxlength="5" style="width:40px;" />
						<t:outputText value="Indirizzo di domicilio: " />
						<t:inputText value="#{tableManager.current.indirizzoDomicilio}"
							style="width:200px;" />
						<t:outputText value="Stato di domicilio: " />
						<t:inputText value="#{tableManager.current.statoDomicilio}"
							style="width:200px;" />
					</t:panelGrid>
				</div>
				<h3>
					<t:outputText value="Residenza" />
				</h3>
				<div class="Box" style="width: 75%;">
					<t:panelGrid columns="4" cellspacing="5">
						<t:outputText value="Comune di residenza: " />
						<t:inputText id="cittaResidenza" value="#{tableManager.current.cittaResidenza}"
							style="width:200px;" />
						<t:outputText value="Provincia di residenza: " />
						<t:inputText id="provinciaResidenza" value="#{tableManager.current.provinciaResidenza}"
							maxlength="2" style="width:30px;" />
						<t:outputText value="CAP di residenza: " />
						<t:inputText id="capResidenza" value="#{tableManager.current.capResidenza}"
							maxlength="5" style="width:40px;" />
						<t:outputText value="Indirizzo di residenza: " />
						<t:inputText id="indirizzoResidenza" value="#{tableManager.current.indirizzoResidenza}"
							style="width:200px;" />
						<t:outputText value="Stato di residenza: " />
						<t:inputText id="statoResidenza" value="#{tableManager.current.statoResidenza}"
							style="width:200px;" />
					</t:panelGrid>
					
					<t:panelGrid columns="1" cellspacing="5">
					
						<cv:requiredFieldValidator
								componentToValidate="cittaResidenza" highlight="true"
								display="dynamic"
								errorMessage="Il comune di residenza è obbligatorio"
								style="error"  />
						
						<cv:requiredFieldValidator
								componentToValidate="provinciaResidenza" highlight="true"
								display="dynamic"
								errorMessage="La provincia di residenza è obbligatoria"
								style="error"  />
								
						<cv:requiredFieldValidator
								componentToValidate="capResidenza" highlight="true"
								display="dynamic"
								errorMessage="Il CAP di residenza è obbligatorio"
								style="error"  />
								
						<cv:requiredFieldValidator
								componentToValidate="indirizzoResidenza" highlight="true"
								display="dynamic"
								errorMessage="L'indirizzo di residenza è obbligatorio"
								style="error"  />
								
						<cv:requiredFieldValidator
								componentToValidate="statoResidenza" highlight="true"
								display="dynamic"
								errorMessage="Lo stato di residenza è obbligatorio"
								style="error"  />		
							
					</t:panelGrid>
					
				</div>
				<h3>
					<t:outputText value="Contatti" />
				</h3>
				<div class="Box" style="width: 75%;">
					<t:panelGrid columns="4" cellspacing="5">
						<t:outputText value="Telefono: " />
						<t:inputText value="#{tableManager.current.telefono}"
							style="width:200px;" />
						<t:outputText value="Cellulare: " />
						<t:inputText value="#{tableManager.current.cellulare}"
							style="width:200px;" />
						<t:outputText value="Email: " />
						<t:inputText id="email" value="#{tableManager.current.email}"
							style="width:200px;" />
						<t:outputText value="Domicilio Elettronico: "/>
						<t:inputText value="#{tableManager.current.domicilioElettronico}"
							style="width:400px;" id="domicilioElettronico"/>
					</t:panelGrid>
					
					<t:panelGrid columns="1" cellspacing="5">
					
					<cv:requiredFieldValidator
							componentToValidate="email" highlight="true"
							display="dynamic"
							errorMessage="L'indirizzo e-mail è obbligatorio"
							style="error"  />	
							
					<cv:requiredFieldValidator
							componentToValidate="domicilioElettronico" highlight="true"
							display="dynamic"
							errorMessage="Il domicilio elettronico è obbligatorio"
							style="error"  />			
							
					</t:panelGrid>

				</div>

				<h3>
					<t:outputText value="Gruppo accreditamento" />
				</h3>
				<div class="Box" style="width: 75%;">
					<t:panelGrid columns="4" cellspacing="5">
						<t:outputText value="Ruolo: " />
						<t:inputText value="#{tableManager.current.ruolo}"
							style="width:200px;" />
						<t:outputText value="Territorio: " />
						<t:inputText value="#{tableManager.current.territorio}"
							style="width:200px;" />
					</t:panelGrid>
				</div>

				<h3>
					<t:outputText value="Registrazione" />
				</h3>
				<div class="Box" style="width: 75%;">
					<t:panelGrid columns="5" cellspacing="5">
					
						<t:outputText value="Data Registrazione: " />
						
						<f:subview id="dataReg1" rendered="#{!tableManager.insert}">
							<t:outputText value="#{tableManager.current.dataRegistrazione}">
								<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" timeZone="GMT+1" type="date" />
							</t:outputText>
						</f:subview>
						
						<f:subview id="dataReg2" rendered="#{tableManager.insert}">
							<t:outputText value="#{tableManager.tableAction.newDataRegistrazione}">
								<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" timeZone="GMT+1" type="date" />
							</t:outputText>
						</f:subview>

						<f:subview id="pwdbox11" rendered="#{!tableManager.insert}">
						    <f:subview id="pwd1" rendered="#{tableManager.tableAction.showPassword}">
								<t:outputText value="Password: " />
							</f:subview>
						</f:subview>
						
						<f:subview id="pwdbox12" rendered="#{!tableManager.insert}">
						    <f:subview id="pwd2" rendered="#{tableManager.tableAction.showPassword}">
								<t:outputText value="#{tableManager.current.password}" style="font-weight: bold;" />
							</f:subview>
						</f:subview>

						<f:subview id="pwdbox21" rendered="#{tableManager.insert}">
						    <f:subview id="pwd3" rendered="#{tableManager.tableAction.showPassword}">
								<t:outputText value="Password: " />
							</f:subview>
						</f:subview>
						<f:subview id="pwdbox22" rendered="#{tableManager.insert}">
						    <f:subview id="pwd4" rendered="#{tableManager.tableAction.showPassword}">
								<t:outputText value="#{tableManager.tableAction.newPassword}" style="font-weight: bold;" />
							</f:subview>
						</f:subview>
						
						<f:subview id="newpwdButton" rendered="#{!tableManager.insert}">
							<t:commandButton value="Genera nuova password"
								action="#{tableManager.tableAction.generateNewPassword}" />
						</f:subview>
						
						<f:subview id="emptyfield1" rendered="#{tableManager.insert}">
							<t:outputText value="" />
						</f:subview>
						
						<t:outputText value="Data Attivazione: " />
						<f:subview id="dataAtt1" rendered="#{!tableManager.insert}">
							<t:outputText value="#{tableManager.current.dataAttivazione}">
								<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" timeZone="GMT+1" type="date" />
							</t:outputText>
						</f:subview>
						
						<f:subview id="dataAtt2" rendered="#{tableManager.insert}">
							<t:outputText value="#{tableManager.tableAction.newDataAttivazione}">
								<f:convertDateTime pattern="dd/MM/yyyy HH:mm:ss" timeZone="GMT+1" type="date" />
							</t:outputText>
						</f:subview>

						<f:subview id="pinBox11" rendered="#{!tableManager.insert}">
							<f:subview id="pin1" rendered="#{tableManager.tableAction.showPin}">
								<t:outputText value="PIN: " />
							</f:subview>
						</f:subview>
						<f:subview id="pinBox12" rendered="#{!tableManager.insert}">
							<f:subview id="pin2" rendered="#{tableManager.tableAction.showPin}">
								<t:outputText value="#{tableManager.current.pin}" style="font-weight: bold;" />
							</f:subview>
						</f:subview>
						
						<f:subview id="pinBox21" rendered="#{tableManager.insert}">
							<f:subview id="pin3" rendered="#{tableManager.tableAction.showPin}">
								<t:outputText value="PIN: " />
							</f:subview>
							
						</f:subview>
						<f:subview id="pinBox22" rendered="#{tableManager.insert}">
							<f:subview id="pin4" rendered="#{tableManager.tableAction.showPin}">
								<t:outputText value="#{tableManager.tableAction.newPin}" style="font-weight: bold;" />
							</f:subview>
						</f:subview>
						
						<f:subview id="newpinButton" rendered="#{!tableManager.insert}">
							<t:commandButton value="Genera nuovo PIN"
								action="#{tableManager.tableAction.generateNewPin}" />
						</f:subview>

						<f:subview id="emptyfield2" rendered="#{tableManager.insert}">
							<t:outputText value="" />
						</f:subview>

						<t:outputText value="Stato: " />
						<t:selectOneMenu value="#{tableManager.current.status}">
							<f:selectItem itemLabel="ATTIVO" itemValue="ATTIVO" />
							<f:selectItem itemLabel="INATTIVO" itemValue="INATTIVO" />
						</t:selectOneMenu>
						<t:outputText value="" />
						<t:outputText value="" />
						<t:outputText value="" />
						<t:outputText value="" />
						
					</t:panelGrid>
					
				</div>

				<br />
				<div align="center" style="width: 75%;">
					<t:commandButton value="#{tableManager.buttonLabel}"
						action="#{tableManager.buttonAction}" onclick="return validate();" />
					<t:commandButton value="Annulla" action="cancel" />
				</div>
				<cv:scriptGenerator form="form1" popup="false" />
			</h:form>
		</f:view>
	</body>
</html>
