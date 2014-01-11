package de.bstreit.java.springaop.observablebean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PropertyChangeSupportTest.class })
@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class PropertyChangeSupportTest {

  @Inject
  private ConfigurableApplicationContext context;


  @Test
  public void testInterfaceMixin() {
    final MyBean bean = context.getBean(MyBean.class);

    final IObservableBean observableBean = (IObservableBean) bean;
    observableBean.addPropertyChangeListener(new PropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("PropChange: " + evt);
      }
    });

    bean.setField("xxx");
  }

}
