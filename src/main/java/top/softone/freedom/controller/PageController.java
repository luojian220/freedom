package top.softone.freedom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author luojian
 * @version 1.0
 * @date 2021年03月03日 14:16
 */
@Controller
public class PageController {

    public static final String HTML = ".html";

    @GetMapping(value = {"/","index","index.html"})
    public String page() {

        return "index.html";
    }

    @GetMapping(value = {"/pages/{path}","/pages/{path}.html"})
    public String page(@PathVariable String path) {
        if (StringUtils.hasText(path) && path.endsWith(HTML)) {
            return "pages/".concat(path);
        }
        return "pages/".concat(path).concat(HTML);
    }

}
