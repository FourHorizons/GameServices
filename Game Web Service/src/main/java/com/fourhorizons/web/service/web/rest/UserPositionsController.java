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

import com.fourhorizons.web.service.model.UserPositions;
import com.fourhorizons.web.service.repository.UserPositionsDao;

@RestController
@RequestMapping(value = "/positions")
public class UserPositionsController {
	@Autowired
	private UserPositionsDao positions;

	@RequestMapping(method = RequestMethod.GET)
	public String getMain() {
		return "/list | /id | /save";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<UserPositions> position() {
		List<UserPositions> target = new ArrayList<>();
		positions.findAll().forEach(target::add);
		return target;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserPositions getById(@PathVariable int id) {
		return positions.findOne(id);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<Void> save(@RequestBody UserPositions position) {
		positions.save(position);
		return ResponseEntity.ok(null);
	}

}
