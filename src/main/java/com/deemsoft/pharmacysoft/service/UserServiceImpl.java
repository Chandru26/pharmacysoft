package com.deemsoft.pharmacysoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Date;

import com.deemsoft.pharmacysoft.dao.UserDao;
import com.deemsoft.pharmacysoft.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
 
     
    public void save(User user){
		String st = "newpass";
		String pass = user.getPassword();
		if( pass.equals(st) ){
			Date date = new Date();
			user.setPassword(passwordEncoder.encode(pass+date.toString()));
		}
        dao.save(user);
    }
	public String EncodePass(String pass){
		return passwordEncoder.encode(pass);
	}
	public User findById(int id) {
		return dao.findById(id);
	}

	public User findBySso(String sso) {
		return dao.findBySSO(sso);
	}
	
	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

}
