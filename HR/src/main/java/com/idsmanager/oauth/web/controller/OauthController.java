package com.idsmanager.oauth.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idsmanager.oauth.domain.dto.UserDto;
import com.idsmanager.oauth.service.UserService;

@Controller("oauthController")
public class OauthController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/test/api/user")
	@ResponseBody
	public String addUser(@RequestBody UserDto user){
		List<String> list = new ArrayList<String>();
		list.add("WEB");
		//list.add("WEB");
		user.setPrivileges(list);
		userService.createUser(user);
		return " user success";
	}
}
