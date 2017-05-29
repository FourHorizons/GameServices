package com.fourhorizons.web.service.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fourhorizons.web.service.model.UserInfo;
import com.fourhorizons.web.service.repository.UserInfoDao;

@RestController
@RequestMapping(value = "/user")
public class UserInfoController {
	@Autowired
	private UserInfoDao appUsers;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMain() {
		return "/list | /id | /save";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<UserInfo> AppUserList() {
		List<UserInfo> target = new ArrayList<>();
		appUsers.findAll().forEach(target::add);
		return target;
	}
	
	@RequestMapping(value = "/byName/{name}", method = RequestMethod.GET)
    public List<UserInfo> person(@PathVariable String name) {
        return appUsers.listByName("%" + name + "%");
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserInfo getById(@PathVariable int id) {
		return appUsers.findOne(id);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody UserInfo appUser) {
		appUsers.save(appUser);
		return ResponseEntity.ok(null);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable int id) {
		appUsers.delete(id);
		return ResponseEntity.ok(null);
	}

}
