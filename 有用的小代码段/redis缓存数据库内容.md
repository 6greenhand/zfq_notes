存入：
            redisTemplate.opsForList().rightPushAll("goods:"+ticket,goodsVo);
      redisTemplate.opsForList().leftPushAll("goods:"+ticket,goodsVo);
取出（获取整个列表）：

           redisTemplate.opsForList().range("goods:" + ticket, 0, -1);