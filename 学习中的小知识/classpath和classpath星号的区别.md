

#### classpath和classpath*的区别

在Java项目中，classpath是指类加载器查找类的路径，也就是用来加载Java类的路径。简单来说，classpath就是应用程序所搜索类、接口、资源等的路径。

而classpath*，则表示在该路径下查找所有的类，包括子目录下的类文件，递归查找所有的类文件。

举个例子，如果指定classpath为"/home/user/myproject/classes"，那么使用classpath加载类的时候，只能查找该目录下的类，子目录下的类无法被加载；如果使用classpath*，则可以查找该目录及其子目录下的所有类。

> 简单来说，classpath**会递归查找指定路径下的类文件，而classpath只会在指定路径中查找类文件。如果需要查找一个目录下的所有类文件，就需要使用classpath*。

![image-20230619195720467](pages/image-20230619195720467.png)

**注意： 用classpath*:需要遍历所有的classpath，所以加载速度是很慢的；**

**因此，在规划的时候，应该尽可能规划好资源文件所在的路径，尽量避免使用classpath*。**