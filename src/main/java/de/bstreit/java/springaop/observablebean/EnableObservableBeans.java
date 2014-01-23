package de.bstreit.java.springaop.observablebean;

import org.jdal.aop.DeclareMixinAutoProxyCreatorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <p>
 * Import this configuration class to enable the DeclareMixin annotation which
 * is necessary to make the {@link ObservableBeanAspectMixin} work.
 * </p>
 * <p>
 * If you use a spring configuration class, you can use the import annotation:
 * </p>
 * 
 * <pre>
 * &#64;Configuration
 * &#64;Import(EnableObservableBeans.class)
 * [...]
 * public class MyConfigClass { ... }
 * </pre>
 * 
 * @author Bernhard Streit (Bernhard.Streit+github@gmail.com)
 */
@Configuration
public class EnableObservableBeans {

  @Bean
  public DeclareMixinAutoProxyCreatorConfigurer getDeclareMixinAutoProxyCreatorConfigurer() {
    return new DeclareMixinAutoProxyCreatorConfigurer();
  }

  @Bean
  public ObservableBeanAspectMixin observableBeanAspectMixin() {
    return new ObservableBeanAspectMixin();
  }
}
