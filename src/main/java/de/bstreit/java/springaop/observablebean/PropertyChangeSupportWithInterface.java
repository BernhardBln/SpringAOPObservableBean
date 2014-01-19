package de.bstreit.java.springaop.observablebean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Helper class to have {@link PropertyChangeSupport} implement the
 * {@link IObservableBean} interface.
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
public class PropertyChangeSupportWithInterface
    implements IObservableBean {

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

  @Override
  public boolean hasListeners(String propertyName) {
    return pcs.hasListeners(propertyName);
  }


  void handleSetterInvocation(ProceedingJoinPoint pjp) throws Throwable {
    // should the first letter be lower or upper case?
    final String propValue = pjp.getSignature().getName().substring(3);

    final Object oldValue = beanWrapper.getPropertyValue(propValue);
    final Object newValue = pjp.getArgs()[0];
    pjp.proceed();
    pcs.firePropertyChange(propValue, oldValue, newValue);
  }

}
