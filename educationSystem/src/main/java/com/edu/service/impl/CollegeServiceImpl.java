package com.edu.service.impl;

import com.edu.entity.College;
import com.edu.mapper.CollegeMapper;
import com.edu.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wl
 */

@Service("collegeService")
public class CollegeServiceImpl extends BaseService<College> implements CollegeService {
    @Autowired
    private CollegeMapper collegeMapper;

    @Override
    public String selectCollegeNameByCollegeId(String collegeid) {
        return collegeMapper.selectCollegeNameByCollegeId(collegeid);
    }

    @Override
    public String selectcollegeidBycollegename(String collegename) {
        return collegeMapper.selectcollegeidBycollegename(collegename);
    }
}
