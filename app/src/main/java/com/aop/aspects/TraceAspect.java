//package com.aop.aspects;
//
//import android.util.Log;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
///**
// * Created by Breno on 5/1/2016.
// */
//@Aspect
//public class TraceAspect {
//    static final String ALL_WIDGETTEST = "execution(* com.br.widgettest..*(..))";
//    static final String TAG = "TRACE ";
//    static String level = "  ";
//
//    @Pointcut("within(@com.aop.annotations.Trace *)")
//    public void beanAnnotatedWithMonitor() {}
//
//    @Pointcut("execution(public * *(..))")
//    public void publicMethod() {}
//
//    @Around("publicMethod() && beanAnnotatedWithMonitor()")
//    public Object around(ProceedingJoinPoint point) throws Throwable{
//        String method = MethodSignature.class.cast(point.getSignature()).getMethod().getName();
//        String klazz = point.getSignature().getDeclaringTypeName();
//
//        Log.d(TAG + ">", String.format("%s %s: %s", level, klazz, method));
//        level += "  ";
//        Object result = point.proceed();
//        level = level.substring(2);
//        Log.d(TAG + "<", String.format("%s %s: %s", level, klazz, method));
//
//        return result;
//    }
//}
