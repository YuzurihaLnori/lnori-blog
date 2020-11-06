package lnori.blog.entity;

import lnori.blog.entity.blog.Blog;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Lnori
 */
@Data
@Table(name = "tb_comment")
public class Comment {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long commentId;
    private Long parentId;
    private Long blogId;
    private String nickName;
    private String email;
    private String content;
    private String avatar;
    private Boolean isParent;
    private Boolean isAdminComment;
    private Date createTime;

    @Transient
    private Blog blog;
    @Transient
    private Comment parentComment;
    @Transient
    private List<Comment> replyCommentList = new ArrayList<>();
}
