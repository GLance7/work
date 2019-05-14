package com.edu.service;

import com.edu.entity.Tcourse;

import java.util.List;


public interface TcourseService extends IService<Tcourse>{
 /*   boolean deleteCourse(String id);
    boolean insertCourse(Tcourse tcourse);
  *//*  @Override
    List<Tcourse> selectByExample(Object example);

    @Override
    Tcourse selectByKey(Object key);*//*

    Tcourse selectCourseById(String id);

    boolean updateCourse(Tcourse tcourse);*/
 List<Tcourse> selectCourseByTeacherid(Integer teacherid);
 List<Tcourse> selectByStatus(String status);

}