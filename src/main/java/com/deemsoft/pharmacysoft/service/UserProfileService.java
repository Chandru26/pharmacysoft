package com.deemsoft.pharmacysoft.service;
 
import java.util.List;
 
import com.deemsoft.pharmacysoft.model.UserProfile;
 
public interface UserProfileService {
 
    List<UserProfile> findAll();
     
    UserProfile findByType(String type);
     
    UserProfile findById(int id);
}