package top.softone.freedom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.softone.freedom.model.User;


/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月05日 21:53
 * @since JDK 1.8
 */
@Service
public class UserService {

    @Autowired
    RedisOperations<Object, Object> redisOperations;


    public void addUser(User user) {

        redisOperations.opsForValue().set("user:".concat(user.getEmail()),user);
    }

    public User getUser(String email) {
        return (User) redisOperations.opsForValue().get("user:".concat(email));
    }

    public User login(String email,String password) {
        User user = (User) redisOperations.opsForValue().get("user:".concat(email));
        Assert.notNull(user,"登录失败");
        Assert.hasText(user.getEmail(),"登录失败");
        Assert.isTrue(user.getPassword().equals(password),"登录失败");
        return user;
    }
}
