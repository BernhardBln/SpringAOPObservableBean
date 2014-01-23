/*
 * Copyright 2014 Bernhard Streit (Bernhard.Streit+springaop@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
