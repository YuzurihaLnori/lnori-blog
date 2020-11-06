package lnori.blog.entity.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lnori
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogInformationVO {
    private Integer blogCount;
    private Integer blogViewsCount;
    private Integer blogCommentCount;
    private Integer blogMessageCount;
}
