package lnori.blog.entity.blog.vo;

import lnori.blog.entity.Comment;
import lnori.blog.entity.Type;
import lnori.blog.entity.User;
import lnori.blog.entity.blog.Blog;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Lnori
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogDetailedVO extends Blog {
    private Type type;
    private User user;
    private List<Comment> commentList;

    public BlogDetailedVO() {
    }

    public BlogDetailedVO(Type type, User user) {
        this.type = type;
        this.user = user;
    }
}

