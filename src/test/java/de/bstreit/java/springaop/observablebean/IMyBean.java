package de.bstreit.java.springaop.observablebean;

@ObservableBean
public interface IMyBean {

  public abstract String getField();

  public abstract void setField(String field);

}