package lnori.blog.search.service;

import lnori.blog.entity.blog.vo.BlogVO;
import lnori.blog.search.model.Blogs;
import lnori.blog.search.model.SearchResult;

/**
 * @author Lnori
 */
public interface SearchService {

    /**
     * 构建搜索结果的实体类
     * @param blog 博客实体类
     * @return 搜索结果类
     */
    Blogs buildBlogs(BlogVO blog);

    /**
     * 搜索博客
     * @param page 搜索当前页
     * @param size 搜索数量
     * @param search 搜索条件
     * @return 符合条件的博客
     */
    SearchResult<Blogs> search(Integer page, Integer size, String search);

    /**
     * 对索引库进行新增或修改
     * @param blogId 博客ID
     */
    void createOrUpdateIndex(Long blogId);

    /**
     * 对索引库进行删除
     * @param blogId 博客ID
     */
    void deleteIndex(Long blogId);
}
