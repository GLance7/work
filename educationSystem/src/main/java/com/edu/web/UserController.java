package com.edu.web;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.edu.entity.*;
import com.edu.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @Author: wl
* @Description: 当前登录用户控制器
*/
@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true")
public class UserController {

	@Resource
	private TroleService troleService;
	
	@Resource
	private TuserService tuserService;
	
	@Resource
	private TmenuService tmenuService;

	@Resource
	private CollegeService collegeService;

	@Resource
	private TcourseService tcourseService;

	@Resource
	private TselectcourseService tselectcourseService;

	Example tuserExample;
	
	@Resource
	private LogService logService;

	Tuser current;
	Trole currentRole;



	/*获得所有的学院*/
	@ResponseBody
	@RequestMapping("/getAllCollege")
	public Map<String,Object> getAllCollege(){
		LinkedHashMap<String,Object> resultMap = new LinkedHashMap<>();
		try {
			Example ex = new Example(College.class);
			List<College> collegelist = collegeService.selectByExample(ex);
			if(collegelist.size()==0){
				resultMap.put("fail",false);
				resultMap.put("msg","系统异常，请联系管理员（13778860798）");
			}
			resultMap.put("success",true);
			resultMap.put("msg","查询成功！");
			resultMap.put("data",collegelist);
		}catch (Exception e){
			resultMap.put("fail",false);
			resultMap.put("msg","系统异常,未知错误，请联系管理员（13778860798）");
		}
		return resultMap;
	}
	
	/**
     * 用户登录请求
     */
	@ResponseBody
	@RequestMapping("/test")
	public String test(){
		System.out.println("this is test part");
		return "this is test part";
	}

    @ResponseBody
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody LoginRequest loginRequest, BindingResult bindingResult, HttpSession session){
		System.out.println("haha   "+loginRequest.getUsername());
    	System.out.println("this is login part");
    	Map<String,Object> map=new HashMap<String,Object>();
   /* 	if(StringUtils.isEmpty(loginRequest.getImageCode())){
    		map.put("success", false);
    		map.put("errorInfo", "请输入验证码！");
    		return map;
    	}*/
    	/*if(!session.getAttribute("checkcode").equals(loginRequest.getImageCode())){
    		map.put("success", false);
    		map.put("errorInfo", "验证码输入错误！");
    		return map;
    	}*/
    	if(bindingResult.hasErrors()){
    		map.put("success", false);
    		map.put("errorInfo", bindingResult.getFieldError().getDefaultMessage());
    		return map;
    	}
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(loginRequest.getUsername(), loginRequest.getPassword());
		System.out.println("ffff   "+token);
		try{
			subject.login(token); // 登录认证
			String userName=(String) SecurityUtils.getSubject().getPrincipal();
			tuserExample=new Example(Tuser.class);
			tuserExample.or().andEqualTo("userName",userName);
			System.out.println("角色 "+userName);
			Tuser currentUser=tuserService.selectByExample(tuserExample).get(0);
			//session.setAttribute("currentUser", currentUser);
			current = currentUser;
			List<Trole> roleList=troleService.selectRolesByUserId(currentUser.getId());
			System.out.println("反  "+currentUser.getId()+"   "+currentUser.getRemarks()   +"    "+roleList.toString());
			map.put("roleList", roleList);
			map.put("roleSize", roleList.size());
			map.put("success", true);
			//logService.save(new Log(Log.LOGIN_ACTION,"用户登录")); // 写入日志


			Example ex = new Example(Trole.class);
			List<Trole>  role = troleService.selectByExample(ex);
			System.out.println("rrr  "+role.get(0).toString());
			currentRole=role.get(0);


			//日志写入
			String title = currentUser.getUserName()+"（"+currentUser.getTrueName()+"）"+"用户登录系统";
			tolog(title);
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "用户名或者密码错误！");
			return map;
		}
    }

    /*将当前操作存入日志表中*/
	@ResponseBody
	@RequestMapping("/tolog")
	public void tolog(String title){
		//获取当前系统时间
		Date time = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//将数据存储到日志表中
		Log log = new Log();
		log.setDate(df.format(time));
		log.setTitle(title);
		logService.save(log);
	}


	/**
	 * 保存角色信息
	 */
