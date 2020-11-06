package lnori.blog.search.mp;

import lnori.blog.search.service.SearchService;
import lnori.blog.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Lnori
 */
@Slf4j
@Component
public class BlogListener {
    @Resource
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.blog.insert.queue", durable = "true"),
            exchange = @Exchange(name = "blog.search.exchange", type = ExchangeTypes.TOPIC),
            key = {"blog.insert","blog.update"}
    ))
    public void listenerInsertOrUpdate(Long blogId){
        if (StringUtils.isNull(blogId)){
            return;
        }
        //处理消息，对索引库进行新增或修改
        searchService.createOrUpdateIndex(blogId);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.blog.delete.queue", durable = "true"),
            exchange = @Exchange(name = "blog.search.exchange", type = ExchangeTypes.TOPIC),
            key = {"blog.delete"}
    ))
    public void listenerDelete(Long blogId){
        if (StringUtils.isNull(blogId)){
            return;
        }
        //处理消息，对索引库进行删除
        searchService.deleteIndex(blogId);
    }

}
