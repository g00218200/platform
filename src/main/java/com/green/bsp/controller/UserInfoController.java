/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.green.bsp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.green.bsp.common.ExcelTool;
import com.green.bsp.common.RWExcelFile_jxl;
import com.green.bsp.pojo.SalaryInfo;
import com.green.bsp.pojo.UserInfo;
import com.green.bsp.service.UserInfoService;

import jxl.read.biff.BiffException;
import net.sf.json.JSONArray;

/**
 * @author guorui
 * @since 2015-12-19 11:10
 */
@RestController
public class UserInfoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/findUserInfo")
    @ResponseBody
    public String findUserInfo(UserInfo userInfo) {
//        List<UserInfo> userInfoList = userInfoService.getAll(userInfo);
//        return new PageInfo<UserInfo>(userInfoList);
    	UserInfo userinfo =  userInfoService.findUserInfo();
    	
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
    	List<UserInfo> userInfoList = new ArrayList<UserInfo>();
    	userInfoList.add(userinfo);
        return JSONArray.fromObject(salarylist).toString();
    }
    
    @RequestMapping("/toUserInfo")
    public String toUserInfo(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
    	UserInfo userinfo =  userInfoService.findUserInfo();
    	model.addAttribute("name", userinfo.getUsername());
        return "homePage";
    }

}
