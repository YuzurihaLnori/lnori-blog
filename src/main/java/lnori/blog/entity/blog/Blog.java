package lnori.blog.entity.blog;

import lnori.blog.entity.Comment;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Lnori
 */
@Data
@Table(name = "tb_blog")
public class Blog {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long blogId;
    private Long typeId;
    private Long userId;
    private String title;
    @Basic(fetch = FetchType.LAZY)
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private Integer commentCount;
    private String description;
    private Boolean appreciation;
    private Boolean comment;
    private Boolean recommend;
    private Boolean published;
    private Date createTime;
    private Date updateTime;
    @Transient
    private List<Comment> commentList = new ArrayList<>();
}
