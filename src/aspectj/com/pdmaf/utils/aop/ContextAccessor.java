
package com.pdmaf.utils.aop;

/*
 * ContextAccessor.java
 *
 * Created on September 22, 2006, 7:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


import com.pdmaf.utils.interfaces.SpringContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author watt
 *  This is a factory class that is used to get Spring managed beans
 *  from the current Spring's ApplicationContext.
 *   
 */
@SpringContextAware("contextAccessor")
public final class ContextAccessor implements ApplicationContextAware {
      
        private ApplicationContext applicationContext;
    
        private static ContextAccessor instance;
      
        /** 
         * Having private contructor restrict object initialization to 
         * Factory instance() method only.
         */
        private ContextAccessor() {}
        
        public static final ContextAccessor instance() {
            if(instance == null) {
                instance = new ContextAccessor();
            }
            return instance;
        }
        
        /**
         * This broker is itself configured by Spring DI, which will
         * pass it a reference to the ApplicationContext
         */
        public void setApplicationContext(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }
      
        /**
         * Pass the name to Spring's ApplicationContext to get the managed bean.
         * @param name - the bean name that is identified in spring applicationContext.xml
         * @throw IllegalStateException - if the applicationContext is null.
         */
        public Object getSpringBean(String name) {
            
            if(applicationContext == null) {
                throw new IllegalStateException("[ContextAccessor : getSpringBean() : invalid state - " +
                        "applicationContext can not be null!] ");
            }
            
            return applicationContext.getBean(name);
        }
}
