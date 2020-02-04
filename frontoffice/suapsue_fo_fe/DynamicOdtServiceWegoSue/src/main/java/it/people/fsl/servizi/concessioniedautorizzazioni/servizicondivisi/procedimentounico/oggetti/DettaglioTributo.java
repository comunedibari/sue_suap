/**
 * Copyright (c) 2011, Regione Emilia-Romagna, Italy
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * 
 * For convenience a plain text copy of the English version
 * of the Licence can be found in the file LICENCE.txt in
 * the top-level directory of this software distribution.
 * 
 * You may obtain a copy of the Licence in any of 22 European
 * Languages at:
 * 
 * http://www.osor.eu/eupl
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
**/
package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti;

import it.people.process.common.entity.AbstractEntity;

public class DettaglioTributo extends AbstractEntity 
{
	private String m_sCodEnte;
	private String m_sCodTributo;
	private String m_sNumDoc;
	private String m_sAnnoDoc;
	private long m_lImpTotDoc;
	private long m_lImpBollo;
	private short m_nNumRate;
	private String m_sDataScadenza;

	public String getCodEnte() { return m_sCodEnte; }
	public void setCodEnte(String sCodEnte) { m_sCodEnte = sCodEnte; }

	public String getCodTributo() { return m_sCodTributo; }
	public void setCodTributo(String sCodTributo) { m_sCodTributo = sCodTributo; }

	public String getNumDoc() { return m_sNumDoc; }
	public void setNumDoc(String sNumDoc) { m_sNumDoc = sNumDoc; }

	public String getAnnoDoc() { return m_sAnnoDoc; }
	public void setAnnoDoc(String sAnnoDoc) { m_sAnnoDoc = sAnnoDoc; }

	public long getImpTotDoc() { return m_lImpTotDoc; }
	public void setImpTotDoc(long lImpTotDoc) { m_lImpTotDoc = lImpTotDoc; }

	public long getImpBollo() { return m_lImpBollo; }
	public void setImpBollo(long lImpBollo) { m_lImpBollo = lImpBollo; }

	public short getNumRate() { return m_nNumRate; }
	public void setNumRate(short nNumRate) { m_nNumRate = nNumRate; }

	public String getDataScadenza() { return m_sDataScadenza; }
	public void setDataScadenza(String sDataScadenza) { m_sDataScadenza = sDataScadenza; }

	public DettaglioTributo()
	{
		m_sCodEnte = "";
		m_sCodTributo = "";
		m_sNumDoc = "";
		m_sAnnoDoc = "";
		m_lImpTotDoc = 0;
		m_lImpBollo = 0;
		m_nNumRate = 0;
		m_sDataScadenza = "";
	}

	public void DummyInit()
	{
		m_sCodEnte = "COBOL";
		m_sCodTributo = "TARSU";
		m_sNumDoc = "1";
		m_sAnnoDoc = "2005";
		m_lImpTotDoc = 10000;
		m_lImpBollo = 129;
		m_nNumRate = 2;
		m_sDataScadenza = "2005-09-25";
	}
}
