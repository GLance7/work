package com.edu.service.impl;

import com.edu.entity.Tuser;
import com.edu.mapper.TuserMapper;
import com.edu.service.TuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wl
 */
@Service("tuserService")
public class TuserServiceImpl   extends BaseService<Tuser> implements TuserService {

    @Autowired
    private TuserMapper tuserMapper;


    @Override
    public void updateChangeInfor(Tuser user) {
        String username = user.getUserName();
        System.out.println("  username  更新 "+username);
        tuserMapper.updateChangeInfor(user);
    }

    @Override
    public String selectTeacherNameByTeacherId(Integer teacherid) {

        return tuserMapper.selectTeacherNameByTeacherId(teacherid);
    }

    @Override
    public List<Tuser> selectByRemarks(Integer remarks) {
        return tuserMapper.selectByRemarks(remarks);
    }

    @Override
    public List<Tuser> selectSpecificInfor(Integer id) {


        return tuserMapper.selectSpecificInfor(id);
    }

    @Override
    public List<Tuser> selectByCollegeidAndClassno(String collegename, String classno) {
        return tuserMapper.selectByCollegeidAndClassno(collegename,classno);
    }

    @Override
    public List<Tuser> selectByCollegeid(String collegeid) {
        return tuserMapper.selectByCollegeid(collegeid);
    }

    @Override
    public Tuser selectByusername(String username) {
        return tuserMapper.selectByusername(username);
    }

    @Override
    public List selectByCollegeidAndClassno(String collegeid) {
        return tuserMapper.selectByCollegeidAndClassno(collegeid);
    }
}
