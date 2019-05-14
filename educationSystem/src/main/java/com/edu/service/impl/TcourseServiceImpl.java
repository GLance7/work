package com.edu.service.impl;

import com.edu.entity.Tcourse;
import com.edu.mapper.TcourseMapper;
import com.edu.service.TcourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wl
 */
@Service("tcourseService")
public class TcourseServiceImpl extends BaseService<Tcourse>  implements TcourseService {
    @Autowired
    private TcourseMapper tcourseMapper;

    @Override
    public List<Tcourse> selectCourseByTeacherid(Integer teacherid) {
        return tcourseMapper.selectCourseByTeacherid(teacherid);
    }

    @Override
    public List<Tcourse> selectByStatus(String status) {
        return tcourseMapper.selectByStatus(status);
    }

   /* @Autowired
    private TcourseMapper tcourseMapper;


    @Override
    public boolean deleteCourse(String id) {
        return false;
    }

    @Override
    public boolean insertCourse(Tcourse tcourse) {
        //tcourseMapper.insert(tcourse);
        int insertnum =  tcourseMapper.insert(tcourse);
        if (insertnum==0){
            //插入失败
            return false;
        }
        //插入成功
        return true;
    }

    @Override
    public Tcourse selectCourseById(String id) {
       Tcourse selectCourseId =  tcourseMapper.selectByPrimaryKey(id);
        return selectCourseId;
    }

    @Override
    public boolean updateCourse(Tcourse tcourse) {

        *//*自己有一个方法 为updateCourse *//*
        int updatenum = tcourseMapper.updateByPrimaryKey(tcourse);
        if(updatenum==0)
            return false;
        return true;
    }*/
}
