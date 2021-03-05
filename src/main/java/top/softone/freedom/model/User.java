package top.softone.freedom.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author luojian
 * @version 1.0
 * @date 2021年03月05日 21:52
 * @since JDK 1.8
 */
@Setter
@Getter
public class User implements Serializable {


    private static final long serialVersionUID = -411119750701964316L;

    private String nickName;

    private String email;

    private String password;



}
