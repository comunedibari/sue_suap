package cie;

public class CIEAccess
{
  private static Cie_jniInterface Cie_jniAdapter = null;
  
  public static Cie_jniInterface getCieAdapter()
  {
    if (Cie_jniAdapter == null) {
      try
      {
        Class c = Class.forName("Cie_jniImpl");
        Cie_jniAdapter = (Cie_jniInterface)c.newInstance();
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      catch (InstantiationException e)
      {
        e.printStackTrace();
      }
      catch (IllegalAccessException e)
      {
        e.printStackTrace();
      }
    }
    return Cie_jniAdapter;
  }
  
  public static String getChipDevice(boolean mode)
  {
    int modo;
   // int modo;
    if (mode) {
      modo = 1;
    } else {
      modo = 0;
    }
    int[] len = new int[1];
    getCieAdapter().CIE_GetChipDevice(modo, null, len);
    if (len[0] == 0) {
      return null;
    }
    byte[] pbtData = new byte[len[0]];
    getCieAdapter().CIE_GetChipDevice(modo, pbtData, len);
    return c2JavaString(pbtData);
  }
  
  public static long connect(String deviceName)
  {
    return getCieAdapter().CIE_Connect(deviceName);
  }
  
  public static long disconnect()
  {
    return getCieAdapter().CIE_Disconnect();
  }
  
  public static byte[] readATR()
  {
    byte[] len = new byte[1];
    long resultCode = getCieAdapter().CIE_Read_ATR(null, len);
    if (resultCode != 0L) {
      return null;
    }
    byte[] pbtATR = new byte[len[0]];
    resultCode = getCieAdapter().CIE_Read_ATR(pbtATR, len);
    if (resultCode == 0L) {
      return pbtATR;
    }
    return null;
  }
  
  public static byte[] readChipSerialNumber()
  {
    byte[] len = new byte[1];
    long resultCode = getCieAdapter().CIE_Read_ChipSerialNumber(null, len);
    if (resultCode != 0L) {
      return null;
    }
    byte[] pbtChipSerialNumber = new byte[len[0]];
    resultCode = getCieAdapter()
      .CIE_Read_ChipSerialNumber(pbtChipSerialNumber, len);
    if (resultCode == 0L) {
      return pbtChipSerialNumber;
    }
    return null;
  }
  
  public static byte[] readSerialNumber()
  {
    int[] len = new int[1];
    getCieAdapter().CIE_ReadSerialNmber(null, len);
    byte[] pbtSerialNumber = new byte[len[0]];
    long resultCode = getCieAdapter().CIE_ReadSerialNmber(pbtSerialNumber, len);
    if (resultCode == 0L) {
      return pbtSerialNumber;
    }
    return null;
  }
  
  public static byte[] readIdCarta()
  {
    int[] len = new int[1];
    getCieAdapter().CIE_ReadIdCarta(null, len);
    byte[] pbtIdCarta = new byte[len[0]];
    long resultCode = getCieAdapter().CIE_ReadIdCarta(pbtIdCarta, len);
    if (resultCode == 0L) {
      return pbtIdCarta;
    }
    return null;
  }
  
  public static byte[] readX509Certificate()
  {
    int[] len = new int[1];
    getCieAdapter().CIE_ReadCCarta(null, len);
    byte[] pbtCCarta = new byte[len[0]];
    long resultCode = getCieAdapter().CIE_ReadCCarta(pbtCCarta, len);
    if (resultCode == 0L) {
      return pbtCCarta;
    }
    return null;
  }
  
  public static byte[] readDatiProcessore()
  {
    int[] len = new int[1];
    getCieAdapter().CIE_ReadDatiProcessore(null, len);
    byte[] pbtDatiProcessore = new byte[len[0]];
    long resultCode = getCieAdapter()
      .CIE_ReadDatiProcessore(pbtDatiProcessore, len);
    if (resultCode == 0L) {
      return pbtDatiProcessore;
    }
    return null;
  }
  
  public static byte[] readParametriAPDU()
  {
    int[] len = new int[1];
    getCieAdapter().CIE_ReadParametriAPDU(null, len);
    byte[] pbtParametriAPDU = new byte[len[0]];
    long resultCode = getCieAdapter().CIE_ReadParametriAPDU(pbtParametriAPDU, len);
    if (resultCode == 0L) {
      return pbtParametriAPDU;
    }
    return null;
  }
  
  public static byte[] readDatiPersonaliRaw()
  {
    int[] len = new int[1];
    getCieAdapter().CIE_ReadDatiPersonaliRaw(null, len);
    byte[] pbtReadDatiPersonaliRaw = new byte[len[0]];
    long resultCode = getCieAdapter().CIE_ReadDatiPersonaliRaw(
      pbtReadDatiPersonaliRaw, len);
    if (resultCode == 0L) {
      return pbtReadDatiPersonaliRaw;
    }
    return null;
  }
  
  public static DatiPersonali readDatiPersonali()
  {
    byte[] datiPersonaliRaw = readDatiPersonaliRaw();
    try
    {
      DatiPersonali datiPersonali = new DatiPersonali(datiPersonaliRaw);
      return datiPersonali;
    }
    catch (Exception _ex)
    {
      return null;
    }
  }
  
  public static int readMemoriaResidua()
  {
    int[] len = new int[1];
    getCieAdapter().CIE_ReadMemoriaResidua(len);
    return len[0];
  }
  
  public static byte[] hashDataSHA1(byte[] data)
  {
    byte[] len = new byte[1];
    getCieAdapter().CIE_HashDataSHA1(data, data.length, null, len);
    byte[] pbtHash = new byte[len[0]];
    long resultCode = getCieAdapter().CIE_HashDataSHA1(data, data.length, pbtHash, 
      len);
    if (resultCode == 0L) {
      return pbtHash;
    }
    return null;
  }
  
  public static byte[] sign(byte[] data)
  {
    int[] len = new int[1];
    getCieAdapter().CIE_Sign(data, (byte)data.length, null, len);
    byte[] pbtSignedData = new byte[len[0]];
    long resultCode = getCieAdapter().CIE_Sign(data, (byte)data.length, 
      pbtSignedData, len);
    if (resultCode == 0L) {
      return pbtSignedData;
    }
    return null;
  }
  
  public static byte[] signPKCS7(byte[] pin, byte[] data)
  {
    int[] len = new int[1];
    getCieAdapter().CIE_MkPkcs7(pin, (byte)pin.length, data, data.length, null, 
      len);
    byte[] pbtSignedData = new byte[len[0]];
    long resultCode = getCieAdapter().CIE_MkPkcs7(pin, (byte)pin.length, data, 
      data.length, pbtSignedData, len);
    if (resultCode == 0L) {
      return pbtSignedData;
    }
    return null;
  }
  
  public static long verifyCert(byte[] cert, int mode)
  {
    return getCieAdapter().CIE_VerifyCert(cert, cert.length, mode);
  }
  
  public static long verifyPIN(byte[] pbtPIN, byte btPinLen)
  {
    return getCieAdapter().CIE_VerifyPIN(pbtPIN, btPinLen);
  }
  
  public static long verifyPUK(byte[] pbtPUK, byte btPukLen)
  {
    return getCieAdapter().CIE_VerifyPUK(pbtPUK, btPukLen);
  }
  
  private static byte[] java2CString(String str)
  {
    byte[] buf = str.getBytes();
    byte[] buf2 = new byte[buf.length + 1];
    System.arraycopy(buf, 0, buf2, 0, buf.length);
    buf2[(buf2.length - 1)] = 0;
    return buf2;
  }
  
  private static String c2JavaString(byte[] cString)
  {
    byte[] buf = new byte[cString.length - 1];
    System.arraycopy(cString, 0, buf, 0, buf.length);
    return new String(buf);
  }
}

