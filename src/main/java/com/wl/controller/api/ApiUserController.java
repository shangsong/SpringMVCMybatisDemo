package com.wl.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wl.authorization.annotation.Authorization;
import com.wl.model.User;
import com.wl.service.UserService;
import com.wl.web.util.ResponseUtils;

/**
 * 用户信息控制类
 * @author wangliang
 *
 */
@Controller
@RequestMapping("/api/user")
public class ApiUserController {
	
	@Autowired
	private UserService userService;
	
	//produces = {"application/json;charset=UTF-8"}
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	@Authorization
	private @ResponseBody String getUserByUid(@RequestParam(value="uid") String uid) {
		
		if(uid == null || "".equals(uid)){
			return ResponseUtils.generFailedJsonStr(1, "uid 不能为空");
		} else {
			int id = Integer.parseInt(uid);
			User user = userService.getUserById(id);
			if(user != null) {
				String resp = ResponseUtils.generSuccJsonStr(JSONObject.parseObject(JSON.toJSONString(user)));
				System.out.println(resp);
				return resp;
			} else {
				return ResponseUtils.generFailedJsonStr(100, "user not exist.");
			}
		}
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@Authorization
	private @ResponseBody String getUserList() {
		List<User> users = userService.getUsers();
		
		JSONObject data = new JSONObject();
		data.put("list", JSONArray.parse(JSON.toJSONString(users)));
		
		String resp = ResponseUtils.generSuccJsonStr(data);
		System.out.println(resp);
		return resp;
	}
	
	
}
