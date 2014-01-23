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
