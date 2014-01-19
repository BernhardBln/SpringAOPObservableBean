package de.bstreit.java.springaop.observablebean;

import java.beans.PropertyChangeSupport;

import javax.inject.Named;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;

/**
 * This aspect enables the automatic property change support and the mixes in
 * the {@link PropertyChangeSupport}.
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
@Named
@Aspect
public class ObservableBeanAspectMixin {

  private static PropertyChangeSupportWithInterface propertyChangeSupportWithInterface;


  @DeclareMixin("@de.bstreit.java.springaop.observablebean.ObservableBean *")
  public static IObservableBean createDelegate(Object object) {
    propertyChangeSupportWithInterface = new PropertyChangeSupportWithInterface(object);
    return propertyChangeSupportWithInterface;
  }

  /**
   * 
   * @param pjp
   * @throws Throwable
   *           not expected to happen with simple beans!
   */
  @Around("execution (* @de.bstreit.java.springaop.observablebean.ObservableBean *.set*(..))")
  public void handleSetterInvocation(ProceedingJoinPoint pjp) throws Throwable {
    propertyChangeSupportWithInterface.handleSetterInvocation(pjp);
  }

  /**
   * @ Before(
   * "execution (* @ de.bstreit.java.springaop.observablebean.ObservableBean *.set*(..))"
   * )
   */

  // @DeclareMixin(value =
  // "@de.bstreit.java.springaop.observablebean.ObservableBean *", defaultImpl =
  // PropertyChangeSupportWithInterface.class)
  // public static IObservableBean mixin;


}
