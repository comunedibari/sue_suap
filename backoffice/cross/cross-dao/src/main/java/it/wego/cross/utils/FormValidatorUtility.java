/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.wego.cross.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author GabrieleM
 */
public class FormValidatorUtility {

 public static   String verificaData(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
        if (dateInString == null || dateInString.equals("")) {
            return "non risulta compilato.";
        } else {
            try {
                Date date = formatter.parse(dateInString);
                return null;
            } catch (Exception e) {
                return "non risulta in formato corretto";
            }
        }
    }

  public static    String verificaNumero(String str) {
        if (str == null) {
            return "non risulta compilato";
        }
        int length = str.length();
        if (length == 0) {
            return "non risulta compilato";
        }
        int i = 0;
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c <= '/' || c >= ':') {
                return "non è solo informato numerico come previsto";
            }
        }
        return null;
    }
    
  public static    String verificaLunghezzaFissata(Integer lunghezza, String string){
        if(lunghezza.equals(string.length())){
            return null;
        }else{
            return "non è della lunghezza prevista";
        }
    }
    
  public static    String verificaCampoCompilato(String string, List<Character> caratteriNonAmmessi){
        if(string!=null&&!"".equals(string)){
        for(int i=0; i<string.length();i++){
            for(Character c: caratteriNonAmmessi ){
                if(c.equals(string.charAt(i))){
                    return "contiene caratteri non ammessi";
                }
            }
        }
        return null;
        }else{
            return "non risulta compilato";
        }
    }
}
