package it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.utility;

import it.people.fsl.servizi.concessioniedautorizzazioni.servizicondivisi.procedimentounico.oggetti.HrefCampiBean;
import java.util.Comparator;

final class HtmlRenderer$1
  implements Comparator
{
  public int compare(Object o1, Object o2)
  {
    HrefCampiBean p1 = (HrefCampiBean)o1;
    HrefCampiBean p2 = (HrefCampiBean)o2;
    if (p1.getRiga() < p2.getRiga()) {
      return -1;
    }
    if (p1.getRiga() == p2.getRiga())
    {
      if (p1.getPosizione() < p2.getPosizione()) {
        return -1;
      }
      if (p1.getPosizione() == p2.getPosizione()) {
        return 0;
      }
      if (p1.getPosizione() > p2.getPosizione()) {
        return 1;
      }
    }
    return 1;
  }
}
