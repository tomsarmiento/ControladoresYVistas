package com.loginandregistration.authentication.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.loginandregistration.authentication.models.User;
import com.loginandregistration.authentication.services.UserService;

@Controller
public class UserController {
	private final UserService s;

	public UserController(UserService s) {
		this.s = s;
	}
	
	@RequestMapping("/registration")
	public String registerForm(@ModelAttribute("user") User u) {
		return "registrationPage.jsp";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "loginPage.jsp";
	}

	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User u, BindingResult rslt, HttpSession session) {
		if(rslt.hasErrors()) {
			return "registrationPage.jsp";
		}
		else {
			s.registerUser(u);
			session.setAttribute("user", u.getId());
			return "redirect:/home";
		}
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginUser(Model model, @RequestParam("email") String e, @RequestParam("password") String p, HttpSession session) {
		if(s.authenticateUser(e, p)==true) {
			session.setAttribute("user", s.findByEmail(e).getId());
			return "redirect:/home";
		}
		else {
			model.addAttribute("error", "Wrong password or email!");
			return "loginPage.jsp";
		}
	}
	
	@RequestMapping("/home")
	public String home(HttpSession session, Model model) {
		Long id = (Long) session.getAttribute("user");
		model.addAttribute("user", s.findUserById(id));
		return "homePage.jsp";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
