package lnori.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lnori.blog.entity.SearchBlog;
import lnori.blog.entity.Type;
import lnori.blog.entity.User;
import lnori.blog.entity.blog.Blog;
import lnori.blog.entity.blog.vo.BlogArchivesVO;
import lnori.blog.entity.blog.vo.BlogDetailedVO;
import lnori.blog.entity.blog.vo.BlogRecommendVO;
import lnori.blog.entity.blog.vo.BlogVO;
import lnori.blog.enums.BlogExceptionEnum;
import lnori.blog.exception.BlogException;
import lnori.blog.mapper.BlogMapper;
import lnori.blog.mapper.TypeMapper;
import lnori.blog.mapper.UserMapper;
import lnori.blog.service.BlogService;
import lnori.blog.util.MarkdownUtils;
import lnori.blog.util.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lnori
 */
@Service("blogService")
@Transactional(rollbackFor = Exception.class)
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogMapper blogMapper;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public BlogVO findBlogById(Long blogId) {
        BlogVO blog = blogMapper.findBlogById(blogId);
        if (StringUtils.isNull(blog)) {
            throw new BlogException(BlogExceptionEnum.BLOG_NOT_FOUND);
        }
        return blog;
    }

    @Override
    public BlogDetailedVO findBlogDetailedById(Long blogId) {
        BlogDetailedVO blog = blogMapper.findBlogDetailedById(blogId);
        if (StringUtils.isNull(blog)) {
            throw new BlogException(BlogExceptionEnum.BLOG_NOT_FOUND);
        }
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        blogMapper.updateViewById(blogId, blog.getViews() + 1);
        return blog;
    }

    @Override
    public List<BlogRecommendVO> findBlogRecommend() {
        return blogMapper.findBlogRecommend();
    }

    @Override
    public List<Integer> findBlogInformation() {
        Integer blogTotal = blogMapper.findBlogCount();
        Integer blogViewsTotal = blogMapper.findBlogViewsCount();
        Integer blogCommentTotal = blogMapper.findBlogCommentCount();
        Integer blogMessageTotal = blogMapper.findBlogMessageCount();
        return Arrays.asList(blogTotal, blogViewsTotal, blogCommentTotal, blogMessageTotal);
    }

    @Override
    public PageInfo<BlogVO> findBlogPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogVO> blogList = blogMapper.findBlogPage();
        if (StringUtils.isEmpty(blogList)) {
            throw new BlogException(BlogExceptionEnum.BLOG_NOT_FOUND);
        }
        return new PageInfo<>(blogList);
    }

    @Override
    public PageInfo<BlogDetailedVO> findAllBlog(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<BlogDetailedVO> blogList = blogMapper.findAllBlog();
        if (StringUtils.isEmpty(blogList)) {
            throw new BlogException(BlogExceptionEnum.BLOG_NOT_FOUND);
        }
        return new PageInfo<>(blogList);
    }

    @Override
    public PageInfo<Blog> findBlogPage(Integer page, Integer size, SearchBlog searchBlog) {
        Blog blog = new Blog();
        if (StringUtils.isNotNull(searchBlog.getTypeId())) {
            blog.setTypeId(searchBlog.getTypeId());
        }
        if (StringUtils.isNotBlank(searchBlog.getTitle())) {
            blog.setTitle(searchBlog.getTitle());
        }
        if (StringUtils.isNotNull(searchBlog.getRecommend())) {
            blog.setRecommend(searchBlog.getRecommend());
        }
        PageHelper.startPage(page, size);
        List<Blog> blogList = blogMapper.select(blog);
        if (StringUtils.isEmpty(blogList)) {
            throw new BlogException(BlogExceptionEnum.BLOG_NOT_FOUND);
        }
        List<Blog> result = blogList.stream().map(this::setBlog).collect(Collectors.toList());
        return new PageInfo<>(result);
    }

    private Blog setBlog(Blog blog) {
        Type type = typeMapper.selectByPrimaryKey(blog.getTypeId());
        User user = userMapper.selectByPrimaryKey(blog.getUserId());
        BlogDetailedVO blogDetailedVO = new BlogDetailedVO(type, user);
        BeanUtils.copyProperties(user, blogDetailedVO);
        return blogDetailedVO;
    }

    @Override
    public List<BlogArchivesVO> findArchivesBlog() {
        return blogMapper.findArchivesBlog();
    }

    @Override
    public void saveBlog(Blog blog) {
        int count = blogMapper.insertSelective(blog);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.BLOG_SAVE_ERROR);
        }

        // 发送消息
        amqpTemplate.convertAndSend("blog.insert", blog.getBlogId());
    }

    @Override
    public void editBlog(Blog blog) {
        if (StringUtils.isNull(blog.getAppreciation())) {
            blog.setAppreciation(false);
        }
        if (StringUtils.isNull(blog.getComment())) {
            blog.setComment(false);
        }
        if (StringUtils.isNull(blog.getRecommend())) {
            blog.setRecommend(false);
        }
        int count = blogMapper.updateByPrimaryKeySelective(blog);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.BLOG_UPDATE_ERROR);
        }

        // 发送消息
        amqpTemplate.convertAndSend("blog.update", blog.getBlogId());
    }

    @Override
    public void deleteBlog(Long blogId) {
        int count = blogMapper.deleteByPrimaryKey(blogId);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.BLOG_DELETE_ERROR);
        }

        // 发送消息
        amqpTemplate.convertAndSend("blog.delete", blogId);
    }
}
