#### 第一步，安装mysql并编写配置

> 安装完创建好用户之后，在my.cnf文件中添加以下配置：
>
> ```cnf
> [mysqld]
> log-bin=mysql-bin
> binlog-format=ROW
> server_id=1
> ```

> 在数据库下执行以下语句可查询是否开启：
>
> ```sql
> SHOW VARIABLES LIKE 'binlog_format';
> SHOW VARIABLES LIKE 'log_bin';
> ```



#### 第二步，安装canal并编写配置



##### 1、修改conf目录下的canal.properties 

```mysql
canal.serverMode = rabbitMQ

rabbitmq.host = 127.0.0.1
rabbitmq.virtual.host = /

# rabbitmq中新建的 Exchange
rabbitmq.exchange = canal_exchange
rabbitmq.username = guest
rabbitmq.password = guest

```

##### 2、修改conf/example下的instance.properties

```mysql
canal.instance.master.address=127.0.0.1:3306
# mysql中配置的用于同步的canal用户
canal.instance.dbUsername=root
canal.instance.dbPassword=123456
# rabbitmq中配置的 绑定的 routingkey
canal.mq.topic=canal_key
```
### 第三步、安装rabbitmq并用创建交换机及队列
> 这里我们直接用java代码，执行程序之前会自动创建exchange和queue并绑定它们
```java
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CanalConfig {

    @Bean
    Queue queue(){
        return  new Queue("canal_queue");
    }

    @Bean
    DirectExchange directExchange(){
        return new DirectExchange("canal_exchange01");
    }

    @Bean
    Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with("canal_key");
    }
}

```

#### 第四步、创建boot项目并添加依赖

```xml
       <!--canal依赖-->
        <dependency>
            <groupId>com.alibaba.otter</groupId>
            <artifactId>canal.client</artifactId>
            <version>1.1.6</version>
        </dependency>
        <!--rabbitmq依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency> 
```

#### 第五步、编写rabbitmq监听器

```java
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Canal消息消费者
 */
@Component
public class CanalComsumer {

        @RabbitHandler
        @RabbitListener(bindings = {@QueueBinding(value = @Queue(value = "canal_queue"), exchange = @Exchange(value = "canal_exchange01"), key = "canal_key")})
        public void process(String testMessage) {
            System.out.println("++++++++++++++++++++++");
            System.out.println("testMessage = " + testMessage);
        }
}
```



> **注意：canal 1.1.5版本之前是不能使用rabbitmq的，所以我们这次使用的是1.1.6版本的**
