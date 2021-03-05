package top.softone.freedom.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.softone.freedom.model.User;
import top.softone.freedom.service.UserService;

/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月05日 22:00
 * @since JDK 1.8
 */
@RestController
@RequestMapping("userX")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("addUser")
    public String addUser(@RequestBody User user) {

        userService.addUser(user);
        return "OK";
    }

    @GetMapping("getUser")
    public String getUser(String email) {

        User user = userService.getUser(email);
        return JSONObject.toJSONString(user);
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
