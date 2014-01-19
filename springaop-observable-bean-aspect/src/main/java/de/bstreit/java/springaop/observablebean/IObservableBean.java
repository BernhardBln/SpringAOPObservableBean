package de.bstreit.java.springaop.observablebean;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This is an interface with all methods from the {@link PropertyChangeSupport}
 * class.
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
public interface IObservableBean {

  public void addPropertyChangeListener(PropertyChangeListener listener);

  public void removePropertyChangeListener(PropertyChangeListener listener);

  public PropertyChangeListener[] getPropertyChangeListeners();

  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

  public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

  public PropertyChangeListener[] getPropertyChangeListeners(String propertyName);


  public boolean hasListeners(String propertyName);


}
