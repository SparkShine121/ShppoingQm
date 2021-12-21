package com.mapper;

import com.chatpojo.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeChatMapper {

    void insert(Message message);

    List<Message> select(Message message);

}
