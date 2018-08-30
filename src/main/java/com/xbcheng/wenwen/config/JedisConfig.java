package com.xbcheng.wenwen.config;

import com.xbcheng.wenwen.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value(("${spring.redis.password}"))
    private String password;

    @Value("${spring.redis.poolMaxTotal}")
    private int maxTotal;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.poolMaxIdle}")
    private int maxIdle;

    @Value("${spring.redis.poolMaxWait}")
    private long maxWaitMillis;



    @Bean
    public JedisPool redisPoolFactory(){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        return new JedisPool(jedisPoolConfig,host,port,timeout,password);
    }


}
