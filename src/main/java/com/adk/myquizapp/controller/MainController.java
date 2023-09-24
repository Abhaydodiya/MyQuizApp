package com.adk.myquizapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

	@GetMapping("/admin")
	public String dashboard()
	{
		return "admin/dashboard";
	}

}
