package de.bstreit.java.springaop.observablebean;

import javax.inject.Named;

@ObservableBean
@Named
public class MyBean implements IMyBean {

  private String field;


  @Override
  public String getField() {
    return field;
  }


  @Override
  public void setField(String field) {
    this.field = field;
  }


}
