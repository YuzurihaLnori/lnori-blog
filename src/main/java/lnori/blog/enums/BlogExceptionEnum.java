package lnori.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Lnori
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum BlogExceptionEnum {
    /**
     * Blog自定义错误信息
     */
    ERROR(500, "服务器内部错误"),
    NOT_FOUND(404, "资源不存在"),
    TYPE_NOT_FOUND(404, "分类不存在"),
    TYPE_SAVE_ERROR(500, "新增分类失败"),
    TYPE_UPDATE_ERROR(500, "修改分类失败"),
    TYPE_DELETE_ERROR(500, "删除分类失败"),
    PICTURE_NOT_FOUND(404, "照片不存在"),
    PICTURE_SAVE_ERROR(500, "新增照片失败"),
    PICTURE_UPDATE_ERROR(500, "修改照片失败"),
    PICTURE_DELETE_ERROR(500, "删除照片失败"),
    BLOG_NOT_FOUND(404, "博客不存在"),
    BLOG_SAVE_ERROR(500, "新增博客失败"),
    BLOG_UPDATE_ERROR(500, "修改博客失败"),
    BLOG_DELETE_ERROR(500, "删除博客失败"),
    COMMENT_SAVE_ERROR(500, "新增评论失败"),
    MESSAGE_SAVE_ERROR(500, "新增留言失败"),
    USER_NOT_FOUND(404, "用户不存在");
    private Integer code;
    private String massage;
}
