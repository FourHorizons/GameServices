package com.fourhorizons.web.service.web.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/game/time")
public class TimeController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMain() {
		return "/date | /ms";
	}
	
	@RequestMapping(value = "/date", method = RequestMethod.GET)
	public String getSystemDate() {
		long milliseconds = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = new Date(milliseconds);
		String serverDate = sdf.format(resultdate);
		return serverDate;
	}

	@RequestMapping(value = "/ms", method = RequestMethod.GET)
	public long getRawMilliseconds() {
		return System.currentTimeMillis();
	}
}
