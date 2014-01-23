package de.bstreit.java.springaop.test.observablebean;

import static org.junit.Assert.assertTrue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.bstreit.java.springaop.observablebean.EnableObservableBeans;
import de.bstreit.java.springaop.observablebean.IObservableBean;


@Configuration
@Import(EnableObservableBeans.class)
@ComponentScan
public class SimpleTest {

  private ConfigurableApplicationContext context;


  @Test
  public void simpleTest() {
    // INIT
    context = new AnnotationConfigApplicationContext(SimpleTest.class);

    final IMyBean myBean = context.getBean(IMyBean.class);
    final StringBuilder stringBuilder = new StringBuilder();

    addPropertyChangeListener(myBean, stringBuilder);

    // RUN
    myBean.setField("xxx");

    // ASSERT
    final String result = stringBuilder.toString();
    final String expectedValue = "java.beans.PropertyChangeEvent[propertyName=Field; oldValue=null; newValue=xxx;";
    assertTrue(result.startsWith(expectedValue));
  }


  private void addPropertyChangeListener(final IMyBean myBean, final StringBuilder stringBuilder) {
    ((IObservableBean) myBean).addPropertyChangeListener(new PropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        stringBuilder.append(evt);
      }

    });
  }


}
