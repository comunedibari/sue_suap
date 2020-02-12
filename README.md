# SUAPSUE AREA VASTA
## INTRODUZIONE
Il seguente readme ha l'obiettivo di descrivere brevemente i moduli che compongono l'applicazione SUAPSUE per Area Vasta e descrivere i prerequisiti e gli step necessari alla costruzione dell'artefatto
 
## INFORMAZIONI GENERALI
Per area vasta SUAPSUE si compone dei seguenti moduli:

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

- **registropubblico**: 



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


**Installazione di un ambiente locale di sviluppo **

Fare riferimento al documento /sue_suap/documentazione/documenti_tecnici/installazione_ambiente_sviluppo_locale.pdf