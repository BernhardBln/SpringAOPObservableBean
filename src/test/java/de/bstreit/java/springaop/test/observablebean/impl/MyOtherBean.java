package de.bstreit.java.springaop.test.observablebean.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import de.bstreit.java.springaop.observablebean.ObservableBean;
import de.bstreit.java.springaop.test.observablebean.IMyOtherBean;

@Named
@ObservableBean
@Scope("prototype")
public class MyOtherBean implements IMyOtherBean {

  private List<String> aCollection;


  /**
   * Make constructor private to prevent people from using the new operator
   */
  private MyOtherBean() {

  }

  @Override
  public List<String> getaCollection() {
    return aCollection;
  }

  @Override
  public void setaCollection(List<String> aCollection) {
    this.aCollection = aCollection;
  }


}
