package com.deemsoft.pharmacysoft.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.deemsoft.pharmacysoft.model.User;
import com.deemsoft.pharmacysoft.model.UserProfile;
import com.deemsoft.pharmacysoft.service.UserProfileService;
import com.deemsoft.pharmacysoft.service.UserService;

import org.apache.log4j.Logger;
 
@Controller
@RequestMapping("/help")
public class DeemsoftHelpController {
	private static final Logger logger = Logger.getLogger(DeemsoftHelpController.class);

    @Autowired
    UserService userService;
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String About(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "help/about";
	}
	
	@RequestMapping(value = "/usermanual", method = RequestMethod.GET)
	public String usermanual(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "help/usermanual";
	}
      
    @RequestMapping(value = "/adminmanual", method = RequestMethod.GET)
    public String adminmanual(ModelMap model) {
       model.addAttribute("user", getPrincipal());
        return "help/adminmanual";
    }
	
	@RequestMapping(value = "/videos", method = RequestMethod.GET)
    public String videos(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "help/videos";
    }
	@RequestMapping(value = "/blogs", method = RequestMethod.GET)
    public String blogs(ModelMap model) {
       model.addAttribute("user", getPrincipal());
        return "help/blogs";
    }
	@RequestMapping(value = "/wiki", method = RequestMethod.GET)
    public String wiki(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "help/wiki";
    }
    
  
  private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
 
}