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
package de.bstreit.java.springaop.test.observablebean.impl;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import de.bstreit.java.springaop.observablebean.ObservableBean;
import de.bstreit.java.springaop.test.observablebean.IMyBean;

@Named
@ObservableBean
@Scope("prototype")
public class MyBean implements IMyBean {

  private String field;

  private int anotherField;


  /**
   * Make constructor private to prevent people from using the new operator
   */
  private MyBean() {

  }


  @Override
  public String getField() {
    return field;
  }


  @Override
  public void setField(String field) {
    this.field = field;
  }


  @Override
  public int getAnotherField() {
    return anotherField;
  }


  @Override
  public void setAnotherField(int anotherField) {
    this.anotherField = anotherField;
  }


}
