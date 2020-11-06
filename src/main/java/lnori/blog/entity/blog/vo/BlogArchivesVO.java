package lnori.blog.entity.blog.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Lnori
 */
@Data
public class BlogArchivesVO {
    private Long blogId;
    private Date createTime;
    private String title;
}
