//
// Questo file  stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.5-2 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr persa durante la ricompilazione dello schema di origine. 
// Generato il: 2012.11.27 alle 11:16:28 AM CET 
//


package it.gov.impresainungiorno.schema.suap.pratica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Classe Java per Carica complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Carica">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="codice" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="ACP"/>
 *             &lt;enumeration value="ACR"/>
 *             &lt;enumeration value="ADP"/>
 *             &lt;enumeration value="AF"/>
 *             &lt;enumeration value="AFF"/>
 *             &lt;enumeration value="AMD"/>
 *             &lt;enumeration value="AMG"/>
 *             &lt;enumeration value="AMM"/>
 *             &lt;enumeration value="AMP"/>
 *             &lt;enumeration value="AMS"/>
 *             &lt;enumeration value="APR"/>
 *             &lt;enumeration value="ART"/>
 *             &lt;enumeration value="ASO"/>
 *             &lt;enumeration value="ATI"/>
 *             &lt;enumeration value="AUN"/>
 *             &lt;enumeration value="AUP"/>
 *             &lt;enumeration value="CA"/>
 *             &lt;enumeration value="CAD"/>
 *             &lt;enumeration value="CAG"/>
 *             &lt;enumeration value="CC"/>
 *             &lt;enumeration value="CCG"/>
 *             &lt;enumeration value="CD"/>
 *             &lt;enumeration value="CDG"/>
 *             &lt;enumeration value="CDP"/>
 *             &lt;enumeration value="CDS"/>
 *             &lt;enumeration value="CDT"/>
 *             &lt;enumeration value="CE"/>
 *             &lt;enumeration value="CEP"/>
 *             &lt;enumeration value="CES"/>
 *             &lt;enumeration value="CF"/>
 *             &lt;enumeration value="CG"/>
 *             &lt;enumeration value="CGE"/>
 *             &lt;enumeration value="CGS"/>
 *             &lt;enumeration value="CI"/>
 *             &lt;enumeration value="CLD"/>
 *             &lt;enumeration value="CLR"/>
 *             &lt;enumeration value="CLT"/>
 *             &lt;enumeration value="CM"/>
 *             &lt;enumeration value="CMS"/>
 *             &lt;enumeration value="CNG"/>
 *             &lt;enumeration value="COA"/>
 *             &lt;enumeration value="COD"/>
 *             &lt;enumeration value="COE"/>
 *             &lt;enumeration value="COF"/>
 *             &lt;enumeration value="COG"/>
 *             &lt;enumeration value="COL"/>
 *             &lt;enumeration value="COM"/>
 *             &lt;enumeration value="CON"/>
 *             &lt;enumeration value="COO"/>
 *             &lt;enumeration value="COP"/>
 *             &lt;enumeration value="COS"/>
 *             &lt;enumeration value="COT"/>
 *             &lt;enumeration value="COV"/>
 *             &lt;enumeration value="CPC"/>
 *             &lt;enumeration value="CPR"/>
 *             &lt;enumeration value="CRT"/>
 *             &lt;enumeration value="CS"/>
 *             &lt;enumeration value="CSA"/>
 *             &lt;enumeration value="CSG"/>
 *             &lt;enumeration value="CSS"/>
 *             &lt;enumeration value="CST"/>
 *             &lt;enumeration value="CT"/>
 *             &lt;enumeration value="CTE"/>
 *             &lt;enumeration value="CU"/>
 *             &lt;enumeration value="CUE"/>
 *             &lt;enumeration value="CUF"/>
 *             &lt;enumeration value="CUM"/>
 *             &lt;enumeration value="CUV"/>
 *             &lt;enumeration value="CVE"/>
 *             &lt;enumeration value="CZ"/>
 *             &lt;enumeration value="C01"/>
 *             &lt;enumeration value="C02"/>
 *             &lt;enumeration value="C03"/>
 *             &lt;enumeration value="C07"/>
 *             &lt;enumeration value="C08"/>
 *             &lt;enumeration value="C09"/>
 *             &lt;enumeration value="C11"/>
 *             &lt;enumeration value="C12"/>
 *             &lt;enumeration value="C13"/>
 *             &lt;enumeration value="C14"/>
 *             &lt;enumeration value="C15"/>
 *             &lt;enumeration value="C16"/>
 *             &lt;enumeration value="C17"/>
 *             &lt;enumeration value="C18"/>
 *             &lt;enumeration value="C19"/>
 *             &lt;enumeration value="C20"/>
 *             &lt;enumeration value="C21"/>
 *             &lt;enumeration value="C22"/>
 *             &lt;enumeration value="C23"/>
 *             &lt;enumeration value="C26"/>
 *             &lt;enumeration value="C28"/>
 *             &lt;enumeration value="C29"/>
 *             &lt;enumeration value="C32"/>
 *             &lt;enumeration value="C34"/>
 *             &lt;enumeration value="DA"/>
 *             &lt;enumeration value="DAM"/>
 *             &lt;enumeration value="DC"/>
 *             &lt;enumeration value="DCO"/>
 *             &lt;enumeration value="DCP"/>
 *             &lt;enumeration value="DE"/>
 *             &lt;enumeration value="DES"/>
 *             &lt;enumeration value="DF"/>
 *             &lt;enumeration value="DFI"/>
 *             &lt;enumeration value="DG"/>
 *             &lt;enumeration value="DI"/>
 *             &lt;enumeration value="DIA"/>
 *             &lt;enumeration value="DIM"/>
 *             &lt;enumeration value="DIP"/>
 *             &lt;enumeration value="DIR"/>
 *             &lt;enumeration value="DLF"/>
 *             &lt;enumeration value="DL2"/>
 *             &lt;enumeration value="DMK"/>
 *             &lt;enumeration value="DNC"/>
 *             &lt;enumeration value="DP"/>
 *             &lt;enumeration value="DR"/>
 *             &lt;enumeration value="DRE"/>
 *             &lt;enumeration value="DRG"/>
 *             &lt;enumeration value="DRR"/>
 *             &lt;enumeration value="DS"/>
 *             &lt;enumeration value="DSA"/>
 *             &lt;enumeration value="DT"/>
 *             &lt;enumeration value="DTD"/>
 *             &lt;enumeration value="DZ"/>
 *             &lt;enumeration value="ELE"/>
 *             &lt;enumeration value="EXS"/>
 *             &lt;enumeration value="FAT"/>
 *             &lt;enumeration value="FC"/>
 *             &lt;enumeration value="FU"/>
 *             &lt;enumeration value="GE"/>
 *             &lt;enumeration value="GER"/>
 *             &lt;enumeration value="GID"/>
 *             &lt;enumeration value="GOV"/>
 *             &lt;enumeration value="GSG"/>
 *             &lt;enumeration value="IG"/>
 *             &lt;enumeration value="IMN"/>
 *             &lt;enumeration value="IMR"/>
 *             &lt;enumeration value="IN"/>
 *             &lt;enumeration value="IS"/>
 *             &lt;enumeration value="LER"/>
 *             &lt;enumeration value="LGR"/>
 *             &lt;enumeration value="LGT"/>
 *             &lt;enumeration value="LI"/>
 *             &lt;enumeration value="LIG"/>
 *             &lt;enumeration value="LRF"/>
 *             &lt;enumeration value="LRT"/>
 *             &lt;enumeration value="LR2"/>
 *             &lt;enumeration value="LSA"/>
 *             &lt;enumeration value="LUL"/>
 *             &lt;enumeration value="MA"/>
 *             &lt;enumeration value="MCA"/>
 *             &lt;enumeration value="MCD"/>
 *             &lt;enumeration value="MCE"/>
 *             &lt;enumeration value="MCG"/>
 *             &lt;enumeration value="MCS"/>
 *             &lt;enumeration value="MCT"/>
 *             &lt;enumeration value="MDC"/>
 *             &lt;enumeration value="MED"/>
 *             &lt;enumeration value="MGD"/>
 *             &lt;enumeration value="MGE"/>
 *             &lt;enumeration value="MGS"/>
 *             &lt;enumeration value="MI"/>
 *             &lt;enumeration value="MP"/>
 *             &lt;enumeration value="MPP"/>
 *             &lt;enumeration value="MS"/>
 *             &lt;enumeration value="MSD"/>
 *             &lt;enumeration value="NE"/>
 *             &lt;enumeration value="OAS"/>
 *             &lt;enumeration value="OCA"/>
 *             &lt;enumeration value="OCO"/>
 *             &lt;enumeration value="ODI"/>
 *             &lt;enumeration value="OES"/>
 *             &lt;enumeration value="OPA"/>
 *             &lt;enumeration value="OPC"/>
 *             &lt;enumeration value="OPN"/>
 *             &lt;enumeration value="PA"/>
 *             &lt;enumeration value="PAD"/>
 *             &lt;enumeration value="PAF"/>
 *             &lt;enumeration value="PB"/>
 *             &lt;enumeration value="PC"/>
 *             &lt;enumeration value="PCA"/>
 *             &lt;enumeration value="PCD"/>
 *             &lt;enumeration value="PCE"/>
 *             &lt;enumeration value="PCG"/>
 *             &lt;enumeration value="PCM"/>
 *             &lt;enumeration value="PCO"/>
 *             &lt;enumeration value="PCP"/>
 *             &lt;enumeration value="PCS"/>
 *             &lt;enumeration value="PCT"/>
 *             &lt;enumeration value="PCV"/>
 *             &lt;enumeration value="PDC"/>
 *             &lt;enumeration value="PDI"/>
 *             &lt;enumeration value="PDS"/>
 *             &lt;enumeration value="PE"/>
 *             &lt;enumeration value="PED"/>
 *             &lt;enumeration value="PEO"/>
 *             &lt;enumeration value="PEP"/>
 *             &lt;enumeration value="PES"/>
 *             &lt;enumeration value="PF"/>
 *             &lt;enumeration value="PG"/>
 *             &lt;enumeration value="PGC"/>
 *             &lt;enumeration value="PGD"/>
 *             &lt;enumeration value="PGE"/>
 *             &lt;enumeration value="PGF"/>
 *             &lt;enumeration value="PGS"/>
 *             &lt;enumeration value="PGT"/>
 *             &lt;enumeration value="PL"/>
 *             &lt;enumeration value="PM"/>
 *             &lt;enumeration value="PN"/>
 *             &lt;enumeration value="PP"/>
 *             &lt;enumeration value="PPP"/>
 *             &lt;enumeration value="PPR"/>
 *             &lt;enumeration value="PR"/>
 *             &lt;enumeration value="PRA"/>
 *             &lt;enumeration value="PRC"/>
 *             &lt;enumeration value="PRE"/>
 *             &lt;enumeration value="PRO"/>
 *             &lt;enumeration value="PRP"/>
 *             &lt;enumeration value="PRQ"/>
 *             &lt;enumeration value="PRS"/>
 *             &lt;enumeration value="PRT"/>
 *             &lt;enumeration value="PS"/>
 *             &lt;enumeration value="PSD"/>
 *             &lt;enumeration value="PSE"/>
 *             &lt;enumeration value="PSS"/>
 *             &lt;enumeration value="PT"/>
 *             &lt;enumeration value="PTE"/>
 *             &lt;enumeration value="PTR"/>
 *             &lt;enumeration value="RA"/>
 *             &lt;enumeration value="RAF"/>
 *             &lt;enumeration value="RAP"/>
 *             &lt;enumeration value="RAS"/>
 *             &lt;enumeration value="RAZ"/>
 *             &lt;enumeration value="RC"/>
 *             &lt;enumeration value="RCD"/>
 *             &lt;enumeration value="RCF"/>
 *             &lt;enumeration value="RCO"/>
 *             &lt;enumeration value="RCS"/>
 *             &lt;enumeration value="RDF"/>
 *             &lt;enumeration value="RE"/>
 *             &lt;enumeration value="RES"/>
 *             &lt;enumeration value="RFM"/>
 *             &lt;enumeration value="RG"/>
 *             &lt;enumeration value="RIN"/>
 *             &lt;enumeration value="RIT"/>
 *             &lt;enumeration value="RPS"/>
 *             &lt;enumeration value="RSS"/>
 *             &lt;enumeration value="RSU"/>
 *             &lt;enumeration value="RTC"/>
 *             &lt;enumeration value="RV"/>
 *             &lt;enumeration value="SA"/>
 *             &lt;enumeration value="SAB"/>
 *             &lt;enumeration value="SAO"/>
 *             &lt;enumeration value="SAP"/>
 *             &lt;enumeration value="SCA"/>
 *             &lt;enumeration value="SCR"/>
 *             &lt;enumeration value="SD"/>
 *             &lt;enumeration value="SDR"/>
 *             &lt;enumeration value="SEP"/>
 *             &lt;enumeration value="SFC"/>
 *             &lt;enumeration value="SFI"/>
 *             &lt;enumeration value="SG"/>
 *             &lt;enumeration value="SGE"/>
 *             &lt;enumeration value="SIE"/>
 *             &lt;enumeration value="SIP"/>
 *             &lt;enumeration value="SIS"/>
 *             &lt;enumeration value="SLA"/>
 *             &lt;enumeration value="SLR"/>
 *             &lt;enumeration value="SNA"/>
 *             &lt;enumeration value="SNC"/>
 *             &lt;enumeration value="SNP"/>
 *             &lt;enumeration value="SNQ"/>
 *             &lt;enumeration value="SOA"/>
 *             &lt;enumeration value="SOC"/>
 *             &lt;enumeration value="SOF"/>
 *             &lt;enumeration value="SOL"/>
 *             &lt;enumeration value="SON"/>
 *             &lt;enumeration value="SOP"/>
 *             &lt;enumeration value="SOR"/>
 *             &lt;enumeration value="SOS"/>
 *             &lt;enumeration value="SOT"/>
 *             &lt;enumeration value="SOU"/>
 *             &lt;enumeration value="SPR"/>
 *             &lt;enumeration value="SQ"/>
 *             &lt;enumeration value="SQU"/>
 *             &lt;enumeration value="STE"/>
 *             &lt;enumeration value="SVR"/>
 *             &lt;enumeration value="TES"/>
 *             &lt;enumeration value="TI"/>
 *             &lt;enumeration value="TIT"/>
 *             &lt;enumeration value="TI2"/>
 *             &lt;enumeration value="TMI"/>
 *             &lt;enumeration value="TPS"/>
 *             &lt;enumeration value="TTE"/>
 *             &lt;enumeration value="TU"/>
 *             &lt;enumeration value="UM1"/>
 *             &lt;enumeration value="UM2"/>
 *             &lt;enumeration value="US"/>
 *             &lt;enumeration value="VAD"/>
 *             &lt;enumeration value="VCA"/>
 *             &lt;enumeration value="VCD"/>
 *             &lt;enumeration value="VCG"/>
 *             &lt;enumeration value="VCO"/>
 *             &lt;enumeration value="VDA"/>
 *             &lt;enumeration value="VDC"/>
 *             &lt;enumeration value="VDE"/>
 *             &lt;enumeration value="VDF"/>
 *             &lt;enumeration value="VDG"/>
 *             &lt;enumeration value="VDI"/>
 *             &lt;enumeration value="VDS"/>
 *             &lt;enumeration value="VDT"/>
 *             &lt;enumeration value="VDZ"/>
 *             &lt;enumeration value="VED"/>
 *             &lt;enumeration value="VGD"/>
 *             &lt;enumeration value="VGE"/>
 *             &lt;enumeration value="VGO"/>
 *             &lt;enumeration value="VIC"/>
 *             &lt;enumeration value="VID"/>
 *             &lt;enumeration value="VIV"/>
 *             &lt;enumeration value="VPA"/>
 *             &lt;enumeration value="VPC"/>
 *             &lt;enumeration value="VPE"/>
 *             &lt;enumeration value="VPP"/>
 *             &lt;enumeration value="VSD"/>
 *             &lt;enumeration value="VSG"/>
 *             &lt;enumeration value="992"/>
 *             &lt;enumeration value="996"/>
 *             &lt;enumeration value="997"/>
 *             &lt;enumeration value="998"/>
 *             &lt;enumeration value="999"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Carica", propOrder = {
    "value"
})
public class Carica {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "codice", required = true)
    protected String codice;

    /**
     * Recupera il valore della propriet value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Imposta il valore della propriet value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Recupera il valore della propriet codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della propriet codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

}
