package com.mapper;

import com.entity.MiaoshaProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MiaoshaProductMapper {

    List<MiaoshaProduct> list();

    MiaoshaProduct selectDetailById(int id);

    List<MiaoshaProduct> selectList();
}
