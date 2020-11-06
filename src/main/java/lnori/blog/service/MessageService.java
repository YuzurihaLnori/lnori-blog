package lnori.blog.service;

import lnori.blog.entity.Message;

import java.util.List;

/**
 * @author Lnori
 */
public interface MessageService {

    /**
     * 查询留言版显示留言
     *
     * @return 留言集合
     */
    List<Message> findAllMessage();


    /**
     * 保存留言
     *
     * @param message 留言
     */
    void saveMessage(Message message);

    /**
     * 删除留言
     *
     * @param messageId 留言ID
     */
    void deleteMessage(Long messageId);
    
}
