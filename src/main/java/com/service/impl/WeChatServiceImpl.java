package com.service.impl;

import com.chatpojo.Message;
import com.mapper.WeChatMapper;
import com.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeChatServiceImpl implements WeChatService {

    @Autowired
    WeChatMapper weChatMapper;

    @Override
    public void add(Message message) {
        weChatMapper.insert(message);
    }

    @Override
    public List<Message> findByName(String username, String toName) {
        Message message = new Message();
        message.setFromName(username);
        message.setToName(toName);
        List<Message> list = weChatMapper.select(message);
        return list;
    }


}
