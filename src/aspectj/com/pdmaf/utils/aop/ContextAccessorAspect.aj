package com.pdmaf.utils.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.pdmaf.utils.interfaces.SpringContextAware;

public aspect ContextAccessorAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;
	  
    /**
     * This broker is itself configured by Spring DI, which will
     * pass it a reference to the ApplicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
    	this.applicationContext = applicationContext;
    }

	  /**
	   * creation of any object that we want to be configured by Spring
	   */
	  public pointcut ContextAccessorCreation(Object newInstance,
                            SpringContextAware annotation) 
	    : initialization((@SpringContextAware *).new(..)) &&
	      this(newInstance) && @this(annotation) &&
              !within(ContextAccessorAspect);
	    
	    /**
	     * Set the applicationContext we get from Spring to the object.
	     */
	    after(Object newInstance, SpringContextAware annotation) returning 
	     : ContextAccessorCreation(newInstance, annotation) {
	      if( newInstance instanceof ApplicationContextAware) {
	    	  ((ApplicationContextAware)newInstance).setApplicationContext(this.applicationContext);
	      }
	    }

}