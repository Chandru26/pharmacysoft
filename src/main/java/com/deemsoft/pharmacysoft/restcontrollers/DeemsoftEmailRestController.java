package com.deemsoft.pharmacysoft.controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.deemsoft.pharmacysoft.service.EmailService;
import com.deemsoft.pharmacysoft.model.EmailInfo;

import org.apache.log4j.Logger;

@RestController
@RequestMapping("/email")
public class DeemsoftEmailRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftEmailRestController.class);
	
	@Autowired
	EmailService emailService;
	@RequestMapping(value = "/sendwithattachment/", method = RequestMethod.POST)
	public ResponseEntity<EmailInfo> SendEmailWithAttachment(@RequestBody EmailInfo emailInfo) {
		
		emailService.SendEmailWithAttachment(emailInfo);
		if( emailInfo == null ){
			return new ResponseEntity<EmailInfo>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<EmailInfo>(emailInfo, HttpStatus.OK);
	}	
	

}