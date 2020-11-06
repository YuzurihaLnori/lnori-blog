package lnori.blog.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Lnori
 */
@Data
@Table(name = "tb_user")
public class User implements Serializable {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long userId;
    private String nickName;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private Integer type;
    private Date createTime;
    private Date updateTime;
}
