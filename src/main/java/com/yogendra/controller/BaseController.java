package com.yogendra.controller;

import java.util.Arrays;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping("jwt")
public class BaseController {

	@GetMapping("test1")
	public String test1(@AuthenticationPrincipal UserDetails userDetails) {
		return new Gson().toJson(Arrays.asList(new Student(1, "Yogi", "Panna"), new Student(2, "Krishna", "Bijawar"),
				new Student(3, "Dhananjay", "Jabalpur")));
	}

	static class Student {
		private int id;
		private String name;
		private String address;

		public Student(int id, String name, String address) {
			super();
			this.id = id;
			this.name = name;
			this.address = address;
		}

	}
}
