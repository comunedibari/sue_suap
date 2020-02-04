package it.sirac.admin.sqlmap.util;

public class CheckParIva
{
  public static boolean isValid(Long piva)
  {
    return isValid(piva.toString());
  }
  
  public static boolean isValid(String piva)
  {
    String strZero = "00000000000";
    int strlen = piva.length();
    if (strlen < strZero.length())
    {
      strZero = strZero.substring(strlen);
      piva = strZero + piva;
    }
    for (int i = 0; i < piva.length(); i++) {
      if (!Character.isDigit(piva.charAt(i))) {
        return false;
      }
    }
    if (piva.length() == 11)
    {
      int i = 0;
      int tot = 0;
      while (i < 10)
      {
        tot += Integer.parseInt(piva.substring(i, i + 1));
        i += 2;
      }
      int pari = 0;
      i = 1;
      while (i < 11)
      {
        pari = Integer.parseInt(piva.substring(i, i + 1)) * 2;
        pari = pari / 10 + pari % 10;
        tot += pari;
        i += 2;
      }
      int control = Integer.parseInt(piva.substring(10, 11));
      if (((tot % 10 == 0) && (control == 0)) || (10 - tot % 10 == control)) {
        return true;
      }
      return false;
    }
    return false;
  }
}
