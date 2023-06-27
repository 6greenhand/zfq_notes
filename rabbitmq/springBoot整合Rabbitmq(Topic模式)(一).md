## 生产者

#### 第一步、添加依赖

```xml
        <!--rabbitmq-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
```



#### 第二步、添加yaml配置

> rabbitmq默认账号密码就是guest

```yaml
spring:
  #rabbitmq配置
  rabbitmq:
    username: guest
    password: guest
    host: 192.168.221.128
    port: 5672
```



#### 第三步、配置类

##### 1、配置Rabbitmq的Topic交换机和队列的绑定关系

```java
@Configuration
public class RabbitMqConfig {
    /**
     * 定义交换机和队列的名字
     */
    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    public static final String QUEUE_NAME = "boot_queue";

    /**
     * 1、声明交换机
     *
     * @return
     */
    @Bean("bootExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 2、声明队列
     *
     * @return
     */
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    /**
     * 3、队列与交换机进行绑定
     *
     * @param queue    需要绑定队列
     * @param exchange 需要绑定的交换机
     * @return
     */
    @Bean
    public Binding bindQueueExchange(@Qualifier("bootQueue") Queue queue,
                                     @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();
    }

}
```

###### 此配置类的解释：

> 这段代码是一个Spring Boot配置类，用于配置Topic类型的RabbitMQ交换机和队列的绑定关系。
>
> 在该配置类中，首先定义了交换机和队列的名称，分别为`boot_topic_exchange`和`boot_queue`。
>
> 接下来有三个Bean方法：
>
> 1. `bootExchange()` 方法声明了一个名为 `bootExchange` 的交换机。
>    - 使用 `ExchangeBuilder.topicExchange()` 创建一个Topic类型的交换机，通过参数传入交换机的名称 `EXCHANGE_NAME`。
>    - `durable(true)` 表示交换机是持久化的。
> 2. `bootQueue()` 方法声明了一个名为 `bootQueue` 的队列。
>    - 使用 `QueueBuilder.durable()` 创建一个持久化的队列，通过参数传入队列的名称 `QUEUE_NAME`。
> 3. `bindQueueExchange()` 方法将队列和交换机进行绑定。
>    - 通过 `@Qualifier` 注解指定需要绑定的队列和交换机。`@Qualifier("bootQueue")` 表示使用名为 `bootQueue` 的队列，`@Qualifier("bootExchange")` 表示使用名为 `bootExchange` 的交换机。
>    - 使用 `BindingBuilder.bind()` 方法将队列和交换机绑定在一起。
>    - 使用 `with("boot.#").noargs()` 指定了一个通配符匹配键，表示消息的Routing Key符合以 `boot.` 开头的模式才会被路由到该队列。
>
> 以上就是这段代码的主要功能和作用。它定义了一个Topic类型的RabbitMQ交换机和队列的绑定关系，并设置了相应的属性参数。



#### 第四步、注入RabbitTemplate，调用方法，完成消息发送

```java
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Resource
    private RabbitTemplate rabbitTemplate;


    @Test
    public void sendTest() {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "boot.#", "boot.mq");
    }
}
```

上述代码的解释：

> 这段代码使用RabbitMQ的`rabbitTemplate`对象将消息发送到指定的交换机和队列。
>
> 通过`rabbitTemplate.convertAndSend()`方法，传入三个参数：
>
> 1. `RabbitMqConfig.EXCHANGE_NAME`：表示要发送消息的交换机名称，即"boot_topic_exchange"。
> 2. `"boot.#"`：表示要发送消息的Routing Key，这里使用通配符匹配键，只要消息的Routing Key以"boot."开头的都会被路由到相应的队列。
> 3. `"boot.mq"`：表示要发送的消息内容，即"[boot.mq](http://boot.mq/)"。
>
> 根据上述配置，该代码会将消息"[boot.mq](http://boot.mq/)"发送到名为"boot_topic_exchange"的交换机，并且根据Routing Key的模式匹配将消息路由到对应的队列。



## 消费者

#### 第一步、添加依赖

```xml
    <!--rabbitmq-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
```



#### 第二步、添加yaml配置

```yaml
spring:
  #rabbitmq配置
  rabbitmq:
    username: guest
    password: guest
    host: 192.168.221.128
    port: 5672
```



#### 第三步、定义监听类，使用@RabbitListener注解完成队列监听。

总的来说，就是获取发送给队列的消息，然后你可以在这个类中进行处理

```java
@Configuration
public class RabbitMqListener {

    /**
     * 定义队列的监听方法，RabbitListener表示监听哪一个队列
     *
     * @param message
     */
    @RabbitListener(queues = "boot_queue")
    public void ListenerQueue(Message message) {
        System.out.println("message:" + message);
    }
}
```

> 这段代码是一个使用RabbitMQ进行消息队列监听的示例。以下是对代码的解释：
>
> 1. `@Configuration`注解标识这是一个配置类，用于定义Spring应用程序的配置。
> 2. `RabbitMqListener`类是一个Java类，用于定义RabbitMQ队列监听器。
> 3. `@RabbitListener(queues = "boot_queue")`注解表示该方法用于监听名为"boot_queue"的队列。
> 4. `ListenerQueue`方法是具体的监听方法，它接收一个`Message`对象作为参数。当队列中有消息到达时，会调用该方法进行处理。
> 5. 在`ListenerQueue`方法中，打印出了接收到的消息内容。
>
> 通过这段代码，你可以定义一个队列监听方法，并指定要监听的队列名称。当消息到达队列时，会触发相应的处理逻辑。



## 最后，运行生产者测试方法，查看结果