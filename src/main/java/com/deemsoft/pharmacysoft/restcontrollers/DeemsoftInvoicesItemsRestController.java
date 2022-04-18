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
import com.deemsoft.pharmacysoft.model.InvoicesItems;
import com.deemsoft.pharmacysoft.model.Catalogs;

@RestController
@RequestMapping("/invoicesitemsrest")
public class DeemsoftInvoicesItemsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftInvoicesItemsRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/saveinvoicesitems/", method = RequestMethod.POST)
	public ResponseEntity<InvoicesItems> saveInvoicesItemsData(@RequestBody List<InvoicesItems> invoicesitems) {
		for ( InvoicesItems temp : invoicesitems ) {			
			Catalogs catalogs = dbService.findCatalogsByID(temp.getcatalogs_id());		
			catalogs.setquantity(catalogs.getquantity() - temp.getquantity());
			dbService.saveCatalogs(catalogs);
			dbService.saveInvoicesItems(temp);
		}		
		InvoicesItems invoicesitem = invoicesitems.get(0);
		if( invoicesitem == null ){
			return new ResponseEntity<InvoicesItems>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<InvoicesItems>(invoicesitem, HttpStatus.OK);
	}

	@RequestMapping(value = "/getinvoicesitems/{id}", method = RequestMethod.GET)
	public ResponseEntity<InvoicesItems> getInvoicesItemsData(@PathVariable("id") int id) {
		InvoicesItems invoicesitems = dbService.findInvoicesItemsByID(id);
		if( invoicesitems == null ){
			return new ResponseEntity<InvoicesItems>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<InvoicesItems>(invoicesitems, HttpStatus.OK);
	}
	@RequestMapping(value = "/listinvoicesitems/", method = RequestMethod.GET)
	public ResponseEntity<List<InvoicesItems>> listAllInvoicesItems() {
		List<InvoicesItems> invoicesitems = dbService.findAllInvoicesItems();
		if(invoicesitems.isEmpty()){
			return new ResponseEntity<List<InvoicesItems>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<InvoicesItems>>(invoicesitems, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchinvoicesitems/", method = RequestMethod.POST)
	public ResponseEntity<List<InvoicesItems>> searchInvoicesItems(@RequestBody InvoicesItems invoicesitems) {
		List<InvoicesItems> invoicesitemslist = dbService.searchInvoicesItems(invoicesitems);
		if(invoicesitemslist.isEmpty()){
			return new ResponseEntity<List<InvoicesItems>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<InvoicesItems>>(invoicesitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchinvoicesitemsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchInvoicesItemsByName(@PathVariable("name") String str) {
		List invoicesitemslist = dbService.searchInvoicesItemsByName(str);
		if(invoicesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(invoicesitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getinvoicesitemsbyinvoiceid/{id}", method = RequestMethod.GET)
	public ResponseEntity<List> getInvoicesItemsbyInvoiceID(@PathVariable("id") int id) {
		List invoicesitemslist = dbService.getInvoicesItemsByInvoiceID(id);
		if(invoicesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(invoicesitemslist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxInvoicesItemsID() {
		List invoicesitemslist = dbService.getMaxInvoicesItemsID();
		if(invoicesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(invoicesitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodinvoicesitems/", method = RequestMethod.POST)
	public ResponseEntity<List> getInvoicesItemsByPeriod(@RequestBody Period pd) {		List invoicesitemslist = dbService.findInvoicesItemsByPeriod(pd);
		if(invoicesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(invoicesitemslist, HttpStatus.OK);
	}}
