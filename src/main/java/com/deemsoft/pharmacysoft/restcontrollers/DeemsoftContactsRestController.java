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
import com.deemsoft.pharmacysoft.model.Contacts;

@RestController
@RequestMapping("/contactsrest")
public class DeemsoftContactsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftContactsRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/savecontacts/", method = RequestMethod.POST)
	public ResponseEntity<Contacts> saveContactsData(@RequestBody Contacts contacts) {
		dbService.saveContacts(contacts);
		contacts = dbService.findContactsByID(contacts.getid());
		if( contacts == null ){
			return new ResponseEntity<Contacts>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Contacts>(contacts, HttpStatus.OK);
	}

	@RequestMapping(value = "/getcontacts/{id}", method = RequestMethod.GET)
	public ResponseEntity<Contacts> getContactsData(@PathVariable("id") int id) {
		Contacts contacts = dbService.findContactsByID(id);
		if( contacts == null ){
			return new ResponseEntity<Contacts>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Contacts>(contacts, HttpStatus.OK);
	}
	@RequestMapping(value = "/listcontacts/", method = RequestMethod.GET)
	public ResponseEntity<List<Contacts>> listAllContacts() {
		List<Contacts> contacts = dbService.findAllContacts();
		if(contacts.isEmpty()){
			return new ResponseEntity<List<Contacts>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Contacts>>(contacts, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchcontacts/", method = RequestMethod.POST)
	public ResponseEntity<List<Contacts>> searchContacts(@RequestBody Contacts contacts) {
		List<Contacts> contactslist = dbService.searchContacts(contacts);
		if(contactslist.isEmpty()){
			return new ResponseEntity<List<Contacts>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Contacts>>(contactslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchcontactsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchContactsByName(@PathVariable("name") String str) {
		List contactslist = dbService.searchContactsByName(str);
		if(contactslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(contactslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxContactsID() {
		List contactslist = dbService.getMaxContactsID();
		if(contactslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(contactslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodcontacts/", method = RequestMethod.POST)
	public ResponseEntity<List> getContactsByPeriod(@RequestBody Period pd) {List contactslist = dbService.findContactsByPeriod(pd);
		if(contactslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(contactslist, HttpStatus.OK);
	}}
