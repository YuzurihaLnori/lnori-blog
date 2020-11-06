package lnori.blog.exception;

import lnori.blog.enums.BlogExceptionEnum;
import lombok.Getter;

/**
 * @author Lnori
 */
@Getter
public class BlogException extends RuntimeException{
    private Integer code;

    public BlogException(BlogExceptionEnum blogExceptionEnum){
        super(blogExceptionEnum.getMassage());
        this.code = blogExceptionEnum.getCode();
    }

    public BlogException(Integer code,String message){
        super(message);
        this.code = code;
    }

}
