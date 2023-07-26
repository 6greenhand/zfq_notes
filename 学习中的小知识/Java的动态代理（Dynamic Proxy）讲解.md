Java的动态代理（Dynamic Proxy）是一种在运行时生成代理类的机制，可以用于生成实现指定接口的代理对象。动态代理通常结合反射机制使用，可以在运行时利用一个代理，访问方法调用，并将其转发到一个或多个具体的执行对象（即被代理对象）上去。

Java中的动态代理提供了两个基本的接口：InvocationHandler和Proxy。其中，InvocationHandler是代理对象所调用的方法的处理器，它定义了一个invoke()方法，用于拦截被代理对象的方法调用并进行处理。而Proxy则是一个工厂类，通过调用它的newProxyInstance()方法创建代理对象实例。

使用动态代理的具体步骤如下：

1. 定义一个接口和实现类，以及一个实现InvocationHandler接口的拦截器类。
2. 在客户端代码中，创建一个InvocationHandler对象并将目标对象传入。
3. 调用Proxy.newProxyInstance()方法生成代理实例，该方法需要传入以下参数：
   - 第一个参数：代理类加载器；
   - 第二个参数：一个Class[]数组，包含所有要代理的接口；
   - 第三个参数：InvocationHandler实现类的实例。
4. 使用代理实例来调用接口方法。

例子：

```java
public interface UserService {
    void addUser(String name);
}

public class UserServiceImpl implements UserService {
    public void addUser(String name) {
        System.out.println("Add user "  + name);
    }
}

public class UserServiceProxy implements InvocationHandler {
    private Object target;

    public UserServiceProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, 
                         Object[] args) throws Throwable {
        System.out.println("Before calling " + method.getName());
        //执行方法
        Object result = method.invoke(target, args);
        System.out.println("After calling " + method.getName());
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        InvocationHandler handler = new UserServiceProxy(userService);
        UserService proxy = (UserService) Proxy.newProxyInstance(
            userService.getClass().getClassLoader(),
            userService.getClass().getInterfaces(),
            handler);
        proxy.addUser("Alice");
    }
}
```

代理类是在运行时动态生成的，它会实现目标接口并重写其中的方法。在调用代理类的方法时，实际上是通过InvocationHandler中的invoke()方法进行处理，并最终将方法调用委托给原始对象。通过使用动态代理，我们可以对某个类的方法调用进行拦截、处理和增强，从而实现一些更高层次的控制和管理。

**这里是dream佬的话，一点就通：**

![image-20230526213213471](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20230526213213471.png)