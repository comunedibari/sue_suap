/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.selenium;

import com.google.common.collect.Lists;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.util.StringUtils;

/**
 *
 * @author GabrieleM
 */
public class TestUtilities {
    
     public static Boolean CheckDateCrescent(List<String> Listdate, Boolean crescent, TMess tmess) {
        Boolean result = true;
        if (crescent) {
            for (int i = 1; i < Listdate.size(); i++) {
                if (Integer.parseInt(Listdate.get(i - 1).split("/")[2]) > Integer.parseInt(Listdate.get(i).split("/")[2])) {
                    result = false;
                    tmess.warning("anno diverso!");
                    break;
                }
                if ((Integer.parseInt(Listdate.get(i - 1).split("/")[2]) == Integer.parseInt(Listdate.get(i).split("/")[2]))
                        && Integer.parseInt(Listdate.get(i - 1).split("/")[1]) > Integer.parseInt(Listdate.get(i).split("/")[1])) {
                    result = false;
                    tmess.warning("mese diverso!");
                    break;
                }
                if ((Integer.parseInt(Listdate.get(i - 1).split("/")[2]) == Integer.parseInt(Listdate.get(i).split("/")[2]))
                        && (Integer.parseInt(Listdate.get(i - 1).split("/")[1]) == Integer.parseInt(Listdate.get(i).split("/")[1]))
                        && Integer.parseInt(Listdate.get(i - 1).split("/")[0]) > Integer.parseInt(Listdate.get(i).split("/")[0])) {
                    result = false;
                    tmess.warning("giorno diverso!");
                    break;
                }
            }
        } else {
            for (int i = 1; i < Listdate.size(); i++) {
                if (Integer.parseInt(Listdate.get(i - 1).split("/")[2]) < Integer.parseInt(Listdate.get(i).split("/")[2])) {
                    result = false;
                    tmess.warning("anno diverso!");
                    break;
                }
                if ((Integer.parseInt(Listdate.get(i - 1).split("/")[2]) == Integer.parseInt(Listdate.get(i).split("/")[2]))
                        && Integer.parseInt(Listdate.get(i).split("/")[1]) < Integer.parseInt(Listdate.get(i).split("/")[1])) {
                    result = false;
                    tmess.warning("mese diverso!");
                    break;
                }
                if ((Integer.parseInt(Listdate.get(i - 1).split("/")[2]) == Integer.parseInt(Listdate.get(i).split("/")[2]))
                        && (Integer.parseInt(Listdate.get(i - 1).split("/")[1]) == Integer.parseInt(Listdate.get(i).split("/")[1]))
                        && Integer.parseInt(Listdate.get(i - 1).split("/")[0]) < Integer.parseInt(Listdate.get(i).split("/")[0])) {
                    result = false;
                    tmess.warning("giorno diverso!");
                    break;
                }
            }
        }
        return result;
    }
    
