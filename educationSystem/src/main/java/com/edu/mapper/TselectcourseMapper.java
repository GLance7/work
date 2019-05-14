package com.edu.mapper;

import com.edu.entity.Tselectcourse;
import com.edu.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TselectcourseMapper extends MyMapper<Tselectcourse> {
  /*  boolean deleteCourse(String id);
    boolean insertCourse(Tcourse tcourse);
    boolean updateCourse(Tcourse tcourse);*/

    List<Tselectcourse> selectBystudentidAndstatus(@Param("id") Integer id,@Param("status") String status);
    List<Tselectcourse> selectByStatus(String status);
    List<Tselectcourse> selectBycourseidAndStatus(@Param("courseid") Integer courseid, @Param("status") String status);

}