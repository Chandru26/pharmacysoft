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
import com.deemsoft.pharmacysoft.model.Shipments;

@RestController
@RequestMapping("/shipmentsrest")
public class DeemsoftShipmentsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftShipmentsRestController.class);

	@Autowired
	DBService dbService;
	@RequestMapping(value = "/saveshipments/", method = RequestMethod.POST)
	public ResponseEntity<Shipments> saveShipmentsData(@RequestBody Shipments shipments) {
		dbService.saveShipments(shipments);
		shipments = dbService.findShipmentsByID(shipments.getid());
		if( shipments == null ){
			return new ResponseEntity<Shipments>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Shipments>(shipments, HttpStatus.OK);
	}

	@RequestMapping(value = "/getshipments/{id}", method = RequestMethod.GET)
	public ResponseEntity<Shipments> getShipmentsData(@PathVariable("id") int id) {
		Shipments shipments = dbService.findShipmentsByID(id);
		if( shipments == null ){
			return new ResponseEntity<Shipments>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Shipments>(shipments, HttpStatus.OK);
	}
	@RequestMapping(value = "/listshipments/", method = RequestMethod.GET)
	public ResponseEntity<List<Shipments>> listAllShipments() {
		List<Shipments> shipments = dbService.findAllShipments();
		if(shipments.isEmpty()){
			return new ResponseEntity<List<Shipments>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Shipments>>(shipments, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchshipments/", method = RequestMethod.POST)
	public ResponseEntity<List<Shipments>> searchShipments(@RequestBody Shipments shipments) {
		List<Shipments> shipmentslist = dbService.searchShipments(shipments);
		if(shipmentslist.isEmpty()){
			return new ResponseEntity<List<Shipments>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Shipments>>(shipmentslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchshipmentsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchShipmentsByName(@PathVariable("name") String str) {
		List shipmentslist = dbService.searchShipmentsByName(str);
		if(shipmentslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(shipmentslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxShipmentsID() {
		List shipmentslist = dbService.getMaxShipmentsID();
		if(shipmentslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(shipmentslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodshipments/", method = RequestMethod.POST)
	public ResponseEntity<List> getShipmentsByPeriod(@RequestBody Period pd) {		List shipmentslist = dbService.findShipmentsByPeriod(pd);
		if(shipmentslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(shipmentslist, HttpStatus.OK);
	}}
