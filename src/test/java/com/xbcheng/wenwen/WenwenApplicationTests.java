package com.xbcheng.wenwen;

import com.xbcheng.wenwen.async.EventModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WenwenApplicationTests {

    @Test
    public void contextLoads() {

       // Jedis jedis = new Jedis("120.79.198.163",6379);
        //System.out.println(jedis.get("k1"));

    }

}
