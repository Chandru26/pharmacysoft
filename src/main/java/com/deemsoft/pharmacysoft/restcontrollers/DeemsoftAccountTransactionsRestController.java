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
import com.deemsoft.pharmacysoft.model.AccountTransactions;

@RestController
@RequestMapping("/accounttransactionsrest")
public class DeemsoftAccountTransactionsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftAccountTransactionsRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/saveaccounttransactions/", method = RequestMethod.POST)
	public ResponseEntity<AccountTransactions> saveAccountTransactionsData(@RequestBody AccountTransactions accounttransactions) {
		dbService.saveAccountTransactions(accounttransactions);
		accounttransactions = dbService.findAccountTransactionsByID(accounttransactions.getid());
		if( accounttransactions == null ){
			return new ResponseEntity<AccountTransactions>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<AccountTransactions>(accounttransactions, HttpStatus.OK);
	}

	@RequestMapping(value = "/getaccounttransactions/{id}", method = RequestMethod.GET)
	public ResponseEntity<AccountTransactions> getAccountTransactionsData(@PathVariable("id") int id) {
		AccountTransactions accounttransactions = dbService.findAccountTransactionsByID(id);
		if( accounttransactions == null ){
			return new ResponseEntity<AccountTransactions>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<AccountTransactions>(accounttransactions, HttpStatus.OK);
	}
	@RequestMapping(value = "/listaccounttransactions/", method = RequestMethod.GET)
	public ResponseEntity<List<AccountTransactions>> listAllAccountTransactions() {
		List<AccountTransactions> accounttransactions = dbService.findAllAccountTransactions();
		if(accounttransactions.isEmpty()){
			return new ResponseEntity<List<AccountTransactions>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<AccountTransactions>>(accounttransactions, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchaccounttransactions/", method = RequestMethod.POST)
	public ResponseEntity<List<AccountTransactions>> searchAccountTransactions(@RequestBody AccountTransactions accounttransactions) {
		List<AccountTransactions> accounttransactionslist = dbService.searchAccountTransactions(accounttransactions);
		if(accounttransactionslist.isEmpty()){
			return new ResponseEntity<List<AccountTransactions>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<AccountTransactions>>(accounttransactionslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchaccounttransactionsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchAccountTransactionsByName(@PathVariable("name") String str) {
		List accounttransactionslist = dbService.searchAccountTransactionsByName(str);
		if(accounttransactionslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(accounttransactionslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxAccountTransactionsID() {
		List accounttransactionslist = dbService.getMaxAccountTransactionsID();
		if(accounttransactionslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(accounttransactionslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodaccounttransactions/", method = RequestMethod.POST)
	public ResponseEntity<List> getAccountTransactionsByPeriod(@RequestBody Period pd) {		List accounttransactionslist = dbService.findAccountTransactionsByPeriod(pd);
		if(accounttransactionslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(accounttransactionslist, HttpStatus.OK);
	}}
