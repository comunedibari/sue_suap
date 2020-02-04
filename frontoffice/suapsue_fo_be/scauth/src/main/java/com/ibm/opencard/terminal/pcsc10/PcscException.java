package com.ibm.opencard.terminal.pcsc10;

public class PcscException
  extends Exception
{
  private int rc;
  
  public PcscException(String s)
  {
    super(s);this.rc = 0;
  }
  
  public PcscException(String msg, int retCode)
  {
    super(msg);
    this.rc = retCode;
  }
  
  public int returnCode()
  {
    return this.rc;
  }
}

