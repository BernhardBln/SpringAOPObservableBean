package de.bstreit.java.springaop.observablebean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <p>
 * Marks a class as observable bean, which means that setters automatically fire
 * property changes, and in addition the annotated type will implement the
 * IObservableBean interface which provides the methods known from the
 * {@link java.beans.PropertyChangeSupport} class.
 * </p>
 * 
 * <p>
 * <b>Important</b> - this annotation will only work if you enable the
 * {@link org.jdal.aop.DeclareMixinAutoProxyCreatorConfigurer}.
 * </p>
 * <p>
 * If you use annotated Spring configuration classes, see
 * {@link EnableObservableBeans} for instructions how to enable the configurer.
 * </p>
 * <p>
 * Otherwise, simply declare the
 * {@link org.jdal.aop.DeclareMixinAutoProxyCreatorConfigurer} as a bean in your
 * spring xml file.
 * </p>
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
@Target({ ElementType.TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ObservableBean {

}