/*
	@ResponseBody
	@PostMapping("/saveRole")
	public Map<String,Object> saveRole(Integer roleId,HttpSession session)throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();

		Trole

//		System.out.println("哈哈哈哈哈哈哈哈哈哈哈哈哈哈  哈哈"+currentRole.getName()+"   "+currentRole.getId() +"   "+currentRole.getBz());
		session.setAttribute("currentRole", currentRole); // 保存当前角色信息
		putTmenuOneClassListIntoSession(session);
		map.put("success", true);
		return map;
	}
*/

	/**
	 * 安全退出
	 */
	@GetMapping("/logout")
	public String logout() throws Exception {
//		logService.save(new Log(Log.LOGOUT_ACTION,"用户注销"));
		Tuser currentUser=tuserService.selectByExample(tuserExample).get(0);
		String title = currentUser.getUserName()+"（"+currentUser.getTrueName()+"）"+"用户退出系统";
		SecurityUtils.getSubject().logout();
		tolog(title);
		return "redirect:/tologin";
	}


	/**
	 * 加载权限菜单
	 * 这里传入的parentId是1
	 */
	@ResponseBody
	@GetMapping("/loadMenuInfo")
	public String loadMenuInfo(@RequestParam String token){

		//putTmenuOneClassListIntoSession(session);
		System.out.println(token+"父");
		Example ex1 = new Example(Tuser.class);
		List<Tuser> tuserList = tuserService.selectByExample(ex1);
		Integer userid = 0 ;
		for(Tuser user:tuserList){
			if(user.getUserName().equals(token)){
				userid = user.getId();
			}else{
				continue;
			}
		}
		System.out.println("ttttt  "+userid+"   "+tuserList.get(0).getUserName());

		System.out.println("rol   oooo  "+userid);
		List<Trole> rolelist = troleService.selectRolesByUserId(userid);

		Trole role = rolelist.get(0);
		System.out.println("uuuuuuu    "+role.getId()+"    iii"+rolelist.toString());
		String json = getAllMenuByParentId(1,role.getId()).toString();


		//根据当前用户的角色id和父节点id查询所有菜单及子集json
		System.out.println(role.getId()+"就这一个");
		System.out.println("爷爷  "+json);
		//String json=getAllMenuByParentId(Integer.parseInt(parentId),currentRole.getId()).toString();
		//System.out.println("拉拉  "+json);
		return json;
	}


	/*根据当前id去查询所有课程*/
