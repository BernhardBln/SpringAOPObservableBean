package de.bstreit.java.springaop.observablebean;

import javax.inject.Named;

@ObservableBean
@Named
public class MyBean {

  private String field;


  public String getField() {
    return field;
  }


  public void setField(String field) {
    this.field = field;
  }


}
