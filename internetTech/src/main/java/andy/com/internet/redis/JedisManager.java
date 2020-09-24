package andy.com.internet.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JedisManager {
    private int maxTotal = 200;
    private int poolMaxWaitMs = 10;
    private int timeOutMs = 100;
    private String ip;
    private int port;
    private String password;

    private JedisPool jedisPool = null;


    public void init(String ip, int port) {
        this.ip = ip;
        this.port = port;
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxWaitMillis(poolMaxWaitMs);
        config.setMaxIdle(maxTotal);
        config.setMinIdle(4);
        config.setBlockWhenExhausted(true);
        config.setTestWhileIdle(true);
        if (password == null || password.trim().equals("")) {
            password = null;
        }
        jedisPool = new JedisPool(config, ip, port, timeOutMs, password);
    }

    public void destroy() {
        jedisPool.close();
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }


    public void batchWriteCache(ArrayList<String> keys, ArrayList<String> values, int expire) {
        List<String> kvs = new ArrayList<>(keys.size() * 2);
        for (int i = 0; i < keys.size(); i++) {
            kvs.add(keys.get(i));
            kvs.add(values.get(i));
        }
        try (Jedis jedis = getJedis()) {
            Pipeline pipeline = jedis.pipelined();
            pipeline.mset(kvs.toArray(new String[kvs.size()]));
            keys.forEach(k -> pipeline.expire(k, expire));
            pipeline.sync();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 不存在的key,数组中对应位置值为null
     *
     * @param keys
     * @return
     */
    public List<String> batchGet(List<String> keys) {
        try (Jedis jedis = getJedis()) {
            return jedis.mget(keys.toArray(new String[keys.size()]));
        } catch (Exception e) {
            throw e;
        }
    }

    public String get(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        } catch (Exception e) {
            throw e;
        }
    }

    public void set(String key, String value, int expire) {
        try (Jedis jedis = getJedis()) {
            jedis.setex(key, expire, value);
        } catch (Exception e) {
            throw e;
        }
    }

    public Long del(String key) {

        try (Jedis jedis = getJedis()) {
            return jedis.del(key);
        } catch (Exception e) {
            throw e;
        }
    }


    public void hmset(String key, Map<String, String> data) {
        if (data == null || data.size() == 0) {
            return;
        }

        try (Jedis jedis = getJedis()) {
            jedis.hmset(key, data);
        } catch (Exception e) {
            throw e;
        }

    }

    public List<String> hmget(String key, String[] keys) {
        if (keys == null || keys.length == 0) {
            return new ArrayList<>();
        }

        try (Jedis jedis = getJedis()) {
            return jedis.hmget(key, keys);
        } catch (Exception e) {
            throw e;
        }
    }

    public long hdel(String key, String[] keys) {
        if (keys == null || keys.length == 0) {
            return 0;
        }

        try (Jedis jedis = getJedis()) {
            return jedis.hdel(key, keys);
        } catch (Exception e) {
            throw e;
        }
    }

    public long expire(String key, int seconds) {
        if (key == null || seconds <= 0) {
            return 0;
        }
        try (Jedis jedis = getJedis()) {
            return jedis.expire(key, seconds);
        } catch (Exception e) {
            throw e;
        }
    }

    public void sadd(String key, List<String> values) {
        if (values == null || values.size() == 0) {
            return;
        }

        try (Jedis jedis = getJedis()) {
            jedis.sadd(key, values.toArray(new String[values.size()]));
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean sismember(String key, String value) {
        try (Jedis jedis = getJedis()) {
            return jedis.sismember(key, value);
        } catch (Exception e) {
            throw e;
        }
    }

}
