### **介绍**

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

本项目采用MinIO构建分布式文件系统，MinIO 是一个非常轻量的服务,可以很简单的和其他应用的结合使用，它兼容亚马逊 S3 云存储服务接口，非常适合于存储大容量非结构化的数据，例如图片、视频、日志文件、备份数据和容器/虚拟机镜像等。

它一大特点就是轻量，使用简单，功能强大，支持各种平台，单个文件最大5TB，兼容 Amazon S3接口，提供了 Java、Python、GO等多版本SDK支持。

官网：https://min.io

中文：https://www.minio.org.cn/，http://docs.minio.org.cn/docs/

 

MinIO集群采用去中心化共享架构，每个结点是对等关系，通过Nginx可对MinIO进行负载均衡访问。

去中心化有什么好处？

在大数据领域，通常的设计理念都是无中心和分布式。Minio分布式模式可以帮助你搭建一个高可用的对象存储服务，你可以使用这些存储设备，而不用考虑其真实物理位置。

它将分布在不同服务器上的多块硬盘组成一个对象存储服务。由于硬盘分布在不同的节点上，分布式Minio避免了单点故障。如下图：

![image-20230510165956933](pages/image-20230510165956933.png)

Minio使用纠删码技术来保护数据，它是一种恢复丢失和损坏数据的数学算法，它将数据分块冗余的分散存储在各各节点的磁盘上，所有的可用磁盘组成一个集合，上图由8块硬盘组成一个集合，当上传一个文件时会通过纠删码算法计算对文件进行分块存储，除了将文件本身分成4个数据块，还会生成4个校验块，数据块和校验块会分散的存储在这8块硬盘上。

使用纠删码的好处是即便丢失一半数量（N/2）的硬盘，仍然可以恢复数据。 比如上边集合中有4个以内的硬盘损害仍可保证数据恢复，不影响上传和下载，如果多于一半的硬盘坏了则无法恢复。

 

#### **3.2.2** **数据恢复演示**

下边在本机演示MinIO恢复数据的过程，在本地创建4个目录表示4个硬盘。

![img](pages/clip_image002.gif)

下载minio，下载地址在https://dl.min.io/server/minio/release/，可从课程资料找到MinIO的安装文件minio.zip解压即可使用，CMD进入有minio.exe的目录，运行下边的命令：

```shell
minio.exe server E:\utils\minio\minio_data\data1  E:\utils\minio\minio_data\data2  E:\utils\minio\minio_data\data3 E:\utils\minio\minio_data\data4
```

启动结果如下：

![image-20230510170323703](pages/image-20230510170323703.png)

说明如下：

```sql
WARNING: MINIO_ACCESS_KEY and MINIO_SECRET_KEY are deprecated.
         Please use MINIO_ROOT_USER and MINIO_ROOT_PASSWORD
Formatting 1st pool, 1 set(s), 4 drives per set.
WARNING: Host local has more than 2 drives of set. A host failure will result in data becoming unavailable.
WARNING: Detected default credentials 'minioadmin:minioadmin', we recommend that you change these values with 'MINIO_ROOT_USER' and 'MINIO_ROOT_PASSWORD' environment variables

```

1）老版本使用的MINIO_ACCESS_KEY 和 MINIO_SECRET_KEY不推荐使用，推荐使用MINIO_ROOT_USER 和MINIO_ROOT_PASSWORD设置账号和密码。

2）pool即minio节点组成的池子，当前有一个pool和4个硬盘组成的set集合

3）因为集合是4个硬盘，大于2的硬盘损坏数据将无法恢复。

4）账号和密码默认为minioadmin、minioadmin，可以在环境变量中设置通过'MINIO_ROOT_USER' and 'MINIO_ROOT_PASSWORD' 进行设置。

下边输入http://localhost:9000进行登录，账号和密码为：minioadmin/minioadmin

 

![img](pages/clip_image002-16837094572032.gif)

 

登录成功：

![img](pages/clip_image004.gif)

下一步创建bucket，桶，它相当于存储文件的目录，可以创建若干的桶。

![img](pages/clip_image006.gif)

输入bucket的名称，点击“CreateBucket”，创建成功

![img](pages/clip_image008.gif)

点击“upload”上传文件。

下边上传几个文件 

![img](pages/clip_image010.gif)

 

下边去四个目录观察文件的存储情况

![img](pages/clip_image012.gif)

我们发现上传的1.mp4文件存储在了四个目录，即四个硬盘上。

下边测试minio的数据恢复过程：

1、首先删除一个目录。

删除目录后仍然可以在web控制台上传文件和下载文件。

稍等片刻删除的目录自动恢复。

2、删除两个目录。

删除两个目录也会自动恢复。

3、删除三个目录 。

由于 集合中共有4块硬盘，有大于一半的硬盘损坏数据无法恢复。

此时报错：We encountered an internal error, please try again. (Read failed. Insufficient number of drives online)在线驱动器数量不足。

#### **3.2.3** **测试****Docker****环境**

开发阶段和生产阶段统一使用Docker下的MINIO。

在下发的虚拟机中已安装了MinIO的镜像和容器，执行sh /data/soft /restart.sh启动Docker下的MinIO

启动完成登录MinIO查看是否正常。

访问http://192.168.101.65:9000

![img](pages/clip_image002-16837095640119.gif)

本项目创建两个buckets：

mediafiles： 普通文件

video：视频文件

 

#### **3.2.4 SDK**

##### **3.2.4.1上传文件**

MinIO提供多个语言版本SDK的支持，下边找到java版本的文档：

地址：https://docs.min.io/docs/java-client-quickstart-guide.html

最低需求Java 1.8或更高版本:

