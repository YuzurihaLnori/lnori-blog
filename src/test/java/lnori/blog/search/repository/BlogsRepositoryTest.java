package lnori.blog.search.repository;

import lnori.blog.search.model.Blogs;
import lnori.blog.search.service.SearchService;
import lnori.blog.service.BlogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogsRepositoryTest {
    @Autowired
    private ElasticsearchTemplate template;
    @Resource
    private BlogService blogService;
    @Resource
    private SearchService searchService;
    @Resource
    private BlogsRepository blogsRepository;

    @Test
    public void testCreateIndex(){
        template.createIndex(Blogs.class);
        template.putMapping(Blogs.class);
    }

    @Test
    public void loadDate(){
        /*PageInfo<BlogVO> page = blogService.findBlogPage(1, 5);
        List<BlogVO> list = page.getList();
        List<Blogs> blogsList = list.stream().map(searchService::buildBlogs).collect(Collectors.toList());
        blogsRepository.saveAll(blogsList);*/

        int[] arr = {1,1,2,3,4,5,5,6,7,7,8,9};
        int index;
        int count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == arr[i + 1]){
                count++;
            }
            /*index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[index] == arr[j]){
                    count++;
                }
            }*/
        }
        System.out.println(count);

    }

}