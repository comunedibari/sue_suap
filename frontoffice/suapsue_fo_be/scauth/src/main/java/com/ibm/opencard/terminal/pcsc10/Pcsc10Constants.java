package com.ibm.opencard.terminal.pcsc10;

public abstract interface Pcsc10Constants
{
  public static final int SCARD_S_SUCCESS = 0;
  public static final int SCARD_E_INVALID_HANDLE = -2146435069;
  public static final int SCARD_E_INVALID_PARAMETER = -2146435068;
  public static final int SCARD_E_NOT_READY = -2146435056;
  public static final int SCARD_E_INVALID_VALUE = -2146435055;
  public static final int SCARD_E_TIMEOUT = -2146435062;
  public static final int SCARD_E_NO_MEMORY = -2146435066;
  public static final int SCARD_E_UNSUPPORTED_REQUEST = -1609564159;
  public static final int SCARD_W_UNRESPONSIVE_CARD = -2146434970;
  public static final int SCARD_W_UNPOWERED_CARD = -2146434969;
  public static final int SCARD_W_RESET_CARD = -2146434968;
  public static final int SCARD_W_REMOVED_CARD = -2146434967;
  public static final int SCARD_SHARE_EXCLUSIVE = 1;
  public static final int SCARD_SHARE_DIRECT = 3;
  public static final int SCARD_PROTOCOL_T0 = 1;
  public static final int SCARD_PROTOCOL_T1 = 2;
  public static final int SCARD_PROTOCOL_RAW = 65536;
  public static final int SCARD_PROTOCOL_DEFAULT = -2147483648;
  public static final int SCARD_PROTOCOL_OPTIMAL = 0;
  public static final int SCARD_LEAVE_CARD = 0;
  public static final int SCARD_RESET_CARD = 1;
  public static final int SCARD_UNPOWER_CARD = 2;
  public static final int SCARD_EJECT_CARD = 3;
  public static final int SCARD_STATE_UNAWARE = 0;
  public static final int SCARD_STATE_IGNORE = 1;
  public static final int SCARD_STATE_CHANGED = 2;
  public static final int SCARD_STATE_UNKNOWN = 4;
  public static final int SCARD_STATE_UNAVAILABLE = 8;
  public static final int SCARD_STATE_EMPTY = 16;
  public static final int SCARD_STATE_PRESENT = 32;
  public static final int SCARD_ABSENT = 1;
  public static final int SCARD_PRESENT = 2;
  public static final int SCARD_POWERED = 4;
  public static final int SCARD_SCOPE_USER = 0;
  public static final int SCARD_ATTR_VENDOR_NAME = 65792;
  public static final int SCARD_ATTR_VENDOR_IFD_TYPE = 65793;
  public static final int SCARD_ATTR_VENDOR_IFD_VERSION = 65794;
  public static final int SCARD_ATTR_ATR_STRING = 590595;
  public static final int SCARD_ATTR_ICC_PRESENCE = 590592;
}

