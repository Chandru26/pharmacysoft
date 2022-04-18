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
@RequestMapping("/pharma")
public class DeemsoftPharmacysoftController {
	private static final Logger logger = Logger.getLogger(DeemsoftPharmacysoftController.class);

    @Autowired
    UserService userService;
	
	@RequestMapping(value = "/catalogs", method = RequestMethod.GET)
	public String catalog(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		model.addAttribute("userid",getUserID());
		return "pharmacysoft/catalogs";
	}	
	
	@RequestMapping(value = "/printbarcodes", method = RequestMethod.GET)
	public String printbarcode(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		model.addAttribute("userid",getUserID());
		return "pharmacysoft/printbarcodes";
	}
		      
    @RequestMapping(value = "/sales", method = RequestMethod.GET)
    public String sales(ModelMap model) {
       model.addAttribute("user", getPrincipal());
	   model.addAttribute("userid",getUserID());
        return "pharmacysoft/sales";
    }
	
	@RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public String Invoices(ModelMap model) {
       model.addAttribute("user", getPrincipal());
	   model.addAttribute("userid",getUserID());
        return "pharmacysoft/invoices";
    }	
	@RequestMapping(value = "/emails", method = RequestMethod.GET)
    public String Emails(ModelMap model) {
       model.addAttribute("user", getPrincipal());
	   model.addAttribute("userid",getUserID());
        return "pharmacysoft/emails";
    }
	
	@RequestMapping(value = "/purchases", method = RequestMethod.GET)
    public String purchases(ModelMap model) {
       model.addAttribute("user", getPrincipal());
	   model.addAttribute("userid",getUserID());
        return "pharmacysoft/purchases";
    }
	
	@RequestMapping(value = "/inventory", method = RequestMethod.GET)
    public String inventory(ModelMap model) {
       model.addAttribute("user", getPrincipal());
	   model.addAttribute("userid",getUserID());
        return "pharmacysoft/inventory";
    }
	
	@RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String coontacts(ModelMap model) {
       model.addAttribute("user", getPrincipal());
	   model.addAttribute("userid",getUserID());
        return "pharmacysoft/contacts";
    }
	
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public String accounts(ModelMap model) {
       model.addAttribute("user", getPrincipal());
	   model.addAttribute("userid",getUserID());
        return "pharmacysoft/accounts";
    }
	
	@RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String reservationReports(ModelMap model) {
        model.addAttribute("user", getPrincipal());
		model.addAttribute("userid",getUserID());
        return "pharmacysoft/reports";
    }
		
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String reservationSettings(ModelMap model) {
        model.addAttribute("user", getPrincipal());
		model.addAttribute("userid",getUserID());
        return "pharmacysoft/settings";
    } 

	@RequestMapping(value = "/email", method = RequestMethod.GET)
    public String reservationEmail(ModelMap model) {
        model.addAttribute("user", getPrincipal());
		model.addAttribute("userid",getUserID());
        return "pharmacysoft/email";
    }

	@RequestMapping(value = "/returnstock", method = RequestMethod.GET)
    public String reservationReturnstock(ModelMap model) {
        model.addAttribute("user", getPrincipal());
		model.addAttribute("userid",getUserID());
        return "pharmacysoft/returnstock";
    }
	
  private int getUserID(){
	  User usr = userService.findBySso("demo1");
	  return usr.getId();
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