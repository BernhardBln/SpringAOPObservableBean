# SpringAOPObservableBeanAspect

**WARNING - This is work in progress and a proof of concept at the moment. Use this code with lots of care!**

Automatically enhances Java Beans to fire property change events when the fields are changed via set*-Methods, 
and provides methods to register property change listeners. 

Works with Spring AOP - it is not necessary to use the aspectj compiler.

This code - so far - is **not thread-safe**.

Refer to the tests in src/test/java to see how to use this.

You can find the git repository under https://github.com/BernhardBln/SpringAOPObservableBean.git

You may contact the author via email: Bernhard.Streit+springaop@gmail.com

## Usage

Clone this git repository, change into its project directory (where you have the pom.xml file) and run:

    mvn install
    
This will generate the jar and install it into your local maven repository.

Reference the jar in your pom:

    <dependency>
        <artifactId>springaop-observable-bean-aspect</artifactId>
        <groupId>de.bstreit.java.springaop.observablebean</groupId>
        <version>1.0.0-M1</version>
    </dependency>
    
In your spring configuration, declare the DeclareMixinAutoProxyCreatorConfigurer as a bean, or alternatively, import the
EnableObservableBeans configuration:

    @Configuration
    @Import(EnableObservableBeans.class)
    ...
    public class MySpringConfig { ... }
    
Then you can annotate your beans with @ObservableBean in order to make them fire property change events.

Since we are not using the aspectj compiler here, keep in mind that you need to retrieve your bean using a spring context (to 
remember this, make sure you only have the default constructor in your beans, and make it private). Further, you need to
extract an interface with all the getters and setters for your beans (and only use the interfaces in your code). 

Also keep in mind that the default scope for spring beans is singleton, so in case you want the context to return a new bean
every time you call context.getBean, you might want to change its scope to prototype, e.g. by adding @Scope("prototype"). 

In order to register listeners, you can safely cast the proxied beans to "IObservableBean".

See src/test/java for a running example.

You will probably find that your beans are getting a strong dependency on Spring (at least when using annotations), 
but on the other hand, in your project, you can just refer to the interfaces and keep the actual bean implementations 
separated from this.

## Limitations

### Only flat hierarchies
 
This currently only observes changes in beans which occur through the setter methods:

    bean.setProperty("xxx");

It does **not** observe deeper changes:
 
    bean.getUserList().add(new User());
    bean.getSomeUser().setName("xxx");
    
Possible workarounds:

1. Add @Observable to all classes which are used for properties of the bean (but don't forget to register listeners there as well!)
2. Artificially (and unnecessary) call the set methods in order to receive coarse-grained change listeners:

    User u = bean.getSomeUser();
    u.setName("xxx");
    bean.setSomeUser(u); // fires a property change event for the someUser field (but not, which would be more precise, of "someUser.name")

### Can only be used when extracting an interface from the bean

I think technically there is no workaround unless you use the aspectj compiler. Surprise me with the opposite ;-) 

## Notice

This software was inspired by https://github.com/damnhandy/Handy-Aspects by Ryan J. McDonough, which requires you to use the AspectJ compiler as part of the build process, however.

We use the DeclareMixinAutoProxyCreatorConfigurer provided by the JDAL Project (http://www.jdal.org). Many thanks to José Luis Martín for his support!

## Licence

Copyright 2014 Bernhard Streit (Bernhard.Streit+springaop@gmail.com)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
