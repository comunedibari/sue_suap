package it.sirac.admin.sqlmap.util;

public class CheckCodFis
{
  public static boolean isValid(String CodFis)
  {
    CodFis = CodFis.toUpperCase();
    if (CodFis.length() == 16)
    {
      char[] Carattere = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
      






      int[] ValoriDispari = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21 };
      






      int[] ValoriPari = new int[36];
      for (int i = 0; i < 26; i++) {
        ValoriPari[i] = i;
      }
      for (int i = 26; i < 36; i++) {
        ValoriPari[i] = (i - 26);
      }
      char[] caratteriCF = CodFis.toCharArray();
      int valore = 0;
      for (int i = 0; i < caratteriCF.length - 1; i++) {
        if ((i + 1) % 2 == 0) {
          for (int j = 0; j < Carattere.length; j++) {
            if (caratteriCF[i] == Carattere[j]) {
              valore += ValoriPari[j];
            }
          }
        } else {
          for (int j = 0; j < Carattere.length; j++) {
            if (caratteriCF[i] == Carattere[j]) {
              valore += ValoriDispari[j];
            }
          }
        }
      }
      valore %= 26;
      for (int i = 0; i < 26; i++) {
        if (caratteriCF[(caratteriCF.length - 1)] == Carattere[i])
        {
          if (valore == i) {
            return true;
          }
          return false;
        }
      }
      return false;
    }
    return false;
  }
}
