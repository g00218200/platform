package com.green.bsp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.green.bsp.common.RWExcelFile_jxl;
import com.green.bsp.pojo.SalaryInfo;
import com.green.bsp.pojo.UserInfo;
import com.green.bsp.service.SalaryInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class SalaryInfoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalaryInfoController.class);
	
	@Autowired
    private SalaryInfoService salaryInfoService;

	@RequestMapping("/toHome")
    public String querySalaryInfo(Model model) 
	{
		List<SalaryInfo> salaryInfoList = salaryInfoService.querySalaryInfo();
		model.addAttribute("salarylist", salaryInfoList);
		return "/core/homepage/homepage";
    }
	
	@RequestMapping("/readSalaryInfoList")
	public String readSalaryInfoList(Model model)
	{
		List<SalaryInfo> salarylist = new LinkedList<SalaryInfo>(); 
		RWExcelFile_jxl jxlrw1 = new RWExcelFile_jxl();
		//读取
		//单元格格式--此处都为"文本"
		String excelPath = "D:/RWExcelFile_jxl.xls";
		File excel = new File(excelPath);
		try {
			salarylist = jxlrw1.readExcelOfFile(excel);
		} catch (Exception ex) {
			LOGGER.error("read and write excel occur exception, {}", ex);
		}
		model.addAttribute("salarylist", salarylist);
		return "homePage";
	}
	
	@RequestMapping("/batchAddSalaryInfoList")
	@ResponseBody
	public String batchAddSalaryInfoList()
	{
		JSONObject resultJSON = new JSONObject();
		List<SalaryInfo> salarylist = new LinkedList<SalaryInfo>(); 
		RWExcelFile_jxl jxlrw1 = new RWExcelFile_jxl();
		//读取
		//单元格格式--此处都为"文本"
		String excelPath = "D:/RWExcelFile_jxl.xls";
		File excel = new File(excelPath);
		try {
			salarylist = jxlrw1.readExcelOfFile(excel);
			
			int result = salaryInfoService.batchAddSalaryInfos(salarylist);
			
			LOGGER.info("batch add salary infos success, result = {}", result);
			
		} catch (Exception ex) {
			LOGGER.error("read and write excel occur exception, {}", ex);
		}
		resultJSON.put("status", "200");
		resultJSON.put("message", "batch add successfully!");
		return resultJSON.toString();
	}
}
