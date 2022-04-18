package com.deemsoft.pharmacysoft.service;

import java.util.List;
import com.deemsoft.pharmacysoft.model.User;

public interface UserService {
    void save(User user);
	
	User findById(int id);
	
	User findBySso(String sso);
	
	List<User> findAllUsers();
	
	public String EncodePass(String pass);
	
}