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
import com.deemsoft.pharmacysoft.model.PurchasesItems;
import com.deemsoft.pharmacysoft.model.Catalogs;

@RestController
@RequestMapping("/purchasesitemsrest")
public class DeemsoftPurchasesItemsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftPurchasesItemsRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/savepurchasesitems/", method = RequestMethod.POST)
	public ResponseEntity<PurchasesItems> savePurchasesItemsData(@RequestBody List<PurchasesItems> purchasesitems) {
		for ( PurchasesItems temp : purchasesitems ) {			
			Catalogs catalogs = dbService.findCatalogsByID(temp.getcatalogs_id());		
			catalogs.setquantity(catalogs.getquantity() + temp.getquantity()+temp.getfreequantity());
			dbService.saveCatalogs(catalogs);
			dbService.savePurchasesItems(temp);
		}
		PurchasesItems purchasesitem = purchasesitems.get(0);
		if( purchasesitem == null ){
			return new ResponseEntity<PurchasesItems>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<PurchasesItems>(purchasesitem, HttpStatus.OK);
	}

	@RequestMapping(value = "/getpurchasesitems/{id}", method = RequestMethod.GET)
	public ResponseEntity<PurchasesItems> getPurchasesItemsData(@PathVariable("id") int id) {
		PurchasesItems purchasesitems = dbService.findPurchasesItemsByID(id);
		if( purchasesitems == null ){
			return new ResponseEntity<PurchasesItems>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<PurchasesItems>(purchasesitems, HttpStatus.OK);
	}
	@RequestMapping(value = "/getpurchasesitemsbypurchaseid/{id}", method = RequestMethod.GET)
	public ResponseEntity<List> getPurchasesItemsbyPurchaseID(@PathVariable("id") int id) {
		List purchasesitemslist = dbService.getPurchasesItemsbyPurchaseID(id);
		if(purchasesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(purchasesitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/listpurchasesitems/", method = RequestMethod.GET)
	public ResponseEntity<List<PurchasesItems>> listAllPurchasesItems() {
		List<PurchasesItems> purchasesitems = dbService.findAllPurchasesItems();
		if(purchasesitems.isEmpty()){
			return new ResponseEntity<List<PurchasesItems>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<PurchasesItems>>(purchasesitems, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchpurchasesitems/", method = RequestMethod.POST)
	public ResponseEntity<List<PurchasesItems>> searchPurchasesItems(@RequestBody PurchasesItems purchasesitems) {
		List<PurchasesItems> purchasesitemslist = dbService.searchPurchasesItems(purchasesitems);
		if(purchasesitemslist.isEmpty()){
			return new ResponseEntity<List<PurchasesItems>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<PurchasesItems>>(purchasesitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchpurchasesitemsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchPurchasesItemsByName(@PathVariable("name") String str) {
		List purchasesitemslist = dbService.searchPurchasesItemsByName(str);
		if(purchasesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(purchasesitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxPurchasesItemsID() {
		List purchasesitemslist = dbService.getMaxPurchasesItemsID();
		if(purchasesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(purchasesitemslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodpurchasesitems/", method = RequestMethod.POST)
	public ResponseEntity<List> getPurchasesItemsByPeriod(@RequestBody Period pd) {		
		List purchasesitemslist = dbService.findPurchasesItemsByPeriod(pd);
		if(purchasesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(purchasesitemslist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/periodstockreport/", method = RequestMethod.POST)
	public ResponseEntity<List> findStocksVerificationReport(@RequestBody Period pd) {		
		List purchasesitemslist = dbService.findStocksVerificationReport(pd);
		if(purchasesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(purchasesitemslist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/fastmovingstockreport/", method = RequestMethod.POST)
	public ResponseEntity<List> findFastmovingStocksReport(@RequestBody Period pd) {		
		List purchasesitemslist = dbService.findFastmovingStocksReport(pd);
		if(purchasesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(purchasesitemslist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lowstockreport/", method = RequestMethod.POST)
	public ResponseEntity<List> findLowStocksReport(@RequestBody Period pd) {		
		List purchasesitemslist = dbService.findLowStocksReport(pd);
		if(purchasesitemslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(purchasesitemslist, HttpStatus.OK);
	}
	
	
	}