    public static Boolean CheckifDateIsCrescent(List<String> dates, Boolean isdecrescent) {
        if (isdecrescent) {
            dates = Lists.reverse(dates);
        }
        List<List<Integer>> datestext = new ArrayList<List<Integer>>();
        for (String date : dates) {
            String os = "";
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 0; i < date.length(); i++) {
                char c = date.charAt(i);
                if (c != '/') {
                    os += c;
                    if (i == date.length() - 1) {
                        numbers.add(Integer.parseInt(os));
                        datestext.add(numbers);
                    }
                } else {
                    numbers.add(Integer.parseInt(os));
                    os = "";
                }

            }
        }
        for (int i = 0; i < datestext.size(); i++) {
            for (int k = i; k < datestext.size(); k++) {
                if (datestext.get(i).get(2).intValue() > datestext.get(k).get(2).intValue()) {
                    return false;
                }
                if ((datestext.get(i).get(2).intValue() == datestext.get(k).get(2).intValue()) && (datestext.get(i).get(1).intValue() > datestext.get(k).get(1).intValue())) {
                    return false;
                }
                if ((datestext.get(i).get(2).intValue() == datestext.get(k).get(2).intValue()) && (datestext.get(i).get(1).intValue() == datestext.get(k).get(1).intValue()) && (datestext.get(i).get(0).intValue() > datestext.get(k).get(0).intValue())) {
                    return false;
                }
            }
        }
        if (isdecrescent) {
            dates = Lists.reverse(dates);
        }
        return true;
    }

    //public static Boolean CheckIfStatusIsCrescent (List<String> status, Boolean isdecrescent){
    //    if (isdecrescent){
    //        status = Lists.reverse(status);
    //    }
    //    List<List<Integer>> statustext = new ArrayList<List<Integer>>();
    // }
    public static List<String> getCol(List<List<String>> table, Integer index) {
        List<String> output = new ArrayList<String>();
        for (List<String> row : table) {
            output.add(row.get(index));
        }
        return output;
    }

 static public boolean isSortedAlphabetically(List<String> list, Boolean crescent) {
        boolean isSorted = true;
        if (crescent) {
            for (int i = 0; i < list.size() - 1; i++) {
                // current String is > than the next one (if there are equal list is still sorted)
                if (list.get(i).compareToIgnoreCase(list.get(i + 1)) > 0) {
                    isSorted = false;
                    break;
                }
            }
        }else{
            for (int i = 0; i < list.size() - 1; i++) {
                // current String is > than the next one (if there are equal list is still sorted)
                if (list.get(i).compareToIgnoreCase(list.get(i + 1)) < 0) {
                    isSorted = false;
                    break;
                }
            }
        }
        return isSorted;
    }
 
    static public boolean allColTheSame(List<List<String>> table, Integer column, String toBeChecked) {
        List<String> tempCol = getCol(table, column);
        for (String s : tempCol) {
            if (s.equals(toBeChecked)) {
                return false;
            }
        }
        return true;
    }

    static public List<String> switchFromDateToDateSearchInput(String date, TMess tmess) {
        List<String> dateSearchInput = new ArrayList<String>();
        String day = date.split("/")[0];
        if (day.charAt(0) == '0') {
            day = day.substring(1, day.length());
        }
        String mounth = date.split("/")[1];
        if (mounth.charAt(0) == '0') {
            mounth = mounth.substring(1, mounth.length());
        }
        tmess.azione(mounth);
        if (mounth.equals("1")) {
            mounth = "Gen";
        }
        if (mounth.equals("2")) {
            mounth = "Feb";
        }
        if (mounth.equals("3")) {
            mounth = "Mar";
        }
        if (mounth.equals("4")) {
            mounth = "Apr";
        }
        if (mounth.equals("5")) {
            mounth = "Mag";
        }
        if (mounth.equals("6")) {
            mounth = "Giu";
        }
        if (mounth.equals("7")) {
            mounth = "Lug";
        }
        if (mounth.equals("8")) {
            mounth = "Ago";
        }
        if (mounth.equals("9")) {
            mounth = "Set";
        }
        if (mounth.equals("10")) {
            mounth = "Ott";
        }
        if (mounth.equals("11")) {
            mounth = "Nov";
        }
        if (mounth.equals("12")) {
            mounth = "Dic";
        }
        tmess.azione(mounth);
        String year = date.split("/")[2];
        dateSearchInput.add(day);
        dateSearchInput.add(mounth);
        dateSearchInput.add(year);
        return dateSearchInput;
    }

    static public List<String> orderDates(String first, String second) throws ParseException {
        List<String> orderedDates = new ArrayList<String>();
        Date datef = new SimpleDateFormat("dd/MM/yyyy").parse(first);
        Date dates = new SimpleDateFormat("dd/MM/yyyy").parse(second);
        if (datef.after(dates)) {
            orderedDates.add(second);
            orderedDates.add(first);
        } else {
            orderedDates.add(first);
            orderedDates.add(second);
        }
        return orderedDates;
    }

    static public List< String> selectBy(List<List<String>> table, Integer idColumn, Integer columnToCheck, String toConfront) {
        List<String> result = new ArrayList<String>();
        for (List<String> row : table) {
            if (row.get(columnToCheck).equals(toConfront)) {
                result.add(row.get(idColumn));
            }
        }
        return result;
    }

    static public List< String> selectByDateInterval(List<List<String>> table, Integer idColumn, Integer columnToCheck, String before, String after) throws ParseException {
        List<String> result = new ArrayList<String>();
        for (List<String> row : table) {
            if (checkDateInterval(before, after, row.get(columnToCheck))) {
                result.add(row.get(idColumn));
            }
        }
        return result;
    }

    static public Boolean checkDateInterval(String first, String second, String check) throws ParseException {
        Boolean result = true;
        Date current = new SimpleDateFormat("dd/MM/yyyy").parse(check);
        Date temp = new SimpleDateFormat("dd/MM/yyyy").parse(first);
        Date dateb = new SimpleDateFormat("dd/MM/yyyy").parse(first);
        Date datea = new SimpleDateFormat("dd/MM/yyyy").parse(second);
        if (current.after(datea) || current.before(dateb)) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    static public Boolean checkIfListSetIsPresent(List<String> listToCheck, List<String> listOrigin, TMess tmess) {
        for (String toCheck : listToCheck) {
            Boolean isPresent = false;
            for (String origin : listOrigin) {
                if (toCheck.equals(origin)) {
                    isPresent = true;
                }
            }
            if (!isPresent) {
                tmess.warning("La stringa " + toCheck + " non e' stata trovata nella lista a cui sarebbe dovuta appartenere");

                for (String origin : listOrigin) {
                    tmess.azione(origin);

                }
                return false;
            }
        }
        for (String origin : listOrigin) {
            tmess.azione("origin" + origin);
        }
        return true;
    }

    public static List<String> getNomeCognomeDaNomeDittaIndividuale(String nomeDittaIndividuale, TMess tmess) {
        List<String> result = new ArrayList<String>();
        List<String> temp = Arrays.asList(nomeDittaIndividuale.split(" "));
        if (temp.size() > 2) {
            result.add(temp.get(temp.size() - 2));
            result.add(temp.get(temp.size() - 1));
        } else if (temp.size() == 2) {
            result.add(temp.get(1));
            result.add(temp.get(0));
        } else if (temp.size() < 2) {
            tmess.warning("La stringa" + nomeDittaIndividuale + "non può rappresentare una ditta individuale");
        }
        return result;

    }

    public static Boolean confrontaAnagrafiche(List<String> labels, List<String> anagrafica1, List<String> anagrafica2, Boolean printMessage, TMess tmess) {
        Boolean result = true;
        List<String> message = new ArrayList<String>();
        if (labels.size() != anagrafica1.size() || labels.size() != anagrafica2.size() || anagrafica1.size() != anagrafica2.size()) {
            tmess.warning("Le dimensioni di label e anagrafiche da confrontatre non coincidono.");
            return false;
        }

        for (Integer anIndex = 0; anIndex < anagrafica1.size(); anIndex++) {
            if (!anagrafica1.get(anIndex).contains(anagrafica2.get(anIndex))) {
                message.add("I valori del campo " + labels.get(anIndex) + " non coincidono, sono: " + anagrafica1.get(anIndex) + " e " + anagrafica2.get(anIndex));
                result = false;
            }
        }
        if (!result && printMessage) {
            for (String s : message) {
                tmess.azione(s);
            }
        }
        return result;
    }

    public static String getStringWihout(String original, String toavoid, TMess tmess) {
        String result = "";
        List<String> l = Arrays.asList(original.split(toavoid));
        for (Integer i = 0; i < l.size(); i++) {
            String s = l.get(i);
            if (i > 0) {
                s = s.substring(1);
                s = removeWhiteSpaces(s);
            }
            tmess.azione(s);
            result = result + s;
        }
        return result;
    }

    public static String removeWhiteSpaces(String original) {
        String result = original;
        while (result.contains("  ")) {
            result = StringUtils.replace(result, "  ", " ");
        }
        return result;
    }

    public static Map<String, String> getAnagraficaMap(List<String> labels, List<String> values, TMess tmess) {
        Map<String, String> anagraficaMap = new TreeMap<String, String>();
        if (labels.size() != values.size()) {
            tmess.warning("La misura delle labels non coincide con la misura dei campi. Una mappa vuota sarà ritornata come risultato.");
            return null;
        } else {
            for (Integer i = 0; i.intValue() < labels.size(); i++) {
                anagraficaMap.put(labels.get(i), values.get(i));
            }
            return anagraficaMap;
        }
    }

}
