package lnori.blog.search.service.impl;

import lnori.blog.entity.blog.vo.BlogVO;
import lnori.blog.search.model.Blogs;
import lnori.blog.search.model.SearchResult;
import lnori.blog.search.repository.BlogsRepository;
import lnori.blog.search.service.SearchService;
import lnori.blog.service.BlogService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Lnori
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {
    @Resource
    private BlogService blogService;
    @Resource
    private BlogsRepository blogsRepository;
    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public Blogs buildBlogs(BlogVO blog) {
        StringJoiner all = new StringJoiner(" ").add(blog.getTitle()).add(blog.getDescription()).add(blog.getUserNickName()).add(blog.getTypeName());
        Blogs blogs = new Blogs();
        blogs.setBlogId(blog.getBlogId());
        blogs.setAll(all.toString());
        blogs.setTitle(blog.getTitle());
        blogs.setDescription(blog.getDescription());
        blogs.setViews(blog.getViews());
        blogs.setCommentCount(blog.getCommentCount());
        blogs.setFirstPicture(blog.getFirstPicture());
        blogs.setCreateTime(blog.getCreateTime());
        blogs.setUserNickName(blog.getUserNickName());
        blogs.setUserAvatar(blog.getUserAvatar());
        blogs.setTypeId(blog.getTypeId());
        blogs.setTypeName(blog.getTypeName());
        return blogs;
    }

    @Override
    public SearchResult<Blogs> search(Integer page, Integer size, String search) {
        // 创建查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 分页
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        // 过滤
        queryBuilder.withQuery(QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery("all", search)));
        // 查询
        AggregatedPage<Blogs> result = template.queryForPage(queryBuilder.build(), Blogs.class);
        // 解析结果
        long total = result.getTotalElements();
        int totalPage = result.getTotalPages();
        List<Blogs> blogsList = result.getContent();

        return new SearchResult<>(total, totalPage, blogsList);
    }

    @Override
    public void createOrUpdateIndex(Long blogId) {
        BlogVO blog = blogService.findBlogById(blogId);
        Blogs blogs = buildBlogs(blog);
        blogsRepository.save(blogs);
    }

    @Override
    public void deleteIndex(Long blogId) {
        blogsRepository.deleteById(blogId);
    }
}
