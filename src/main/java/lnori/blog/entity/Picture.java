package lnori.blog.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Lnori
 */
@Data
@Table(name = "tb_picture")
public class Picture {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long pictureId;
    private String link;
    private String title;
    private String description;
    private String address;
    private Date createTime;
}
