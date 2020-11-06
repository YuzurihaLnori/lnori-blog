package lnori.blog.entity;

import lombok.Data;

/**
 * @author Lnori
 */
@Data
public class SearchBlog {
    private Long typeId;
    private String title;
    private Boolean recommend;
}
