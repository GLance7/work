package com.edu.service;


import com.edu.entity.Tinfor;

import java.util.List;


public interface TinforService extends IService<Tinfor> {
   List<Tinfor> selectAll();
}
