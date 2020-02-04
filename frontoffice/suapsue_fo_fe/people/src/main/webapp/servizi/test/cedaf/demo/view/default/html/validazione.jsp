<%@ taglib uri="/WEB-INF/people.tld" prefix="ppl" %>
<jsp:useBean id="pplProcess" scope="session" type="it.people.process.AbstractPplProcess" />

<html:xhtml/>
<h1>Pagina per effettuare il test della validazione</h1>
<br />

Nel caso torni indietro dallo uno step, vengono tolti gli &quot;errors.required&quot;,
iterando su di essi. Il problema &egrave; che se ci sono pi&ugrave; errori di
required e viene fatto un loop su di essi per toglierli, da un'errore.<br />
<ppl:errors/>

<ppl:fieldLabel key="label.validazione.valore1" fieldName="data.valore1" />
<ppl:textField name="pplProcess" property="data.valore1"/>
<br />
<ppl:fieldLabel key="label.validazione.valore2" fieldName="data.valore2" />
<ppl:textField name="pplProcess" property="data.valore2"/>
<br />
<ppl:fieldLabel key="label.validazione.valore3" fieldName="data.valore3" />
<ppl:textField name="pplProcess" property="data.valore3"/>
<br />
<ppl:fieldLabel key="label.validazione.valore4" fieldName="data.valore4" />
<ppl:textField name="pplProcess" property="data.valore4"/>
<br />
<ppl:fieldLabel key="label.validazione.valore5" fieldName="data.valore5" />
<ppl:textField name="pplProcess" property="data.valore5"/>
<br />
<ppl:fieldLabel key="label.validazione.valore6" fieldName="data.valore6" />
<ppl:textField name="pplProcess" property="data.valore6"/>
<br />
<ppl:fieldLabel key="label.validazione.valore7" fieldName="data.valore7" />
<ppl:textField name="pplProcess" property="data.valore7"/>
<br />
<ppl:fieldLabel key="label.validazione.valore8" fieldName="data.valore8" />
<ppl:textField name="pplProcess" property="data.valore8"/>
<br />
<ppl:fieldLabel key="label.validazione.valore9" fieldName="data.valore9" />
<ppl:textField name="pplProcess" property="data.valore9"/>
<br />
<ppl:fieldLabel key="label.validazione.valore10" fieldName="data.valore10" />
<ppl:textField name="pplProcess" property="data.valore10"/>
<br />
