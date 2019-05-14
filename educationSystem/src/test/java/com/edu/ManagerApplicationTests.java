package com.edu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerApplicationTests {
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp(){
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@Test
	public void contextLoads() throws Exception {

		/*无参数的测试*/
	/*	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/selectcourse/selectBycollegenameAndclassno"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();*/
	/*	Tuser testUser = new Tuser();
		testUser.setBz("测试管理员");
		testUser.setTrueName("测试珍视明");
		testUser.setSex("测试男女");
		//testUser.setPolitical();
		//collegeid
		testUser.setGraduate("测试学历");
		testUser.setPhone("测试电话号码");
		testUser.setNativeplace("测试机关");*/
		//Tcourse tcourse = new Tcourse();
	//	tcourse.setId("C01");


		/*有参数的测试*/
	/*	MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/course/selectCourseById")
				.accept(MediaType.APPLICATION_JSON).param("tcourse", "tcourse"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		System.out.println("出错  "+mvcResult.getResponse().getContentAsString());*/
	//JSONObject jsonObject = new JSONObject(tcourse);
	/*MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/course/selectCourseById").content(JSON.toJSONString(tcourse))).andExpect(MockMvcResultMatchers.status().is(200)).andDo(MockMvcResultHandlers.print()).andReturn();
	int status = mvcResult.getResponse().getStatus();
	System.out.println("现在的status "+status);
	String result = mvcResult.getResponse().getContentAsString();
		System.out.println("接口返回结果：" + result);
		JSONObject resultObj = JSON.parseObject(result);
		System.out.println("接口返回JSON结果：" + resultObj);*/

	}

}
