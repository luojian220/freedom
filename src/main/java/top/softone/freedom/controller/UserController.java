package top.softone.freedom.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.softone.freedom.model.User;
import top.softone.freedom.service.UserService;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月05日 22:00
 * @since JDK 1.8
 */
@Slf4j
@RestController
@RequestMapping("userX")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedissonClient redissonClient;

    @PostMapping("addUser")
    public String addUser(@RequestBody User user) {

        userService.addUser(user);
        return "OK";
    }

    @GetMapping("getUser")
    public String getUser(String email) {

        setLock();
        User user = userService.getUser(email);
        return JSONObject.toJSONString(user);
    }

    // 测试redisson 执行不释放锁，自动续期
    private void setLock() {
        RLock lock = redissonClient.getLock("lock_key_111");
        try {
            boolean res = lock.tryLock(10, TimeUnit.SECONDS);
            if (res) {
                System.out.println("获取锁成功！！！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //注释就会自动续期
            lock.unlock();
        }
    }

    @GetMapping("login")
    public String login(@RequestParam("email") String email, @RequestParam("password")String password) {
        try {
            User user = userService.login(email, password);
            return JSONObject.toJSONString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}
