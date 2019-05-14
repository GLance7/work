package com.edu.web;

import com.edu.entity.Tcourse;
import com.edu.entity.Tselectcourse;
import com.edu.model.PageRusult;
import com.edu.service.CollegeService;
import com.edu.service.TcourseService;
import com.edu.service.TselectcourseService;
import com.edu.service.TuserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class TcourseController {
    @Resource
    private TcourseService tcourseService;
    @Resource
    private TselectcourseService tselectcourseService;
    @Resource
    private CollegeService collegeService;
    @Resource
    private TuserService tuserService;


    /*安全退出*/
    @GetMapping("/logout")
    @RequiresPermissions(value = {"安全退出"})
    public String logout() throws Exception {
//		logService.save(new Log(Log.LOGOUT_ACTION,"用户注销"));
        SecurityUtils.getSubject().logout();
        return "redirect:/tologin";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {"课程管理"})
    public Map<String, Object> list() throws Exception {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        LinkedHashMap<String, Object> datamap = new LinkedHashMap<String, Object>();

        Example tcourseExample = new Example(Tcourse.class);
      //  Example.Criteria criteria = tcourseExample.createCriteria();

        List<Tcourse> tcoursesList = tcourseService.selectByExample(tcourseExample);
        PageRusult<Tcourse> pageRusult =new PageRusult<Tcourse>(tcoursesList);

        System.out.println("显示  "+tcoursesList.toString()+"  先  "+tcoursesList.get(0).getTeacherid());
        System.out.println("这种  "+tcoursesList.get(0).getCollegeid());

        for(Tcourse c:tcoursesList){
            StringBuffer sb = new StringBuffer();
            StringBuffer sb2 = new StringBuffer();

            String collegename = collegeService.selectCollegeNameByCollegeId(c.getCollegeid());
            String teachername = tuserService.selectTeacherNameByTeacherId(c.getTeacherid());
            sb.append(","+teachername);
            c.setTeachername(sb.toString().replaceFirst(",",""));

            sb2.append(","+collegename);
            c.setCollegename(sb2.toString().replaceFirst(",",""));
        }

       resultmap.put("currpage", String.valueOf(pageRusult.getPageNum()));
        resultmap.put("totalpages", String.valueOf(pageRusult.getPages()));
        resultmap.put("totalrecords", String.valueOf(pageRusult.getTotal()));
        resultmap.put("datamap", tcoursesList);
        return resultmap;
    }


    /*
    * 添加课程*/
    @ResponseBody
    @RequestMapping(value = "/addUpdateCourse")
    @RequiresPermissions(value = {"课程管理"})
    public Map<String, Object> addCourse(Tcourse tcourse) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if (tcourse.getId() == null) {//新建
                //首先判断课程名是否可用
                Example courseExample = new Example(Tcourse.class);
                //courseExample.or().andEqualTo("userName",tuser.getUserName());
                List<Tcourse> tcourseList = tcourseService.selectByExample(courseExample);
                if (tcourseList != null && tcourseList.size() > 0) {
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "当前课程名已存在");
                    return resultmap;
                }
                tcourseService.saveNotNull(tcourse);
            } else {//编辑
                Tcourse oldObject=tcourseService.selectByKey(tcourse.getId());
                if(oldObject==null){
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "当前课程名不存在");
                    return resultmap;
                }else{
                    tcourseService.updateNotNull(tcourse);
                }
            }
            resultmap.put("state", "success");
            resultmap.put("mesg", "操作成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "操作失败，系统异常");
            return resultmap;
        }
    }



    @ResponseBody
    @RequestMapping(value = "/deleteCourse")
    @RequiresPermissions(value = {"课程管理"})
    public Map<String, Object> deleteCourse(Tcourse tcourse) {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if(tcourse.getId()!=null&&!tcourse.getId().equals(0)){
                Tcourse course=tcourseService.selectByKey(tcourse.getId());
                if(course==null){
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "删除失败,无法找到该记录");
                    return resultmap;
                }else{

                    //还需删除学生课程中间表（即选课表tselectcourse表）
                    Example tselectcourseExample=new Example(Tselectcourse.class);
                    tselectcourseExample.or().andEqualTo("courseid",tcourse.getId());
                    tselectcourseService.deleteByExample(tselectcourseExample);

                    tcourseService.delete(tcourse.getId());

                }
            }else{
                resultmap.put("state", "fail");
                resultmap.put("mesg", "删除失败");
            }


            resultmap.put("state", "success");
            resultmap.put("mesg", "删除成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "删除失败，系统异常");
            return resultmap;
        }
    }




    @ResponseBody
    @RequestMapping(value = "/selectCourseById")
    @RequiresPermissions(value = {"课程管理"})
    public Map<String, Object> selectCourseById(Tcourse tcourse) {

        System.out.println("拿到他course  "+tcourse.toString());

        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        try {
            if(tcourse.getId()!=null&&!tcourse.getId().equals(0)){
                tcourse=tcourseService.selectByKey(tcourse.getId());
                if(tcourse==null){
                    resultmap.put("state", "fail");
                    resultmap.put("mesg", "无法找到该记录");
                    return resultmap;
                }
            }else{
                resultmap.put("state", "fail");
                resultmap.put("mesg", "无法找到该记录的id");
                return resultmap;
            }
            resultmap.put("tcourse",tcourse);
            resultmap.put("state", "success");
            resultmap.put("mesg", "获取成功");
            return resultmap;
        } catch (Exception e) {
            e.printStackTrace();
            resultmap.put("state", "fail");
            resultmap.put("mesg", "获取失败，系统异常");
            return resultmap;
        }
    }


    /*@ResponseBody
    @RequestMapping("/myCourse")*/










}
