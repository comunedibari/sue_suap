Per il deploy � sufficiente utilizzare il file WAR rilasciato.
E' consigliabile dispiegare l'applicazione in un contesto web accessibile via protocollo sicuro HTTPS. L'applicazione viene gi� configurata per richiedere username e password all'accesso, utilizzando il supporto al login offerto dal container (Tomcat), mediante alcune direttive di "security-constraint" presenti nel web.xml.
In particolare sono richieste le credenziali di un'utenza avente come ruolo "manager".

L'applicazione IdpPeopleAdmin necessita di accedere ai due database "sirac" e "capeople". E' quindi necessario che in Tomcat sia stata definita la risorsa globale di nome people/WSAuthDB (normalmente presente in caso di installazione People con CA-People dimostrativa) la quale punti al database (Oracle, nel vostro caso) corretto con tutti i parametri del caso.
Analogamente, in Tomcat deve essere definita la risorsa globale people/SIRAC per l'aggancio al relativo schema di database.
L'applicazione IdpPeopleAdmin utilizzer� tali risorse per l'accesso ai database e utilizzer� per le query alcune mappe SQL, definite in modo diverso per i due database supportati, MySQL e Oracle.
 
Altri file di configurazione su cui intervenire:

==========================================================
- WEB-INF/classes/config/capeople/CAPeopleSqlMapConfig.xml
==========================================================

In questo file � necessario commentare/decommentare la sezione relativa alla mappa SQL da utilizzare seondo che si utilizzi MySQL o ORACLE.

=======================================================
- WEB-INF/classes/config/capeople/SiracSqlMapConfig.xml
=======================================================

In questo file � necessario commentare/decommentare la sezione relativa alla mappa SQL da utilizzare seondo che si utilizzi MySQL o ORACLE.

================================================
- WEB-INF/classes/properties/userdata.properties
================================================

In questo file sono definiti i parametri di configurazione utilizzati durante il funzionamento dell'applicazione, ad esempio per inviare le comunicazioni via e-mail agli utenti, in fase di creazione/modifica del relativo account. Di seguito l'elenco dei parametri e il loro significato:

- users.showPassword= (true/false): indica se � abilitata la visualizzazione della password durante le operazioni di creazione/modifica utente
- users.showPin (true/false): indica se � abilitata la visualizzazione del PIN durante le operazioni di creazione/modifica utente
- users.sendNewPwdMail (true/false): indica se deve essere inviata una email all'utente per comunicare l'assegnazione di una nuova password
- users.sendNewPinMail (true/false): indica se deve essere inviata una email all'utente per comunicare l'assegnazione di un nuovo PIN
- users.sendNewUserMail (true/false): indica se deve essere inviata una email all'utente per comunicare la creazione del nuovo account
- users.CAKeystoreFile: � il percorso (relativo alla directory WEB-INF della webapp IdpPeopleAdmin) del keystore contenente la chiave dell'authority che firma i certificati che saranno poi utilizzabili durante le operazioni di firma remota
- users.CAKeystoreAlias: indica l'identificativo della chiave presente nel keystore di cui sopra
- users.CAKeystorePassword: indica la password da utilizzare per accedere al keystore di cui sopra
- users.CAKeystoreType: indica il tipo (PKCS12 o JKS) del keystore di cui sopra

- smtp.server.host: indica l'hostname del server utilizzato per l'invio email
- smtp.server.port: indica la porta del server di cui sopra
- smtp.server.useAuth (true/false): indica se occorre effettuare l'autenticazione presso il mail server prima dell'invio delle email
- smtp.server.useSSL (true/false): indica se deve essere utilizzato il protocollo SMTP over SSL per l'invio della posta
- smtp.server.useTLS (true/false): indica se deve essere utilizzato il protocollo SMTP over TLS per l'invio della posta
- smtp.server.username: lo username da utilizzare per l'autenticazione al mail server (se richiesto)
- smtp.server.password: la password da utilizzare per l'autenticazione al mail server (se richiesto)
- mail.session.debug (true/false): parametro di servizio per alzare il livello di logging durante la fase di invio mail

- mail.originator: l'indirizzo email utilizzato come mittente
- mail.newPwd.subject: il subject indicato per le comunicazioni di assegnazione di una nuova password
- mail.newPwd.message: il testo del messaggio per le comunicazioni di assegnazione di una nuova password
- mail.newPin.subject: il subject indicato per le comunicazioni di assegnazione di un nuovo PIN
- mail.newPin.message: il testo del messaggio per le comunicazioni di assegnazione di un nuovo PIN
- mail.newUser.subject: il subject indicato per le comunicazioni di attivazione nuova utenza
- mail.newUser.message: il test del messaggio per le comunicazioni di attivazione nuova utenza


L'applicazione si accede mediante connessione all'URL http://<hostname>:<port>/IdpPeopleAdmin dove <hostname> e <port> sono rispettivamente il nome del server su cui gira il container J2EE (es. Tomcat) che ospita la web application IdpPeopleAdmine e la porta utilizzata. 
La prima operazione da effettuare � impostare i due parametri "idcomune" e "suffisso username" nella sezione di configurazione. Entrambi devono coincidere con quelli utilizzati nella propria installazione SiRAC.
