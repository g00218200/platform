package com.green.bsp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.bsp.mapper.SalaryInfoMapper;
import com.green.bsp.pojo.SalaryInfo;


/**
 * @author guorui
 * @since 2016-01-31 21:42
 */
@Service
public class SalaryInfoService {

    @Autowired
    private SalaryInfoMapper salaryInfoMapper;

    public List<SalaryInfo> querySalaryInfo() 
    {
        return salaryInfoMapper.querySalaryInfo();
    }
    
    public int batchAddSalaryInfos(List<SalaryInfo> salarylist) 
    {
        return salaryInfoMapper.batchAddSalaryInfos(salarylist);
    }
    
}
