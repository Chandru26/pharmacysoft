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
import com.deemsoft.pharmacysoft.model.Accounts;

@RestController
@RequestMapping("/accountsrest")
public class DeemsoftAccountsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftAccountsRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/saveaccounts/", method = RequestMethod.POST)
	public ResponseEntity<Accounts> saveAccountsData(@RequestBody Accounts accounts) {
		dbService.saveAccounts(accounts);
		accounts = dbService.findAccountsByID(accounts.getid());
		if( accounts == null ){
			return new ResponseEntity<Accounts>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Accounts>(accounts, HttpStatus.OK);
	}

	@RequestMapping(value = "/getaccounts/{id}", method = RequestMethod.GET)
	public ResponseEntity<Accounts> getAccountsData(@PathVariable("id") int id) {
		Accounts accounts = dbService.findAccountsByID(id);
		if( accounts == null ){
			return new ResponseEntity<Accounts>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Accounts>(accounts, HttpStatus.OK);
	}
	@RequestMapping(value = "/listaccounts/", method = RequestMethod.GET)
	public ResponseEntity<List<Accounts>> listAllAccounts() {
		List<Accounts> accounts = dbService.findAllAccounts();
		if(accounts.isEmpty()){
			return new ResponseEntity<List<Accounts>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Accounts>>(accounts, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchaccounts/", method = RequestMethod.POST)
	public ResponseEntity<List<Accounts>> searchAccounts(@RequestBody Accounts accounts) {
		List<Accounts> accountslist = dbService.searchAccounts(accounts);
		if(accountslist.isEmpty()){
			return new ResponseEntity<List<Accounts>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Accounts>>(accountslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchaccountsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchAccountsByName(@PathVariable("name") String str) {
		List accountslist = dbService.searchAccountsByName(str);
		if(accountslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(accountslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxAccountsID() {
		List accountslist = dbService.getMaxAccountsID();
		if(accountslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(accountslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodaccounts/", method = RequestMethod.POST)
	public ResponseEntity<List> getAccountsByPeriod(@RequestBody Period pd) {		List accountslist = dbService.findAccountsByPeriod(pd);
		if(accountslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(accountslist, HttpStatus.OK);
	}}
