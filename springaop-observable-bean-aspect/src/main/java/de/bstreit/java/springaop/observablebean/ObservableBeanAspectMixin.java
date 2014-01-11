package de.bstreit.java.springaop.observablebean;

import java.beans.PropertyChangeSupport;

import javax.inject.Named;

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


  @DeclareMixin("@de.bstreit.java.springaop.observablebean.ObservableBean *")
  public static IObservableBean createDelegate(Object object) {
    return new PropertyChangeSupportWithInterface(object);
  }


}
