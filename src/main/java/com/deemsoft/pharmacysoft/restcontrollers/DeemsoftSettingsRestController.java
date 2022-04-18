package com.deemsoft.pharmacysoft.controller;

import java.util.List;
import java.util.Date;
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
import com.deemsoft.pharmacysoft.model.Settings;
import com.deemsoft.pharmacysoft.model.User;
import com.deemsoft.pharmacysoft.service.UserService;
import com.deemsoft.pharmacysoft.service.EmailService;

@RestController
@RequestMapping("/settingsrest")
public class DeemsoftSettingsRestController {
	private static final Logger logger = Logger.getLogger(DeemsoftSettingsRestController.class);

	@Autowired
	DBService dbService;
	
	@Autowired
    EmailService emailService;
	
	@Autowired
    UserService userService;
	
	@RequestMapping(value = "/savesettings/", method = RequestMethod.POST)
	public ResponseEntity<Settings> saveSettingsData(@RequestBody Settings settings) {
		dbService.saveSettings(settings);
		settings = dbService.findSettingsByID(settings.getid());
		if( settings == null ){
			return new ResponseEntity<Settings>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Settings>(settings, HttpStatus.OK);
	}

	@RequestMapping(value = "/getsettings/{id}", method = RequestMethod.GET)
	public ResponseEntity<Settings> getSettingsData(@PathVariable("id") int id) {
		Settings settings = dbService.findSettingsByID(id);
		if( settings == null ){
			return new ResponseEntity<Settings>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Settings>(settings, HttpStatus.OK);
	}
	@RequestMapping(value = "/listsettings/", method = RequestMethod.GET)
	public ResponseEntity<List<Settings>> listAllSettings() {
		List<Settings> settings = dbService.findAllSettings();
		if(settings.isEmpty()){
			return new ResponseEntity<List<Settings>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Settings>>(settings, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchsettings/", method = RequestMethod.POST)
	public ResponseEntity<List<Settings>> searchSettings(@RequestBody Settings settings) {
		List<Settings> settingslist = dbService.searchSettings(settings);
		if(settingslist.isEmpty()){
			return new ResponseEntity<List<Settings>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Settings>>(settingslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/searchsettingsbyname/{name}", method = RequestMethod.GET)
	public ResponseEntity<List> searchSettingsByName(@PathVariable("name") String str) {
		List settingslist = dbService.searchSettingsByName(str);
		if(settingslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(settingslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/getmaxid/", method = RequestMethod.GET)
	public ResponseEntity<List> getMaxSettingsID() {
		List settingslist = dbService.getMaxSettingsID();
		if(settingslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List>(settingslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/periodsettings/", method = RequestMethod.POST)
	public ResponseEntity<List> getSettingsByPeriod(@RequestBody Period pd) {		
	List settingslist = dbService.findSettingsByPeriod(pd);
		if(settingslist.isEmpty()){
			return new ResponseEntity<List>(HttpStatus.NO_CONTENT);
			}
		return new ResponseEntity<List>(settingslist, HttpStatus.OK);
	}
	@RequestMapping(value = "/listusers/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listUsers() {
		List<User> userslist = userService.findAllUsers();
		if(userslist.isEmpty()){
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(userslist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/saveusers/", method = RequestMethod.POST)
	public ResponseEntity<User> saveUsersData(@RequestBody User user) {	
		userService.save(user);
		if( user == null ){
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/resetpass/{userid}", method = RequestMethod.GET)
	public ResponseEntity<User> resetPassData(@PathVariable("userid") String userid) {	
		User user = userService.findBySso(userid);
		Date date = new Date();
		user.setResetID(Long.toString(date.getTime()));
		user.setPassword(userService.EncodePass(date.toString()));
		userService.save(user);
		String body="<html><body>Please click <a href='http://localhost:8080/bookingsoft/resetpass/"+user.getResetID()+"'>here</a> to reset the password or copy this link to web browser and follow the instructions</body></html>";
		emailService.emailSend(user.getEmail(),"gangaswamy@gmail.com","Password Reset",body);
		if( user == null ){
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/setpass/{resetid}/{userid}/{password}", method = RequestMethod.GET)
	public ResponseEntity<User> resetPassData(@PathVariable("userid") String userid,@PathVariable("resetid") String resetid,@PathVariable("password") String password) {	
		User user = userService.findBySso(userid);
		if( user.getResetID().equals(resetid) ){
			user.setPassword(userService.EncodePass(password));
			Date date = new Date();
			user.setResetID(Long.toString(date.getTime()));
			userService.save(user);
		}else
		{
			user = null;
		}
		
		if( user == null ){
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	
	
	
	}
