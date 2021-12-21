package com.mapper;

import com.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

    /**
     * 查询通知消息
     */
    List<Notice> selectNotice(Notice notice);

    /**
     * 添加新增的消息
     */
    void insertNotice(Notice notice);
}
