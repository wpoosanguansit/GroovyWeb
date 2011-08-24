package com.pdmaf.utils.service

import org.aspectj.weaver.tools.PointcutExpression
import org.aspectj.weaver.tools.PointcutParser
import java.lang.annotation.Annotation

trait Invocator {
  protected val parser = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution

  protected def matches(pointcut: PointcutExpression, invocation: Invocation): Boolean = {
    pointcut.matchesMethodExecution(invocation.method).alwaysMatches ||
    pointcut.matchesMethodCall(invocation.method, invocation.method).alwaysMatches ||  
    invocation.target.getClass.getDeclaredMethods.exists(pointcut.matchesMethodExecution(_).alwaysMatches) ||
    false
  }

  protected def matches(annotationClass: Class[T] forSome {type T <: Annotation}, invocation: Invocation): Boolean = {
    invocation.method.isAnnotationPresent(annotationClass) ||
    invocation.target.getClass.isAnnotationPresent(annotationClass) ||
    false
  }
  def invoke(invocation: Invocation): AnyRef
}

trait Interceptor { 
  protected val parser = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution
   
  protected def matches(pointcut: PointcutExpression, interception: Interception): Boolean = {
    pointcut.matchesMethodExecution(interception.method).alwaysMatches ||
    pointcut.matchesMethodCall(interception.method, interception.method).alwaysMatches ||  
    interception.target.getClass.getDeclaredMethods.exists(pointcut.matchesMethodExecution(_).alwaysMatches) ||
    false
  }

  protected def matches(annotationClass: Class[T] forSome {type T <: Annotation}, interception: Interception): Boolean = {
    interception.method.isAnnotationPresent(annotationClass) ||
    interception.target.getClass.isAnnotationPresent(annotationClass) ||
    false
  }
  
  def intercept(interception: Interception): AnyRef
}

