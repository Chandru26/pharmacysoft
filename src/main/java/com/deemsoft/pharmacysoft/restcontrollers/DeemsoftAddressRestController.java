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
import com.deemsoft.pharmacysoft.model.Address;

@RestController
@RequestMapping("/addressrest")
public class DeemsoftAddressRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftAddressRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/saveaddress/", method = RequestMethod.POST)
	public ResponseEntity<Address> saveAddressData(@RequestBody Address address) {
		dbService.saveAddress(address);
		address = dbService.findAddressByID(address.getid());
		if( address == null ){
			return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}

	@RequestMapping(value = "/getaddress/{id}", method = RequestMethod.GET)
	public ResponseEntity<Address> getAddressData(@PathVariable("id") int id) {
		Address address = dbService.findAddressByID(id);
		if( address == null ){
			return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}
	@RequestMapping(value = "/listaddress/", method = RequestMethod.GET)
	public ResponseEntity<List<Address>> listAllAddress() {
		List<Address> address = dbService.findAllAddress();
		if(address.isEmpty()){
			return new ResponseEntity<List<Address>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Address>>(address, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchaddress/", method = RequestMethod.POST)
	public ResponseEntity<List<Address>> searchAddress(@RequestBody Address address) {
		List<Address> addresslist = dbService.searchAddress(address);
		if(addresslist.isEmpty()){
			return new ResponseEntity<List<Address>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Address>>(addresslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchaddressbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchAddressByName(@PathVariable("name") String str) {
		List addresslist = dbService.searchAddressByName(str);
		if(addresslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(addresslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxAddressID() {
		List addresslist = dbService.getMaxAddressID();
		if(addresslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(addresslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodaddress/", method = RequestMethod.POST)
	public ResponseEntity<List> getAddressByPeriod(@RequestBody Period pd) {		List addresslist = dbService.findAddressByPeriod(pd);
		if(addresslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(addresslist, HttpStatus.OK);
	}}
