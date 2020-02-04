Nell'invocazione dei servizi di back end l'xml passato viene sottoposto a validazione. 
Qundi nel caso in cui viene sollevata un'eccezione di validazione dell'xml (con xmlbeans) si deve verificare se qualche parametro è errato.

Ad esempio se il codice fiscale impostato in modify-headers non è di 16 carattere, la validazione fallisce.