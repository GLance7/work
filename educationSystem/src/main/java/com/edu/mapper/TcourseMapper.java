package com.edu.mapper;

import com.edu.entity.Tcourse;
import com.edu.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TcourseMapper extends MyMapper<Tcourse> {
    List<Tcourse> selectCourseByTeacherid(Integer teacherid);
    List<Tcourse> selectByStatus(String status);
}