package lnori.blog.service.impl;

import lnori.blog.entity.Message;
import lnori.blog.enums.BlogExceptionEnum;
import lnori.blog.exception.BlogException;
import lnori.blog.mapper.MessageMapper;
import lnori.blog.service.MessageService;
import lnori.blog.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lnori
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;
    private List<Message> tempReplyList = new ArrayList<>();

    @Override
    public List<Message> findAllMessage() {
        return eachMessage(messageMapper.findMessage());
    }

    /**
     * 循环每个顶级的评论节点
     *
     * @param messageList 需要循环的评论集合
     * @return 循环好的评论集合
     */
    private List<Message> eachMessage(List<Message> messageList) {
        List<Message> messageListView = new ArrayList<>();
        for (Message message : messageList) {
            if (StringUtils.isNotEmpty(message.getReplyMessageList())) {
                List<Message> replyMessageList = message.getReplyMessageList();
                for (Message reply : replyMessageList) {
                    tempReplyList.add(reply);
                    recursively(reply);
                }
                message.setReplyMessageList(tempReplyList);
                tempReplyList = new ArrayList<>();
            }
            messageListView.add(message);
        }
        return messageListView;
    }

    /**
     * 递归迭代，剥洋葱
     *
     * @param message 被迭代的对象
     */
    public void recursively(Message message) {
        if (message.getReplyMessageList().size() > 0) {
            List<Message> replyMessageList = message.getReplyMessageList();
            for (Message reply : replyMessageList) {
                tempReplyList.add(reply);
                if (reply.getReplyMessageList().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }

    @Override
    public void saveMessage(Message message) {
        int count = messageMapper.insertSelective(message);
        if (count != 1) {
            throw new BlogException(BlogExceptionEnum.MESSAGE_SAVE_ERROR);
        }
        Message parent = new Message();
        if (message.getParentId() > 0){
            parent.setMessageId(message.getParentId());
            parent.setIsParent(true);
            messageMapper.updateByPrimaryKeySelective(parent);
        }
    }

    @Override
    public void deleteMessage(Long messageId) {
        Message message = messageMapper.findMessageByMessageId(messageId);
        List<Message> messageList = eachMessage(Collections.singletonList(message));
        List<Long> messageIdList = messageList.get(0).getReplyMessageList().stream().map(Message::getMessageId).collect(Collectors.toList());
        messageIdList.add(messageId);
        messageMapper.deleteByIdList(messageIdList);
    }
}
