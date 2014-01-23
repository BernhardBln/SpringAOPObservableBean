package de.bstreit.java.springaop.observablebean;

import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.WeakHashMap;

import javax.inject.Named;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareMixin;

/**
 * This aspect enables the automatic property change support and the mixes in
 * the {@link PropertyChangeSupport}.
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
@Aspect
@Named
public class ObservableBeanAspectMixin {

  private final Map<Object, PropertyChangeSupportWithInterface> weakReferences = new WeakHashMap<Object, PropertyChangeSupportWithInterface>();


  @DeclareMixin("@de.bstreit.java.springaop.observablebean.ObservableBean *")
  public IObservableBean createDelegate(Object object) {
    final PropertyChangeSupportWithInterface pcs = new PropertyChangeSupportWithInterface(object);
    weakReferences.put(object, pcs);
    return pcs;
  }

  /**
   * 
   * @param pjp
   * @throws Throwable
   *           not expected to happen with simple beans!
   */
  @Around("execution (* @de.bstreit.java.springaop.observablebean.ObservableBean *.set*(..))")
  public void handleSetterInvocation(ProceedingJoinPoint pjp) throws Throwable {
    // the target is the actual bean, with the set and get methods
    final Object target = pjp.getTarget();

    /*
     * It seems that aop does not create the mixin unless at least one of the
     * IObservableBean methods (e.g. addPropertyChangeListener) is actually
     * called.
     * 
     * That certainly reduces overhead; we must, however, take into
     * consideration that in this case, we do not have a
     * PropertyChangeSupportWithInterface instance and need to call the setter
     * ourself, by calling pjp.proceed().
     */
    final boolean mixinExists = weakReferences.containsKey(target);

    if (mixinExists) {
      final PropertyChangeSupportWithInterface mixin = weakReferences.get(target);
      mixin.handleSetterInvocation(pjp);
    } else {
      // No listeners at all, simply call setter without firing property change
      // events
      pjp.proceed();
    }

  }
}
