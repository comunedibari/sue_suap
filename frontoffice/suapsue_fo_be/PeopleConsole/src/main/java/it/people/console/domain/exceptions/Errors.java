/*  1:   */ package it.people.console.domain.exceptions;
/*  2:   */ 
/*  3:   */ public class Errors
/*  4:   */   implements IErrors
/*  5:   */ {
/*  6:   */   private String referer;
/*  7:   */   private String exception;
/*  8:   */   private String message;
/*  9:   */   
/* 10:   */   public void clear()
/* 11:   */   {
/* 12:24 */     setReferer(null);
/* 13:25 */     setException(null);
/* 14:26 */     setMessage(null);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String getReferer()
/* 18:   */   {
/* 19:30 */     return this.referer;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setReferer(String referer)
/* 23:   */   {
/* 24:34 */     this.referer = referer;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public String getException()
/* 28:   */   {
/* 29:38 */     return this.exception;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setException(String exception)
/* 33:   */   {
/* 34:42 */     this.exception = exception;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String getMessage()
/* 38:   */   {
/* 39:46 */     return this.message;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setMessage(String message)
/* 43:   */   {
/* 44:50 */     this.message = message;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\delpretea\Desktop\PeopleConsole\WEB-INF\classes\
 * Qualified Name:     it.people.console.domain.exceptions.Errors
 * JD-Core Version:    0.7.0.1
 */