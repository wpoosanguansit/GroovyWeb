package com.pdmaf.utils.service


import java.util.Random
import java.lang.reflect.Method
import net.sf.cglib.proxy.MethodProxy
import net.sf.cglib.proxy.MethodInterceptor

case class Invocation(val method: Method, val args: Array[AnyRef], val target: AnyRef) {
  def invoke: AnyRef = method.invoke(target, args:_*)
  override def toString: String = "Invocation [method: " + method.getName + ", args: " + args + ", target: " + target + "]"
  override def hashCode(): Int = { 
    var hash = 7;
    var num = 0;
	hash = 31 * hash + num;
	hash = 31 * hash + (new Random()).nextInt
	return hash;

  }
  override def equals(that: Any): Boolean = { this.equals(that) }
}

case class Interception(val method: Method, val args: Array[AnyRef], val methodProxy: MethodProxy, val target: AnyRef) {
  def intercept: AnyRef = methodProxy.invokeSuper(target, args)
  override def toString: String = "Intercetption [method: " + method.getName + ", args: " + args + ", target: " + target + "]"
  override def hashCode(): Int = { 
    var hash = 7;
    var num = 0;
	hash = 31 * hash + num;
	hash = 31 * hash + (new Random()).nextInt
	return hash;

  }
  override def equals(that: Any): Boolean = { this.equals(that) }
}