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

import com.fourhorizons.web.service.model.UserPoints;
import com.fourhorizons.web.service.repository.UserPointsDao;

@RestController
@RequestMapping(value = "/points")
public class UserPointsController {
	@Autowired
	private UserPointsDao points;

	@RequestMapping(method = RequestMethod.GET)
	public String getMain() {
		return "/list | /id | /update | /save";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<UserPoints> PointsList() {
		List<UserPoints> target = new ArrayList<>();
		points.findAll().forEach(target::add);
		return target;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserPoints getById(@PathVariable Integer id) {
		return points.findOne(id);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(UserPoints userPoints) {
		UserPoints current = points.findOne(userPoints.getId());
		current.setPoints(userPoints.getPoints());
		return ResponseEntity.ok(null);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody UserPoints pts) {
		points.save(pts);
		return ResponseEntity.ok(null);
	}
}