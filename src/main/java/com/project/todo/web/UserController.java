package com.project.todo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.todo.model.User;
import com.project.todo.service.SecurityService;
import com.project.todo.service.UserService;
import com.project.todo.validator.UserValidator;


@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	private static final String LOGIN_ERROR = "Your username and password is invalid";
	private static final String LOGOUT_MSG = "You have been logged out successfully";

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		logger.debug("GET request received for /registration");

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("userForm", new User());
		modelAndView.setViewName("registration");

		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		logger.debug("POST request received for /registration");

		ModelAndView modelAndView = new ModelAndView();

		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			logger.debug("userForm unsuccessfully validated - returning registration");

			modelAndView.setViewName("registration");
		}
		else {
			logger.debug("userForm successfully validated - return todo");

			userService.save(userForm);
			securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
			modelAndView.setViewName("redirect:/todo");
		}

		return modelAndView;
	}


	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(String error, String logout) {
		logger.debug("GET request received for /login");

		ModelAndView modelAndView = new ModelAndView();

		if (error != null)
			modelAndView.addObject("error", LOGIN_ERROR);

		if (logout != null)
			modelAndView.addObject("message", LOGOUT_MSG);

		modelAndView.setViewName("login");

		return modelAndView;
	}
}
