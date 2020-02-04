package com.ibm.opencard.terminal.pcsc10;

import java.io.PrintStream;

public class OCFPCSC1
{
  public OCFPCSC1()
    throws PcscException
  {}
  
  public static void loadLib()
  {
    try
    {
      String tempDir = System.getProperty("java.io.tmpdir");
      String libraryFile = tempDir + "OCFPCSC1.dll";
      System.out.println("Loading library: " + libraryFile);
      System.load(libraryFile);
      System.out.println("Library " + libraryFile + " succesfully loaded.");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public native void initTrace();
  
  public synchronized native String[] SCardListReaders(String paramString)
    throws PcscException;
  
  public synchronized native int SCardEstablishContext(int paramInt)
    throws PcscException;
  
  public synchronized native void SCardReleaseContext(int paramInt)
    throws PcscException;
  
  public synchronized native int SCardConnect(int paramInt1, String paramString, int paramInt2, int paramInt3, Integer paramInteger)
    throws PcscException;
  
  public synchronized native void SCardReconnect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Integer paramInteger)
    throws PcscException;
  
  public synchronized native void SCardDisconnect(int paramInt1, int paramInt2)
    throws PcscException;
  
  public synchronized native void SCardGetStatusChange(int paramInt1, int paramInt2, PcscReaderState[] paramArrayOfPcscReaderState)
    throws PcscException;
  
  public synchronized native byte[] SCardGetAttrib(int paramInt1, int paramInt2)
    throws PcscException;
  
  public synchronized native byte[] SCardControl(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
    throws PcscException;
  
  public synchronized native byte[] SCardTransmit(int paramInt, byte[] paramArrayOfByte)
    throws PcscException;
}

