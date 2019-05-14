package com.edu.service;

import com.edu.entity.Tselectcourse;

import java.util.List;

public interface TselectcourseService extends IService<Tselectcourse>{
    List<Tselectcourse> selectBystudentidAndstatus(Integer id, String status);
    List<Tselectcourse> selectByStatus(String status);
    List<Tselectcourse> selectBycourseidAndStatus(Integer courseid,String status);
}