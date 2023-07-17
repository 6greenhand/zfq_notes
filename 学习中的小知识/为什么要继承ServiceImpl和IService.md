原来是这样的：

​    接口**继承Iservice<>**

![image-20230527235907916](pages/image-20230527235907916.png)

上面的接口对应的service**继承ServiceImpl<>**

![image-20230527235946478](pages/image-20230527235946478.png)

一开始没搞懂为什么要加这两个继承的原因，后面才知道。

如果你把service继承的**ServiceImpl<>删掉**，他会爆红

![image-20230528000020006](pages/image-20230528000020006.png)

然后就会让你 重写 **接口继承的Iservice带来的基本的crud**

![image-20230528000052461](pages/image-20230528000052461.png)

**简而言之：**就是service继承serviceimpl你就不用实现接口继承iservice带来的那些简单的crud的方法了(所以这两个继承我认为以后必须每个都写上，很好用)