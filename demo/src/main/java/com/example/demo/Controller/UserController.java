// package com.example.demo.Controller;

// import java.security.Principal;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;

// import com.example.demo.dto.UserDto;
// import com.example.demo.service.UserService;



// @Controller
// public class UserController {
	
// 	@Autowired
// 	UserService userDetailsService;
	
// 	@Autowired
// 	private UserService userService;
	
	
// 	@GetMapping("/registration")
// 	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
// 		return "register";
// 	}
	
// 	@PostMapping("/registration")
// 	public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
// 		userService.save(userDto);
// 		model.addAttribute("message", "Registered Successfuly!");
// 		return "register";
// 	}
	
// 	@GetMapping("/login")
// 	public String login() {
// 		return "login";
// 	}
	
// 	@GetMapping("user-page")
// 	public String userPage (Model model, Principal principal) {
// 		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
// 		model.addAttribute("user", userDetails);
// 		return "user";
// 	}
	
// 	@GetMapping("admin-page")
// 	public String adminPage (Model model, Principal principal) {
// 		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
// 		model.addAttribute("user", userDetails);
// 		return "admin";
// 	}

// }
package com.example.demo.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/registration")
	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
		return "register";
	}
	
	@PostMapping("/registration")
	public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
		userService.save(userDto);
		model.addAttribute("message", "Registered Successfully!");
		return "register";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/login-success")
	public String loginSuccess(Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		
		// Check roles and redirect accordingly
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				return "redirect:/admin-page";
			} else if (authority.getAuthority().equals("ROLE_CLIENT")) {
				return "redirect:/user-page";
			}
		}
		
		// Default redirect if no role matches
		return "redirect:/login"; 
	}
	
	@GetMapping("/user-page")
	public String userPage(Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		return "user";
	}
	
	@GetMapping("/admin-page")
	public String adminPage(Model model, Principal principal) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
		model.addAttribute("user", userDetails);
		return "admin";
	}
}
