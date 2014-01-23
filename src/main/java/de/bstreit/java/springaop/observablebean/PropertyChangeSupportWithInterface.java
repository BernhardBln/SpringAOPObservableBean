package de.bstreit.java.springaop.observablebean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Internal helper class, one instance per proxied object, manages listener
 * registration and fires the change event.
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
class PropertyChangeSupportWithInterface implements IObservableBean
{

  private PropertyChangeSupport pcs;
  private BeanWrapper beanWrapper;


  PropertyChangeSupportWithInterface(Object observedObject) {
    pcs = new PropertyChangeSupport(observedObject);
    beanWrapper = new BeanWrapperImpl(observedObject);
  }


  public void addPropertyChangeListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }


  public void removePropertyChangeListener(PropertyChangeListener listener) {
    pcs.removePropertyChangeListener(listener);
  }


  public PropertyChangeListener[] getPropertyChangeListeners() {
    return pcs.getPropertyChangeListeners();
  }


  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(propertyName, listener);
  }


  public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    pcs.removePropertyChangeListener(propertyName, listener);
  }


  public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
    return pcs.getPropertyChangeListeners(propertyName);
  }


  public boolean hasListeners(String propertyName) {
    return pcs.hasListeners(propertyName);
  }

  /**
   * Retrieve old and new value, invoke setter and finally fire property change
   * event.
   * 
   * @param pjp
   * @throws Throwable
   */
  void handleSetterInvocation(ProceedingJoinPoint pjp) throws Throwable {
    // the method name is "setPropertyName", hence stripping of the "set"
    // from the beginning gives us "PropertyName"
    final String propertyName = pjp.getSignature().getName().substring(3);

    final Object oldValue = beanWrapper.getPropertyValue(propertyName);
    final Object newValue = pjp.getArgs()[0];

    // perform the setting
    pjp.proceed();

    // fire property change, if no exception occurred
    pcs.firePropertyChange(propertyName, oldValue, newValue);
  }
}
