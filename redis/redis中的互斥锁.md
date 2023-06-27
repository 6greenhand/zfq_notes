思路是

1. 当有审核的请求线程时，先通过订单编号(订单的唯一索引)往redis中set一组值(使用

   ```java
   RedisTemplate.opsForValue().setIfAbsent(key, value)
   ```

   ​       这个就相当于redis命令中的setnx, 如果已经存在key，返回false且不做任何改变，不存在就将 key 的值设为 value)，在这里我把订单编号作为key，set成功后在设置一个过期时间(为了避免死锁)

2. 当1返回true时代表加锁成功，当前请求线程继续执行，执行结束后需要释放锁,即删除redis中的key

3. 当1返回false时，等待,继续执行2

这是锁实现

```java
  private final static String LOCK_PREFIX = "LOCK:";
20 
21     @Autowired
22     private RedisTemplate<String, String> redisTemplate;
23 
24     public boolean lock(String key) {
25         while (true) {
26             try {
27                 if (setIfAbsent(key)) {
28                     return true;
29                 }
30                 Thread.sleep(100);
31             } catch (Exception e) {
32                 return false;
33             } finally {
34                 unlock(key);
35             }
36         }
37     }
38 
39     private synchronized boolean setIfAbsent(String key) {
40         try {
               //这个key存在值就返回false,不存在值就返回true，并赋值1
41             Boolean locked = redisTemplate.opsForValue().setIfAbsent(LOCK_PREFIX + key, key);
42             if (locked != null && locked) {
43                 redisTemplate.expire(LOCK_PREFIX + key, 120, TimeUnit.SECONDS);
44                 return true;
45             }
46         } finally {
47             unlock(key);
48         }
49         return false;
50     }
51 
52     public void unlock(String key) {
53         redisTemplate.delete(LOCK_PREFIX + key);
54     }
```

这种方案不好的地方在于，set和expire操作不是原子的，于是setIfAbsent()方法是互斥的，并发性能并不是很好