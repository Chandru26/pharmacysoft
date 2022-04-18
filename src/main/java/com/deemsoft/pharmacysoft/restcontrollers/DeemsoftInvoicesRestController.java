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
import com.deemsoft.pharmacysoft.model.Invoices;

@RestController
@RequestMapping("/invoicesrest")
public class DeemsoftInvoicesRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftInvoicesRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/saveinvoices/", method = RequestMethod.POST)
	public ResponseEntity<Invoices> saveInvoicesData(@RequestBody Invoices invoices) {
		dbService.saveInvoices(invoices);
		invoices = dbService.findInvoicesByID(invoices.getid());
		if( invoices == null ){
			return new ResponseEntity<Invoices>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Invoices>(invoices, HttpStatus.OK);
	}

	@RequestMapping(value = "/getinvoices/{id}", method = RequestMethod.GET)
	public ResponseEntity<Invoices> getInvoicesData(@PathVariable("id") int id) {
		Invoices invoices = dbService.findInvoicesByID(id);
		if( invoices == null ){
			return new ResponseEntity<Invoices>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Invoices>(invoices, HttpStatus.OK);
	}
	@RequestMapping(value = "/listinvoices/", method = RequestMethod.GET)
	public ResponseEntity<List<Invoices>> listAllInvoices() {
		List<Invoices> invoices = dbService.findAllInvoices();
		if(invoices.isEmpty()){
			return new ResponseEntity<List<Invoices>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Invoices>>(invoices, HttpStatus.OK);
	}
	@RequestMapping(value = "/listinvoicesbystatusanduser/{usr}", method = RequestMethod.GET)
	public ResponseEntity<List> listInvoicesByStatusAndUser(@PathVariable("usr") int usr) {
		List invoiceslist = dbService.ListInvoicesByStatusAndUser(usr);
		if(invoiceslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(invoiceslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchinvoices/", method = RequestMethod.POST)
	public ResponseEntity<List<Invoices>> searchInvoices(@RequestBody Invoices invoices) {
		List<Invoices> invoiceslist = dbService.searchInvoices(invoices);
		if(invoiceslist.isEmpty()){
			return new ResponseEntity<List<Invoices>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Invoices>>(invoiceslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchinvoicesbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchInvoicesByName(@PathVariable("name") String str) {
		List invoiceslist = dbService.searchInvoicesByName(str);
		if(invoiceslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(invoiceslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxInvoicesID() {
		List invoiceslist = dbService.getMaxInvoicesID();
		if(invoiceslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(invoiceslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodinvoices/", method = RequestMethod.POST)
	public ResponseEntity<List> getInvoicesByPeriod(@RequestBody Period pd) {		
		List invoiceslist = dbService.findInvoicesByPeriod(pd);
		if(invoiceslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(invoiceslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodsales/", method = RequestMethod.POST)
	public ResponseEntity<List> getSalesByPeriod(@RequestBody Period pd) {		
		List invoiceslist = dbService.findSalesByPeriod(pd);
		if(invoiceslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(invoiceslist, HttpStatus.OK);
	}
	}
