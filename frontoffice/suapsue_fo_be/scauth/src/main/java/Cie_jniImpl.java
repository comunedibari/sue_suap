import cie.Cie_jniInterface;

public class Cie_jniImpl
  implements Cie_jniInterface
{
  public long CIE_Connect(String s)
  {
    return Cie_jni.CIE_Connect(s);
  }
  
  public long CIE_Disconnect()
  {
    return Cie_jni.CIE_Disconnect();
  }
  
  public long CIE_GetChipDevice(int i, byte[] abyte0, int[] ai)
  {
    return Cie_jni.CIE_GetChipDevice(i, abyte0, ai);
  }
  
  public long CIE_Read_ATR(byte[] abyte0, byte[] abyte1)
  {
    return Cie_jni.CIE_Read_ATR(abyte0, abyte1);
  }
  
  public long CIE_Read_ChipSerialNumber(byte[] abyte0, byte[] abyte1)
  {
    return Cie_jni.CIE_Read_ChipSerialNumber(abyte0, abyte1);
  }
  
  public long CIE_VerifyPIN(byte[] abyte0, byte byte0)
  {
    return Cie_jni.CIE_VerifyPIN(abyte0, byte0);
  }
  
  public long CIE_ChangePIN(byte[] abyte0, byte[] abyte1, byte byte0)
  {
    return Cie_jni.CIE_ChangePIN(abyte0, abyte1, byte0);
  }
  
  public long CIE_UnblockPIN(byte[] abyte0, byte byte0, byte[] abyte1, byte byte1)
  {
    return Cie_jni.CIE_UnblockPIN(abyte0, byte0, abyte1, byte1);
  }
  
  public long CIE_VerifyPUK(byte[] abyte0, byte byte0)
  {
    return Cie_jni.CIE_VerifyPUK(abyte0, byte0);
  }
  
  public long CIE_ChangePUK(byte[] abyte0, byte[] abyte1, byte byte0)
  {
    return Cie_jni.CIE_ChangePUK(abyte0, abyte1, byte0);
  }
  
  public long CIE_ReadSerialNmber(byte[] abyte0, int[] ai)
  {
    return Cie_jni.CIE_ReadSerialNmber(abyte0, ai);
  }
  
  public long CIE_ReadDatiProcessore(byte[] abyte0, int[] ai)
  {
    return Cie_jni.CIE_ReadDatiProcessore(abyte0, ai);
  }
  
  public long CIE_ReadParametriAPDU(byte[] abyte0, int[] ai)
  {
    return Cie_jni.CIE_ReadParametriAPDU(abyte0, ai);
  }
  
  public long CIE_ReadIdCarta(byte[] abyte0, int[] ai)
  {
    return Cie_jni.CIE_ReadIdCarta(abyte0, ai);
  }
  
  public long CIE_ReadCCarta(byte[] abyte0, int[] ai)
  {
    return Cie_jni.CIE_ReadCCarta(abyte0, ai);
  }
  
  public long CIE_ReadDatiPersonaliRaw(byte[] abyte0, int[] ai)
  {
    return Cie_jni.CIE_ReadDatiPersonaliRaw(abyte0, ai);
  }
  
  public long CIE_ReadDatiPersonaliAggiuntivi(byte[] abyte0, int[] ai)
  {
    return Cie_jni.CIE_ReadDatiPersonaliAggiuntivi(abyte0, ai);
  }
  
  public long CIE_ReadMemoriaResidua(int[] ai)
  {
    return Cie_jni.CIE_ReadMemoriaResidua(ai);
  }
  
  public long CIE_ReadServiziInstallati(byte[] abyte0, int[] ai)
  {
    return Cie_jni.CIE_ReadServiziInstallati(abyte0, ai);
  }
  
  public long CIE_HashDataSHA1(byte[] abyte0, int i, byte[] abyte1, byte[] abyte2)
  {
    return Cie_jni.CIE_HashDataSHA1(abyte0, i, abyte1, abyte2);
  }
  
  public long CIE_Sign(byte[] abyte0, byte byte0, byte[] abyte1, int[] ai)
  {
    return Cie_jni.CIE_Sign(abyte0, byte0, abyte1, ai);
  }
  
  public long CIE_MkPkcs7(byte[] abyte0, byte byte0, byte[] abyte1, int i, byte[] abyte2, int[] ai)
  {
    return Cie_jni.CIE_MkPkcs7(abyte0, byte0, abyte1, i, abyte2, ai);
  }
  
  public long CIE_VerifyCert(byte[] abyte0, int i, int j)
  {
    return Cie_jni.CIE_VerifyCert(abyte0, i, j);
  }
}

