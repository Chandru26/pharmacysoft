package com.deemsoft.pharmacysoft.service;

import com.deemsoft.pharmacysoft.model.EmailInfo;

public interface EmailService {
	
	public void emailSend(String to,String from,String sub, String body);    
	
	public void SendEmailWithAttachment(EmailInfo emailInfo);
}