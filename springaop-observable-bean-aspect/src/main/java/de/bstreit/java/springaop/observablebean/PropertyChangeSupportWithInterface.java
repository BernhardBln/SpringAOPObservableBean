package de.bstreit.java.springaop.observablebean;

import java.beans.PropertyChangeSupport;

/**
 * Helper class to have {@link PropertyChangeSupport} implement the
 * {@link IObservableBean} interface.
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
public class PropertyChangeSupportWithInterface extends PropertyChangeSupport
    implements IObservableBean {

  public PropertyChangeSupportWithInterface(Object sourceBean) {
    super(sourceBean);
  }

}
