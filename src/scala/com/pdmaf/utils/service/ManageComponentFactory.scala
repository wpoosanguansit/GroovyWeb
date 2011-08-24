package com.pdmaf.utils.service

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy
import java.lang.reflect.Method
import net.sf.cglib.proxy.MethodProxy
import net.sf.cglib.proxy.MethodInterceptor
import net.sf.cglib.proxy.Enhancer
import net.sf.cglib

object ManagedComponentFactory {
  def createComponentProxy[T](intf: Class[T] forSome {type T}, proxy: ManagedComponentProxy): T = {
    Proxy.newProxyInstance(
      proxy.target.getClass.getClassLoader,
      Array(intf),
      proxy).asInstanceOf[T]
  }
  
  /**
   * This is used to initialize an object with non default contructor.
   * You have to pass the argument types and the argument objects in the form
   * Array(classOf[String]), Array("arguement object") for example.
   */
  def createComponentInterceptorWithArguments[T](intf: Class[T] forSome {type T}, cargsType: Array[Class[_]], 
                         cargs: Array[AnyRef], proxy: ManagedComponentInterceptor): T = {
    var enhancer = new Enhancer
    enhancer.setSuperclass(intf)
//  enhancer.setInterfaces(Array(classOf[com.pdmaf.business.models.Entity]))
    enhancer.setCallback(proxy)
    enhancer.create(cargsType, cargs).asInstanceOf[T];

  }
   
  def createComponentInterceptorDefault[T](intf: Class[T] forSome {type T}, proxy: ManagedComponentInterceptor): T = {
    var enhancer = new Enhancer
    enhancer.setSuperclass(intf)
    enhancer.setCallback(proxy)
    enhancer.create().asInstanceOf[T];

  }
}

class ManagedComponentProxy(val target: AnyRef) extends InvocationHandler {
  def invoke(proxy: AnyRef, m: Method, args: Array[AnyRef]): AnyRef = invoke(Invocation(m, args, target))
  def invoke(invocation: Invocation): AnyRef = invocation.invoke
}

class ManagedComponentInterceptor(val target: AnyRef) extends MethodInterceptor  {
  def intercept(target: AnyRef, m: Method, args: Array[AnyRef], mp: MethodProxy): AnyRef = intercept(Interception(m, args, mp, target))
  def intercept(interception: Interception): AnyRef = interception.intercept
}


