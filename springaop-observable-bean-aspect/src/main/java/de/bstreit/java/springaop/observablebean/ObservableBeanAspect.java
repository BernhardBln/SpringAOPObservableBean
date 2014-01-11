package de.bstreit.java.springaop.observablebean;

import java.beans.PropertyChangeSupport;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * This aspect enables the automatic property change support and the mixes in
 * the {@link PropertyChangeSupport}.
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
// @Named
@Aspect
public class ObservableBeanAspect {


  @Before("execution (* @de.bstreit.java.springaop.observablebean.ObservableBean *.set*(..))")
  public void firePropertyChange() {
    System.out.println("### PROP CHANGE!!!");
  }

}
