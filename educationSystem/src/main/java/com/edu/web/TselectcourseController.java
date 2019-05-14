package com.edu.web;

import com.edu.entity.College;
import com.edu.entity.Tcourse;
import com.edu.entity.Tselectcourse;
import com.edu.entity.Tuser;
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
import java.util.*;

@Controller
@RequestMapping("/selectcourse")
public class TselectcourseController {
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
    @RequiresPermissions(value = {"选课管理"})
    public Map<String, Object> list() throws Exception {
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        LinkedHashMap<String, Object> datamap = new LinkedHashMap<String, Object>();

        Example tselectcourseExample = new Example(Tselectcourse.class);


        List<Tselectcourse> tselectcoursesList = tselectcourseService.selectByExample(tselectcourseExample);
        PageRusult<Tselectcourse> pageRusult =new PageRusult<Tselectcourse>(tselectcoursesList);

        System.out.println("显示  "+tselectcoursesList.toString()+"  先  ");


        for(Tselectcourse c:tselectcoursesList) {

            List<Tuser> userByselectcourselist = tuserService.selectSpecificInfor(c.getStudentid());
           // System.out.println("获得具体学生的name  " + userByselectcourselist.get(0).getUserName());

            Tcourse courseByselectcourselist = tcourseService.selectByKey(c.getCourseid());
           // System.out.println("获得课程具体信息  " + courseByselectcourselist.getCourseno());

           // System.out.println("获得具体teacherid  " + courseByselectcourselist.getTeacherid() + "和collegeid" + courseByselectcourselist.getCollegeid());
            String teachername = tuserService.selectTeacherNameByTeacherId(courseByselectcourselist.getTeacherid());
            String collegename = collegeService.selectCollegeNameByCollegeId(courseByselectcourselist.getCollegeid());

           // System.out.println("获得学院名和老师名" + collegename + "   " + teachername);
            StringBuffer sb = new StringBuffer();
            StringBuffer sb1 = new StringBuffer();
            StringBuffer sb2 = new StringBuffer();
            for (Tuser r : userByselectcourselist) {
                sb.append("," + r.getUserName() + "," + r.getTrueName());
            }
            sb1.append(","+courseByselectcourselist.getCoursename()+","+courseByselectcourselist.getCourseno());
            sb.append(","+teachername);
            sb2.append(","+collegename);

            c.setUser(sb.toString());
            c.setCourse(sb1.toString());
            c.setCollegename(sb2.toString());
        }
       resultmap.put("currpage", String.valueOf(pageRusult.getPageNum()));
        resultmap.put("totalpages", String.valueOf(pageRusult.getPages()));
        resultmap.put("totalrecords", String.valueOf(pageRusult.getTotal()));
        resultmap.put("datamap", tselectcoursesList);
        return resultmap;
    }

    /*成绩信息*/
    /*选课表中状态为已考试的数据*/
    @ResponseBody
    @RequestMapping("/getgradeInfor")
    public Map<String,Object> getgradeInfor(){
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        String status = "已考试";
        List<Tselectcourse> selectcourselist = tselectcourseService.selectByStatus(status);
        StringBuffer sb = new StringBuffer();
        for(Tselectcourse tselectcourse:selectcourselist){
            Tuser tuser = tuserService.selectByKey( tselectcourse.getStudentid());
            Tcourse tcourse = tcourseService.selectByKey(tselectcourse.getCourseid());
            sb.append(tuser.getUserName()+","+tuser.getTrueName());
            tselectcourse.setUser(sb.toString());
            sb.delete(0,sb.length());
            sb.append(tcourse.getCourseno()+","+tcourse.getCoursename());
            tselectcourse.setCourse(sb.toString());
        }
        resultmap.put("data",selectcourselist);
        System.out.println("成绩数据测试   "+selectcourselist.toString()+"成绩返回数据测试 "+resultmap.toString());
        return resultmap;
    }

    /*查询某个学院的班级*/
    @ResponseBody
    @RequestMapping("/selectClassnoByCollege")
    public Map<String,Object> selectClassnoByCollege(String collegename){
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        /*用set存储，不重复存储相同的班级号*/
        Set<String> setclassno = new HashSet<>();
        try {
            String c = collegeService.selectcollegeidBycollegename(collegename);
            List list = tuserService.selectByCollegeidAndClassno(c);
            for(Object s:list){
                setclassno.add(s.toString());
            }
            resultmap.put("state","success");
        }catch (Exception e){
            resultmap.put("state","fail");
        }
        return  resultmap;
    }

    /*通过学院名和班级号来查询选课结果*/
    /*返回数据中key-status（通过/未通过/等待），value为对应查出的数据*/
    @ResponseBody
    @RequestMapping("/selectBycollegenameAndclassno")
    public Map<String,Object> selectBycollegenameAndclassno(String collegename,String classno){
        LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
        List<String> statuslist = new ArrayList<>();
        statuslist.add("通过");
        statuslist.add("未通过");
        statuslist.add("等待");
        List<Tselectcourse> selectcourse = new ArrayList<>();
        //如果学院名为null，则通过默认的status查询
        if(collegename==null){
            for(String s1:statuslist){
                selectcourse = tselectcourseService.selectByStatus(s1);
                resultmap.put(s1,selectcourse);
            }
            resultmap.put("success","选课结果在此");
           return resultmap;
        }
        //如果学院名不为空
        else{
            //查询相应的collegeid
            String collegeid = collegeService.selectcollegeidBycollegename(collegename);
            //但班级为空，则为status+学院查询
            if(classno==null){
                //通过学院id查询所有人的id
                List<Tuser> userlist = tuserService.selectByCollegeid(collegeid);
                Integer userid;
                for(String status:statuslist){
                    for(Tuser u:userlist){
                        userid = u.getId();
                        selectcourse=tselectcourseService.selectBystudentidAndstatus(userid,status);
                    }
                    resultmap.put(status,selectcourse);
                }
                resultmap.put("success","此"+collegename+"选课结果如下");
                return  resultmap;
            }
            //班级也不为空，则为学院+班级+status查询
            else{
                //通过学院id+班级名查询所有人的id
                List<Tuser> userlist = tuserService.selectByCollegeidAndClassno(collegename,classno);
                Integer userid;
                for(String status:statuslist){
                    for(Tuser u:userlist){
                        userid = u.getId();
                        selectcourse=tselectcourseService.selectBystudentidAndstatus(userid,status);
                    }
                    resultmap.put(status,selectcourse);
                }
                System.out.println("选课管理测试  "+resultmap.toString()+"    "+resultmap.get("通过").getClass());
                resultmap.put("success","此"+collegename+"-"+classno+"选课结果成功");
                return  resultmap;
            }
        }
    }

    /*管理员审核选课状态*/
    @ResponseBody
    @RequestMapping("/changeselectcourseStatus")
    public String changeselectcourseStatus(Tselectcourse tselectcourse){
        //返回信息
        String message = "success";
        try{
            tselectcourseService.updateNotNull(tselectcourse);
        }catch(Exception e){
            message = "审核失败,系统异常！";
        }
        return message;
    }





}
