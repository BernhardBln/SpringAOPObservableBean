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
