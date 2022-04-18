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
import com.deemsoft.pharmacysoft.model.Purchases;

@RestController
@RequestMapping("/purchasesrest")
public class DeemsoftPurchasesRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftPurchasesRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/savepurchases/", method = RequestMethod.POST)
	public ResponseEntity<Purchases> savePurchasesData(@RequestBody Purchases purchases) {
		dbService.savePurchases(purchases);
		purchases = dbService.findPurchasesByID(purchases.getid());
		if( purchases == null ){
			return new ResponseEntity<Purchases>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Purchases>(purchases, HttpStatus.OK);
	}

	@RequestMapping(value = "/getpurchases/{id}", method = RequestMethod.GET)
	public ResponseEntity<Purchases> getPurchasesData(@PathVariable("id") int id) {
		Purchases purchases = dbService.findPurchasesByID(id);
		if( purchases == null ){
			return new ResponseEntity<Purchases>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Purchases>(purchases, HttpStatus.OK);
	}
	@RequestMapping(value = "/listpurchases/", method = RequestMethod.GET)
	public ResponseEntity<List<Purchases>> listAllPurchases() {
		List<Purchases> purchases = dbService.findAllPurchases();
		if(purchases.isEmpty()){
			return new ResponseEntity<List<Purchases>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Purchases>>(purchases, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchpurchases/", method = RequestMethod.POST)
	public ResponseEntity<List<Purchases>> searchPurchases(@RequestBody Purchases purchases) {
		List<Purchases> purchaseslist = dbService.searchPurchases(purchases);
		if(purchaseslist.isEmpty()){
			return new ResponseEntity<List<Purchases>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Purchases>>(purchaseslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchpurchasesbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchPurchasesByName(@PathVariable("name") String str) {
		List purchaseslist = dbService.searchPurchasesByName(str);
		if(purchaseslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(purchaseslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxPurchasesID() {
		List purchaseslist = dbService.getMaxPurchasesID();
		if(purchaseslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(purchaseslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodpurchases/", method = RequestMethod.POST)
	public ResponseEntity<List> getPurchasesByPeriod(@RequestBody Period pd) {		
		List purchaseslist = dbService.findPurchasesByPeriod(pd);
		if(purchaseslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(purchaseslist, HttpStatus.OK);
	}}
