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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This is an interface with methods from the {@link PropertyChangeSupport}
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
