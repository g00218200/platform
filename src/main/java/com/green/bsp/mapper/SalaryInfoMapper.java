package com.green.bsp.mapper;

import java.util.List;

import com.green.bsp.pojo.SalaryInfo;

public interface SalaryInfoMapper {
	
	public List<SalaryInfo> querySalaryInfo();
	
	public int batchAddSalaryInfos(List<SalaryInfo> salarylist);
	
}
