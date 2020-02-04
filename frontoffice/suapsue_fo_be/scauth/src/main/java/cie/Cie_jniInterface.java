package cie;

public abstract interface Cie_jniInterface
{
  public abstract long CIE_Connect(String paramString);
  
  public abstract long CIE_Disconnect();
  
  public abstract long CIE_GetChipDevice(int paramInt, byte[] paramArrayOfByte, int[] paramArrayOfInt);
  
  public abstract long CIE_Read_ATR(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract long CIE_Read_ChipSerialNumber(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract long CIE_VerifyPIN(byte[] paramArrayOfByte, byte paramByte);
  
  public abstract long CIE_ChangePIN(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte paramByte);
  
  public abstract long CIE_UnblockPIN(byte[] paramArrayOfByte1, byte paramByte1, byte[] paramArrayOfByte2, byte paramByte2);
  
  public abstract long CIE_VerifyPUK(byte[] paramArrayOfByte, byte paramByte);
  
  public abstract long CIE_ChangePUK(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte paramByte);
  
  public abstract long CIE_ReadSerialNmber(byte[] paramArrayOfByte, int[] paramArrayOfInt);
  
  public abstract long CIE_ReadDatiProcessore(byte[] paramArrayOfByte, int[] paramArrayOfInt);
  
  public abstract long CIE_ReadParametriAPDU(byte[] paramArrayOfByte, int[] paramArrayOfInt);
  
  public abstract long CIE_ReadIdCarta(byte[] paramArrayOfByte, int[] paramArrayOfInt);
  
  public abstract long CIE_ReadCCarta(byte[] paramArrayOfByte, int[] paramArrayOfInt);
  
  public abstract long CIE_ReadDatiPersonaliRaw(byte[] paramArrayOfByte, int[] paramArrayOfInt);
  
  public abstract long CIE_ReadDatiPersonaliAggiuntivi(byte[] paramArrayOfByte, int[] paramArrayOfInt);
  
  public abstract long CIE_ReadMemoriaResidua(int[] paramArrayOfInt);
  
  public abstract long CIE_ReadServiziInstallati(byte[] paramArrayOfByte, int[] paramArrayOfInt);
  
  public abstract long CIE_HashDataSHA1(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract long CIE_Sign(byte[] paramArrayOfByte1, byte paramByte, byte[] paramArrayOfByte2, int[] paramArrayOfInt);
  
  public abstract long CIE_MkPkcs7(byte[] paramArrayOfByte1, byte paramByte, byte[] paramArrayOfByte2, int paramInt, byte[] paramArrayOfByte3, int[] paramArrayOfInt);
  
  public abstract long CIE_VerifyCert(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
}

