/*
 * SpringContextAware.java
 *
 * Created on September 22, 2006, 7:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * This is the run time argument to the JVM.
 * -javaagent:C:\Sun\AppServer\spring-framework-2.0-rc4\lib\aspectj\aspectjweaver.jar
 */

package com.pdmaf.utils.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
/**
 *
 * @author watt
 *  This is the interface used in conjunction with AspectJ and Spring
 *  to inject Spring's applicationContext to the objects outside Spring
 *  container.  The class that is annotated with this flag has to implement
 *  Spring's <br>org.springframework.context.ApplicationContextAware</br>.
 *
 * @Required org.springframework.context.ApplicationContextAware
 */
public @interface SpringContextAware {
    
    /**
	 * The name of the bean definition that serves as the template.
	 */
	String value() default "SpringContextAware";
    
}