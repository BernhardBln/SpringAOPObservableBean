package de.bstreit.java.springaop.observablebean;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.inject.Inject;

import org.jdal.aop.DeclareMixinAutoProxyCreatorConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;


/*
 * rested in this feature too and I already done some work for use it:

 A DelegateFactoryIntroductionInterceptor to support creating delegates from the aspect instance.
 A DeclareMixinAdvisor to join the inteceptor with the type pattern.
 A DeclareMixinAspectJAdvisorFactory to support the DeclareMixin annotation.
 To use it you only need to declare a bean of type DeclareMixinAutoProxyCreatorConfigurer for configuring the AnnotationAwareAspectJAutoProxyCreator with the AdvisorFactory above.

 I'm just testing, but seem that work fin
 */
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
    // INIT
    final List<PropertyChangeEvent> propChangeEvents = Lists.newArrayList();

    final IObservableBean observableBean = (IObservableBean) context.getBean("myBean");

    observableBean.addPropertyChangeListener(new PropertyChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        propChangeEvents.add(evt);
      }

    });

    // RUN
    ((IMyBean) observableBean).setField("xxx");
    ((IMyBean) observableBean).setField("yyy");

    // ASSERT
    assertEquals("Expected two prop change events", 2, propChangeEvents.size());

    assertEquals("Field", propChangeEvents.get(0).getPropertyName());
    assertEquals(null, propChangeEvents.get(0).getOldValue());
    assertEquals("xxx", propChangeEvents.get(0).getNewValue());

    assertEquals("Field", propChangeEvents.get(1).getPropertyName());
    assertEquals("xxx", propChangeEvents.get(1).getOldValue());
    assertEquals("yyy", propChangeEvents.get(1).getNewValue());
  }


  @Bean
  public DeclareMixinAutoProxyCreatorConfigurer getDeclareMixinAutoProxyCreatorConfigurer() {
    return new DeclareMixinAutoProxyCreatorConfigurer();
  }

}
