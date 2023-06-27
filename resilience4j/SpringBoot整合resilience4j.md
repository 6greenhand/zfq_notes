#### 第一步、加依赖下面的必须每个都加上

##### 注意（重点）：

> 该模块期望 `org.springframework.boot:spring-boot-starter-actuator` 和 `org.springframework.boot:spring-boot-starter-aop` 已经在运行时提供。 如果你在 spring boot2 中使用 *webflux*，你还需要 `io.github.resilience4j:resilience4j-reactor`

```xml
<!--        actuator依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
<!--        aop依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
<!--        resilience4j整合springboot2依赖-->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot2</artifactId>
            <version>1.7.1</version>
        </dependency>
```

> 解释依赖：
>
> 在Spring Boot中整合Resilience4j时，需要引入以下几个依赖，并逐个解释它们的作用：
>
> 1. `spring-boot-starter-actuator`：该依赖提供了一组用于监控和管理应用程序的端点（endpoints），例如/health、/info等。在Resilience4j中，通过这些端点可以获取限流器的状态信息、指标数据等。
>
>    > springboot整合resilience4j的限流操作一定要有actuator吗？
>    >
>    > ​       不，Spring Boot整合Resilience4j的限流操作并不一定需要使用Actuator。Actuator是Spring Boot提供的监控和管理功能的扩展库，通过暴露一组端点（endpoints）可以获取应用程序的健康状态、运行指标等信息。当使用Resilience4j进行限流时，Actuator可以用于查看限流器的状态信息、触发重置操作等。但如果您不需要使用Actuator提供的监控功能，也可以不引入`spring-boot-starter-actuator`依赖，仅使用`spring-boot-starter-aop`和`resilience4j-spring-boot2`依赖即可配置和使用Resilience4j的限流功能。
>
> 2. `spring-boot-starter-aop`：AOP（面向切面编程）是一种编程思想，可以在应用程序运行期间将通用的横切逻辑与业务逻辑分离开来。在Resilience4j中，使用AOP可以方便地将限流、重试等功能织入到应用程序的方法调用中。
>
>    > springboot整合resilience4j的限流操作一定要有aop吗?       
>    >
>    > Resilience4j通过AOP将限流逻辑织入到应用程序的方法调用中，以实现限流功能。通过配置方法级别的注解或者在代码中手动创建限流器，Resilience4j可以对指定的方法进行限流控制。而AOP则可以将这些限流逻辑与业务逻辑分离开来，使得限流的处理变得简洁和高效。
>    >
>    > ​     因此，为了在Spring Boot项目中使用Resilience4j的限流功能，一般会引入`spring-boot-starter-aop`依赖，以支持AOP的使用。通过AOP的方式，可以在方法调用之前对其应用限流策略，确保系统在高负载情况下能够有效地进行限流保护。
>
> 3. `resilience4j-spring-boot2`：这是Resilience4j与Spring Boot 2.x版本集成的库。它提供了自动配置、指标收集、事件监听等功能，简化了Resilience4j的使用。通过该依赖，您可以在Spring Boot应用程序中轻松地配置和管理Resilience4j的组件，如限流器、重试器、熔断器等。
>
> 这些依赖的引入使得在Spring Boot项目中使用Resilience4j变得更加便捷和高效，同时可以借助Spring Boot的其他特性来优化和管理应用程序的运行。
>
> > 反正全加了肯定对

#### 第二步、编写yaml文件

##### 1、限流

```bash
resilience4j.ratelimiter:
    instances:
      backendA:
        limitForPeriod: 10
        limitRefreshPeriod: 1s
        timeoutDuration: 0
        registerHealthIndicator: true
        eventConsumerBufferSize: 100
     backendB:
        limitForPeriod: 6
        limitRefreshPeriod: 500ms
        timeoutDuration: 3s
```

> 解释限流pom配置：您可以配置以下属性：
>
> - `limitForPeriod`：在限定时间周期内允许的最大请求量。
> - `limitRefreshPeriod`：重新填充令牌桶的时间周期。也就是说，在这个时间间隔内，令牌桶会被重新填充到`limitForPeriod`的大小。
> - `timeoutDuration`：请求等待限流器可用令牌的超时时间。如果在超时时间内无法获取到令牌，则请求可能会被拒绝。
> - `registerHealthIndicator`：是否注册健康指示器，用于监控限流器的状态。
> - `eventConsumerBufferSize`：事件消费者缓冲区的大小。用于存储触发的限流事件，如果缓冲区已满，新的事件可能会被丢弃。
>
> 上述配置示例中，`backendA`的限流器配置如下：
>
> - `limitForPeriod`为10，表示在1秒内最多允许10个请求通过。
> - `limitRefreshPeriod`为1秒，每隔1秒重新填充令牌桶。
> - `timeoutDuration`为0，表示请求将立即失败而不会等待可用令牌。
>
> `backendB`的限流器配置如下：
>
> - `limitForPeriod`为6，表示在500毫秒内最多允许6个请求通过。
> - `limitRefreshPeriod`为500毫秒，每隔500毫秒重新填充令牌桶。
> - `timeoutDuration`为3秒，如果在3秒内无法获取到令牌，则请求会超时。
>
> 这些配置可以根据您的具体需求进行调整。限流器配置的目的是限制应用程序对某个资源的并发访问量，以保护该资源免受过载和崩溃的风险。



#### 第三步、加注解

##### 1、限流

```java
    @RateLimiter(name = "backendA")
    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, HttpServletRequest request, long goodsId){
     ......
    }
```

