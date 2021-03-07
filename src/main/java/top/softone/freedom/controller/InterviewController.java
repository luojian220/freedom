package top.softone.freedom.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.softone.freedom.model.Article;
import top.softone.freedom.model.ResponseVO;
import top.softone.freedom.service.ArticleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月07日 13:07
 * @since JDK 1.8
 */
@RestController
@RequestMapping("interview")
public class InterviewController {

    @Autowired
    private ArticleService articleService;


    @PostMapping("add")
    public ResponseVO<Article> add(@RequestBody Article article) {
        ResponseVO<Article> responseVO = ResponseVO.success();
        Article articleDB = articleService.addArticle(article);
        responseVO.setData(articleDB);
        System.out.println(JSONObject.toJSON(responseVO));
        return responseVO;
    }

    @GetMapping("detail")
    public ResponseVO<Article> getDetail(String type,String id) {

        ResponseVO<Article> responseVO = ResponseVO.success();
        Article articleDB = articleService.getArticle(type,id);
        responseVO.setData(articleDB);
        return responseVO;
    }

    @GetMapping("list")
    public ResponseVO<Object> getDetail(String type) {

        ResponseVO<Object> responseVO = ResponseVO.success();
        List<Object> articleList = articleService.getArticleList(type);
        List<List<Object>> rowsList = new ArrayList<>();
        if (articleList.size() > 0 ) {
            int rows = (articleList.size() - 1) / 3 + 1;
            System.out.println("rows:" + rows);
            List<Object> temp ;
            for (int i = 0 ; i < rows ; i ++) {
                temp = new ArrayList<>();
                for (int j= 0 ; j < 3 ; j ++) {
                    int index = i * 3 + j;
                    System.out.println(index);
                    temp.add(articleList.get(index));
                    if ((index + 1) == articleList.size()) {
                        break;
                    }
                }
                rowsList.add(temp);
            }
        }
        responseVO.setData(rowsList);
        return responseVO;
    }


    @GetMapping("delete")
    public ResponseVO delete(String type,String id) {
        ResponseVO responseVO = ResponseVO.success();
        articleService.delete(type,id);
        return responseVO;
    }

}
