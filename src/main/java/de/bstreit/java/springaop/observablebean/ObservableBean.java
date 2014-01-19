package de.bstreit.java.springaop.observablebean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as observable bean, which means that setters automatically fire
 * property changes, and in addition the annotated type will implement the
 * IObservableBean interface which provides the methods known from the
 * {@link java.beans.PropertyChangeSupport} class.
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
@Target({ ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ObservableBean {

}
