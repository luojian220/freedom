package top.softone.freedom.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luojian
 * @version 1.0
 * @date: 2021年03月07日 19:52
 * @since JDK 1.8
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Article implements Serializable {

    private static final long serialVersionUID = 4333269888038840398L;

    private String id;

    private String author;

    private Date createTime;

    private String mdValue;

    private String title;

    private String type;
}
