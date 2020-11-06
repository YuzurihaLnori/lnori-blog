package lnori.blog.handler;

import lnori.blog.exception.BlogException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lnori
 */
@Slf4j
@ControllerAdvice
public class BlogExceptionHandler {

    @ExceptionHandler(BlogException.class)
    public ModelAndView blogExceptionHandler(HttpServletRequest request, BlogException blgException) {
        log.error("---------------->Request URL : {}, ErrorMassage : {}", request.getRequestURL(), blgException.getMessage());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exception", blgException);

        int notFound = 404;
        if (blgException.getCode().equals(notFound)) {
            modelAndView.setViewName("error/404");
        } else {
            modelAndView.setViewName("error/error");
        }

        return modelAndView;
    }

}
