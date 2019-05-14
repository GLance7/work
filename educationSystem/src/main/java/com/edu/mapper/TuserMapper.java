package com.edu.mapper;

import com.edu.entity.Tuser;
import com.edu.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TuserMapper extends MyMapper<Tuser> {
    void updateChangeInfor(Tuser tuser);
    String selectTeacherNameByTeacherId(Integer teacherid);

    List<Tuser> selectByRemarks(Integer remarks);
    List<Tuser> selectSpecificInfor(Integer id);
    List<Tuser> selectByCollegeidAndClassno(String collegeid,String classno);
    List<Tuser> selectByCollegeid(String collegeid);
    Tuser selectByusername(String username);
    List selectByCollegeidAndClassno(String collegeid);

}