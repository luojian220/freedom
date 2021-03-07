package top.softone.freedom.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;
import top.softone.freedom.model.Article;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月07日 19:54
 * @since JDK 1.8
 */
@Slf4j
@Service
public class ArticleService {

    @Autowired
    RedisOperations<Object, Object> redisOperations;


    public Article addArticle(Article article) {

        String id = UUID.randomUUID().toString();
        article.setId(id);
        article.setCreateTime(new Date());
        redisOperations.opsForHash().put(article.getType(),id,article);
        return article;
    }

    public Article getArticle(String type,String id) {

        Article article = (Article) redisOperations.opsForHash().get(type, id);
        return article;
    }

    public List<Object> getArticleList(String type) {

        Set<Object> keys = redisOperations.opsForHash().keys(type);
        if (keys.isEmpty()) {
            return null;
        }
        List<Object> articleList = redisOperations.opsForHash().multiGet(type, keys);
        return articleList;
    }

    public void delete(String type, String id) {

        redisOperations.opsForHash().delete(type,id);
    }
}
