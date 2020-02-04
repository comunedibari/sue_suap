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