/*	@ResponseBody
	@RequestMapping("/myCourse")
	public */

	/*
	显示个人信息
	*/
	@ResponseBody
	@GetMapping("/getPersonInfo")
	public String getPersonInfor(@RequestParam String token) throws Exception{
	//	Tuser currentUser=tuserService.selectByExample(tuserExample).get(0);
		System.out.println("当前  "+token);
		Tuser u = tuserService.selectByusername(token);
		String collegeid = u.getCollegeid();
		System.out.println("反   "+collegeid);
        String collegename = collegeService.selectCollegeNameByCollegeId(collegeid);
		List<Object> personlist = new ArrayList<>();
		personlist.add(u.getBz());
		personlist.add(u.getUserName());
		personlist.add(u.getTrueName());
		personlist.add(u.getPhone());
		personlist.add(u.getGraduate());
		personlist.add(u.getWorkingtime());
		personlist.add(u.getNativeplace());
		personlist.add(u.getSex());
		personlist.add(u.getPolitical());
		personlist.add(collegename);
		System.out.println("哦哦  "+personlist.toString());
		return personlist.toString();
	}

	/*
	* 修改个人信息
	* 这里有点问题
	* */
	@ResponseBody
	@PostMapping("/updatePersonInfor")
	//*@RequestParam("username") String name
	public String updatePersonInfor(Tuser tuser){
		String resultmsg = new String();
		try {
			Integer num = tuserService.updateNotNull(tuser);
			System.out.println("测试修改个人信息："+num);
			resultmsg = "success：恭喜你，操作成功了！！";
		}catch (Exception e){
			resultmsg = "fail:系统异常，操作失败了！！";
		}
		return resultmsg;
		/*Tuser tuser = new Tuser();
		Tuser currentUser=tuserService.selectByExample(tuserExample).get(0);
		tuser.setTrueName("测试名");
		System.out.println("当前  "+currentUser.getUserName()+"    "+tuser.toString());
		tuserService.updateChangeInfor(tuser);
		return "success";*/
	}
	/*获得当前用户教的课程*/
	/*根据传入的用户名来查找他所教课程*/
	@ResponseBody
	@RequestMapping("/teachercourse")
	public Map<String,Object> teachercourse(@RequestParam String token){
		LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();

		List<Integer> list = getCourseBycurrentid(token);
		if(list.size()==0){
			resultmap.put("state","fail");
			resultmap.put("msg","您还没有教授的课程哟！");
		}
		List<Tcourse> courselist = new ArrayList<>() ;
		for (Integer id:list){
			courselist.add(tcourseService.selectByKey(id));
		}
		/*StringBuffer sb = new StringBuffer();
		for (Trole r : roleList) {
			sb.append("," + r.getName());
		}
		u.setRoles(sb.toString().replaceFirst(",", ""));
*/
		resultmap.put("state","success");
		resultmap.put("datamap",courselist);
		System.out.println("当前课程  "+resultmap.toString());
		return resultmap;
	}
	@ResponseBody
	@RequestMapping("/selectAllLog")
	public List<Log> selectAllLog(){
		Example example = new Example(Log.class);
		List<Log> list = logService.selectByExample(example);
		return  list;
	}

	/*获得当前用户的学生和当前用户学生的成绩*/
	@ResponseBody
	@RequestMapping("/myStudentAndGrade")
	public Map<String,Object> myStudentAndGrade(@RequestParam String token){
		LinkedHashMap<String, Object> datamap = new LinkedHashMap<String, Object>();
		LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
		//获得courseid
		List<Integer> list = getCourseBycurrentid(token);
		System.out.println("查询当前老师教的课程号   "+list.toString());

		List<String> statuslist = new ArrayList<>();
		/*三种状态*/
		statuslist.add("已考试");
		statuslist.add("通过");
		statuslist.add("未通过");
		//statuslist.add("等待");
		if(list.size()==0){
			resultmap.put("state","fail");
			resultmap.put("msg","您还没有教授的课程哟！");
		}else{
			List<List> list111 = new ArrayList<>();
			List<Tselectcourse> tselectcourseList = new ArrayList<>();
			System.out.println("测试状态list里是否有数据  "+statuslist.toString());
			//保留学生的id
			Set<Integer> setid = new HashSet<>();
			for(String status:statuslist){
				for(Integer id:list){
					System.out.println("测试每种status的学生的id  "+id+"   "+status);
					tselectcourseList = tselectcourseService.selectBycourseidAndStatus(id,status);
					for(Tselectcourse tselectcourse:tselectcourseList){
						setid.add(tselectcourse.getStudentid());
					}
					list111.add(tselectcourseList);
				}
				System.out.println("hehe   "+list111.toString()+"    "+list111.get(0)+"     ooo  "+setid.toString());
			}
			resultmap.put("data",list111);
			resultmap.put("state","success");
			resultmap.put("msg","成功！");
		}
		System.out.println("当前教授的学生测试 "+resultmap.toString());
		return resultmap;
	}




	/*查询该老师教的所有课程*/
	private List<Integer> getCourseBycurrentid(String name){
		Tuser user = tuserService.selectByusername(name);
		//Tuser currentUser=tuserService.selectByExample(tuserExample).get(0);
		Integer teacherid = user.getId();
		List<Integer> list = new ArrayList<>();
		List<Tcourse> courselist = tcourseService.selectCourseByTeacherid(teacherid);
		//System.out.println("老师  "+courselist.get(0).getCoursename());
		for(Tcourse tcourse:courselist){
			list.add(tcourse.getId());
		}
		System.out.println("老师  "+list.toString());
		return list;
	}

	/*查询当前用户的成绩(学生)*/
	@ResponseBody
	@RequestMapping("/getGradeByid")
	public  Map<String,Object> getGradeid(@RequestParam String token){
		LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
		//获得当前用户的id
		//Tuser currentUser=tuserService.selectByExample(tuserExample).get(0);
		Tuser u = tuserService.selectByusername(token);
		Integer id = u.getId();
		String status = "已考试";
		List<Tselectcourse> tselectcourseList = tselectcourseService.selectBystudentidAndstatus(id,status);
		if(tselectcourseList.size()==0){
			resultmap.put("state","fail");
			resultmap.put("msg","您还没有成绩哦！！！");
		}
		StringBuffer sb = new StringBuffer();
		for(Tselectcourse s:tselectcourseList){
			Tcourse course = tcourseService.selectByKey(s.getCourseid());
			sb.append(course.getCourseno()+","+course.getCoursename());
			s.setCourse(sb.toString());
			sb.delete(0,sb.length());
			sb.append(u.getUserName()+","+u.getTrueName());
			s.setUser(sb.toString());
		}
		resultmap.put("state","success");
		resultmap.put("state","成功");
		resultmap.put("datamap",tselectcourseList);
		resultmap.put("currentUser",u);

		/*分页处理，暂时不做*/
		/*     PageHelper.startPage(jqgridbean.getPage(), jqgridbean.getLength());
        List<Tuser> userList = userService.selectByExample(tuserExample);
        PageRusult<Tuser> pageRusult =new PageRusult<Tuser>(userList);*/

		return resultmap;
	}

	/*查看当前用户的选课结果*/
	/*根据当前用户名查*/
	@ResponseBody
	@RequestMapping("/getselectResult")
	public Map<String,Object> getselectResult(@RequestParam String token){
		LinkedHashMap<String, Object> resultmap = new LinkedHashMap<String, Object>();
		//获得当前用户的id
		//Tuser currentUser=tuserService.selectByExample(tuserExample).get(0);
		Tuser u = tuserService.selectByusername(token);
		Integer id = u.getId();

		List<String> statuslist = new ArrayList<>();
		statuslist.add("通过");
		statuslist.add("未通过");
		StringBuffer sb = new StringBuffer();
		for(String status:statuslist){
			List<Tselectcourse> tselectcourseList = tselectcourseService.selectBystudentidAndstatus(id,status);
			for(Tselectcourse t:tselectcourseList){
				Tcourse course = tcourseService.selectByKey(t.getCourseid());
				Integer teacherid = course.getTeacherid();
				System.out.println("没错呀  "+teacherid);
				Tuser user = tuserService.selectByKey(teacherid);

				System.out.println("lll   lllll "+course.getCollegeid());
				College college = collegeService.selectByKey(course.getCollegeid());
				//System.out.println("ooooo  "+user.getTrueName());
				sb.append(course.getCourseno()+","+course.getCoursename()+","+course.getCredit()+","+course.getStarttime()+","+course.getClasstime()+","+course.getPlace()+",");
				t.setCourse(sb.toString());
				t.setUser(u.getTrueName());
				t.setTeachername(","+user.getTrueName());
				t.setCollegename(college.getCollegename());
			}
			resultmap.put(status,tselectcourseList);
		}
		return resultmap;
	}

	/*学生的我要选课功能*/
	/*根据当前传入的课程id和用户名来插入到表中*/
	@ResponseBody
	@RequestMapping("/toMyselectCourse")
	public String toMyselectCourse(@RequestParam Integer courseid,@RequestParam String token){
		String msg = new String();
		//获得当前用户的id
		//Tuser currentUser=tuserService.selectByExample(tuserExample).get(0);
		try{
			Tuser u = tuserService.selectByusername(token);
			Integer id = u.getId();
			Tselectcourse tselectcourse = new Tselectcourse();
			tselectcourse.setStudentid(id);
			tselectcourse.setCourseid(courseid);
			tselectcourse.setStatus("等待");
			tselectcourseService.saveNotNull(tselectcourse);
			msg = "success:选课成功，等待审核！请及时刷新和注意您的选课结果！";
		}catch (Exception e){
			msg = "fail:选课失败哟，系统异常！";
		}
		return  msg;
	}


	/*获得正开选的课程*/
	@ResponseBody
	@RequestMapping("/getstartSelect")
	public List<Tcourse> getstartSelect(){
		String status = "已开选";
		List<Tcourse> course = tcourseService.selectByStatus(status);
		return course;
	}


	/**
	 * 获取根频道所有菜单信息
	 */
	private JsonArray getAllMenuByParentId(Integer parentId,Integer roleId){
		JsonArray result=new JsonArray();
		JsonObject jsonObject = new JsonObject();
		List<Object> list = new ArrayList<>();
		System.out.println("原因   "+roleId+"   "+parentId);
		JsonArray jsonArray=this.getMenuByParentId(parentId, roleId);//得到所有一级菜单
		System.out.println("与  "+jsonArray.toString()+"   "+jsonArray.size());
		for(int i=0;i<jsonArray.size();i++){
			 jsonObject=(JsonObject) jsonArray.get(i);
			//判断该节点下时候还有子节点
			Example example=new Example(Tmenu.class);
			System.out.println("吗 "+example.toString());
			example.or().andEqualTo("pId",jsonObject.get("id").getAsString());
			//if("true".equals(jsonObject.get("spread").getAsString())){
			if (tmenuService.selectCountByExample(example)==0) {
				continue;
			}else{
				jsonObject.add("children",getAllMenuJsonArrayByParentId(jsonObject.get("id").getAsInt(),roleId));
				result.add(jsonObject);
				System.out.println("嘛  "+jsonObject.toString());
			}
		}
		/*System.out.println("wwww  "+result.toString());
		System.out.println("嘛 jyjy   "+jsonArray.toString());
		System.out.println("嘛  "+jsonObject.toString());*/
		return result;
	}



	//获取根频道下子频道菜单列表集合
	private JsonArray getAllMenuJsonArrayByParentId(Integer parentId,Integer roleId){
		JsonArray jsonArray=this.getMenuByParentId(parentId, roleId);
		//System.out.println("二  "+parentId+"    "+roleId+"   "+jsonArray.toString());
		for(int i=0;i<jsonArray.size();i++){
			JsonObject jsonObject=(JsonObject) jsonArray.get(i);
			//判断该节点下是否还有子节点
			Example example=new Example(Tmenu.class);
			//System.out.println("呵呵     "+example.toString());
			example.or().andEqualTo("pId",jsonObject.get("id").getAsString());
			if (tmenuService.selectCountByExample(example)==0) {
				continue;
			}else{
				//二级菜单
				jsonObject.add("children", getAllMenuJsonArrayByParentId(jsonObject.get("id").getAsInt(),roleId));
			}
		}
		return jsonArray;
	}

	/**
	 * 根据父节点和用户角色id查询菜单
	 */
	private JsonArray getMenuByParentId(Integer parentId,Integer roleId){
		//List<Menu> menuList=menuService.findByParentIdAndRoleId(parentId, roleId);
		HashMap<String,Object> paraMap=new HashMap<String,Object>();
		//System.out.println("获得pid   "+parentId+"   "+roleId);
		paraMap.put("pid",parentId);
		paraMap.put("roleid",roleId);
		List<Tmenu> menuList=tmenuService.selectByParentIdAndRoleId(paraMap);

		JsonArray jsonArray=new JsonArray();
		for(Tmenu menu:menuList){
			//System.out.println("哈了   "+menu);
			JsonObject jsonObject=new JsonObject();
			jsonObject.addProperty("id", menu.getId()); // 节点id
			jsonObject.addProperty("title", menu.getName()); // 节点名称
			jsonObject.addProperty("spread", false); // 不展开（暂时不适用）
			jsonObject.addProperty("icon", menu.getIcon());
			jsonObject.addProperty("url", menu.getUrl()); // 菜单请求地址
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

/*保存一下session，但是没解决的是：前后分离情况下每次建立连接后会有一个session，
* 每个sessionId不一样，怎么办呢？？？
* */
	/*public void putTmenuOneClassListIntoSession(HttpSession session){
		//用来在welcome.ftl中获取主菜单列表
		Example example=new Example(Tmenu.class);
		example.or().andEqualTo("pId",1);
		List<Tmenu> tmenuOneClassList=tmenuService.selectByExample(example);
		//System.out.println("前期   "+tmenuOneClassList.toString());
		session.setAttribute("tmenuOneClassList", tmenuOneClassList);
	}*/
}
