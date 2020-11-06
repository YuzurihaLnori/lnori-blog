package lnori.blog.entity.blog.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Lnori
 */
@Data
public class BlogVO {
    private Long blogId;
    private String title;
    private String description;
    private Integer views;
    private Integer commentCount;
    private String firstPicture;
    private Date createTime;
    private String userNickName;
    private String userAvatar;
    private Long typeId;
    private String typeName;
}
