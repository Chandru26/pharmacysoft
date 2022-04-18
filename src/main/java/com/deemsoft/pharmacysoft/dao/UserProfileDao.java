package com.deemsoft.pharmacysoft.dao;
 
import java.util.List;
 
import com.deemsoft.pharmacysoft.model.UserProfile;
 
public interface UserProfileDao {
 
    List<UserProfile> findAll();
     
    UserProfile findByType(String type);
     
    UserProfile findById(int id);
}