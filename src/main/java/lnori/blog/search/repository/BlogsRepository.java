package lnori.blog.search.repository;

import lnori.blog.search.model.Blogs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lnori
 */
@Repository
public interface BlogsRepository extends ElasticsearchRepository<Blogs,Long> {
}