maven依赖如下：

```xml
<dependency>
    <groupId>io.minio</groupId>
    <artifactId>minio</artifactId>
    <version>8.4.3</version>
</dependency>
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.8.1</version>
</dependency>

```

在media-service工程添加此依赖。

参数说明：

需要三个参数才能连接到minio服务。

| 参数        | 说明                                          |
| ----------- | --------------------------------------------- |
| Endpoint    | 对象存储服务的URL                             |
| Access  Key | Access  key就像用户ID，可以唯一标识你的账户。 |
| Secret  Key | Secret  key是你账户的密码。                   |

 

官方的示例代码如下：

```java
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
public class FileUploader {
  public static void main(String[] args)throws IOException, NoSuchAlgorithmException, InvalidKeyException {
    try {
      // Create a minioClient with the MinIO server playground, its access key and secret key.
      MinioClient minioClient =
          MinioClient.builder()
              .endpoint("https://play.min.io")
              .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
              .build();
      // Make 'asiatrip' bucket if not exist.
      boolean found =
          minioClient.bucketExists(BucketExistsArgs.builder().bucket("asiatrip").build());
      if (!found) {
        // Make a new bucket called 'asiatrip'.
        minioClient.makeBucket(MakeBucketArgs.builder().bucket("asiatrip").build());
      } else {
        System.out.println("Bucket 'asiatrip' already exists.");
      }
      // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
      // 'asiatrip'.
      minioClient.uploadObject(
          UploadObjectArgs.builder()
              .bucket("asiatrip")
              .object("asiaphotos-2015.zip")
              .filename("/home/user/Photos/asiaphotos.zip")
              .build());
      System.out.println(
          "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
              + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
    } catch (MinioException e) {
      System.out.println("Error occurred: " + e);
      System.out.println("HTTP trace: " + e.httpTrace());
    }
  }
}

```

参考示例在media-service工程中 测试上传文件功能，

首先创建一个用于测试的bucket

![img](pages/clip_image004-168370956401110.gif)

点击“Manage”修改bucket的访问权限

![img](pages/clip_image006-168370956401111.gif)

选择public权限

![img](pages/clip_image008-168370956401112.gif)

在xuecheng-plus-media-service工程 的test下编写测试代码如下：

```java
package com.xuecheng.media;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @description 测试MinIO
 * @author Mr.M
 * @date 2022/9/11 21:24
 * @version 1.0
 */
public class MinioTest {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://192.168.101.65:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

   //上传文件
    @Test
    public  void upload() {
        try {
            UploadObjectArgs testbucket = UploadObjectArgs.builder()
                    .bucket("testbucket")
//                    .object("test001.mp4")
                    .object("001/test001.mp4")//添加子目录
                    .filename("D:\\develop\\upload\\1mp4.temp")
                    .contentType("video/mp4")//默认根据扩展名确定文件内容类型，也可以指定
                    .build();
            minioClient.uploadObject(testbucket);
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }

    }

}

```

执行upload方法，分别测试向桶的根目录上传文件以及子目录上传文件。

上传成功，通过web控制台查看文件，并预览文件。

说明：

设置contentType可以通过com.j256.simplemagic.ContentType枚举类查看常用的mimeType（媒体类型）

通过扩展名得到mimeType，代码如下：

```java
    //根据扩展名取出mimeType
    ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
    String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流

```

完善上边的代码 如下：

```java
@Test
    public  void upload() {
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(".mp4");
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流
        if(extensionMatch!=null){
            mimeType = extensionMatch.getMimeType();
        }
        try {
            UploadObjectArgs testbucket = UploadObjectArgs.builder()
                    .bucket("testbucket")
//                    .object("test001.mp4")
                    .object("001/test001.mp4")//添加子目录
                    .filename("D:\\develop\\upload\\1mp4.temp")
                    .contentType(mimeType)//默认根据扩展名确定文件内容类型，也可以指定
                    .build();
            minioClient.uploadObject(testbucket);
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
        }

    }

```

 

**3.2.4.2** **删除文件**

下边测试删除文件

参考：https://docs.min.io/docs/java-client-api-reference#removeObject

 

```java
@Test
public void delete(){
    try {
        minioClient.removeObject(
               RemoveObjectArgs.builder().bucket("testbucket").object("001/test001.mp4").build());
        System.out.println("删除成功");
    } catch (Exception e) {
       e.printStackTrace();
        System.out.println("删除失败");
    }
}

```

**3.2.4.3** **查询文件**

通过查询文件查看文件是否存在minio中。

参考：https://docs.min.io/docs/java-client-api-reference#getObject

```java
//查询文件
@Test
public void getFile() {
    GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket("testbucket").object("test001.mp4").build();
    try(
        FilterInputStream inputStream = minioClient.getObject(getObjectArgs);
        FileOutputStream outputStream = new FileOutputStream(new File("D:\\develop\\upload\\1_2.mp4"));
     ) {
        IOUtils.copy(inputStream,outputStream);
     } catch (Exception e) {
        e.printStackTrace();
     }
}

```

​     

校验文件的完整性，对文件计算出md5值，比较原始文件的md5和目标文件的md5，一致则说明完整



```java
//校验文件的完整性对文件的内容进行md5
FileInputStream fileInputStream1 = new FileInputStream(new File("D:\\develop\\upload\\1.mp4"));
String source_md5 = DigestUtils.md5Hex(fileInputStream1);
FileInputStream fileInputStream = new FileInputStream(new File("D:\\develop\\upload\\1a.mp4"));
String local_md5 = DigestUtils.md5Hex(fileInputStream);
if(source_md5.equals(local_md5)){
    System.out.println("下载成功");
}
```

