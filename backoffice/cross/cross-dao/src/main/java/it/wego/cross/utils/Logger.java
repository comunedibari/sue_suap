package it.wego.cross.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author CS
 */
@Component
@Aspect
public class Logger {

//    @Around("execution(* it.wego.cross..*.*(..))")
//    public Object logTimeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        Object retVal = joinPoint.proceed();
//        stopWatch.stop();
//        StringBuilder logMessage = new StringBuilder();
//        logMessage.append(joinPoint.getTarget().getClass().getName());
//        logMessage.append(".");
//        logMessage.append(joinPoint.getSignature().getName());
//        logMessage.append("(");
//        // append args
//        Object[] args = joinPoint.getArgs();
//        for (Object arg : args) {
//            logMessage.append(arg).append(",");
//        }
//        if (args.length > 0) {
//            logMessage.deleteCharAt(logMessage.length() - 1);
//        }
//        logMessage.append(")");
//        logMessage.append(" execution time: ");
//        logMessage.append(stopWatch.getTotalTimeMillis());
//        logMessage.append(" ms");
//        Log.APP.error(logMessage.toString());
//        return retVal;
//    }

//    @Before("execution(* it.wego.cross.service..*.*(..)) || execution(* it.wego.cross.dao..*.*(..))")
    public void beforeLog(JoinPoint joinPoint) {
//        StringBuilder logToWrite = new StringBuilder();
//        List<String> notLog = new ArrayList<String>();
//        notLog.add("it.wego.cross.dao.ConfigurationDao");
//        notLog.add("it.wego.cross.dao.Filter");
//        notLog.add("it.wego.cross.service.ConfigurationServiceImpl");
//        if (!notLog.contains(joinPoint.getThis().getClass().getName())) {
////            logToWrite.append("\n########################################################################\n");
//            logToWrite.append("-->>>>>>>>>CHIAMATA : ").append(joinPoint.getSignature().getName());
//            Object[] args = joinPoint.getArgs();
//            if (args.length > 0) {
//                logToWrite.append(" con parametri: ");
//                for (int i = 0; i < args.length; i++) {
//                    ObjectMapper mapper = new ObjectMapper();
//                    try {
//                        if (args[i] != null) {
//                            logToWrite.append(mapper.defaultPrettyPrintingWriter().writeValueAsString(args[i]));
//
//                        } else {
//                            logToWrite.append(args[i]);
//                        }
//                    } catch (Exception ex) {
//                        logToWrite.append(args[i]);
//                    }
//                }
//            }
////            logToWrite.append("\n########################################################################");
//            Log.APP.debug(logToWrite.toString());
//        }
    }

//    @After("execution(* it.wego.cross.service..*.*(..))")
//    public void afterLog(JoinPoint joinPoint) {
//    }
    public static String getCurrentCallStack() {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            sb.append(ste).append("\r\n");
        }
        return sb.toString();
    }
}
