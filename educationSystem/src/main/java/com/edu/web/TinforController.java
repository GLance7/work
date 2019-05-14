package com.edu.web;


import com.edu.entity.Tinfor;
import com.edu.service.TinforService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/infor")
public class TinforController {
    @Resource
    private TinforService tinforService;

    /*所有信息*/
    @ResponseBody
    @RequestMapping("/inforlist")
    public List<Tinfor> selectAllInfor(){
        List<Tinfor> inforlist = tinforService.selectAll();
        return inforlist;
    }
    /*关于模糊查找（根据title），暂时未做，有时间在做*/
}
