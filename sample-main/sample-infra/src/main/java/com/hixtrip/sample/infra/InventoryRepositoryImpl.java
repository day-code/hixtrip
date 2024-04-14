package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.enums.InventoryNameSpaceEnum;
import com.hixtrip.sample.infra.db.utils.RedisTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * infra层是domain定义的接口具体的实现
 */
@Slf4j
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Integer getInventory(String skuId) {
        return RedisTemplateUtil.get(InventoryNameSpaceEnum.SELLABLE.getRedisNameSpace(skuId));
    }

    @Override
    public Boolean changeInventory(Inventory inventory) {
        String skuId = inventory.getSkuId();
        Integer sellableQuantity = inventory.getSellableQuantity(); // 可售库存
        Integer withholdingQuantity = inventory.getWithholdingQuantity();  // 预占库存
        Integer occupiedQuantity = inventory.getOccupiedQuantity();
        try {
            List<Object> results = redisTemplate.execute(new RedisCallback<List<Object>>() {
                @Override
                public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.multi();
                    if (Objects.nonNull(sellableQuantity)) {
                        connection.set(rawKey(InventoryNameSpaceEnum.SELLABLE.getRedisNameSpace(skuId)), rawValue(sellableQuantity));
                    }
                    if (Objects.nonNull(withholdingQuantity)) {
                        connection.set(rawKey(InventoryNameSpaceEnum.WITHHOLDING.getRedisNameSpace(skuId)), rawValue(withholdingQuantity));
                    }
                    if (Objects.nonNull(occupiedQuantity)) {
                        connection.set(rawKey(InventoryNameSpaceEnum.OCCUPIED.getRedisNameSpace(skuId)), rawValue(occupiedQuantity));
                    }
                    // 提交事务
                    return connection.exec();
                }

                private byte[] rawKey(String key) {
                    RedisSerializer<String> serializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
                    return serializer.serialize(key);
                }

                private byte[] rawValue(Object value) {
                    RedisSerializer<Object> serializer = (RedisSerializer<Object>) redisTemplate.getValueSerializer();
                    return serializer.serialize(value);
                }
            });
            // 检查事务是否成功
            return !results.isEmpty();
        } catch (Exception e) {
            // 处理异常，比如记录日志或抛出运行时异常
            log.error("修改库存出错", e.toString());
            return false;
        }
    }

    public boolean sellProduct(String skuId, Integer quantity) {
        String luaScript =
                "local available = redis.call('get', KEYS[1] )\n" +
                        "if available then\n" +
                        "    if (tonumber(available) >= tonumber(ARGV[1])) then\n" +
                        "        redis.call('decrby', KEYS[1] , ARGV[1])\n" +
                        "        redis.call('incrby', KEYS[2] , ARGV[1])\n" +
                        "        return 1\n" +
                        "    else\n" +
                        "        return 0\n" +
                        "    end\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(Long.class);

        Long result = (Long) redisTemplate.execute(redisScript,
                Arrays.asList(InventoryNameSpaceEnum.SELLABLE.getRedisNameSpace(skuId), InventoryNameSpaceEnum.WITHHOLDING.getRedisNameSpace(skuId)),
                quantity);
        return result != null && result == 1;
    }


    public boolean changeQuantity(String decrbyKey, String incrbyKey, int quantity) {
        String luaScript =
                "        redis.call('decrby', KEYS[1] , ARGV[1])\n" +
                        "        redis.call('incrby', KEYS[2] , ARGV[1])\n" +
                        "        return 1\n";

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(Long.class);

        Long result = (Long) redisTemplate.execute(redisScript,
                Arrays.asList(decrbyKey, incrbyKey),
                quantity);
        return result != null && result == 1;
    }

}
