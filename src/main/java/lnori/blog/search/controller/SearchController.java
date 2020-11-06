package lnori.blog.search.controller;

import lnori.blog.search.model.Blogs;
import lnori.blog.search.model.SearchResult;
import lnori.blog.search.service.SearchService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@Controller
public class SearchController {
    @Resource
    private SearchService searchService;

    @GetMapping("search")
    public ModelAndView search(@RequestParam(name = "page", defaultValue = "1") Integer page,
                               @RequestParam(name = "size", defaultValue = "5") Integer size,
                               @RequestParam(name = "search", required = false) String search) {
        ModelAndView modelAndView = new ModelAndView();
        SearchResult<Blogs> result = searchService.search(page, size, search);
        modelAndView.addObject("page", result);
        modelAndView.addObject("pageNum",page);
        modelAndView.addObject("search", search);
        if (CollectionUtils.isNotEmpty(result.getList())){
            modelAndView.setViewName("search");
            return modelAndView;
        }
        modelAndView.setViewName("error/not-found");
        return modelAndView;
    }

}
