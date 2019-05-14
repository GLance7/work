package com.edu.service.impl;

import com.edu.entity.Tselectcourse;
import com.edu.mapper.TselectcourseMapper;
import com.edu.service.TselectcourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wl
 */
@Service("tselectcourseService")
public class TselectcourseServiceImpl extends BaseService<Tselectcourse>  implements TselectcourseService {

    @Autowired
    private TselectcourseMapper tselectcourseMapper;


    @Override
    public List<Tselectcourse> selectBystudentidAndstatus(Integer id, String status) {

        return tselectcourseMapper.selectBystudentidAndstatus(id,status);
    }

    @Override
    public List<Tselectcourse> selectByStatus(String status) {
        return tselectcourseMapper.selectByStatus(status);
    }

    @Override
    public List<Tselectcourse> selectBycourseidAndStatus( Integer courseid, String status) {

        return tselectcourseMapper.selectBycourseidAndStatus(courseid,status);
    }
}
