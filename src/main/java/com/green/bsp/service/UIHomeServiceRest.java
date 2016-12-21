package com.green.bsp.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UIHomeServiceRest {

	@RequestMapping("/getMessages")
	public String getHomeMessage(@RequestParam(value="name", required=false, defaultValue="World") String name) {
		return "homemessages";
	}
	
	@RequestMapping("/getUserInfo")
	public String getUserInfo(@RequestParam(value="name", required=false, defaultValue="World") String name) {
		return "userinfo";
	}
	
}
