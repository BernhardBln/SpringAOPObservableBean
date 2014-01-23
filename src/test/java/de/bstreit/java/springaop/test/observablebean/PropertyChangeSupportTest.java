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
package de.bstreit.java.springaop.test.observablebean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.bstreit.java.springaop.observablebean.EnableObservableBeans;
import de.bstreit.java.springaop.observablebean.IObservableBean;


/*
 * rested in this feature too and I already done some work for use it:

 A DelegateFactoryIntroductionInterceptor to support creating delegates from the aspect instance.
 A DeclareMixinAdvisor to join the inteceptor with the type pattern.
 A DeclareMixinAspectJAdvisorFactory to support the DeclareMixin annotation.
 To use it you only need to declare a bean of type DeclareMixinAutoProxyCreatorConfigurer for configuring the AnnotationAwareAspectJAutoProxyCreator with the AdvisorFactory above.

 I'm just testing, but seem that work fin
 */

@Configuration
@Import(EnableObservableBeans.class)
@ComponentScan()
@ContextConfiguration(classes = { PropertyChangeSupportTest.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class PropertyChangeSupportTest implements PropertyChangeListener {

  @Inject
  private ConfigurableApplicationContext context;

  private List<PropertyChangeEvent> propChangeEvents = new ArrayList<PropertyChangeEvent>();
  private int propChangeEventIdx = 0;


  /**
   * Test that we get all property change events, in correct order
   */
  @Test
  public void testInterfaceMixinOneBean() {
    // INIT
    final IMyBean observableBean = context.getBean(IMyBean.class);

    ((IObservableBean) observableBean).addPropertyChangeListener(this);

    // RUN
    observableBean.setField("xxx");
    observableBean.setAnotherField(1);
    observableBean.setField("yyy");
    observableBean.setAnotherField(7);

    // ASSERT
    assertEquals(4, propChangeEvents.size());

    checkPropChangeEvent("Field", null, "xxx");
    checkPropChangeEvent("AnotherField", 0, 1);
    checkPropChangeEvent("Field", "xxx", "yyy");
    checkPropChangeEvent("AnotherField", 1, 7);
  }

  /**
   * Test that in case we only register for a certain property, we do not
   * receive property change events for other properties.
   */
  @Test
  public void testPropertyChangeListenerFilter() {
    // INIT
    final IMyBean observableBean = context.getBean(IMyBean.class);

    // only listen to property change events for field "AnotherField"
    ((IObservableBean) observableBean).addPropertyChangeListener("AnotherField", this);

    // RUN
    observableBean.setField("xxx");
    observableBean.setAnotherField(1);
    observableBean.setField("yyy");
    observableBean.setAnotherField(7);

    // ASSERT
    // should be 2 events, not 4, as we only registered for "AnotherField"
    assertEquals(2, propChangeEvents.size());

    checkPropChangeEvent("AnotherField", 0, 1);
    checkPropChangeEvent("AnotherField", 1, 7);
  }


  /**
   * Now we have two different beans; check that the property change events
   * arrive for both and in correct order.
   */
  @Test
  public void testInterfaceMixinTwoBeansSameType() {
    // INIT
    final IMyBean observableBean1 = context.getBean(IMyBean.class);
    final IMyBean observableBean2 = context.getBean(IMyBean.class);


    assertNotSame(observableBean1, observableBean2);

    ((IObservableBean) observableBean1).addPropertyChangeListener(this);
    ((IObservableBean) observableBean2).addPropertyChangeListener(this);

    // RUN
    observableBean1.setField("xxx");
    observableBean2.setField("abc");
    observableBean1.setField("yyy");
    observableBean2.setField("def");

    // ASSERT
    assertEquals(4, propChangeEvents.size());

    checkPropChangeEvent("Field", null, "xxx");
    checkPropChangeEvent("Field", null, "abc");
    checkPropChangeEvent("Field", "xxx", "yyy");
    checkPropChangeEvent("Field", "abc", "def");
  }

  /**
   * Two different beans, but we only registered to listen for property change
   * events from the first bean. Make sure we do not receive events from the
   * second bean.
   */
  @Test
  public void testInterfaceMixinTwoBeansOnlyListeningToOne() {
    // INIT
    final IMyBean observableBean1 = context.getBean(IMyBean.class);
    final IMyBean observableBean2 = context.getBean(IMyBean.class);


    assertNotSame(observableBean1, observableBean2);

    ((IObservableBean) observableBean1).addPropertyChangeListener(this);

    // RUN
    observableBean1.setField("xxx");
    observableBean2.setField("abc");
    observableBean1.setField("yyy");
    observableBean2.setField("def");

    // ASSERT
    assertEquals(2, propChangeEvents.size());

    checkPropChangeEvent("Field", null, "xxx");
    checkPropChangeEvent("Field", "xxx", "yyy");
  }

  @Test
  public void collectionPropChangeEvent() {
    // INIT
    final IMyOtherBean observableBean = context.getBean(IMyOtherBean.class);


    ((IObservableBean) observableBean).addPropertyChangeListener(this);

    // RUN
    observableBean.setaCollection(Arrays.asList("xx", "yy"));
    observableBean.setaCollection(Arrays.asList("yy", "zz"));

    // ASSERT
    assertEquals("Expected two prop change events", 2,
        propChangeEvents.size());

    checkPropChangeEvent("aCollection", null, Arrays.asList("xx", "yy"));
    checkPropChangeEvent("aCollection", Arrays.asList("xx", "yy"), Arrays.asList("yy", "zz"));
  }

  @Test
  public void receiveEventsTwice() {
    // INIT
    final IMyBean observableBean = context.getBean(IMyBean.class);

    // adding twice - hence we should get every event twice!
    ((IObservableBean) observableBean).addPropertyChangeListener(this);
    ((IObservableBean) observableBean).addPropertyChangeListener(this);

    // RUN
    observableBean.setField("xxx");
    observableBean.setField("abc");

    // ASSERT
    assertEquals(4, propChangeEvents.size());

    checkPropChangeEvent("Field", null, "xxx");
    checkPropChangeEvent("Field", null, "xxx");
    checkPropChangeEvent("Field", "xxx", "abc");
    checkPropChangeEvent("Field", "xxx", "abc");
  }


  @Test
  public void testUnregister() {
    // INIT
    final IMyBean observableBean = context.getBean(IMyBean.class);

    // adding and removing - there should be no events coming!
    ((IObservableBean) observableBean).addPropertyChangeListener(this);
    ((IObservableBean) observableBean).removePropertyChangeListener(this);

    // RUN
    observableBean.setField("xxx");
    observableBean.setField("abc");

    // ASSERT
    assertEquals(0, propChangeEvents.size());
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    propChangeEvents.add(evt);
  }

  private void checkPropChangeEvent(final String fieldName,
      final Object oldValue, Object newValue) {

    final PropertyChangeEvent propChangeEvent = propChangeEvents.get(propChangeEventIdx++);

    assertEquals(fieldName, propChangeEvent.getPropertyName());
    assertEquals(oldValue, propChangeEvent.getOldValue());
    assertEquals(newValue, propChangeEvent.getNewValue());

  }


}
