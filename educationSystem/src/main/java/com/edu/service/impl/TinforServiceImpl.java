package com.edu.service.impl;

import com.edu.entity.Tinfor;
import com.edu.mapper.TinforMapper;
import com.edu.service.TinforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wl
 */
@Service("tinforService")
public class TinforServiceImpl extends BaseService<Tinfor>  implements TinforService {

    @Autowired
    private TinforMapper tinforMapper;
    @Override
    public List<Tinfor> selectAll() {
        return tinforMapper.selectAll();
    }
}
