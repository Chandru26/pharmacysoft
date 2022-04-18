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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.log4j.Logger;

import com.deemsoft.pharmacysoft.model.Period;
import com.deemsoft.pharmacysoft.service.DBService;
import com.deemsoft.pharmacysoft.model.Returnstock;

@RestController
@RequestMapping("/returnstockrest")
public class DeemsoftReturnstockRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftReturnstockRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/savereturnstock/", method = RequestMethod.POST)
	public ResponseEntity<Returnstock> saveReturnstockData(@RequestBody Returnstock returnstock) {
		dbService.saveReturnstock(returnstock);
		returnstock = dbService.findReturnstockByID(returnstock.getid());
		if( returnstock == null ){
			return new ResponseEntity<Returnstock>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Returnstock>(returnstock, HttpStatus.OK);
	}

	@RequestMapping(value = "/getreturnstock/{id}", method = RequestMethod.GET)
	public ResponseEntity<Returnstock> getReturnstockData(@PathVariable("id") int id) {
		Returnstock returnstock = dbService.findReturnstockByID(id);
		if( returnstock == null ){
			return new ResponseEntity<Returnstock>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Returnstock>(returnstock, HttpStatus.OK);
	}
	@RequestMapping(value = "/listreturnstock/", method = RequestMethod.GET)
	public ResponseEntity<List<Returnstock>> listAllReturnstock() {
		List<Returnstock> returnstock = dbService.findAllReturnstock();
		if(returnstock.isEmpty()){
			return new ResponseEntity<List<Returnstock>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Returnstock>>(returnstock, HttpStatus.OK);
	}
	@RequestMapping(value = "/listreturnstockbystatusanduser/{usr}", method = RequestMethod.GET)
	public ResponseEntity<List> listReturnstockByStatusAndUser(@PathVariable("usr") int usr) {
		List returnstocklist = dbService.ListReturnstockByStatusAndUser(usr);
		if(returnstocklist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(returnstocklist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchreturnstock/", method = RequestMethod.POST)
	public ResponseEntity<List<Returnstock>> searchReturnstock(@RequestBody Returnstock returnstock) {
		List<Returnstock> returnstocklist = dbService.searchReturnstock(returnstock);
		if(returnstocklist.isEmpty()){
			return new ResponseEntity<List<Returnstock>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Returnstock>>(returnstocklist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchreturnstockbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchReturnstockByName(@PathVariable("name") String str) {
		List returnstocklist = dbService.searchReturnstockByName(str);
		if(returnstocklist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(returnstocklist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxReturnstockID() {
		List returnstocklist = dbService.getMaxReturnstockID();
		if(returnstocklist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(returnstocklist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodreturnstock/", method = RequestMethod.POST)
	public ResponseEntity<List> getReturnstockByPeriod(@RequestBody Period pd) {		
		List returnstocklist = dbService.findReturnstockByPeriod(pd);
		if(returnstocklist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(returnstocklist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodreturn/", method = RequestMethod.POST)
	public ResponseEntity<List> getReturnByPeriod(@RequestBody Period pd) {		
		List returnstocklist = dbService.findReturnByPeriod(pd);
		if(returnstocklist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(returnstocklist, HttpStatus.OK);
	}
	}
