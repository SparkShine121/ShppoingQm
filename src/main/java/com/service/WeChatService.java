package com.service;

import com.chatpojo.Message;

import java.util.List;

public interface WeChatService {

    void add(Message message);

    List<Message> findByName(String username, String toName);
}
