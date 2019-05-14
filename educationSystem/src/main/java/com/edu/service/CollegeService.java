package com.edu.service;

import com.edu.entity.College;


public interface CollegeService extends IService<College> {
   String selectCollegeNameByCollegeId(String collegeid);
   String selectcollegeidBycollegename(String collegename);
}
