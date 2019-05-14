package com.edu.mapper;

import com.edu.entity.Tinfor;
import com.edu.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TinforMapper extends MyMapper<Tinfor> {
  List<Tinfor> selectAll();
}
