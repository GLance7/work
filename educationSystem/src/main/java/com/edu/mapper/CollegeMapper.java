package com.edu.mapper;

import com.edu.entity.College;
import com.edu.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollegeMapper extends MyMapper<College> {
   String selectCollegeNameByCollegeId(String collegeid);
   String selectcollegeidBycollegename(String collegename);
}
