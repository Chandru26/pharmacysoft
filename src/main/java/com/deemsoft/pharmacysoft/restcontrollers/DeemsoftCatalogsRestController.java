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
import com.deemsoft.pharmacysoft.model.Catalogs;

@RestController
@RequestMapping("/catalogsrest")
public class DeemsoftCatalogsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftCatalogsRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/savecatalogs/", method = RequestMethod.POST)
	public ResponseEntity<Catalogs> saveCatalogsData(@RequestBody Catalogs catalogs) {
		dbService.saveCatalogs(catalogs);
		catalogs = dbService.findCatalogsByID(catalogs.getid());
		if( catalogs == null ){
			return new ResponseEntity<Catalogs>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Catalogs>(catalogs, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getcatalogs/{id}", method = RequestMethod.GET)
	public ResponseEntity<Catalogs> getCatalogsData(@PathVariable("id") int id) {
		Catalogs catalogs = dbService.findCatalogsByID(id);
		if( catalogs == null ){
			return new ResponseEntity<Catalogs>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Catalogs>(catalogs, HttpStatus.OK);
	}
	@RequestMapping(value = "/listcatalogs/", method = RequestMethod.GET)
	public ResponseEntity<List<Catalogs>> listAllCatalogs() {
		List<Catalogs> catalogs = dbService.findAllCatalogs();
		if(catalogs.isEmpty()){
			return new ResponseEntity<List<Catalogs>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Catalogs>>(catalogs, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchcatalogs/", method = RequestMethod.POST)
	public ResponseEntity<List<Catalogs>> searchCatalogs(@RequestBody Catalogs catalogs) {
		List<Catalogs> catalogslist = dbService.searchCatalogs(catalogs);
		if(catalogslist.isEmpty()){
			return new ResponseEntity<List<Catalogs>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Catalogs>>(catalogslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchcatalogsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchCatalogsByName(@PathVariable("name") String str) {
		List catalogslist = dbService.searchCatalogsByName(str);
		if(catalogslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(catalogslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchcatalogsbybarcode/{barcode}", method = RequestMethod.GET)
	public ResponseEntity<List> searchCatalogsByBarcode(@PathVariable("barcode") String str) {
		List catalogslist = dbService.searchCatalogsByBarcode(str);
		if(catalogslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(catalogslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxCatalogsID() {
		List catalogslist = dbService.getMaxCatalogsID();
		if(catalogslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(catalogslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodcatalogs/", method = RequestMethod.POST)
	public ResponseEntity<List> getCatalogsByPeriod(@RequestBody Period pd) {		List catalogslist = dbService.findCatalogsByPeriod(pd);
		if(catalogslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(catalogslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodcatalogsbar/", method = RequestMethod.POST)
	public ResponseEntity<List> getCatalogsBarByPeriod(@RequestBody Period pd) {		List catalogslist = dbService.findCatalogsBarByPeriod(pd);
		if(catalogslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(catalogslist, HttpStatus.OK);
	}}
