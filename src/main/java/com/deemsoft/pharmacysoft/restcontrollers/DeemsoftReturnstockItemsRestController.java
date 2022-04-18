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
import com.deemsoft.pharmacysoft.model.ReturnstockItems;
import com.deemsoft.pharmacysoft.model.Catalogs;

@RestController
@RequestMapping("/returnstockitemsrest")
public class DeemsoftReturnstockItemsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftReturnstockItemsRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/savereturnstockitems/", method = RequestMethod.POST)
	public ResponseEntity<ReturnstockItems> saveReturnstockItemsData(@RequestBody List<ReturnstockItems> returnstockitems) {
		for ( ReturnstockItems temp : returnstockitems ) {			
			Catalogs catalogs = dbService.findCatalogsByID(temp.getcatalogs_id());		
			catalogs.setquantity(catalogs.getquantity() + temp.getquantity());
			dbService.saveCatalogs(catalogs);
			dbService.saveReturnstockItems(temp);
		}		
		ReturnstockItems returnstockitem = returnstockitems.get(0);
		if( returnstockitem == null ){
			return new ResponseEntity<ReturnstockItems>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ReturnstockItems>(returnstockitem, HttpStatus.OK);
	}

	@RequestMapping(value = "/getreturnstockitems/{id}", method = RequestMethod.GET)
	public ResponseEntity<ReturnstockItems> getReturnstockItemsData(@PathVariable("id") int id) {
		ReturnstockItems returnstockitems = dbService.findReturnstockItemsByID(id);
		if( returnstockitems == null ){
			return new ResponseEntity<ReturnstockItems>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ReturnstockItems>(returnstockitems, HttpStatus.OK);
	}
	@RequestMapping(value = "/listreturnstockitems/", method = RequestMethod.GET)
	public ResponseEntity<List<ReturnstockItems>> listAllReturnstockItems() {
		List<ReturnstockItems> returnstockitems = dbService.findAllReturnstockItems();
		if(returnstockitems.isEmpty()){
			return new ResponseEntity<List<ReturnstockItems>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ReturnstockItems>>(returnstockitems, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchreturnstockitems/", method = RequestMethod.POST)
	public ResponseEntity<List<ReturnstockItems>> searchReturnstockItems(@RequestBody ReturnstockItems returnstockitems) {
		List<ReturnstockItems> returnstockitemslist = dbService.searchReturnstockItems(returnstockitems);
		if(returnstockitemslist.isEmpty()){
			return new ResponseEntity<List<ReturnstockItems>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ReturnstockItems>>(returnstockitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchreturnstockitemsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchReturnstockItemsByName(@PathVariable("name") String str) {
		List returnstockitemslist = dbService.searchReturnstockItemsByName(str);
		if(returnstockitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(returnstockitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getreturnstockitemsbyreturnid/{id}", method = RequestMethod.GET)
	public ResponseEntity<List> getReturnstockItemsbyInvoiceID(@PathVariable("id") int id) {
		List returnstockitemslist = dbService.getReturnstockItemsByReturnID(id);
		if(returnstockitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(returnstockitemslist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxReturnstockItemsID() {
		List returnstockitemslist = dbService.getMaxReturnstockItemsID();
		if(returnstockitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(returnstockitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodreturnstockitems/", method = RequestMethod.POST)
	public ResponseEntity<List> getReturnstockItemsByPeriod(@RequestBody Period pd) {		List returnstockitemslist = dbService.findReturnstockItemsByPeriod(pd);
		if(returnstockitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(returnstockitemslist, HttpStatus.OK);
	}}
