# Gestione sportelli SUAP SUE
## INTRODUZIONE
Il seguente readme ha l'obiettivo di descrivere brevemente i moduli che compongono l'applicazione SUAPSUE per Area Vasta Metropoli Terra di Bari e descrivere i prerequisiti e gli step necessari alla costruzione dell'artefatto

## Licenza
Il software è rilasciato con licenza aperta ai sensi dell'art. 69 comma 1 del <a href="https://cad.readthedocs.io/" rel="nofollow">Codice dell’Amministrazione Digitale</a> con una licenza GPL 3.0
 
## INFORMAZIONI GENERALI
Per Area Vasta Metropoli Terra di Bari SUAPSUE si compone di un modulo di Front Office e di un modulo di Back Office aventi le finalità di seguito riportate:

**Finalita' del modulo di FRONT OFFICE**

Il modulo di front office è una web application che permette ad un
cittadino di generare un'istanza di SUE o di SUAP e di inviarla all'ente
preposto alla sua gestione.


**Finalita' del modulo di BACK OFFICE**

Il modulo di back office è una web application, ad uso esclusivo dell'ente
SUE o SUAP, riceve le istanze create dal cittadino, e ne permette la
gestione dell'istruttoria.

Il back office permette la gestione simultanea, di più enti e ne consente
una propria personalizzazione.

Permette inoltre l'interfacciamento a sistemi di protocollo esterni e
permette la scelta di protocollare in automatico le operazioni desiderate.


Di seguito i sotto moduli di ci si compongono il modulo di Front Office e il modulo di Back Office

- **frontoffice backend**: In particolare si troveranno i seguenti war per il backend:
    - **BEService**: 
    - **Dbm**: 
    - **idp_people**: 
    - **IdpPeopleAdmin**: 
    - **PeopleConsole**: 
    - **scauth**: 
    - **sirac**: 
    - **ws_accr_121**: 
    - **ws_regauth**:

- **frontoffice frontend**: In particolare si troveranno i seguenti war per il frontend:
	- **AllegatiService**: 
    - **DynamicOdtServiceWego**: 
    - **FEService**: 
    - **firmasemplice**: 
    - **people**: 
    - **PeopleService**: 
    - **SimpleDesk**: 
        
- **backoffice**: 
	- **cross-web**:
	- **cross-ws**:

- **registropubblico**: 


**Installazione di un ambiente locale di sviluppo **

Fare riferimento al documento /sue_suap/documentazione/documenti_tecnici/installazione_ambiente_sviluppo_locale.pdf