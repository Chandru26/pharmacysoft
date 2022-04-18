package com.deemsoft.pharmacysoft.dao;

import java.util.List;
import com.deemsoft.pharmacysoft.model.User;

public interface UserDao {
	
	void save(User user);
	
	User findById(int id);
	
	User findBySSO(String sso);
	
	List<User> findAllUsers(); 
	
}

