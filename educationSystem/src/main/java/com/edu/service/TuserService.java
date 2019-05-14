package com.edu.service;

import com.edu.entity.Tuser;

import java.util.List;

public interface TuserService  extends IService<Tuser>{
    void updateChangeInfor(Tuser user);
    String selectTeacherNameByTeacherId(Integer teacherid);

    List<Tuser> selectByRemarks(Integer remarks);
    List<Tuser> selectSpecificInfor(Integer id);
    List<Tuser> selectByCollegeidAndClassno(String collegename,String classno);
    List<Tuser> selectByCollegeid(String collegeid);
}