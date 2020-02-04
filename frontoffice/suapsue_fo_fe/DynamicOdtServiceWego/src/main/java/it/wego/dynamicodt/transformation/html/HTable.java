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
package it.wego.dynamicodt.transformation.html;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HTable {

    private static Log log = LogFactory.getLog(HTable.class);
    private List htrs;
    private int numberColumnsMax;
    private boolean repeteadColumns;
    private List relativeColumnsWidth; //lista di Integer che hanno la stessa logica
    //di style:rel-column-width="...*" del ODT
    //che è la seguente:
    //ogni colonna è larga quanto esprime il rapporto
    //della suo valore e la somma di tutti i valori.
    //Es.
    //Se si hanno tre colonne e valgono
    //1,3,4
    //la prima colonna è larga 1/8 della larghezza di tutta la colonna,
    //la seconda colonna è larga 3/8.
    //la terza colonna è larga 4/8.
    private String name;
    private boolean inHead;    //se true allora la tabella e' una tabella "di root" nel html
    //Una tabella e' di root quando non e' contenuta in nessuna altra tabella
    private boolean isFakeTable = false;
    private boolean isParagrafo = false;
    private String paragrafoBackGround = "#e6e6e6";

    public boolean isFakeTable() {
        return isFakeTable;
    }

    public void setFakeTable(boolean isFakeTable) {
        this.isFakeTable = isFakeTable;
    }

    public HTable() {
        htrs = new ArrayList();
        relativeColumnsWidth = new ArrayList();
    }

    public List getHTrs() {
        return htrs;
    }

    public int getNumberColumnsMax() {
        return this.numberColumnsMax;
    }

    public String getName() {
        return this.name;
    }

    public boolean getInHead() {
        return this.inHead;
    }

    public List getRelativeColumnsWidth() {
        return this.relativeColumnsWidth;
    }

    public boolean getRepeteadColumns() {
        return this.repeteadColumns;
    }

    public void setNumberColumnsMax(int numberColumnsMax) {
        this.numberColumnsMax = numberColumnsMax;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInHead(boolean inHead) {
        this.inHead = inHead;
    }

    public void setRepeteadColumns(boolean repeteadColumns) {
        this.repeteadColumns = repeteadColumns;
    }

    public void clear() {
        this.htrs.clear();
        this.numberColumnsMax = 0;
    }

    public void adjust() {

        {
            //creo le nuove colonne derivanti dal <input type=...>
            int htdsSize;
            HTr htr;
            HTd htd;
            List htds;
            HFragment hfragment;

            int htrsSize = htrs.size();
            for (int i = 0; i < htrsSize; i++) {
                htr = (HTr) htrs.get(i);
                htds = htr.getHTds();
                htdsSize = htds.size();
                for (int j = 0; j < htdsSize; j++) {
                    htd = (HTd) htds.get(j);
                    hfragment = htd.getHFragment();
                    if (hfragment.getType() == HFragment.TYPE_INPUT_AND_STRING
                            || hfragment.getType() == HFragment.TYPE_STRING_AND_INPUT) {
                        HTd htdNew = new HTd();
                        HFragment hfragmentNew = new HFragment();
                        hfragmentNew.setType(HFragment.TYPE_STRING_ALONE);
                        hfragmentNew.setStringValue(hfragment.getStringValue());
                        htdNew.setHFragment(hfragmentNew);

                        if (hfragment.getType() == HFragment.TYPE_INPUT_AND_STRING) {
                            //aggiungo nuova colonna a destra del TYPE_INPUT_ALONE
                            hfragment.setType(HFragment.TYPE_INPUT_ALONE);
                            hfragment.setStringValue("");
                            if (j == (htdsSize - 1)) {
                                htds.add(htdNew);
                            } else {
                                htds.add(j + 1, htdNew);
                            }
                        } else {
                            //aggiungo nuova colonna a sinistra del TYPE_INPUT_ALONE
                            hfragment.setType(HFragment.TYPE_INPUT_ALONE);
                            hfragment.setStringValue("");
                            htds.add(j, htdNew);
                        }
                        htdsSize++;
                        j++;
                    }
                }
            }
            //qui dovrebbero esistere solo 3 possibilità di tipo frammento:
            //TYPE_STRING_ALONE, TYPE_INPUT_ALONE, TYPE_TABLES
        }

        {
            //setto il numero di colonne al numero di colonne della linea che ha piu' colonne
            int numberColumnsMax = 0;
            int htdsSize;
            HTr htr;
            List htds;

            int htrsSize = htrs.size();
            for (int i = 0; i < htrsSize; i++) {
                htr = (HTr) htrs.get(i);
                htds = htr.getHTds();
                htdsSize = htds.size();

                //aggiorno htdsSize con i valori del colspan nel caso di colspan maggiore di 1
                HTd htd;
                int colspan;
                for (int j = 0; j < htds.size(); j++) {
                    htd = (HTd) htds.get(j);
                    colspan = htd.getColspan();
                    if (colspan > 1) {
                        htdsSize += (colspan - 1);
                    }
                }

                if (htdsSize > numberColumnsMax) {
                    numberColumnsMax = htdsSize;
                }
            }
            this.numberColumnsMax = numberColumnsMax;
        }

        //System.out.println ("htable.name(" + this.getName() + ")" +
        //                    "numberColumnsMax(" + numberColumnsMax + ")");

        {
            //setto il flag di colonne ripetute ("colonne ripetute"="colonne dal width sempre uguale")
            //e se non sono ripetute setto le dimensioni relative della colonne.
            //Le colonne sono ripetute se non hanno neanche un TYPE_INPUT_ALONE
            //Per settare il relative width analizzo solamente la prima riga
            //e dopo effettuo un controllo che tutte le altre linee
            //abbiano la stessa composizione di colonne TYPE_INPUT_ALONE;
            //Nel caso una o piu' linee abbia una composizione diversa di TYPE_INPUT_ALONE
            //(es. la prima linea contiene un TYPE_INPUT_ALONE in seconda posizione
            // mentre la quarta linea contiene un TYPE_STRING_ALONE in seconda posizione)
            //si ignora il settaggio delle colonne ripetute e dei width relativi
            //basati sull'analisi della prima riga e si setta a vero il flag di colonne ripetute.
            //Quando il flag di colonne ripetute e' uguale a vero il relative width delle singole colonne
            //non è significativo.
            int htdsSize;
            HTr htr;
            List htds;
            HTd htd;
            HFragment hfragment;
            int counterTypeInput;
            int colspan;

            this.repeteadColumns = true;
            relativeColumnsWidth.clear();

            if (htrs.size() > 0) {
                htr = (HTr) htrs.get(0);
                htds = htr.getHTds();
                counterTypeInput = 0;
                for (int j = 0; j < htds.size(); j++) {
                    htd = (HTd) htds.get(j);
                    hfragment = htd.getHFragment();
                    if (hfragment.getType() == HFragment.TYPE_INPUT_ALONE) {
                        counterTypeInput++;
                    }
                }
                if (counterTypeInput == 0
                        || counterTypeInput == htds.size()) {
                    this.repeteadColumns = true;
                } else {
                    //esiste almeno una colonna di tipo TYPE_INPUT_ALONE
                    //per determinare il relative width applico la seguente regola:
                    //il relative width totale di tutte le colonne deve essere uguale a 20000,
                    //ogni colonna di tipo TYPE_INPUT_ALONE ha un relative width uguale a 1000,
                    //ogni colonna non di tipo TYPE_INPUT_ALONE ha un relative width settato
                    //in modo che la somma totale faccia 20000.
                    this.repeteadColumns = false;
                    int counterTypeOther = htds.size() - counterTypeInput;
                    int relativeWidthInput = 1000;
                    int relativeWidthOther = ((20000 - (relativeWidthInput * counterTypeInput))) / counterTypeOther;
                    for (int j = 0; j < htds.size(); j++) {
                        htd = (HTd) htds.get(j);
                        hfragment = htd.getHFragment();
                        if (hfragment.getType() == HFragment.TYPE_INPUT_ALONE) {
                            colspan = htd.getColspan();
                            if (colspan > 1) {
                                //se il colspan e' maggiore di 1 per le <td> di tipo radio/checkbox
                                //devo creare tante relativeColumnsWith quante le colonne definite nel colspan
                                //con un width uguale ad un singolo radio/chechbox (superando quindi la "soglia" dei 20000)
                                int relativeWidthOtherColspan = relativeWidthOther / colspan;
                                for (int k = 0; k < colspan; k++) {
                                    relativeColumnsWidth.add(new Integer(relativeWidthInput));
                                }
                            } else {
                                relativeColumnsWidth.add(new Integer(relativeWidthInput));
                            }
                        } else {
                            colspan = htd.getColspan();
                            if (colspan > 1) {
                                //se il colspan e' maggiore di 1 per le <td> di tipo stringa
                                //devo creare tante relativeColumnsWith quante le colonne definite nel colspan
                                int relativeWidthOtherColspan = relativeWidthOther / colspan;
                                for (int k = 0; k < colspan; k++) {
                                    relativeColumnsWidth.add(new Integer(relativeWidthOtherColspan));
                                }
                            } else {
                                relativeColumnsWidth.add(new Integer(relativeWidthOther));
                            }
                        }
                    }
                }

                //verifico se le linee successive hanno la stessa composizione di TYPE_INPUT_ALONE
                if (repeteadColumns) {
                    //se l'analisi della prima linea ha determinato che le colonne sono ripetute
                    //e' ininfluente analizzare anche le linee successive,
                    //in ogni caso si suppone che tutte le colonne sono ripetute.
                } else {
                    for (int i = 1; i < htrs.size(); i++) {
                        htr = (HTr) htrs.get(i);
                        htds = htr.getHTds();
                        counterTypeInput = 0;
                        for (int j = 0; j < htds.size() && (!repeteadColumns); j++) {
                            htd = (HTd) htds.get(j);
                            hfragment = htd.getHFragment();
                            log.debug("htds.size()=" + htds.size() + "," + "colspan=" + htd.getColspan());
                            if (relativeColumnsWidth.size() >= htds.size()) {
                                if (((Integer) relativeColumnsWidth.get(j)).intValue() == 1000) {
                                    if (hfragment.getType() == HFragment.TYPE_INPUT_ALONE) {
                                        //ok
                                    } else {
                                        log.info("HTABLE ADJUST: colonne diversamente composte,"
                                                + " trovato una stringa non incolonnata omogeneamente."
                                                + "j=" + j + "," + relativeColumnsWidth + "," + hfragment);
                                        repeteadColumns = true;
                                        relativeColumnsWidth.clear();
                                    }
                                } else {
                                    if (hfragment.getType() == HFragment.TYPE_INPUT_ALONE) {
                                        log.info("HTABLE ADJUST: colonne diversamente composte,"
                                                + " trovato un controllo input non incolonnato omogeneamente."
                                                + "j=" + j + "," + relativeColumnsWidth + "," + hfragment);
                                        repeteadColumns = true;
                                        relativeColumnsWidth.clear();
                                    } else {
                                        //ok;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String toString() {

        String sep = ",";

        StringBuffer buffer = new StringBuffer("\nHTable");
        buffer.append("{");

        buffer.append("name = ");
        buffer.append(name);
        buffer.append(sep);
        buffer.append("numberColumnsMax = ");
        buffer.append(numberColumnsMax);
        buffer.append(sep);
        buffer.append("repeteadColumns = ");
        buffer.append(repeteadColumns);
        buffer.append(sep);
        buffer.append("relativeColumnsWidth = ");
        buffer.append(relativeColumnsWidth);
        buffer.append(sep);
        buffer.append("inHead = ");
        buffer.append(inHead);
        buffer.append(sep);
        buffer.append("htrs = ");
        for (int i = 0; i < htrs.size(); i++) {
            buffer.append("\n\t" + htrs.get(i));
        }
        buffer.append("}");

        return buffer.toString();
    }

    public boolean isParagrafo() {
        return isParagrafo;
    }

    public void setParagrafo(boolean isParagrafo) {
        this.isParagrafo = isParagrafo;
    }

    public String getParagrafoBackGround() {
        return paragrafoBackGround;
    }

    public void setParagrafoBackGround(String paragrafoBackGround) {
        this.paragrafoBackGround = paragrafoBackGround;
    }
}
