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
import com.deemsoft.pharmacysoft.model.Payments;

@RestController
@RequestMapping("/paymentsrest")
public class DeemsoftPaymentsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftPaymentsRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/savepayments/", method = RequestMethod.POST)
	public ResponseEntity<Payments> savePaymentsData(@RequestBody Payments payments) {
		dbService.savePayments(payments);
		payments = dbService.findPaymentsByID(payments.getid());
		if( payments == null ){
			return new ResponseEntity<Payments>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Payments>(payments, HttpStatus.OK);
	}

	@RequestMapping(value = "/getpayments/{id}", method = RequestMethod.GET)
	public ResponseEntity<Payments> getPaymentsData(@PathVariable("id") int id) {
		Payments payments = dbService.findPaymentsByID(id);
		if( payments == null ){
			return new ResponseEntity<Payments>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Payments>(payments, HttpStatus.OK);
	}
	@RequestMapping(value = "/listpayments/", method = RequestMethod.GET)
	public ResponseEntity<List<Payments>> listAllPayments() {
		List<Payments> payments = dbService.findAllPayments();
		if(payments.isEmpty()){
			return new ResponseEntity<List<Payments>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Payments>>(payments, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchpayments/", method = RequestMethod.POST)
	public ResponseEntity<List<Payments>> searchPayments(@RequestBody Payments payments) {
		List<Payments> paymentslist = dbService.searchPayments(payments);
		if(paymentslist.isEmpty()){
			return new ResponseEntity<List<Payments>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Payments>>(paymentslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchpaymentsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchPaymentsByName(@PathVariable("name") String str) {
		List paymentslist = dbService.searchPaymentsByName(str);
		if(paymentslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(paymentslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxPaymentsID() {
		List paymentslist = dbService.getMaxPaymentsID();
		if(paymentslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(paymentslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodpayments/", method = RequestMethod.POST)
	public ResponseEntity<List> getPaymentsByPeriod(@RequestBody Period pd) {		List paymentslist = dbService.findPaymentsByPeriod(pd);
		if(paymentslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(paymentslist, HttpStatus.OK);
	}}
