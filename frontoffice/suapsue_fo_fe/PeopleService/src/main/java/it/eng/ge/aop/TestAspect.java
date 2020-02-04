package it.eng.ge.aop;

import java.io.PrintStream;
import org.aspectj.lang.annotation.Aspect;  
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
 
@Aspect 
public class TestAspect
{
  @Pointcut("execution(* *.*(..)")
  public void anycall() {}
  
  @Before("anycall()")
  public void doPrint()
  {
    System.out.println("Test from after aspect");
  }
}
